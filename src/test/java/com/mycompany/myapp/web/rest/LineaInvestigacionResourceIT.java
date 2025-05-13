package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.LineaInvestigacion;
import com.mycompany.myapp.repository.LineaInvestigacionRepository;
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
 * Integration tests for the {@link LineaInvestigacionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LineaInvestigacionResourceIT {

    private static final String DEFAULT_LINEA = "AAAAAAAAAA";
    private static final String UPDATED_LINEA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/linea-investigacions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LineaInvestigacionRepository lineaInvestigacionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLineaInvestigacionMockMvc;

    private LineaInvestigacion lineaInvestigacion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LineaInvestigacion createEntity(EntityManager em) {
        LineaInvestigacion lineaInvestigacion = new LineaInvestigacion().linea(DEFAULT_LINEA);
        return lineaInvestigacion;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LineaInvestigacion createUpdatedEntity(EntityManager em) {
        LineaInvestigacion lineaInvestigacion = new LineaInvestigacion().linea(UPDATED_LINEA);
        return lineaInvestigacion;
    }

    @BeforeEach
    public void initTest() {
        lineaInvestigacion = createEntity(em);
    }

    @Test
    @Transactional
    void createLineaInvestigacion() throws Exception {
        int databaseSizeBeforeCreate = lineaInvestigacionRepository.findAll().size();
        // Create the LineaInvestigacion
        restLineaInvestigacionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lineaInvestigacion))
            )
            .andExpect(status().isCreated());

        // Validate the LineaInvestigacion in the database
        List<LineaInvestigacion> lineaInvestigacionList = lineaInvestigacionRepository.findAll();
        assertThat(lineaInvestigacionList).hasSize(databaseSizeBeforeCreate + 1);
        LineaInvestigacion testLineaInvestigacion = lineaInvestigacionList.get(lineaInvestigacionList.size() - 1);
        assertThat(testLineaInvestigacion.getLinea()).isEqualTo(DEFAULT_LINEA);
    }

    @Test
    @Transactional
    void createLineaInvestigacionWithExistingId() throws Exception {
        // Create the LineaInvestigacion with an existing ID
        lineaInvestigacion.setId(1L);

        int databaseSizeBeforeCreate = lineaInvestigacionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLineaInvestigacionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lineaInvestigacion))
            )
            .andExpect(status().isBadRequest());

        // Validate the LineaInvestigacion in the database
        List<LineaInvestigacion> lineaInvestigacionList = lineaInvestigacionRepository.findAll();
        assertThat(lineaInvestigacionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLineaIsRequired() throws Exception {
        int databaseSizeBeforeTest = lineaInvestigacionRepository.findAll().size();
        // set the field null
        lineaInvestigacion.setLinea(null);

        // Create the LineaInvestigacion, which fails.

        restLineaInvestigacionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lineaInvestigacion))
            )
            .andExpect(status().isBadRequest());

        List<LineaInvestigacion> lineaInvestigacionList = lineaInvestigacionRepository.findAll();
        assertThat(lineaInvestigacionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLineaInvestigacions() throws Exception {
        // Initialize the database
        lineaInvestigacionRepository.saveAndFlush(lineaInvestigacion);

        // Get all the lineaInvestigacionList
        restLineaInvestigacionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lineaInvestigacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].linea").value(hasItem(DEFAULT_LINEA)));
    }

    @Test
    @Transactional
    void getLineaInvestigacion() throws Exception {
        // Initialize the database
        lineaInvestigacionRepository.saveAndFlush(lineaInvestigacion);

        // Get the lineaInvestigacion
        restLineaInvestigacionMockMvc
            .perform(get(ENTITY_API_URL_ID, lineaInvestigacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(lineaInvestigacion.getId().intValue()))
            .andExpect(jsonPath("$.linea").value(DEFAULT_LINEA));
    }

    @Test
    @Transactional
    void getNonExistingLineaInvestigacion() throws Exception {
        // Get the lineaInvestigacion
        restLineaInvestigacionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewLineaInvestigacion() throws Exception {
        // Initialize the database
        lineaInvestigacionRepository.saveAndFlush(lineaInvestigacion);

        int databaseSizeBeforeUpdate = lineaInvestigacionRepository.findAll().size();

        // Update the lineaInvestigacion
        LineaInvestigacion updatedLineaInvestigacion = lineaInvestigacionRepository.findById(lineaInvestigacion.getId()).get();
        // Disconnect from session so that the updates on updatedLineaInvestigacion are not directly saved in db
        em.detach(updatedLineaInvestigacion);
        updatedLineaInvestigacion.linea(UPDATED_LINEA);

        restLineaInvestigacionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLineaInvestigacion.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLineaInvestigacion))
            )
            .andExpect(status().isOk());

        // Validate the LineaInvestigacion in the database
        List<LineaInvestigacion> lineaInvestigacionList = lineaInvestigacionRepository.findAll();
        assertThat(lineaInvestigacionList).hasSize(databaseSizeBeforeUpdate);
        LineaInvestigacion testLineaInvestigacion = lineaInvestigacionList.get(lineaInvestigacionList.size() - 1);
        assertThat(testLineaInvestigacion.getLinea()).isEqualTo(UPDATED_LINEA);
    }

    @Test
    @Transactional
    void putNonExistingLineaInvestigacion() throws Exception {
        int databaseSizeBeforeUpdate = lineaInvestigacionRepository.findAll().size();
        lineaInvestigacion.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLineaInvestigacionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, lineaInvestigacion.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(lineaInvestigacion))
            )
            .andExpect(status().isBadRequest());

        // Validate the LineaInvestigacion in the database
        List<LineaInvestigacion> lineaInvestigacionList = lineaInvestigacionRepository.findAll();
        assertThat(lineaInvestigacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLineaInvestigacion() throws Exception {
        int databaseSizeBeforeUpdate = lineaInvestigacionRepository.findAll().size();
        lineaInvestigacion.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLineaInvestigacionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(lineaInvestigacion))
            )
            .andExpect(status().isBadRequest());

        // Validate the LineaInvestigacion in the database
        List<LineaInvestigacion> lineaInvestigacionList = lineaInvestigacionRepository.findAll();
        assertThat(lineaInvestigacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLineaInvestigacion() throws Exception {
        int databaseSizeBeforeUpdate = lineaInvestigacionRepository.findAll().size();
        lineaInvestigacion.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLineaInvestigacionMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lineaInvestigacion))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LineaInvestigacion in the database
        List<LineaInvestigacion> lineaInvestigacionList = lineaInvestigacionRepository.findAll();
        assertThat(lineaInvestigacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLineaInvestigacionWithPatch() throws Exception {
        // Initialize the database
        lineaInvestigacionRepository.saveAndFlush(lineaInvestigacion);

        int databaseSizeBeforeUpdate = lineaInvestigacionRepository.findAll().size();

        // Update the lineaInvestigacion using partial update
        LineaInvestigacion partialUpdatedLineaInvestigacion = new LineaInvestigacion();
        partialUpdatedLineaInvestigacion.setId(lineaInvestigacion.getId());

        partialUpdatedLineaInvestigacion.linea(UPDATED_LINEA);

        restLineaInvestigacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLineaInvestigacion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLineaInvestigacion))
            )
            .andExpect(status().isOk());

        // Validate the LineaInvestigacion in the database
        List<LineaInvestigacion> lineaInvestigacionList = lineaInvestigacionRepository.findAll();
        assertThat(lineaInvestigacionList).hasSize(databaseSizeBeforeUpdate);
        LineaInvestigacion testLineaInvestigacion = lineaInvestigacionList.get(lineaInvestigacionList.size() - 1);
        assertThat(testLineaInvestigacion.getLinea()).isEqualTo(UPDATED_LINEA);
    }

    @Test
    @Transactional
    void fullUpdateLineaInvestigacionWithPatch() throws Exception {
        // Initialize the database
        lineaInvestigacionRepository.saveAndFlush(lineaInvestigacion);

        int databaseSizeBeforeUpdate = lineaInvestigacionRepository.findAll().size();

        // Update the lineaInvestigacion using partial update
        LineaInvestigacion partialUpdatedLineaInvestigacion = new LineaInvestigacion();
        partialUpdatedLineaInvestigacion.setId(lineaInvestigacion.getId());

        partialUpdatedLineaInvestigacion.linea(UPDATED_LINEA);

        restLineaInvestigacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLineaInvestigacion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLineaInvestigacion))
            )
            .andExpect(status().isOk());

        // Validate the LineaInvestigacion in the database
        List<LineaInvestigacion> lineaInvestigacionList = lineaInvestigacionRepository.findAll();
        assertThat(lineaInvestigacionList).hasSize(databaseSizeBeforeUpdate);
        LineaInvestigacion testLineaInvestigacion = lineaInvestigacionList.get(lineaInvestigacionList.size() - 1);
        assertThat(testLineaInvestigacion.getLinea()).isEqualTo(UPDATED_LINEA);
    }

    @Test
    @Transactional
    void patchNonExistingLineaInvestigacion() throws Exception {
        int databaseSizeBeforeUpdate = lineaInvestigacionRepository.findAll().size();
        lineaInvestigacion.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLineaInvestigacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, lineaInvestigacion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(lineaInvestigacion))
            )
            .andExpect(status().isBadRequest());

        // Validate the LineaInvestigacion in the database
        List<LineaInvestigacion> lineaInvestigacionList = lineaInvestigacionRepository.findAll();
        assertThat(lineaInvestigacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLineaInvestigacion() throws Exception {
        int databaseSizeBeforeUpdate = lineaInvestigacionRepository.findAll().size();
        lineaInvestigacion.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLineaInvestigacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(lineaInvestigacion))
            )
            .andExpect(status().isBadRequest());

        // Validate the LineaInvestigacion in the database
        List<LineaInvestigacion> lineaInvestigacionList = lineaInvestigacionRepository.findAll();
        assertThat(lineaInvestigacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLineaInvestigacion() throws Exception {
        int databaseSizeBeforeUpdate = lineaInvestigacionRepository.findAll().size();
        lineaInvestigacion.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLineaInvestigacionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(lineaInvestigacion))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LineaInvestigacion in the database
        List<LineaInvestigacion> lineaInvestigacionList = lineaInvestigacionRepository.findAll();
        assertThat(lineaInvestigacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLineaInvestigacion() throws Exception {
        // Initialize the database
        lineaInvestigacionRepository.saveAndFlush(lineaInvestigacion);

        int databaseSizeBeforeDelete = lineaInvestigacionRepository.findAll().size();

        // Delete the lineaInvestigacion
        restLineaInvestigacionMockMvc
            .perform(delete(ENTITY_API_URL_ID, lineaInvestigacion.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LineaInvestigacion> lineaInvestigacionList = lineaInvestigacionRepository.findAll();
        assertThat(lineaInvestigacionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
