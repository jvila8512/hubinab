package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.LikeIdea;
import com.mycompany.myapp.repository.LikeIdeaRepository;
import com.mycompany.myapp.service.LikeIdeaService;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link LikeIdeaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class LikeIdeaResourceIT {

    private static final Integer DEFAULT_LIKE = 1;
    private static final Integer UPDATED_LIKE = 2;

    private static final LocalDate DEFAULT_FECHA_INSCRIPCION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_INSCRIPCION = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/like-ideas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LikeIdeaRepository likeIdeaRepository;

    @Mock
    private LikeIdeaRepository likeIdeaRepositoryMock;

    @Mock
    private LikeIdeaService likeIdeaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLikeIdeaMockMvc;

    private LikeIdea likeIdea;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LikeIdea createEntity(EntityManager em) {
        LikeIdea likeIdea = new LikeIdea().like(DEFAULT_LIKE).fechaInscripcion(DEFAULT_FECHA_INSCRIPCION);
        return likeIdea;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LikeIdea createUpdatedEntity(EntityManager em) {
        LikeIdea likeIdea = new LikeIdea().like(UPDATED_LIKE).fechaInscripcion(UPDATED_FECHA_INSCRIPCION);
        return likeIdea;
    }

    @BeforeEach
    public void initTest() {
        likeIdea = createEntity(em);
    }

    @Test
    @Transactional
    void createLikeIdea() throws Exception {
        int databaseSizeBeforeCreate = likeIdeaRepository.findAll().size();
        // Create the LikeIdea
        restLikeIdeaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(likeIdea)))
            .andExpect(status().isCreated());

        // Validate the LikeIdea in the database
        List<LikeIdea> likeIdeaList = likeIdeaRepository.findAll();
        assertThat(likeIdeaList).hasSize(databaseSizeBeforeCreate + 1);
        LikeIdea testLikeIdea = likeIdeaList.get(likeIdeaList.size() - 1);
        assertThat(testLikeIdea.getLike()).isEqualTo(DEFAULT_LIKE);
        assertThat(testLikeIdea.getFechaInscripcion()).isEqualTo(DEFAULT_FECHA_INSCRIPCION);
    }

    @Test
    @Transactional
    void createLikeIdeaWithExistingId() throws Exception {
        // Create the LikeIdea with an existing ID
        likeIdea.setId(1L);

        int databaseSizeBeforeCreate = likeIdeaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLikeIdeaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(likeIdea)))
            .andExpect(status().isBadRequest());

        // Validate the LikeIdea in the database
        List<LikeIdea> likeIdeaList = likeIdeaRepository.findAll();
        assertThat(likeIdeaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLikeIsRequired() throws Exception {
        int databaseSizeBeforeTest = likeIdeaRepository.findAll().size();
        // set the field null
        likeIdea.setLike(null);

        // Create the LikeIdea, which fails.

        restLikeIdeaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(likeIdea)))
            .andExpect(status().isBadRequest());

        List<LikeIdea> likeIdeaList = likeIdeaRepository.findAll();
        assertThat(likeIdeaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFechaInscripcionIsRequired() throws Exception {
        int databaseSizeBeforeTest = likeIdeaRepository.findAll().size();
        // set the field null
        likeIdea.setFechaInscripcion(null);

        // Create the LikeIdea, which fails.

        restLikeIdeaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(likeIdea)))
            .andExpect(status().isBadRequest());

        List<LikeIdea> likeIdeaList = likeIdeaRepository.findAll();
        assertThat(likeIdeaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLikeIdeas() throws Exception {
        // Initialize the database
        likeIdeaRepository.saveAndFlush(likeIdea);

        // Get all the likeIdeaList
        restLikeIdeaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(likeIdea.getId().intValue())))
            .andExpect(jsonPath("$.[*].like").value(hasItem(DEFAULT_LIKE)))
            .andExpect(jsonPath("$.[*].fechaInscripcion").value(hasItem(DEFAULT_FECHA_INSCRIPCION.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllLikeIdeasWithEagerRelationshipsIsEnabled() throws Exception {
        when(likeIdeaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restLikeIdeaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(likeIdeaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllLikeIdeasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(likeIdeaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restLikeIdeaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(likeIdeaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getLikeIdea() throws Exception {
        // Initialize the database
        likeIdeaRepository.saveAndFlush(likeIdea);

        // Get the likeIdea
        restLikeIdeaMockMvc
            .perform(get(ENTITY_API_URL_ID, likeIdea.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(likeIdea.getId().intValue()))
            .andExpect(jsonPath("$.like").value(DEFAULT_LIKE))
            .andExpect(jsonPath("$.fechaInscripcion").value(DEFAULT_FECHA_INSCRIPCION.toString()));
    }

    @Test
    @Transactional
    void getNonExistingLikeIdea() throws Exception {
        // Get the likeIdea
        restLikeIdeaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewLikeIdea() throws Exception {
        // Initialize the database
        likeIdeaRepository.saveAndFlush(likeIdea);

        int databaseSizeBeforeUpdate = likeIdeaRepository.findAll().size();

        // Update the likeIdea
        LikeIdea updatedLikeIdea = likeIdeaRepository.findById(likeIdea.getId()).get();
        // Disconnect from session so that the updates on updatedLikeIdea are not directly saved in db
        em.detach(updatedLikeIdea);
        updatedLikeIdea.like(UPDATED_LIKE).fechaInscripcion(UPDATED_FECHA_INSCRIPCION);

        restLikeIdeaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLikeIdea.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLikeIdea))
            )
            .andExpect(status().isOk());

        // Validate the LikeIdea in the database
        List<LikeIdea> likeIdeaList = likeIdeaRepository.findAll();
        assertThat(likeIdeaList).hasSize(databaseSizeBeforeUpdate);
        LikeIdea testLikeIdea = likeIdeaList.get(likeIdeaList.size() - 1);
        assertThat(testLikeIdea.getLike()).isEqualTo(UPDATED_LIKE);
        assertThat(testLikeIdea.getFechaInscripcion()).isEqualTo(UPDATED_FECHA_INSCRIPCION);
    }

    @Test
    @Transactional
    void putNonExistingLikeIdea() throws Exception {
        int databaseSizeBeforeUpdate = likeIdeaRepository.findAll().size();
        likeIdea.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLikeIdeaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, likeIdea.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(likeIdea))
            )
            .andExpect(status().isBadRequest());

        // Validate the LikeIdea in the database
        List<LikeIdea> likeIdeaList = likeIdeaRepository.findAll();
        assertThat(likeIdeaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLikeIdea() throws Exception {
        int databaseSizeBeforeUpdate = likeIdeaRepository.findAll().size();
        likeIdea.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLikeIdeaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(likeIdea))
            )
            .andExpect(status().isBadRequest());

        // Validate the LikeIdea in the database
        List<LikeIdea> likeIdeaList = likeIdeaRepository.findAll();
        assertThat(likeIdeaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLikeIdea() throws Exception {
        int databaseSizeBeforeUpdate = likeIdeaRepository.findAll().size();
        likeIdea.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLikeIdeaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(likeIdea)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LikeIdea in the database
        List<LikeIdea> likeIdeaList = likeIdeaRepository.findAll();
        assertThat(likeIdeaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLikeIdeaWithPatch() throws Exception {
        // Initialize the database
        likeIdeaRepository.saveAndFlush(likeIdea);

        int databaseSizeBeforeUpdate = likeIdeaRepository.findAll().size();

        // Update the likeIdea using partial update
        LikeIdea partialUpdatedLikeIdea = new LikeIdea();
        partialUpdatedLikeIdea.setId(likeIdea.getId());

        partialUpdatedLikeIdea.like(UPDATED_LIKE);

        restLikeIdeaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLikeIdea.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLikeIdea))
            )
            .andExpect(status().isOk());

        // Validate the LikeIdea in the database
        List<LikeIdea> likeIdeaList = likeIdeaRepository.findAll();
        assertThat(likeIdeaList).hasSize(databaseSizeBeforeUpdate);
        LikeIdea testLikeIdea = likeIdeaList.get(likeIdeaList.size() - 1);
        assertThat(testLikeIdea.getLike()).isEqualTo(UPDATED_LIKE);
        assertThat(testLikeIdea.getFechaInscripcion()).isEqualTo(DEFAULT_FECHA_INSCRIPCION);
    }

    @Test
    @Transactional
    void fullUpdateLikeIdeaWithPatch() throws Exception {
        // Initialize the database
        likeIdeaRepository.saveAndFlush(likeIdea);

        int databaseSizeBeforeUpdate = likeIdeaRepository.findAll().size();

        // Update the likeIdea using partial update
        LikeIdea partialUpdatedLikeIdea = new LikeIdea();
        partialUpdatedLikeIdea.setId(likeIdea.getId());

        partialUpdatedLikeIdea.like(UPDATED_LIKE).fechaInscripcion(UPDATED_FECHA_INSCRIPCION);

        restLikeIdeaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLikeIdea.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLikeIdea))
            )
            .andExpect(status().isOk());

        // Validate the LikeIdea in the database
        List<LikeIdea> likeIdeaList = likeIdeaRepository.findAll();
        assertThat(likeIdeaList).hasSize(databaseSizeBeforeUpdate);
        LikeIdea testLikeIdea = likeIdeaList.get(likeIdeaList.size() - 1);
        assertThat(testLikeIdea.getLike()).isEqualTo(UPDATED_LIKE);
        assertThat(testLikeIdea.getFechaInscripcion()).isEqualTo(UPDATED_FECHA_INSCRIPCION);
    }

    @Test
    @Transactional
    void patchNonExistingLikeIdea() throws Exception {
        int databaseSizeBeforeUpdate = likeIdeaRepository.findAll().size();
        likeIdea.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLikeIdeaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, likeIdea.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(likeIdea))
            )
            .andExpect(status().isBadRequest());

        // Validate the LikeIdea in the database
        List<LikeIdea> likeIdeaList = likeIdeaRepository.findAll();
        assertThat(likeIdeaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLikeIdea() throws Exception {
        int databaseSizeBeforeUpdate = likeIdeaRepository.findAll().size();
        likeIdea.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLikeIdeaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(likeIdea))
            )
            .andExpect(status().isBadRequest());

        // Validate the LikeIdea in the database
        List<LikeIdea> likeIdeaList = likeIdeaRepository.findAll();
        assertThat(likeIdeaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLikeIdea() throws Exception {
        int databaseSizeBeforeUpdate = likeIdeaRepository.findAll().size();
        likeIdea.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLikeIdeaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(likeIdea)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LikeIdea in the database
        List<LikeIdea> likeIdeaList = likeIdeaRepository.findAll();
        assertThat(likeIdeaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLikeIdea() throws Exception {
        // Initialize the database
        likeIdeaRepository.saveAndFlush(likeIdea);

        int databaseSizeBeforeDelete = likeIdeaRepository.findAll().size();

        // Delete the likeIdea
        restLikeIdeaMockMvc
            .perform(delete(ENTITY_API_URL_ID, likeIdea.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LikeIdea> likeIdeaList = likeIdeaRepository.findAll();
        assertThat(likeIdeaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
