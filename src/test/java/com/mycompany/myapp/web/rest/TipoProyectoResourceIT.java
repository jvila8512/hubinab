package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.TipoProyecto;
import com.mycompany.myapp.repository.TipoProyectoRepository;
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
 * Integration tests for the {@link TipoProyectoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TipoProyectoResourceIT {

    private static final String DEFAULT_TIPO_PROYECTO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_PROYECTO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tipo-proyectos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TipoProyectoRepository tipoProyectoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTipoProyectoMockMvc;

    private TipoProyecto tipoProyecto;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoProyecto createEntity(EntityManager em) {
        TipoProyecto tipoProyecto = new TipoProyecto().tipoProyecto(DEFAULT_TIPO_PROYECTO);
        return tipoProyecto;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoProyecto createUpdatedEntity(EntityManager em) {
        TipoProyecto tipoProyecto = new TipoProyecto().tipoProyecto(UPDATED_TIPO_PROYECTO);
        return tipoProyecto;
    }

    @BeforeEach
    public void initTest() {
        tipoProyecto = createEntity(em);
    }

    @Test
    @Transactional
    void createTipoProyecto() throws Exception {
        int databaseSizeBeforeCreate = tipoProyectoRepository.findAll().size();
        // Create the TipoProyecto
        restTipoProyectoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoProyecto)))
            .andExpect(status().isCreated());

        // Validate the TipoProyecto in the database
        List<TipoProyecto> tipoProyectoList = tipoProyectoRepository.findAll();
        assertThat(tipoProyectoList).hasSize(databaseSizeBeforeCreate + 1);
        TipoProyecto testTipoProyecto = tipoProyectoList.get(tipoProyectoList.size() - 1);
        assertThat(testTipoProyecto.getTipoProyecto()).isEqualTo(DEFAULT_TIPO_PROYECTO);
    }

    @Test
    @Transactional
    void createTipoProyectoWithExistingId() throws Exception {
        // Create the TipoProyecto with an existing ID
        tipoProyecto.setId(1L);

        int databaseSizeBeforeCreate = tipoProyectoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoProyectoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoProyecto)))
            .andExpect(status().isBadRequest());

        // Validate the TipoProyecto in the database
        List<TipoProyecto> tipoProyectoList = tipoProyectoRepository.findAll();
        assertThat(tipoProyectoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTipoProyectoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoProyectoRepository.findAll().size();
        // set the field null
        tipoProyecto.setTipoProyecto(null);

        // Create the TipoProyecto, which fails.

        restTipoProyectoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoProyecto)))
            .andExpect(status().isBadRequest());

        List<TipoProyecto> tipoProyectoList = tipoProyectoRepository.findAll();
        assertThat(tipoProyectoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTipoProyectos() throws Exception {
        // Initialize the database
        tipoProyectoRepository.saveAndFlush(tipoProyecto);

        // Get all the tipoProyectoList
        restTipoProyectoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoProyecto.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoProyecto").value(hasItem(DEFAULT_TIPO_PROYECTO)));
    }

    @Test
    @Transactional
    void getTipoProyecto() throws Exception {
        // Initialize the database
        tipoProyectoRepository.saveAndFlush(tipoProyecto);

        // Get the tipoProyecto
        restTipoProyectoMockMvc
            .perform(get(ENTITY_API_URL_ID, tipoProyecto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tipoProyecto.getId().intValue()))
            .andExpect(jsonPath("$.tipoProyecto").value(DEFAULT_TIPO_PROYECTO));
    }

    @Test
    @Transactional
    void getNonExistingTipoProyecto() throws Exception {
        // Get the tipoProyecto
        restTipoProyectoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTipoProyecto() throws Exception {
        // Initialize the database
        tipoProyectoRepository.saveAndFlush(tipoProyecto);

        int databaseSizeBeforeUpdate = tipoProyectoRepository.findAll().size();

        // Update the tipoProyecto
        TipoProyecto updatedTipoProyecto = tipoProyectoRepository.findById(tipoProyecto.getId()).get();
        // Disconnect from session so that the updates on updatedTipoProyecto are not directly saved in db
        em.detach(updatedTipoProyecto);
        updatedTipoProyecto.tipoProyecto(UPDATED_TIPO_PROYECTO);

        restTipoProyectoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTipoProyecto.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTipoProyecto))
            )
            .andExpect(status().isOk());

        // Validate the TipoProyecto in the database
        List<TipoProyecto> tipoProyectoList = tipoProyectoRepository.findAll();
        assertThat(tipoProyectoList).hasSize(databaseSizeBeforeUpdate);
        TipoProyecto testTipoProyecto = tipoProyectoList.get(tipoProyectoList.size() - 1);
        assertThat(testTipoProyecto.getTipoProyecto()).isEqualTo(UPDATED_TIPO_PROYECTO);
    }

    @Test
    @Transactional
    void putNonExistingTipoProyecto() throws Exception {
        int databaseSizeBeforeUpdate = tipoProyectoRepository.findAll().size();
        tipoProyecto.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoProyectoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tipoProyecto.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipoProyecto))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoProyecto in the database
        List<TipoProyecto> tipoProyectoList = tipoProyectoRepository.findAll();
        assertThat(tipoProyectoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTipoProyecto() throws Exception {
        int databaseSizeBeforeUpdate = tipoProyectoRepository.findAll().size();
        tipoProyecto.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoProyectoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipoProyecto))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoProyecto in the database
        List<TipoProyecto> tipoProyectoList = tipoProyectoRepository.findAll();
        assertThat(tipoProyectoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTipoProyecto() throws Exception {
        int databaseSizeBeforeUpdate = tipoProyectoRepository.findAll().size();
        tipoProyecto.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoProyectoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoProyecto)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TipoProyecto in the database
        List<TipoProyecto> tipoProyectoList = tipoProyectoRepository.findAll();
        assertThat(tipoProyectoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTipoProyectoWithPatch() throws Exception {
        // Initialize the database
        tipoProyectoRepository.saveAndFlush(tipoProyecto);

        int databaseSizeBeforeUpdate = tipoProyectoRepository.findAll().size();

        // Update the tipoProyecto using partial update
        TipoProyecto partialUpdatedTipoProyecto = new TipoProyecto();
        partialUpdatedTipoProyecto.setId(tipoProyecto.getId());

        restTipoProyectoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTipoProyecto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTipoProyecto))
            )
            .andExpect(status().isOk());

        // Validate the TipoProyecto in the database
        List<TipoProyecto> tipoProyectoList = tipoProyectoRepository.findAll();
        assertThat(tipoProyectoList).hasSize(databaseSizeBeforeUpdate);
        TipoProyecto testTipoProyecto = tipoProyectoList.get(tipoProyectoList.size() - 1);
        assertThat(testTipoProyecto.getTipoProyecto()).isEqualTo(DEFAULT_TIPO_PROYECTO);
    }

    @Test
    @Transactional
    void fullUpdateTipoProyectoWithPatch() throws Exception {
        // Initialize the database
        tipoProyectoRepository.saveAndFlush(tipoProyecto);

        int databaseSizeBeforeUpdate = tipoProyectoRepository.findAll().size();

        // Update the tipoProyecto using partial update
        TipoProyecto partialUpdatedTipoProyecto = new TipoProyecto();
        partialUpdatedTipoProyecto.setId(tipoProyecto.getId());

        partialUpdatedTipoProyecto.tipoProyecto(UPDATED_TIPO_PROYECTO);

        restTipoProyectoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTipoProyecto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTipoProyecto))
            )
            .andExpect(status().isOk());

        // Validate the TipoProyecto in the database
        List<TipoProyecto> tipoProyectoList = tipoProyectoRepository.findAll();
        assertThat(tipoProyectoList).hasSize(databaseSizeBeforeUpdate);
        TipoProyecto testTipoProyecto = tipoProyectoList.get(tipoProyectoList.size() - 1);
        assertThat(testTipoProyecto.getTipoProyecto()).isEqualTo(UPDATED_TIPO_PROYECTO);
    }

    @Test
    @Transactional
    void patchNonExistingTipoProyecto() throws Exception {
        int databaseSizeBeforeUpdate = tipoProyectoRepository.findAll().size();
        tipoProyecto.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoProyectoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tipoProyecto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tipoProyecto))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoProyecto in the database
        List<TipoProyecto> tipoProyectoList = tipoProyectoRepository.findAll();
        assertThat(tipoProyectoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTipoProyecto() throws Exception {
        int databaseSizeBeforeUpdate = tipoProyectoRepository.findAll().size();
        tipoProyecto.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoProyectoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tipoProyecto))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoProyecto in the database
        List<TipoProyecto> tipoProyectoList = tipoProyectoRepository.findAll();
        assertThat(tipoProyectoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTipoProyecto() throws Exception {
        int databaseSizeBeforeUpdate = tipoProyectoRepository.findAll().size();
        tipoProyecto.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoProyectoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tipoProyecto))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TipoProyecto in the database
        List<TipoProyecto> tipoProyectoList = tipoProyectoRepository.findAll();
        assertThat(tipoProyectoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTipoProyecto() throws Exception {
        // Initialize the database
        tipoProyectoRepository.saveAndFlush(tipoProyecto);

        int databaseSizeBeforeDelete = tipoProyectoRepository.findAll().size();

        // Delete the tipoProyecto
        restTipoProyectoMockMvc
            .perform(delete(ENTITY_API_URL_ID, tipoProyecto.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoProyecto> tipoProyectoList = tipoProyectoRepository.findAll();
        assertThat(tipoProyectoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
