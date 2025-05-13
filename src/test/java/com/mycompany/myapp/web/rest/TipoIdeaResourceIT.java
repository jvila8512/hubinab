package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.TipoIdea;
import com.mycompany.myapp.repository.TipoIdeaRepository;
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
 * Integration tests for the {@link TipoIdeaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TipoIdeaResourceIT {

    private static final String DEFAULT_TIPO_IDEA = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_IDEA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tipo-ideas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TipoIdeaRepository tipoIdeaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTipoIdeaMockMvc;

    private TipoIdea tipoIdea;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoIdea createEntity(EntityManager em) {
        TipoIdea tipoIdea = new TipoIdea().tipoIdea(DEFAULT_TIPO_IDEA);
        return tipoIdea;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoIdea createUpdatedEntity(EntityManager em) {
        TipoIdea tipoIdea = new TipoIdea().tipoIdea(UPDATED_TIPO_IDEA);
        return tipoIdea;
    }

    @BeforeEach
    public void initTest() {
        tipoIdea = createEntity(em);
    }

    @Test
    @Transactional
    void createTipoIdea() throws Exception {
        int databaseSizeBeforeCreate = tipoIdeaRepository.findAll().size();
        // Create the TipoIdea
        restTipoIdeaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoIdea)))
            .andExpect(status().isCreated());

        // Validate the TipoIdea in the database
        List<TipoIdea> tipoIdeaList = tipoIdeaRepository.findAll();
        assertThat(tipoIdeaList).hasSize(databaseSizeBeforeCreate + 1);
        TipoIdea testTipoIdea = tipoIdeaList.get(tipoIdeaList.size() - 1);
        assertThat(testTipoIdea.getTipoIdea()).isEqualTo(DEFAULT_TIPO_IDEA);
    }

    @Test
    @Transactional
    void createTipoIdeaWithExistingId() throws Exception {
        // Create the TipoIdea with an existing ID
        tipoIdea.setId(1L);

        int databaseSizeBeforeCreate = tipoIdeaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoIdeaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoIdea)))
            .andExpect(status().isBadRequest());

        // Validate the TipoIdea in the database
        List<TipoIdea> tipoIdeaList = tipoIdeaRepository.findAll();
        assertThat(tipoIdeaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTipoIdeaIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoIdeaRepository.findAll().size();
        // set the field null
        tipoIdea.setTipoIdea(null);

        // Create the TipoIdea, which fails.

        restTipoIdeaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoIdea)))
            .andExpect(status().isBadRequest());

        List<TipoIdea> tipoIdeaList = tipoIdeaRepository.findAll();
        assertThat(tipoIdeaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTipoIdeas() throws Exception {
        // Initialize the database
        tipoIdeaRepository.saveAndFlush(tipoIdea);

        // Get all the tipoIdeaList
        restTipoIdeaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoIdea.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoIdea").value(hasItem(DEFAULT_TIPO_IDEA)));
    }

    @Test
    @Transactional
    void getTipoIdea() throws Exception {
        // Initialize the database
        tipoIdeaRepository.saveAndFlush(tipoIdea);

        // Get the tipoIdea
        restTipoIdeaMockMvc
            .perform(get(ENTITY_API_URL_ID, tipoIdea.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tipoIdea.getId().intValue()))
            .andExpect(jsonPath("$.tipoIdea").value(DEFAULT_TIPO_IDEA));
    }

    @Test
    @Transactional
    void getNonExistingTipoIdea() throws Exception {
        // Get the tipoIdea
        restTipoIdeaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTipoIdea() throws Exception {
        // Initialize the database
        tipoIdeaRepository.saveAndFlush(tipoIdea);

        int databaseSizeBeforeUpdate = tipoIdeaRepository.findAll().size();

        // Update the tipoIdea
        TipoIdea updatedTipoIdea = tipoIdeaRepository.findById(tipoIdea.getId()).get();
        // Disconnect from session so that the updates on updatedTipoIdea are not directly saved in db
        em.detach(updatedTipoIdea);
        updatedTipoIdea.tipoIdea(UPDATED_TIPO_IDEA);

        restTipoIdeaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTipoIdea.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTipoIdea))
            )
            .andExpect(status().isOk());

        // Validate the TipoIdea in the database
        List<TipoIdea> tipoIdeaList = tipoIdeaRepository.findAll();
        assertThat(tipoIdeaList).hasSize(databaseSizeBeforeUpdate);
        TipoIdea testTipoIdea = tipoIdeaList.get(tipoIdeaList.size() - 1);
        assertThat(testTipoIdea.getTipoIdea()).isEqualTo(UPDATED_TIPO_IDEA);
    }

    @Test
    @Transactional
    void putNonExistingTipoIdea() throws Exception {
        int databaseSizeBeforeUpdate = tipoIdeaRepository.findAll().size();
        tipoIdea.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoIdeaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tipoIdea.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipoIdea))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoIdea in the database
        List<TipoIdea> tipoIdeaList = tipoIdeaRepository.findAll();
        assertThat(tipoIdeaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTipoIdea() throws Exception {
        int databaseSizeBeforeUpdate = tipoIdeaRepository.findAll().size();
        tipoIdea.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoIdeaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipoIdea))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoIdea in the database
        List<TipoIdea> tipoIdeaList = tipoIdeaRepository.findAll();
        assertThat(tipoIdeaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTipoIdea() throws Exception {
        int databaseSizeBeforeUpdate = tipoIdeaRepository.findAll().size();
        tipoIdea.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoIdeaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoIdea)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TipoIdea in the database
        List<TipoIdea> tipoIdeaList = tipoIdeaRepository.findAll();
        assertThat(tipoIdeaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTipoIdeaWithPatch() throws Exception {
        // Initialize the database
        tipoIdeaRepository.saveAndFlush(tipoIdea);

        int databaseSizeBeforeUpdate = tipoIdeaRepository.findAll().size();

        // Update the tipoIdea using partial update
        TipoIdea partialUpdatedTipoIdea = new TipoIdea();
        partialUpdatedTipoIdea.setId(tipoIdea.getId());

        restTipoIdeaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTipoIdea.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTipoIdea))
            )
            .andExpect(status().isOk());

        // Validate the TipoIdea in the database
        List<TipoIdea> tipoIdeaList = tipoIdeaRepository.findAll();
        assertThat(tipoIdeaList).hasSize(databaseSizeBeforeUpdate);
        TipoIdea testTipoIdea = tipoIdeaList.get(tipoIdeaList.size() - 1);
        assertThat(testTipoIdea.getTipoIdea()).isEqualTo(DEFAULT_TIPO_IDEA);
    }

    @Test
    @Transactional
    void fullUpdateTipoIdeaWithPatch() throws Exception {
        // Initialize the database
        tipoIdeaRepository.saveAndFlush(tipoIdea);

        int databaseSizeBeforeUpdate = tipoIdeaRepository.findAll().size();

        // Update the tipoIdea using partial update
        TipoIdea partialUpdatedTipoIdea = new TipoIdea();
        partialUpdatedTipoIdea.setId(tipoIdea.getId());

        partialUpdatedTipoIdea.tipoIdea(UPDATED_TIPO_IDEA);

        restTipoIdeaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTipoIdea.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTipoIdea))
            )
            .andExpect(status().isOk());

        // Validate the TipoIdea in the database
        List<TipoIdea> tipoIdeaList = tipoIdeaRepository.findAll();
        assertThat(tipoIdeaList).hasSize(databaseSizeBeforeUpdate);
        TipoIdea testTipoIdea = tipoIdeaList.get(tipoIdeaList.size() - 1);
        assertThat(testTipoIdea.getTipoIdea()).isEqualTo(UPDATED_TIPO_IDEA);
    }

    @Test
    @Transactional
    void patchNonExistingTipoIdea() throws Exception {
        int databaseSizeBeforeUpdate = tipoIdeaRepository.findAll().size();
        tipoIdea.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoIdeaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tipoIdea.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tipoIdea))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoIdea in the database
        List<TipoIdea> tipoIdeaList = tipoIdeaRepository.findAll();
        assertThat(tipoIdeaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTipoIdea() throws Exception {
        int databaseSizeBeforeUpdate = tipoIdeaRepository.findAll().size();
        tipoIdea.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoIdeaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tipoIdea))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoIdea in the database
        List<TipoIdea> tipoIdeaList = tipoIdeaRepository.findAll();
        assertThat(tipoIdeaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTipoIdea() throws Exception {
        int databaseSizeBeforeUpdate = tipoIdeaRepository.findAll().size();
        tipoIdea.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoIdeaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tipoIdea)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TipoIdea in the database
        List<TipoIdea> tipoIdeaList = tipoIdeaRepository.findAll();
        assertThat(tipoIdeaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTipoIdea() throws Exception {
        // Initialize the database
        tipoIdeaRepository.saveAndFlush(tipoIdea);

        int databaseSizeBeforeDelete = tipoIdeaRepository.findAll().size();

        // Delete the tipoIdea
        restTipoIdeaMockMvc
            .perform(delete(ENTITY_API_URL_ID, tipoIdea.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoIdea> tipoIdeaList = tipoIdeaRepository.findAll();
        assertThat(tipoIdeaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
