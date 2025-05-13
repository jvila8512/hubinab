package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.TipoNoticia;
import com.mycompany.myapp.repository.TipoNoticiaRepository;
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
 * Integration tests for the {@link TipoNoticiaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TipoNoticiaResourceIT {

    private static final String DEFAULT_TIPO_NOTICIA = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_NOTICIA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tipo-noticias";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TipoNoticiaRepository tipoNoticiaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTipoNoticiaMockMvc;

    private TipoNoticia tipoNoticia;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoNoticia createEntity(EntityManager em) {
        TipoNoticia tipoNoticia = new TipoNoticia().tipoNoticia(DEFAULT_TIPO_NOTICIA);
        return tipoNoticia;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoNoticia createUpdatedEntity(EntityManager em) {
        TipoNoticia tipoNoticia = new TipoNoticia().tipoNoticia(UPDATED_TIPO_NOTICIA);
        return tipoNoticia;
    }

    @BeforeEach
    public void initTest() {
        tipoNoticia = createEntity(em);
    }

    @Test
    @Transactional
    void createTipoNoticia() throws Exception {
        int databaseSizeBeforeCreate = tipoNoticiaRepository.findAll().size();
        // Create the TipoNoticia
        restTipoNoticiaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoNoticia)))
            .andExpect(status().isCreated());

        // Validate the TipoNoticia in the database
        List<TipoNoticia> tipoNoticiaList = tipoNoticiaRepository.findAll();
        assertThat(tipoNoticiaList).hasSize(databaseSizeBeforeCreate + 1);
        TipoNoticia testTipoNoticia = tipoNoticiaList.get(tipoNoticiaList.size() - 1);
        assertThat(testTipoNoticia.getTipoNoticia()).isEqualTo(DEFAULT_TIPO_NOTICIA);
    }

    @Test
    @Transactional
    void createTipoNoticiaWithExistingId() throws Exception {
        // Create the TipoNoticia with an existing ID
        tipoNoticia.setId(1L);

        int databaseSizeBeforeCreate = tipoNoticiaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoNoticiaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoNoticia)))
            .andExpect(status().isBadRequest());

        // Validate the TipoNoticia in the database
        List<TipoNoticia> tipoNoticiaList = tipoNoticiaRepository.findAll();
        assertThat(tipoNoticiaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTipoNoticiaIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoNoticiaRepository.findAll().size();
        // set the field null
        tipoNoticia.setTipoNoticia(null);

        // Create the TipoNoticia, which fails.

        restTipoNoticiaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoNoticia)))
            .andExpect(status().isBadRequest());

        List<TipoNoticia> tipoNoticiaList = tipoNoticiaRepository.findAll();
        assertThat(tipoNoticiaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTipoNoticias() throws Exception {
        // Initialize the database
        tipoNoticiaRepository.saveAndFlush(tipoNoticia);

        // Get all the tipoNoticiaList
        restTipoNoticiaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoNoticia.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoNoticia").value(hasItem(DEFAULT_TIPO_NOTICIA)));
    }

    @Test
    @Transactional
    void getTipoNoticia() throws Exception {
        // Initialize the database
        tipoNoticiaRepository.saveAndFlush(tipoNoticia);

        // Get the tipoNoticia
        restTipoNoticiaMockMvc
            .perform(get(ENTITY_API_URL_ID, tipoNoticia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tipoNoticia.getId().intValue()))
            .andExpect(jsonPath("$.tipoNoticia").value(DEFAULT_TIPO_NOTICIA));
    }

    @Test
    @Transactional
    void getNonExistingTipoNoticia() throws Exception {
        // Get the tipoNoticia
        restTipoNoticiaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTipoNoticia() throws Exception {
        // Initialize the database
        tipoNoticiaRepository.saveAndFlush(tipoNoticia);

        int databaseSizeBeforeUpdate = tipoNoticiaRepository.findAll().size();

        // Update the tipoNoticia
        TipoNoticia updatedTipoNoticia = tipoNoticiaRepository.findById(tipoNoticia.getId()).get();
        // Disconnect from session so that the updates on updatedTipoNoticia are not directly saved in db
        em.detach(updatedTipoNoticia);
        updatedTipoNoticia.tipoNoticia(UPDATED_TIPO_NOTICIA);

        restTipoNoticiaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTipoNoticia.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTipoNoticia))
            )
            .andExpect(status().isOk());

        // Validate the TipoNoticia in the database
        List<TipoNoticia> tipoNoticiaList = tipoNoticiaRepository.findAll();
        assertThat(tipoNoticiaList).hasSize(databaseSizeBeforeUpdate);
        TipoNoticia testTipoNoticia = tipoNoticiaList.get(tipoNoticiaList.size() - 1);
        assertThat(testTipoNoticia.getTipoNoticia()).isEqualTo(UPDATED_TIPO_NOTICIA);
    }

    @Test
    @Transactional
    void putNonExistingTipoNoticia() throws Exception {
        int databaseSizeBeforeUpdate = tipoNoticiaRepository.findAll().size();
        tipoNoticia.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoNoticiaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tipoNoticia.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipoNoticia))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoNoticia in the database
        List<TipoNoticia> tipoNoticiaList = tipoNoticiaRepository.findAll();
        assertThat(tipoNoticiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTipoNoticia() throws Exception {
        int databaseSizeBeforeUpdate = tipoNoticiaRepository.findAll().size();
        tipoNoticia.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoNoticiaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipoNoticia))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoNoticia in the database
        List<TipoNoticia> tipoNoticiaList = tipoNoticiaRepository.findAll();
        assertThat(tipoNoticiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTipoNoticia() throws Exception {
        int databaseSizeBeforeUpdate = tipoNoticiaRepository.findAll().size();
        tipoNoticia.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoNoticiaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoNoticia)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TipoNoticia in the database
        List<TipoNoticia> tipoNoticiaList = tipoNoticiaRepository.findAll();
        assertThat(tipoNoticiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTipoNoticiaWithPatch() throws Exception {
        // Initialize the database
        tipoNoticiaRepository.saveAndFlush(tipoNoticia);

        int databaseSizeBeforeUpdate = tipoNoticiaRepository.findAll().size();

        // Update the tipoNoticia using partial update
        TipoNoticia partialUpdatedTipoNoticia = new TipoNoticia();
        partialUpdatedTipoNoticia.setId(tipoNoticia.getId());

        partialUpdatedTipoNoticia.tipoNoticia(UPDATED_TIPO_NOTICIA);

        restTipoNoticiaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTipoNoticia.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTipoNoticia))
            )
            .andExpect(status().isOk());

        // Validate the TipoNoticia in the database
        List<TipoNoticia> tipoNoticiaList = tipoNoticiaRepository.findAll();
        assertThat(tipoNoticiaList).hasSize(databaseSizeBeforeUpdate);
        TipoNoticia testTipoNoticia = tipoNoticiaList.get(tipoNoticiaList.size() - 1);
        assertThat(testTipoNoticia.getTipoNoticia()).isEqualTo(UPDATED_TIPO_NOTICIA);
    }

    @Test
    @Transactional
    void fullUpdateTipoNoticiaWithPatch() throws Exception {
        // Initialize the database
        tipoNoticiaRepository.saveAndFlush(tipoNoticia);

        int databaseSizeBeforeUpdate = tipoNoticiaRepository.findAll().size();

        // Update the tipoNoticia using partial update
        TipoNoticia partialUpdatedTipoNoticia = new TipoNoticia();
        partialUpdatedTipoNoticia.setId(tipoNoticia.getId());

        partialUpdatedTipoNoticia.tipoNoticia(UPDATED_TIPO_NOTICIA);

        restTipoNoticiaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTipoNoticia.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTipoNoticia))
            )
            .andExpect(status().isOk());

        // Validate the TipoNoticia in the database
        List<TipoNoticia> tipoNoticiaList = tipoNoticiaRepository.findAll();
        assertThat(tipoNoticiaList).hasSize(databaseSizeBeforeUpdate);
        TipoNoticia testTipoNoticia = tipoNoticiaList.get(tipoNoticiaList.size() - 1);
        assertThat(testTipoNoticia.getTipoNoticia()).isEqualTo(UPDATED_TIPO_NOTICIA);
    }

    @Test
    @Transactional
    void patchNonExistingTipoNoticia() throws Exception {
        int databaseSizeBeforeUpdate = tipoNoticiaRepository.findAll().size();
        tipoNoticia.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoNoticiaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tipoNoticia.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tipoNoticia))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoNoticia in the database
        List<TipoNoticia> tipoNoticiaList = tipoNoticiaRepository.findAll();
        assertThat(tipoNoticiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTipoNoticia() throws Exception {
        int databaseSizeBeforeUpdate = tipoNoticiaRepository.findAll().size();
        tipoNoticia.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoNoticiaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tipoNoticia))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoNoticia in the database
        List<TipoNoticia> tipoNoticiaList = tipoNoticiaRepository.findAll();
        assertThat(tipoNoticiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTipoNoticia() throws Exception {
        int databaseSizeBeforeUpdate = tipoNoticiaRepository.findAll().size();
        tipoNoticia.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoNoticiaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tipoNoticia))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TipoNoticia in the database
        List<TipoNoticia> tipoNoticiaList = tipoNoticiaRepository.findAll();
        assertThat(tipoNoticiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTipoNoticia() throws Exception {
        // Initialize the database
        tipoNoticiaRepository.saveAndFlush(tipoNoticia);

        int databaseSizeBeforeDelete = tipoNoticiaRepository.findAll().size();

        // Delete the tipoNoticia
        restTipoNoticiaMockMvc
            .perform(delete(ENTITY_API_URL_ID, tipoNoticia.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoNoticia> tipoNoticiaList = tipoNoticiaRepository.findAll();
        assertThat(tipoNoticiaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
