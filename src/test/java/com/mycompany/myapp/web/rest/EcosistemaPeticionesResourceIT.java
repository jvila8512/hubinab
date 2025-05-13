package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.EcosistemaPeticiones;
import com.mycompany.myapp.repository.EcosistemaPeticionesRepository;
import com.mycompany.myapp.service.EcosistemaPeticionesService;
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
 * Integration tests for the {@link EcosistemaPeticionesResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class EcosistemaPeticionesResourceIT {

    private static final String DEFAULT_MOTIVO = "AAAAAAAAAA";
    private static final String UPDATED_MOTIVO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_PETICION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_PETICION = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_APROBADA = false;
    private static final Boolean UPDATED_APROBADA = true;

    private static final String ENTITY_API_URL = "/api/ecosistema-peticiones";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EcosistemaPeticionesRepository ecosistemaPeticionesRepository;

    @Mock
    private EcosistemaPeticionesRepository ecosistemaPeticionesRepositoryMock;

    @Mock
    private EcosistemaPeticionesService ecosistemaPeticionesServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEcosistemaPeticionesMockMvc;

    private EcosistemaPeticiones ecosistemaPeticiones;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EcosistemaPeticiones createEntity(EntityManager em) {
        EcosistemaPeticiones ecosistemaPeticiones = new EcosistemaPeticiones()
            .motivo(DEFAULT_MOTIVO)
            .fechaPeticion(DEFAULT_FECHA_PETICION)
            .aprobada(DEFAULT_APROBADA);
        return ecosistemaPeticiones;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EcosistemaPeticiones createUpdatedEntity(EntityManager em) {
        EcosistemaPeticiones ecosistemaPeticiones = new EcosistemaPeticiones()
            .motivo(UPDATED_MOTIVO)
            .fechaPeticion(UPDATED_FECHA_PETICION)
            .aprobada(UPDATED_APROBADA);
        return ecosistemaPeticiones;
    }

    @BeforeEach
    public void initTest() {
        ecosistemaPeticiones = createEntity(em);
    }

    @Test
    @Transactional
    void createEcosistemaPeticiones() throws Exception {
        int databaseSizeBeforeCreate = ecosistemaPeticionesRepository.findAll().size();
        // Create the EcosistemaPeticiones
        restEcosistemaPeticionesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ecosistemaPeticiones))
            )
            .andExpect(status().isCreated());

        // Validate the EcosistemaPeticiones in the database
        List<EcosistemaPeticiones> ecosistemaPeticionesList = ecosistemaPeticionesRepository.findAll();
        assertThat(ecosistemaPeticionesList).hasSize(databaseSizeBeforeCreate + 1);
        EcosistemaPeticiones testEcosistemaPeticiones = ecosistemaPeticionesList.get(ecosistemaPeticionesList.size() - 1);
        assertThat(testEcosistemaPeticiones.getMotivo()).isEqualTo(DEFAULT_MOTIVO);
        assertThat(testEcosistemaPeticiones.getFechaPeticion()).isEqualTo(DEFAULT_FECHA_PETICION);
        assertThat(testEcosistemaPeticiones.getAprobada()).isEqualTo(DEFAULT_APROBADA);
    }

    @Test
    @Transactional
    void createEcosistemaPeticionesWithExistingId() throws Exception {
        // Create the EcosistemaPeticiones with an existing ID
        ecosistemaPeticiones.setId(1L);

        int databaseSizeBeforeCreate = ecosistemaPeticionesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEcosistemaPeticionesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ecosistemaPeticiones))
            )
            .andExpect(status().isBadRequest());

        // Validate the EcosistemaPeticiones in the database
        List<EcosistemaPeticiones> ecosistemaPeticionesList = ecosistemaPeticionesRepository.findAll();
        assertThat(ecosistemaPeticionesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkMotivoIsRequired() throws Exception {
        int databaseSizeBeforeTest = ecosistemaPeticionesRepository.findAll().size();
        // set the field null
        ecosistemaPeticiones.setMotivo(null);

        // Create the EcosistemaPeticiones, which fails.

        restEcosistemaPeticionesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ecosistemaPeticiones))
            )
            .andExpect(status().isBadRequest());

        List<EcosistemaPeticiones> ecosistemaPeticionesList = ecosistemaPeticionesRepository.findAll();
        assertThat(ecosistemaPeticionesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFechaPeticionIsRequired() throws Exception {
        int databaseSizeBeforeTest = ecosistemaPeticionesRepository.findAll().size();
        // set the field null
        ecosistemaPeticiones.setFechaPeticion(null);

        // Create the EcosistemaPeticiones, which fails.

        restEcosistemaPeticionesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ecosistemaPeticiones))
            )
            .andExpect(status().isBadRequest());

        List<EcosistemaPeticiones> ecosistemaPeticionesList = ecosistemaPeticionesRepository.findAll();
        assertThat(ecosistemaPeticionesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEcosistemaPeticiones() throws Exception {
        // Initialize the database
        ecosistemaPeticionesRepository.saveAndFlush(ecosistemaPeticiones);

        // Get all the ecosistemaPeticionesList
        restEcosistemaPeticionesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ecosistemaPeticiones.getId().intValue())))
            .andExpect(jsonPath("$.[*].motivo").value(hasItem(DEFAULT_MOTIVO)))
            .andExpect(jsonPath("$.[*].fechaPeticion").value(hasItem(DEFAULT_FECHA_PETICION.toString())))
            .andExpect(jsonPath("$.[*].aprobada").value(hasItem(DEFAULT_APROBADA.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEcosistemaPeticionesWithEagerRelationshipsIsEnabled() throws Exception {
        when(ecosistemaPeticionesServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEcosistemaPeticionesMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(ecosistemaPeticionesServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEcosistemaPeticionesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(ecosistemaPeticionesServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEcosistemaPeticionesMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(ecosistemaPeticionesServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getEcosistemaPeticiones() throws Exception {
        // Initialize the database
        ecosistemaPeticionesRepository.saveAndFlush(ecosistemaPeticiones);

        // Get the ecosistemaPeticiones
        restEcosistemaPeticionesMockMvc
            .perform(get(ENTITY_API_URL_ID, ecosistemaPeticiones.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ecosistemaPeticiones.getId().intValue()))
            .andExpect(jsonPath("$.motivo").value(DEFAULT_MOTIVO))
            .andExpect(jsonPath("$.fechaPeticion").value(DEFAULT_FECHA_PETICION.toString()))
            .andExpect(jsonPath("$.aprobada").value(DEFAULT_APROBADA.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingEcosistemaPeticiones() throws Exception {
        // Get the ecosistemaPeticiones
        restEcosistemaPeticionesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEcosistemaPeticiones() throws Exception {
        // Initialize the database
        ecosistemaPeticionesRepository.saveAndFlush(ecosistemaPeticiones);

        int databaseSizeBeforeUpdate = ecosistemaPeticionesRepository.findAll().size();

        // Update the ecosistemaPeticiones
        EcosistemaPeticiones updatedEcosistemaPeticiones = ecosistemaPeticionesRepository.findById(ecosistemaPeticiones.getId()).get();
        // Disconnect from session so that the updates on updatedEcosistemaPeticiones are not directly saved in db
        em.detach(updatedEcosistemaPeticiones);
        updatedEcosistemaPeticiones.motivo(UPDATED_MOTIVO).fechaPeticion(UPDATED_FECHA_PETICION).aprobada(UPDATED_APROBADA);

        restEcosistemaPeticionesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEcosistemaPeticiones.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEcosistemaPeticiones))
            )
            .andExpect(status().isOk());

        // Validate the EcosistemaPeticiones in the database
        List<EcosistemaPeticiones> ecosistemaPeticionesList = ecosistemaPeticionesRepository.findAll();
        assertThat(ecosistemaPeticionesList).hasSize(databaseSizeBeforeUpdate);
        EcosistemaPeticiones testEcosistemaPeticiones = ecosistemaPeticionesList.get(ecosistemaPeticionesList.size() - 1);
        assertThat(testEcosistemaPeticiones.getMotivo()).isEqualTo(UPDATED_MOTIVO);
        assertThat(testEcosistemaPeticiones.getFechaPeticion()).isEqualTo(UPDATED_FECHA_PETICION);
        assertThat(testEcosistemaPeticiones.getAprobada()).isEqualTo(UPDATED_APROBADA);
    }

    @Test
    @Transactional
    void putNonExistingEcosistemaPeticiones() throws Exception {
        int databaseSizeBeforeUpdate = ecosistemaPeticionesRepository.findAll().size();
        ecosistemaPeticiones.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEcosistemaPeticionesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ecosistemaPeticiones.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ecosistemaPeticiones))
            )
            .andExpect(status().isBadRequest());

        // Validate the EcosistemaPeticiones in the database
        List<EcosistemaPeticiones> ecosistemaPeticionesList = ecosistemaPeticionesRepository.findAll();
        assertThat(ecosistemaPeticionesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEcosistemaPeticiones() throws Exception {
        int databaseSizeBeforeUpdate = ecosistemaPeticionesRepository.findAll().size();
        ecosistemaPeticiones.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEcosistemaPeticionesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ecosistemaPeticiones))
            )
            .andExpect(status().isBadRequest());

        // Validate the EcosistemaPeticiones in the database
        List<EcosistemaPeticiones> ecosistemaPeticionesList = ecosistemaPeticionesRepository.findAll();
        assertThat(ecosistemaPeticionesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEcosistemaPeticiones() throws Exception {
        int databaseSizeBeforeUpdate = ecosistemaPeticionesRepository.findAll().size();
        ecosistemaPeticiones.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEcosistemaPeticionesMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ecosistemaPeticiones))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EcosistemaPeticiones in the database
        List<EcosistemaPeticiones> ecosistemaPeticionesList = ecosistemaPeticionesRepository.findAll();
        assertThat(ecosistemaPeticionesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEcosistemaPeticionesWithPatch() throws Exception {
        // Initialize the database
        ecosistemaPeticionesRepository.saveAndFlush(ecosistemaPeticiones);

        int databaseSizeBeforeUpdate = ecosistemaPeticionesRepository.findAll().size();

        // Update the ecosistemaPeticiones using partial update
        EcosistemaPeticiones partialUpdatedEcosistemaPeticiones = new EcosistemaPeticiones();
        partialUpdatedEcosistemaPeticiones.setId(ecosistemaPeticiones.getId());

        partialUpdatedEcosistemaPeticiones.motivo(UPDATED_MOTIVO).fechaPeticion(UPDATED_FECHA_PETICION);

        restEcosistemaPeticionesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEcosistemaPeticiones.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEcosistemaPeticiones))
            )
            .andExpect(status().isOk());

        // Validate the EcosistemaPeticiones in the database
        List<EcosistemaPeticiones> ecosistemaPeticionesList = ecosistemaPeticionesRepository.findAll();
        assertThat(ecosistemaPeticionesList).hasSize(databaseSizeBeforeUpdate);
        EcosistemaPeticiones testEcosistemaPeticiones = ecosistemaPeticionesList.get(ecosistemaPeticionesList.size() - 1);
        assertThat(testEcosistemaPeticiones.getMotivo()).isEqualTo(UPDATED_MOTIVO);
        assertThat(testEcosistemaPeticiones.getFechaPeticion()).isEqualTo(UPDATED_FECHA_PETICION);
        assertThat(testEcosistemaPeticiones.getAprobada()).isEqualTo(DEFAULT_APROBADA);
    }

    @Test
    @Transactional
    void fullUpdateEcosistemaPeticionesWithPatch() throws Exception {
        // Initialize the database
        ecosistemaPeticionesRepository.saveAndFlush(ecosistemaPeticiones);

        int databaseSizeBeforeUpdate = ecosistemaPeticionesRepository.findAll().size();

        // Update the ecosistemaPeticiones using partial update
        EcosistemaPeticiones partialUpdatedEcosistemaPeticiones = new EcosistemaPeticiones();
        partialUpdatedEcosistemaPeticiones.setId(ecosistemaPeticiones.getId());

        partialUpdatedEcosistemaPeticiones.motivo(UPDATED_MOTIVO).fechaPeticion(UPDATED_FECHA_PETICION).aprobada(UPDATED_APROBADA);

        restEcosistemaPeticionesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEcosistemaPeticiones.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEcosistemaPeticiones))
            )
            .andExpect(status().isOk());

        // Validate the EcosistemaPeticiones in the database
        List<EcosistemaPeticiones> ecosistemaPeticionesList = ecosistemaPeticionesRepository.findAll();
        assertThat(ecosistemaPeticionesList).hasSize(databaseSizeBeforeUpdate);
        EcosistemaPeticiones testEcosistemaPeticiones = ecosistemaPeticionesList.get(ecosistemaPeticionesList.size() - 1);
        assertThat(testEcosistemaPeticiones.getMotivo()).isEqualTo(UPDATED_MOTIVO);
        assertThat(testEcosistemaPeticiones.getFechaPeticion()).isEqualTo(UPDATED_FECHA_PETICION);
        assertThat(testEcosistemaPeticiones.getAprobada()).isEqualTo(UPDATED_APROBADA);
    }

    @Test
    @Transactional
    void patchNonExistingEcosistemaPeticiones() throws Exception {
        int databaseSizeBeforeUpdate = ecosistemaPeticionesRepository.findAll().size();
        ecosistemaPeticiones.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEcosistemaPeticionesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ecosistemaPeticiones.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ecosistemaPeticiones))
            )
            .andExpect(status().isBadRequest());

        // Validate the EcosistemaPeticiones in the database
        List<EcosistemaPeticiones> ecosistemaPeticionesList = ecosistemaPeticionesRepository.findAll();
        assertThat(ecosistemaPeticionesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEcosistemaPeticiones() throws Exception {
        int databaseSizeBeforeUpdate = ecosistemaPeticionesRepository.findAll().size();
        ecosistemaPeticiones.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEcosistemaPeticionesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ecosistemaPeticiones))
            )
            .andExpect(status().isBadRequest());

        // Validate the EcosistemaPeticiones in the database
        List<EcosistemaPeticiones> ecosistemaPeticionesList = ecosistemaPeticionesRepository.findAll();
        assertThat(ecosistemaPeticionesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEcosistemaPeticiones() throws Exception {
        int databaseSizeBeforeUpdate = ecosistemaPeticionesRepository.findAll().size();
        ecosistemaPeticiones.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEcosistemaPeticionesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ecosistemaPeticiones))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EcosistemaPeticiones in the database
        List<EcosistemaPeticiones> ecosistemaPeticionesList = ecosistemaPeticionesRepository.findAll();
        assertThat(ecosistemaPeticionesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEcosistemaPeticiones() throws Exception {
        // Initialize the database
        ecosistemaPeticionesRepository.saveAndFlush(ecosistemaPeticiones);

        int databaseSizeBeforeDelete = ecosistemaPeticionesRepository.findAll().size();

        // Delete the ecosistemaPeticiones
        restEcosistemaPeticionesMockMvc
            .perform(delete(ENTITY_API_URL_ID, ecosistemaPeticiones.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EcosistemaPeticiones> ecosistemaPeticionesList = ecosistemaPeticionesRepository.findAll();
        assertThat(ecosistemaPeticionesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
