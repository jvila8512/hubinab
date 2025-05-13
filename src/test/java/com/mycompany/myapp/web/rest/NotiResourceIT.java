package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Noti;
import com.mycompany.myapp.repository.NotiRepository;
import com.mycompany.myapp.service.NotiService;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link NotiResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class NotiResourceIT {

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_VISTO = false;
    private static final Boolean UPDATED_VISTO = true;

    private static final Instant DEFAULT_FECHA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/notis";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NotiRepository notiRepository;

    @Mock
    private NotiRepository notiRepositoryMock;

    @Mock
    private NotiService notiServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNotiMockMvc;

    private Noti noti;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Noti createEntity(EntityManager em) {
        Noti noti = new Noti().descripcion(DEFAULT_DESCRIPCION).visto(DEFAULT_VISTO).fecha(DEFAULT_FECHA);
        return noti;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Noti createUpdatedEntity(EntityManager em) {
        Noti noti = new Noti().descripcion(UPDATED_DESCRIPCION).visto(UPDATED_VISTO).fecha(UPDATED_FECHA);
        return noti;
    }

    @BeforeEach
    public void initTest() {
        noti = createEntity(em);
    }

    @Test
    @Transactional
    void createNoti() throws Exception {
        int databaseSizeBeforeCreate = notiRepository.findAll().size();
        // Create the Noti
        restNotiMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(noti)))
            .andExpect(status().isCreated());

        // Validate the Noti in the database
        List<Noti> notiList = notiRepository.findAll();
        assertThat(notiList).hasSize(databaseSizeBeforeCreate + 1);
        Noti testNoti = notiList.get(notiList.size() - 1);
        assertThat(testNoti.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testNoti.getVisto()).isEqualTo(DEFAULT_VISTO);
        assertThat(testNoti.getFecha()).isEqualTo(DEFAULT_FECHA);
    }

    @Test
    @Transactional
    void createNotiWithExistingId() throws Exception {
        // Create the Noti with an existing ID
        noti.setId(1L);

        int databaseSizeBeforeCreate = notiRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNotiMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(noti)))
            .andExpect(status().isBadRequest());

        // Validate the Noti in the database
        List<Noti> notiList = notiRepository.findAll();
        assertThat(notiList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDescripcionIsRequired() throws Exception {
        int databaseSizeBeforeTest = notiRepository.findAll().size();
        // set the field null
        noti.setDescripcion(null);

        // Create the Noti, which fails.

        restNotiMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(noti)))
            .andExpect(status().isBadRequest());

        List<Noti> notiList = notiRepository.findAll();
        assertThat(notiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVistoIsRequired() throws Exception {
        int databaseSizeBeforeTest = notiRepository.findAll().size();
        // set the field null
        noti.setVisto(null);

        // Create the Noti, which fails.

        restNotiMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(noti)))
            .andExpect(status().isBadRequest());

        List<Noti> notiList = notiRepository.findAll();
        assertThat(notiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = notiRepository.findAll().size();
        // set the field null
        noti.setFecha(null);

        // Create the Noti, which fails.

        restNotiMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(noti)))
            .andExpect(status().isBadRequest());

        List<Noti> notiList = notiRepository.findAll();
        assertThat(notiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllNotis() throws Exception {
        // Initialize the database
        notiRepository.saveAndFlush(noti);

        // Get all the notiList
        restNotiMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(noti.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].visto").value(hasItem(DEFAULT_VISTO.booleanValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllNotisWithEagerRelationshipsIsEnabled() throws Exception {
        when(notiServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restNotiMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(notiServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllNotisWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(notiServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restNotiMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(notiServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getNoti() throws Exception {
        // Initialize the database
        notiRepository.saveAndFlush(noti);

        // Get the noti
        restNotiMockMvc
            .perform(get(ENTITY_API_URL_ID, noti.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(noti.getId().intValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.visto").value(DEFAULT_VISTO.booleanValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()));
    }

    @Test
    @Transactional
    void getNonExistingNoti() throws Exception {
        // Get the noti
        restNotiMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewNoti() throws Exception {
        // Initialize the database
        notiRepository.saveAndFlush(noti);

        int databaseSizeBeforeUpdate = notiRepository.findAll().size();

        // Update the noti
        Noti updatedNoti = notiRepository.findById(noti.getId()).get();
        // Disconnect from session so that the updates on updatedNoti are not directly saved in db
        em.detach(updatedNoti);
        updatedNoti.descripcion(UPDATED_DESCRIPCION).visto(UPDATED_VISTO).fecha(UPDATED_FECHA);

        restNotiMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNoti.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedNoti))
            )
            .andExpect(status().isOk());

        // Validate the Noti in the database
        List<Noti> notiList = notiRepository.findAll();
        assertThat(notiList).hasSize(databaseSizeBeforeUpdate);
        Noti testNoti = notiList.get(notiList.size() - 1);
        assertThat(testNoti.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testNoti.getVisto()).isEqualTo(UPDATED_VISTO);
        assertThat(testNoti.getFecha()).isEqualTo(UPDATED_FECHA);
    }

    @Test
    @Transactional
    void putNonExistingNoti() throws Exception {
        int databaseSizeBeforeUpdate = notiRepository.findAll().size();
        noti.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNotiMockMvc
            .perform(
                put(ENTITY_API_URL_ID, noti.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(noti))
            )
            .andExpect(status().isBadRequest());

        // Validate the Noti in the database
        List<Noti> notiList = notiRepository.findAll();
        assertThat(notiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNoti() throws Exception {
        int databaseSizeBeforeUpdate = notiRepository.findAll().size();
        noti.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotiMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(noti))
            )
            .andExpect(status().isBadRequest());

        // Validate the Noti in the database
        List<Noti> notiList = notiRepository.findAll();
        assertThat(notiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNoti() throws Exception {
        int databaseSizeBeforeUpdate = notiRepository.findAll().size();
        noti.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotiMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(noti)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Noti in the database
        List<Noti> notiList = notiRepository.findAll();
        assertThat(notiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNotiWithPatch() throws Exception {
        // Initialize the database
        notiRepository.saveAndFlush(noti);

        int databaseSizeBeforeUpdate = notiRepository.findAll().size();

        // Update the noti using partial update
        Noti partialUpdatedNoti = new Noti();
        partialUpdatedNoti.setId(noti.getId());

        partialUpdatedNoti.descripcion(UPDATED_DESCRIPCION);

        restNotiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNoti.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNoti))
            )
            .andExpect(status().isOk());

        // Validate the Noti in the database
        List<Noti> notiList = notiRepository.findAll();
        assertThat(notiList).hasSize(databaseSizeBeforeUpdate);
        Noti testNoti = notiList.get(notiList.size() - 1);
        assertThat(testNoti.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testNoti.getVisto()).isEqualTo(DEFAULT_VISTO);
        assertThat(testNoti.getFecha()).isEqualTo(DEFAULT_FECHA);
    }

    @Test
    @Transactional
    void fullUpdateNotiWithPatch() throws Exception {
        // Initialize the database
        notiRepository.saveAndFlush(noti);

        int databaseSizeBeforeUpdate = notiRepository.findAll().size();

        // Update the noti using partial update
        Noti partialUpdatedNoti = new Noti();
        partialUpdatedNoti.setId(noti.getId());

        partialUpdatedNoti.descripcion(UPDATED_DESCRIPCION).visto(UPDATED_VISTO).fecha(UPDATED_FECHA);

        restNotiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNoti.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNoti))
            )
            .andExpect(status().isOk());

        // Validate the Noti in the database
        List<Noti> notiList = notiRepository.findAll();
        assertThat(notiList).hasSize(databaseSizeBeforeUpdate);
        Noti testNoti = notiList.get(notiList.size() - 1);
        assertThat(testNoti.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testNoti.getVisto()).isEqualTo(UPDATED_VISTO);
        assertThat(testNoti.getFecha()).isEqualTo(UPDATED_FECHA);
    }

    @Test
    @Transactional
    void patchNonExistingNoti() throws Exception {
        int databaseSizeBeforeUpdate = notiRepository.findAll().size();
        noti.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNotiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, noti.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(noti))
            )
            .andExpect(status().isBadRequest());

        // Validate the Noti in the database
        List<Noti> notiList = notiRepository.findAll();
        assertThat(notiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNoti() throws Exception {
        int databaseSizeBeforeUpdate = notiRepository.findAll().size();
        noti.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(noti))
            )
            .andExpect(status().isBadRequest());

        // Validate the Noti in the database
        List<Noti> notiList = notiRepository.findAll();
        assertThat(notiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNoti() throws Exception {
        int databaseSizeBeforeUpdate = notiRepository.findAll().size();
        noti.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotiMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(noti)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Noti in the database
        List<Noti> notiList = notiRepository.findAll();
        assertThat(notiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNoti() throws Exception {
        // Initialize the database
        notiRepository.saveAndFlush(noti);

        int databaseSizeBeforeDelete = notiRepository.findAll().size();

        // Delete the noti
        restNotiMockMvc
            .perform(delete(ENTITY_API_URL_ID, noti.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Noti> notiList = notiRepository.findAll();
        assertThat(notiList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
