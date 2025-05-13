package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.RedesSociales;
import com.mycompany.myapp.repository.RedesSocialesRepository;
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
 * Integration tests for the {@link RedesSocialesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RedesSocialesResourceIT {

    private static final String DEFAULT_REDES_URL = "AAAAAAAAAA";
    private static final String UPDATED_REDES_URL = "BBBBBBBBBB";

    private static final byte[] DEFAULT_LOGO_URL = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_LOGO_URL = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_LOGO_URL_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_LOGO_URL_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/redes-sociales";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RedesSocialesRepository redesSocialesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRedesSocialesMockMvc;

    private RedesSociales redesSociales;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RedesSociales createEntity(EntityManager em) {
        RedesSociales redesSociales = new RedesSociales()
            .redesUrl(DEFAULT_REDES_URL)
            .logoUrl(DEFAULT_LOGO_URL)
            .logoUrlContentType(DEFAULT_LOGO_URL_CONTENT_TYPE);
        return redesSociales;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RedesSociales createUpdatedEntity(EntityManager em) {
        RedesSociales redesSociales = new RedesSociales()
            .redesUrl(UPDATED_REDES_URL)
            .logoUrl(UPDATED_LOGO_URL)
            .logoUrlContentType(UPDATED_LOGO_URL_CONTENT_TYPE);
        return redesSociales;
    }

    @BeforeEach
    public void initTest() {
        redesSociales = createEntity(em);
    }

    @Test
    @Transactional
    void createRedesSociales() throws Exception {
        int databaseSizeBeforeCreate = redesSocialesRepository.findAll().size();
        // Create the RedesSociales
        restRedesSocialesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(redesSociales)))
            .andExpect(status().isCreated());

        // Validate the RedesSociales in the database
        List<RedesSociales> redesSocialesList = redesSocialesRepository.findAll();
        assertThat(redesSocialesList).hasSize(databaseSizeBeforeCreate + 1);
        RedesSociales testRedesSociales = redesSocialesList.get(redesSocialesList.size() - 1);
        assertThat(testRedesSociales.getRedesUrl()).isEqualTo(DEFAULT_REDES_URL);
        assertThat(testRedesSociales.getLogoUrl()).isEqualTo(DEFAULT_LOGO_URL);
        assertThat(testRedesSociales.getLogoUrlContentType()).isEqualTo(DEFAULT_LOGO_URL_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createRedesSocialesWithExistingId() throws Exception {
        // Create the RedesSociales with an existing ID
        redesSociales.setId(1L);

        int databaseSizeBeforeCreate = redesSocialesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRedesSocialesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(redesSociales)))
            .andExpect(status().isBadRequest());

        // Validate the RedesSociales in the database
        List<RedesSociales> redesSocialesList = redesSocialesRepository.findAll();
        assertThat(redesSocialesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkRedesUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = redesSocialesRepository.findAll().size();
        // set the field null
        redesSociales.setRedesUrl(null);

        // Create the RedesSociales, which fails.

        restRedesSocialesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(redesSociales)))
            .andExpect(status().isBadRequest());

        List<RedesSociales> redesSocialesList = redesSocialesRepository.findAll();
        assertThat(redesSocialesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRedesSociales() throws Exception {
        // Initialize the database
        redesSocialesRepository.saveAndFlush(redesSociales);

        // Get all the redesSocialesList
        restRedesSocialesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(redesSociales.getId().intValue())))
            .andExpect(jsonPath("$.[*].redesUrl").value(hasItem(DEFAULT_REDES_URL)))
            .andExpect(jsonPath("$.[*].logoUrlContentType").value(hasItem(DEFAULT_LOGO_URL_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].logoUrl").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOGO_URL))));
    }

    @Test
    @Transactional
    void getRedesSociales() throws Exception {
        // Initialize the database
        redesSocialesRepository.saveAndFlush(redesSociales);

        // Get the redesSociales
        restRedesSocialesMockMvc
            .perform(get(ENTITY_API_URL_ID, redesSociales.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(redesSociales.getId().intValue()))
            .andExpect(jsonPath("$.redesUrl").value(DEFAULT_REDES_URL))
            .andExpect(jsonPath("$.logoUrlContentType").value(DEFAULT_LOGO_URL_CONTENT_TYPE))
            .andExpect(jsonPath("$.logoUrl").value(Base64Utils.encodeToString(DEFAULT_LOGO_URL)));
    }

    @Test
    @Transactional
    void getNonExistingRedesSociales() throws Exception {
        // Get the redesSociales
        restRedesSocialesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRedesSociales() throws Exception {
        // Initialize the database
        redesSocialesRepository.saveAndFlush(redesSociales);

        int databaseSizeBeforeUpdate = redesSocialesRepository.findAll().size();

        // Update the redesSociales
        RedesSociales updatedRedesSociales = redesSocialesRepository.findById(redesSociales.getId()).get();
        // Disconnect from session so that the updates on updatedRedesSociales are not directly saved in db
        em.detach(updatedRedesSociales);
        updatedRedesSociales.redesUrl(UPDATED_REDES_URL).logoUrl(UPDATED_LOGO_URL).logoUrlContentType(UPDATED_LOGO_URL_CONTENT_TYPE);

        restRedesSocialesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRedesSociales.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRedesSociales))
            )
            .andExpect(status().isOk());

        // Validate the RedesSociales in the database
        List<RedesSociales> redesSocialesList = redesSocialesRepository.findAll();
        assertThat(redesSocialesList).hasSize(databaseSizeBeforeUpdate);
        RedesSociales testRedesSociales = redesSocialesList.get(redesSocialesList.size() - 1);
        assertThat(testRedesSociales.getRedesUrl()).isEqualTo(UPDATED_REDES_URL);
        assertThat(testRedesSociales.getLogoUrl()).isEqualTo(UPDATED_LOGO_URL);
        assertThat(testRedesSociales.getLogoUrlContentType()).isEqualTo(UPDATED_LOGO_URL_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingRedesSociales() throws Exception {
        int databaseSizeBeforeUpdate = redesSocialesRepository.findAll().size();
        redesSociales.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRedesSocialesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, redesSociales.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(redesSociales))
            )
            .andExpect(status().isBadRequest());

        // Validate the RedesSociales in the database
        List<RedesSociales> redesSocialesList = redesSocialesRepository.findAll();
        assertThat(redesSocialesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRedesSociales() throws Exception {
        int databaseSizeBeforeUpdate = redesSocialesRepository.findAll().size();
        redesSociales.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRedesSocialesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(redesSociales))
            )
            .andExpect(status().isBadRequest());

        // Validate the RedesSociales in the database
        List<RedesSociales> redesSocialesList = redesSocialesRepository.findAll();
        assertThat(redesSocialesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRedesSociales() throws Exception {
        int databaseSizeBeforeUpdate = redesSocialesRepository.findAll().size();
        redesSociales.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRedesSocialesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(redesSociales)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RedesSociales in the database
        List<RedesSociales> redesSocialesList = redesSocialesRepository.findAll();
        assertThat(redesSocialesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRedesSocialesWithPatch() throws Exception {
        // Initialize the database
        redesSocialesRepository.saveAndFlush(redesSociales);

        int databaseSizeBeforeUpdate = redesSocialesRepository.findAll().size();

        // Update the redesSociales using partial update
        RedesSociales partialUpdatedRedesSociales = new RedesSociales();
        partialUpdatedRedesSociales.setId(redesSociales.getId());

        partialUpdatedRedesSociales.logoUrl(UPDATED_LOGO_URL).logoUrlContentType(UPDATED_LOGO_URL_CONTENT_TYPE);

        restRedesSocialesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRedesSociales.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRedesSociales))
            )
            .andExpect(status().isOk());

        // Validate the RedesSociales in the database
        List<RedesSociales> redesSocialesList = redesSocialesRepository.findAll();
        assertThat(redesSocialesList).hasSize(databaseSizeBeforeUpdate);
        RedesSociales testRedesSociales = redesSocialesList.get(redesSocialesList.size() - 1);
        assertThat(testRedesSociales.getRedesUrl()).isEqualTo(DEFAULT_REDES_URL);
        assertThat(testRedesSociales.getLogoUrl()).isEqualTo(UPDATED_LOGO_URL);
        assertThat(testRedesSociales.getLogoUrlContentType()).isEqualTo(UPDATED_LOGO_URL_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateRedesSocialesWithPatch() throws Exception {
        // Initialize the database
        redesSocialesRepository.saveAndFlush(redesSociales);

        int databaseSizeBeforeUpdate = redesSocialesRepository.findAll().size();

        // Update the redesSociales using partial update
        RedesSociales partialUpdatedRedesSociales = new RedesSociales();
        partialUpdatedRedesSociales.setId(redesSociales.getId());

        partialUpdatedRedesSociales.redesUrl(UPDATED_REDES_URL).logoUrl(UPDATED_LOGO_URL).logoUrlContentType(UPDATED_LOGO_URL_CONTENT_TYPE);

        restRedesSocialesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRedesSociales.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRedesSociales))
            )
            .andExpect(status().isOk());

        // Validate the RedesSociales in the database
        List<RedesSociales> redesSocialesList = redesSocialesRepository.findAll();
        assertThat(redesSocialesList).hasSize(databaseSizeBeforeUpdate);
        RedesSociales testRedesSociales = redesSocialesList.get(redesSocialesList.size() - 1);
        assertThat(testRedesSociales.getRedesUrl()).isEqualTo(UPDATED_REDES_URL);
        assertThat(testRedesSociales.getLogoUrl()).isEqualTo(UPDATED_LOGO_URL);
        assertThat(testRedesSociales.getLogoUrlContentType()).isEqualTo(UPDATED_LOGO_URL_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingRedesSociales() throws Exception {
        int databaseSizeBeforeUpdate = redesSocialesRepository.findAll().size();
        redesSociales.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRedesSocialesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, redesSociales.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(redesSociales))
            )
            .andExpect(status().isBadRequest());

        // Validate the RedesSociales in the database
        List<RedesSociales> redesSocialesList = redesSocialesRepository.findAll();
        assertThat(redesSocialesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRedesSociales() throws Exception {
        int databaseSizeBeforeUpdate = redesSocialesRepository.findAll().size();
        redesSociales.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRedesSocialesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(redesSociales))
            )
            .andExpect(status().isBadRequest());

        // Validate the RedesSociales in the database
        List<RedesSociales> redesSocialesList = redesSocialesRepository.findAll();
        assertThat(redesSocialesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRedesSociales() throws Exception {
        int databaseSizeBeforeUpdate = redesSocialesRepository.findAll().size();
        redesSociales.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRedesSocialesMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(redesSociales))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RedesSociales in the database
        List<RedesSociales> redesSocialesList = redesSocialesRepository.findAll();
        assertThat(redesSocialesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRedesSociales() throws Exception {
        // Initialize the database
        redesSocialesRepository.saveAndFlush(redesSociales);

        int databaseSizeBeforeDelete = redesSocialesRepository.findAll().size();

        // Delete the redesSociales
        restRedesSocialesMockMvc
            .perform(delete(ENTITY_API_URL_ID, redesSociales.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RedesSociales> redesSocialesList = redesSocialesRepository.findAll();
        assertThat(redesSocialesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
