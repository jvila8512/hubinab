package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Entidad;
import com.mycompany.myapp.repository.EntidadRepository;
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

/**
 * Integration tests for the {@link EntidadResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EntidadResourceIT {

    private static final String DEFAULT_ENTIDAD = "AAAAAAAAAA";
    private static final String UPDATED_ENTIDAD = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/entidads";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EntidadRepository entidadRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEntidadMockMvc;

    private Entidad entidad;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Entidad createEntity(EntityManager em) {
        Entidad entidad = new Entidad().entidad(DEFAULT_ENTIDAD);
        return entidad;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Entidad createUpdatedEntity(EntityManager em) {
        Entidad entidad = new Entidad().entidad(UPDATED_ENTIDAD);
        return entidad;
    }

    @BeforeEach
    public void initTest() {
        entidad = createEntity(em);
    }

    @Test
    @Transactional
    void createEntidad() throws Exception {
        int databaseSizeBeforeCreate = entidadRepository.findAll().size();
        // Create the Entidad
        restEntidadMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entidad)))
            .andExpect(status().isCreated());

        // Validate the Entidad in the database
        List<Entidad> entidadList = entidadRepository.findAll();
        assertThat(entidadList).hasSize(databaseSizeBeforeCreate + 1);
        Entidad testEntidad = entidadList.get(entidadList.size() - 1);
        assertThat(testEntidad.getEntidad()).isEqualTo(DEFAULT_ENTIDAD);
    }

    @Test
    @Transactional
    void createEntidadWithExistingId() throws Exception {
        // Create the Entidad with an existing ID
        entidad.setId(1L);

        int databaseSizeBeforeCreate = entidadRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEntidadMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entidad)))
            .andExpect(status().isBadRequest());

        // Validate the Entidad in the database
        List<Entidad> entidadList = entidadRepository.findAll();
        assertThat(entidadList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkEntidadIsRequired() throws Exception {
        int databaseSizeBeforeTest = entidadRepository.findAll().size();
        // set the field null
        entidad.setEntidad(null);

        // Create the Entidad, which fails.

        restEntidadMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entidad)))
            .andExpect(status().isBadRequest());

        List<Entidad> entidadList = entidadRepository.findAll();
        assertThat(entidadList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEntidads() throws Exception {
        // Initialize the database
        entidadRepository.saveAndFlush(entidad);

        // Get all the entidadList
        restEntidadMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entidad.getId().intValue())))
            .andExpect(jsonPath("$.[*].entidad").value(hasItem(DEFAULT_ENTIDAD)));
    }

    @Test
    @Transactional
    void getEntidad() throws Exception {
        // Initialize the database
        entidadRepository.saveAndFlush(entidad);

        // Get the entidad
        restEntidadMockMvc
            .perform(get(ENTITY_API_URL_ID, entidad.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(entidad.getId().intValue()))
            .andExpect(jsonPath("$.entidad").value(DEFAULT_ENTIDAD));
    }

    @Test
    @Transactional
    void getNonExistingEntidad() throws Exception {
        // Get the entidad
        restEntidadMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEntidad() throws Exception {
        // Initialize the database
        entidadRepository.saveAndFlush(entidad);

        int databaseSizeBeforeUpdate = entidadRepository.findAll().size();

        // Update the entidad
        Entidad updatedEntidad = entidadRepository.findById(entidad.getId()).get();
        // Disconnect from session so that the updates on updatedEntidad are not directly saved in db
        em.detach(updatedEntidad);
        updatedEntidad.entidad(UPDATED_ENTIDAD);

        restEntidadMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEntidad.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEntidad))
            )
            .andExpect(status().isOk());

        // Validate the Entidad in the database
        List<Entidad> entidadList = entidadRepository.findAll();
        assertThat(entidadList).hasSize(databaseSizeBeforeUpdate);
        Entidad testEntidad = entidadList.get(entidadList.size() - 1);
        assertThat(testEntidad.getEntidad()).isEqualTo(UPDATED_ENTIDAD);
    }

    @Test
    @Transactional
    void putNonExistingEntidad() throws Exception {
        int databaseSizeBeforeUpdate = entidadRepository.findAll().size();
        entidad.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEntidadMockMvc
            .perform(
                put(ENTITY_API_URL_ID, entidad.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(entidad))
            )
            .andExpect(status().isBadRequest());

        // Validate the Entidad in the database
        List<Entidad> entidadList = entidadRepository.findAll();
        assertThat(entidadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEntidad() throws Exception {
        int databaseSizeBeforeUpdate = entidadRepository.findAll().size();
        entidad.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntidadMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(entidad))
            )
            .andExpect(status().isBadRequest());

        // Validate the Entidad in the database
        List<Entidad> entidadList = entidadRepository.findAll();
        assertThat(entidadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEntidad() throws Exception {
        int databaseSizeBeforeUpdate = entidadRepository.findAll().size();
        entidad.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntidadMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entidad)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Entidad in the database
        List<Entidad> entidadList = entidadRepository.findAll();
        assertThat(entidadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEntidadWithPatch() throws Exception {
        // Initialize the database
        entidadRepository.saveAndFlush(entidad);

        int databaseSizeBeforeUpdate = entidadRepository.findAll().size();

        // Update the entidad using partial update
        Entidad partialUpdatedEntidad = new Entidad();
        partialUpdatedEntidad.setId(entidad.getId());

        restEntidadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEntidad.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEntidad))
            )
            .andExpect(status().isOk());

        // Validate the Entidad in the database
        List<Entidad> entidadList = entidadRepository.findAll();
        assertThat(entidadList).hasSize(databaseSizeBeforeUpdate);
        Entidad testEntidad = entidadList.get(entidadList.size() - 1);
        assertThat(testEntidad.getEntidad()).isEqualTo(DEFAULT_ENTIDAD);
    }

    @Test
    @Transactional
    void fullUpdateEntidadWithPatch() throws Exception {
        // Initialize the database
        entidadRepository.saveAndFlush(entidad);

        int databaseSizeBeforeUpdate = entidadRepository.findAll().size();

        // Update the entidad using partial update
        Entidad partialUpdatedEntidad = new Entidad();
        partialUpdatedEntidad.setId(entidad.getId());

        partialUpdatedEntidad.entidad(UPDATED_ENTIDAD);

        restEntidadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEntidad.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEntidad))
            )
            .andExpect(status().isOk());

        // Validate the Entidad in the database
        List<Entidad> entidadList = entidadRepository.findAll();
        assertThat(entidadList).hasSize(databaseSizeBeforeUpdate);
        Entidad testEntidad = entidadList.get(entidadList.size() - 1);
        assertThat(testEntidad.getEntidad()).isEqualTo(UPDATED_ENTIDAD);
    }

    @Test
    @Transactional
    void patchNonExistingEntidad() throws Exception {
        int databaseSizeBeforeUpdate = entidadRepository.findAll().size();
        entidad.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEntidadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, entidad.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(entidad))
            )
            .andExpect(status().isBadRequest());

        // Validate the Entidad in the database
        List<Entidad> entidadList = entidadRepository.findAll();
        assertThat(entidadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEntidad() throws Exception {
        int databaseSizeBeforeUpdate = entidadRepository.findAll().size();
        entidad.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntidadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(entidad))
            )
            .andExpect(status().isBadRequest());

        // Validate the Entidad in the database
        List<Entidad> entidadList = entidadRepository.findAll();
        assertThat(entidadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEntidad() throws Exception {
        int databaseSizeBeforeUpdate = entidadRepository.findAll().size();
        entidad.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntidadMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(entidad)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Entidad in the database
        List<Entidad> entidadList = entidadRepository.findAll();
        assertThat(entidadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEntidad() throws Exception {
        // Initialize the database
        entidadRepository.saveAndFlush(entidad);

        int databaseSizeBeforeDelete = entidadRepository.findAll().size();

        // Delete the entidad
        restEntidadMockMvc
            .perform(delete(ENTITY_API_URL_ID, entidad.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Entidad> entidadList = entidadRepository.findAll();
        assertThat(entidadList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
