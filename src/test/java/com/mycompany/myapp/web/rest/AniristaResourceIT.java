package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Anirista;
import com.mycompany.myapp.repository.AniristaRepository;
import com.mycompany.myapp.service.AniristaService;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link AniristaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AniristaResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_ENTRADA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_ENTRADA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/aniristas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AniristaRepository aniristaRepository;

    @Mock
    private AniristaRepository aniristaRepositoryMock;

    @Mock
    private AniristaService aniristaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAniristaMockMvc;

    private Anirista anirista;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Anirista createEntity(EntityManager em) {
        Anirista anirista = new Anirista().nombre(DEFAULT_NOMBRE).fechaEntrada(DEFAULT_FECHA_ENTRADA).descripcion(DEFAULT_DESCRIPCION);
        return anirista;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Anirista createUpdatedEntity(EntityManager em) {
        Anirista anirista = new Anirista().nombre(UPDATED_NOMBRE).fechaEntrada(UPDATED_FECHA_ENTRADA).descripcion(UPDATED_DESCRIPCION);
        return anirista;
    }

    @BeforeEach
    public void initTest() {
        anirista = createEntity(em);
    }

    @Test
    @Transactional
    void createAnirista() throws Exception {
        int databaseSizeBeforeCreate = aniristaRepository.findAll().size();
        // Create the Anirista
        restAniristaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(anirista)))
            .andExpect(status().isCreated());

        // Validate the Anirista in the database
        List<Anirista> aniristaList = aniristaRepository.findAll();
        assertThat(aniristaList).hasSize(databaseSizeBeforeCreate + 1);
        Anirista testAnirista = aniristaList.get(aniristaList.size() - 1);
        assertThat(testAnirista.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testAnirista.getFechaEntrada()).isEqualTo(DEFAULT_FECHA_ENTRADA);
        assertThat(testAnirista.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    void createAniristaWithExistingId() throws Exception {
        // Create the Anirista with an existing ID
        anirista.setId(1L);

        int databaseSizeBeforeCreate = aniristaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAniristaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(anirista)))
            .andExpect(status().isBadRequest());

        // Validate the Anirista in the database
        List<Anirista> aniristaList = aniristaRepository.findAll();
        assertThat(aniristaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = aniristaRepository.findAll().size();
        // set the field null
        anirista.setNombre(null);

        // Create the Anirista, which fails.

        restAniristaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(anirista)))
            .andExpect(status().isBadRequest());

        List<Anirista> aniristaList = aniristaRepository.findAll();
        assertThat(aniristaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFechaEntradaIsRequired() throws Exception {
        int databaseSizeBeforeTest = aniristaRepository.findAll().size();
        // set the field null
        anirista.setFechaEntrada(null);

        // Create the Anirista, which fails.

        restAniristaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(anirista)))
            .andExpect(status().isBadRequest());

        List<Anirista> aniristaList = aniristaRepository.findAll();
        assertThat(aniristaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAniristas() throws Exception {
        // Initialize the database
        aniristaRepository.saveAndFlush(anirista);

        // Get all the aniristaList
        restAniristaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(anirista.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].fechaEntrada").value(hasItem(DEFAULT_FECHA_ENTRADA.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAniristasWithEagerRelationshipsIsEnabled() throws Exception {
        when(aniristaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAniristaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(aniristaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAniristasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(aniristaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAniristaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(aniristaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getAnirista() throws Exception {
        // Initialize the database
        aniristaRepository.saveAndFlush(anirista);

        // Get the anirista
        restAniristaMockMvc
            .perform(get(ENTITY_API_URL_ID, anirista.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(anirista.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.fechaEntrada").value(DEFAULT_FECHA_ENTRADA.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAnirista() throws Exception {
        // Get the anirista
        restAniristaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAnirista() throws Exception {
        // Initialize the database
        aniristaRepository.saveAndFlush(anirista);

        int databaseSizeBeforeUpdate = aniristaRepository.findAll().size();

        // Update the anirista
        Anirista updatedAnirista = aniristaRepository.findById(anirista.getId()).get();
        // Disconnect from session so that the updates on updatedAnirista are not directly saved in db
        em.detach(updatedAnirista);
        updatedAnirista.nombre(UPDATED_NOMBRE).fechaEntrada(UPDATED_FECHA_ENTRADA).descripcion(UPDATED_DESCRIPCION);

        restAniristaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAnirista.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAnirista))
            )
            .andExpect(status().isOk());

        // Validate the Anirista in the database
        List<Anirista> aniristaList = aniristaRepository.findAll();
        assertThat(aniristaList).hasSize(databaseSizeBeforeUpdate);
        Anirista testAnirista = aniristaList.get(aniristaList.size() - 1);
        assertThat(testAnirista.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testAnirista.getFechaEntrada()).isEqualTo(UPDATED_FECHA_ENTRADA);
        assertThat(testAnirista.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void putNonExistingAnirista() throws Exception {
        int databaseSizeBeforeUpdate = aniristaRepository.findAll().size();
        anirista.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAniristaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, anirista.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(anirista))
            )
            .andExpect(status().isBadRequest());

        // Validate the Anirista in the database
        List<Anirista> aniristaList = aniristaRepository.findAll();
        assertThat(aniristaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAnirista() throws Exception {
        int databaseSizeBeforeUpdate = aniristaRepository.findAll().size();
        anirista.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAniristaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(anirista))
            )
            .andExpect(status().isBadRequest());

        // Validate the Anirista in the database
        List<Anirista> aniristaList = aniristaRepository.findAll();
        assertThat(aniristaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAnirista() throws Exception {
        int databaseSizeBeforeUpdate = aniristaRepository.findAll().size();
        anirista.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAniristaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(anirista)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Anirista in the database
        List<Anirista> aniristaList = aniristaRepository.findAll();
        assertThat(aniristaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAniristaWithPatch() throws Exception {
        // Initialize the database
        aniristaRepository.saveAndFlush(anirista);

        int databaseSizeBeforeUpdate = aniristaRepository.findAll().size();

        // Update the anirista using partial update
        Anirista partialUpdatedAnirista = new Anirista();
        partialUpdatedAnirista.setId(anirista.getId());

        partialUpdatedAnirista.fechaEntrada(UPDATED_FECHA_ENTRADA);

        restAniristaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnirista.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnirista))
            )
            .andExpect(status().isOk());

        // Validate the Anirista in the database
        List<Anirista> aniristaList = aniristaRepository.findAll();
        assertThat(aniristaList).hasSize(databaseSizeBeforeUpdate);
        Anirista testAnirista = aniristaList.get(aniristaList.size() - 1);
        assertThat(testAnirista.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testAnirista.getFechaEntrada()).isEqualTo(UPDATED_FECHA_ENTRADA);
        assertThat(testAnirista.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    void fullUpdateAniristaWithPatch() throws Exception {
        // Initialize the database
        aniristaRepository.saveAndFlush(anirista);

        int databaseSizeBeforeUpdate = aniristaRepository.findAll().size();

        // Update the anirista using partial update
        Anirista partialUpdatedAnirista = new Anirista();
        partialUpdatedAnirista.setId(anirista.getId());

        partialUpdatedAnirista.nombre(UPDATED_NOMBRE).fechaEntrada(UPDATED_FECHA_ENTRADA).descripcion(UPDATED_DESCRIPCION);

        restAniristaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnirista.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnirista))
            )
            .andExpect(status().isOk());

        // Validate the Anirista in the database
        List<Anirista> aniristaList = aniristaRepository.findAll();
        assertThat(aniristaList).hasSize(databaseSizeBeforeUpdate);
        Anirista testAnirista = aniristaList.get(aniristaList.size() - 1);
        assertThat(testAnirista.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testAnirista.getFechaEntrada()).isEqualTo(UPDATED_FECHA_ENTRADA);
        assertThat(testAnirista.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void patchNonExistingAnirista() throws Exception {
        int databaseSizeBeforeUpdate = aniristaRepository.findAll().size();
        anirista.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAniristaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, anirista.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(anirista))
            )
            .andExpect(status().isBadRequest());

        // Validate the Anirista in the database
        List<Anirista> aniristaList = aniristaRepository.findAll();
        assertThat(aniristaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAnirista() throws Exception {
        int databaseSizeBeforeUpdate = aniristaRepository.findAll().size();
        anirista.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAniristaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(anirista))
            )
            .andExpect(status().isBadRequest());

        // Validate the Anirista in the database
        List<Anirista> aniristaList = aniristaRepository.findAll();
        assertThat(aniristaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAnirista() throws Exception {
        int databaseSizeBeforeUpdate = aniristaRepository.findAll().size();
        anirista.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAniristaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(anirista)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Anirista in the database
        List<Anirista> aniristaList = aniristaRepository.findAll();
        assertThat(aniristaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAnirista() throws Exception {
        // Initialize the database
        aniristaRepository.saveAndFlush(anirista);

        int databaseSizeBeforeDelete = aniristaRepository.findAll().size();

        // Delete the anirista
        restAniristaMockMvc
            .perform(delete(ENTITY_API_URL_ID, anirista.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Anirista> aniristaList = aniristaRepository.findAll();
        assertThat(aniristaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
