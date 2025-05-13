package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Componentes;
import com.mycompany.myapp.repository.ComponentesRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link ComponentesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ComponentesResourceIT {

    private static final String DEFAULT_COMPONENTE = "AAAAAAAAAA";
    private static final String UPDATED_COMPONENTE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVO = false;
    private static final Boolean UPDATED_ACTIVO = true;

    private static final String ENTITY_API_URL = "/api/componentes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ComponentesRepository componentesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restComponentesMockMvc;

    private Componentes componentes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Componentes createEntity(EntityManager em) {
        Componentes componentes = new Componentes().componente(DEFAULT_COMPONENTE).descripcion(DEFAULT_DESCRIPCION).activo(DEFAULT_ACTIVO);
        return componentes;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Componentes createUpdatedEntity(EntityManager em) {
        Componentes componentes = new Componentes().componente(UPDATED_COMPONENTE).descripcion(UPDATED_DESCRIPCION).activo(UPDATED_ACTIVO);
        return componentes;
    }

    @BeforeEach
    public void initTest() {
        componentes = createEntity(em);
    }

    @Test
    @Transactional
    void createComponentes() throws Exception {
        int databaseSizeBeforeCreate = componentesRepository.findAll().size();
        // Create the Componentes
        restComponentesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(componentes)))
            .andExpect(status().isCreated());

        // Validate the Componentes in the database
        List<Componentes> componentesList = componentesRepository.findAll();
        assertThat(componentesList).hasSize(databaseSizeBeforeCreate + 1);
        Componentes testComponentes = componentesList.get(componentesList.size() - 1);
        assertThat(testComponentes.getComponente()).isEqualTo(DEFAULT_COMPONENTE);
        assertThat(testComponentes.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testComponentes.getActivo()).isEqualTo(DEFAULT_ACTIVO);
    }

    @Test
    @Transactional
    void createComponentesWithExistingId() throws Exception {
        // Create the Componentes with an existing ID
        componentes.setId(1L);

        int databaseSizeBeforeCreate = componentesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restComponentesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(componentes)))
            .andExpect(status().isBadRequest());

        // Validate the Componentes in the database
        List<Componentes> componentesList = componentesRepository.findAll();
        assertThat(componentesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkComponenteIsRequired() throws Exception {
        int databaseSizeBeforeTest = componentesRepository.findAll().size();
        // set the field null
        componentes.setComponente(null);

        // Create the Componentes, which fails.

        restComponentesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(componentes)))
            .andExpect(status().isBadRequest());

        List<Componentes> componentesList = componentesRepository.findAll();
        assertThat(componentesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllComponentes() throws Exception {
        // Initialize the database
        componentesRepository.saveAndFlush(componentes);

        // Get all the componentesList
        restComponentesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(componentes.getId().intValue())))
            .andExpect(jsonPath("$.[*].componente").value(hasItem(DEFAULT_COMPONENTE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].activo").value(hasItem(DEFAULT_ACTIVO.booleanValue())));
    }

    @Test
    @Transactional
    void getComponentes() throws Exception {
        // Initialize the database
        componentesRepository.saveAndFlush(componentes);

        // Get the componentes
        restComponentesMockMvc
            .perform(get(ENTITY_API_URL_ID, componentes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(componentes.getId().intValue()))
            .andExpect(jsonPath("$.componente").value(DEFAULT_COMPONENTE))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.activo").value(DEFAULT_ACTIVO.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingComponentes() throws Exception {
        // Get the componentes
        restComponentesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewComponentes() throws Exception {
        // Initialize the database
        componentesRepository.saveAndFlush(componentes);

        int databaseSizeBeforeUpdate = componentesRepository.findAll().size();

        // Update the componentes
        Componentes updatedComponentes = componentesRepository.findById(componentes.getId()).get();
        // Disconnect from session so that the updates on updatedComponentes are not directly saved in db
        em.detach(updatedComponentes);
        updatedComponentes.componente(UPDATED_COMPONENTE).descripcion(UPDATED_DESCRIPCION).activo(UPDATED_ACTIVO);

        restComponentesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedComponentes.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedComponentes))
            )
            .andExpect(status().isOk());

        // Validate the Componentes in the database
        List<Componentes> componentesList = componentesRepository.findAll();
        assertThat(componentesList).hasSize(databaseSizeBeforeUpdate);
        Componentes testComponentes = componentesList.get(componentesList.size() - 1);
        assertThat(testComponentes.getComponente()).isEqualTo(UPDATED_COMPONENTE);
        assertThat(testComponentes.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testComponentes.getActivo()).isEqualTo(UPDATED_ACTIVO);
    }

    @Test
    @Transactional
    void putNonExistingComponentes() throws Exception {
        int databaseSizeBeforeUpdate = componentesRepository.findAll().size();
        componentes.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restComponentesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, componentes.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(componentes))
            )
            .andExpect(status().isBadRequest());

        // Validate the Componentes in the database
        List<Componentes> componentesList = componentesRepository.findAll();
        assertThat(componentesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchComponentes() throws Exception {
        int databaseSizeBeforeUpdate = componentesRepository.findAll().size();
        componentes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restComponentesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(componentes))
            )
            .andExpect(status().isBadRequest());

        // Validate the Componentes in the database
        List<Componentes> componentesList = componentesRepository.findAll();
        assertThat(componentesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamComponentes() throws Exception {
        int databaseSizeBeforeUpdate = componentesRepository.findAll().size();
        componentes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restComponentesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(componentes)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Componentes in the database
        List<Componentes> componentesList = componentesRepository.findAll();
        assertThat(componentesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateComponentesWithPatch() throws Exception {
        // Initialize the database
        componentesRepository.saveAndFlush(componentes);

        int databaseSizeBeforeUpdate = componentesRepository.findAll().size();

        // Update the componentes using partial update
        Componentes partialUpdatedComponentes = new Componentes();
        partialUpdatedComponentes.setId(componentes.getId());

        partialUpdatedComponentes.descripcion(UPDATED_DESCRIPCION).activo(UPDATED_ACTIVO);

        restComponentesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedComponentes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedComponentes))
            )
            .andExpect(status().isOk());

        // Validate the Componentes in the database
        List<Componentes> componentesList = componentesRepository.findAll();
        assertThat(componentesList).hasSize(databaseSizeBeforeUpdate);
        Componentes testComponentes = componentesList.get(componentesList.size() - 1);
        assertThat(testComponentes.getComponente()).isEqualTo(DEFAULT_COMPONENTE);
        assertThat(testComponentes.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testComponentes.getActivo()).isEqualTo(UPDATED_ACTIVO);
    }

    @Test
    @Transactional
    void fullUpdateComponentesWithPatch() throws Exception {
        // Initialize the database
        componentesRepository.saveAndFlush(componentes);

        int databaseSizeBeforeUpdate = componentesRepository.findAll().size();

        // Update the componentes using partial update
        Componentes partialUpdatedComponentes = new Componentes();
        partialUpdatedComponentes.setId(componentes.getId());

        partialUpdatedComponentes.componente(UPDATED_COMPONENTE).descripcion(UPDATED_DESCRIPCION).activo(UPDATED_ACTIVO);

        restComponentesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedComponentes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedComponentes))
            )
            .andExpect(status().isOk());

        // Validate the Componentes in the database
        List<Componentes> componentesList = componentesRepository.findAll();
        assertThat(componentesList).hasSize(databaseSizeBeforeUpdate);
        Componentes testComponentes = componentesList.get(componentesList.size() - 1);
        assertThat(testComponentes.getComponente()).isEqualTo(UPDATED_COMPONENTE);
        assertThat(testComponentes.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testComponentes.getActivo()).isEqualTo(UPDATED_ACTIVO);
    }

    @Test
    @Transactional
    void patchNonExistingComponentes() throws Exception {
        int databaseSizeBeforeUpdate = componentesRepository.findAll().size();
        componentes.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restComponentesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, componentes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(componentes))
            )
            .andExpect(status().isBadRequest());

        // Validate the Componentes in the database
        List<Componentes> componentesList = componentesRepository.findAll();
        assertThat(componentesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchComponentes() throws Exception {
        int databaseSizeBeforeUpdate = componentesRepository.findAll().size();
        componentes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restComponentesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(componentes))
            )
            .andExpect(status().isBadRequest());

        // Validate the Componentes in the database
        List<Componentes> componentesList = componentesRepository.findAll();
        assertThat(componentesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamComponentes() throws Exception {
        int databaseSizeBeforeUpdate = componentesRepository.findAll().size();
        componentes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restComponentesMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(componentes))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Componentes in the database
        List<Componentes> componentesList = componentesRepository.findAll();
        assertThat(componentesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteComponentes() throws Exception {
        // Initialize the database
        componentesRepository.saveAndFlush(componentes);

        int databaseSizeBeforeDelete = componentesRepository.findAll().size();

        // Delete the componentes
        restComponentesMockMvc
            .perform(delete(ENTITY_API_URL_ID, componentes.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Componentes> componentesList = componentesRepository.findAll();
        assertThat(componentesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
