package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Ods;
import com.mycompany.myapp.repository.OdsRepository;
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
 * Integration tests for the {@link OdsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OdsResourceIT {

    private static final String DEFAULT_ODS = "AAAAAAAAAA";
    private static final String UPDATED_ODS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ods";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OdsRepository odsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOdsMockMvc;

    private Ods ods;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ods createEntity(EntityManager em) {
        Ods ods = new Ods().ods(DEFAULT_ODS);
        return ods;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ods createUpdatedEntity(EntityManager em) {
        Ods ods = new Ods().ods(UPDATED_ODS);
        return ods;
    }

    @BeforeEach
    public void initTest() {
        ods = createEntity(em);
    }

    @Test
    @Transactional
    void createOds() throws Exception {
        int databaseSizeBeforeCreate = odsRepository.findAll().size();
        // Create the Ods
        restOdsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ods)))
            .andExpect(status().isCreated());

        // Validate the Ods in the database
        List<Ods> odsList = odsRepository.findAll();
        assertThat(odsList).hasSize(databaseSizeBeforeCreate + 1);
        Ods testOds = odsList.get(odsList.size() - 1);
        assertThat(testOds.getOds()).isEqualTo(DEFAULT_ODS);
    }

    @Test
    @Transactional
    void createOdsWithExistingId() throws Exception {
        // Create the Ods with an existing ID
        ods.setId(1L);

        int databaseSizeBeforeCreate = odsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOdsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ods)))
            .andExpect(status().isBadRequest());

        // Validate the Ods in the database
        List<Ods> odsList = odsRepository.findAll();
        assertThat(odsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkOdsIsRequired() throws Exception {
        int databaseSizeBeforeTest = odsRepository.findAll().size();
        // set the field null
        ods.setOds(null);

        // Create the Ods, which fails.

        restOdsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ods)))
            .andExpect(status().isBadRequest());

        List<Ods> odsList = odsRepository.findAll();
        assertThat(odsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllOds() throws Exception {
        // Initialize the database
        odsRepository.saveAndFlush(ods);

        // Get all the odsList
        restOdsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ods.getId().intValue())))
            .andExpect(jsonPath("$.[*].ods").value(hasItem(DEFAULT_ODS)));
    }

    @Test
    @Transactional
    void getOds() throws Exception {
        // Initialize the database
        odsRepository.saveAndFlush(ods);

        // Get the ods
        restOdsMockMvc
            .perform(get(ENTITY_API_URL_ID, ods.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ods.getId().intValue()))
            .andExpect(jsonPath("$.ods").value(DEFAULT_ODS));
    }

    @Test
    @Transactional
    void getNonExistingOds() throws Exception {
        // Get the ods
        restOdsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOds() throws Exception {
        // Initialize the database
        odsRepository.saveAndFlush(ods);

        int databaseSizeBeforeUpdate = odsRepository.findAll().size();

        // Update the ods
        Ods updatedOds = odsRepository.findById(ods.getId()).get();
        // Disconnect from session so that the updates on updatedOds are not directly saved in db
        em.detach(updatedOds);
        updatedOds.ods(UPDATED_ODS);

        restOdsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOds.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedOds))
            )
            .andExpect(status().isOk());

        // Validate the Ods in the database
        List<Ods> odsList = odsRepository.findAll();
        assertThat(odsList).hasSize(databaseSizeBeforeUpdate);
        Ods testOds = odsList.get(odsList.size() - 1);
        assertThat(testOds.getOds()).isEqualTo(UPDATED_ODS);
    }

    @Test
    @Transactional
    void putNonExistingOds() throws Exception {
        int databaseSizeBeforeUpdate = odsRepository.findAll().size();
        ods.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOdsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ods.getId()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ods))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ods in the database
        List<Ods> odsList = odsRepository.findAll();
        assertThat(odsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOds() throws Exception {
        int databaseSizeBeforeUpdate = odsRepository.findAll().size();
        ods.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOdsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ods))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ods in the database
        List<Ods> odsList = odsRepository.findAll();
        assertThat(odsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOds() throws Exception {
        int databaseSizeBeforeUpdate = odsRepository.findAll().size();
        ods.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOdsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ods)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ods in the database
        List<Ods> odsList = odsRepository.findAll();
        assertThat(odsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOdsWithPatch() throws Exception {
        // Initialize the database
        odsRepository.saveAndFlush(ods);

        int databaseSizeBeforeUpdate = odsRepository.findAll().size();

        // Update the ods using partial update
        Ods partialUpdatedOds = new Ods();
        partialUpdatedOds.setId(ods.getId());

        partialUpdatedOds.ods(UPDATED_ODS);

        restOdsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOds.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOds))
            )
            .andExpect(status().isOk());

        // Validate the Ods in the database
        List<Ods> odsList = odsRepository.findAll();
        assertThat(odsList).hasSize(databaseSizeBeforeUpdate);
        Ods testOds = odsList.get(odsList.size() - 1);
        assertThat(testOds.getOds()).isEqualTo(UPDATED_ODS);
    }

    @Test
    @Transactional
    void fullUpdateOdsWithPatch() throws Exception {
        // Initialize the database
        odsRepository.saveAndFlush(ods);

        int databaseSizeBeforeUpdate = odsRepository.findAll().size();

        // Update the ods using partial update
        Ods partialUpdatedOds = new Ods();
        partialUpdatedOds.setId(ods.getId());

        partialUpdatedOds.ods(UPDATED_ODS);

        restOdsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOds.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOds))
            )
            .andExpect(status().isOk());

        // Validate the Ods in the database
        List<Ods> odsList = odsRepository.findAll();
        assertThat(odsList).hasSize(databaseSizeBeforeUpdate);
        Ods testOds = odsList.get(odsList.size() - 1);
        assertThat(testOds.getOds()).isEqualTo(UPDATED_ODS);
    }

    @Test
    @Transactional
    void patchNonExistingOds() throws Exception {
        int databaseSizeBeforeUpdate = odsRepository.findAll().size();
        ods.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOdsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ods.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ods))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ods in the database
        List<Ods> odsList = odsRepository.findAll();
        assertThat(odsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOds() throws Exception {
        int databaseSizeBeforeUpdate = odsRepository.findAll().size();
        ods.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOdsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ods))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ods in the database
        List<Ods> odsList = odsRepository.findAll();
        assertThat(odsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOds() throws Exception {
        int databaseSizeBeforeUpdate = odsRepository.findAll().size();
        ods.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOdsMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(ods)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ods in the database
        List<Ods> odsList = odsRepository.findAll();
        assertThat(odsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOds() throws Exception {
        // Initialize the database
        odsRepository.saveAndFlush(ods);

        int databaseSizeBeforeDelete = odsRepository.findAll().size();

        // Delete the ods
        restOdsMockMvc.perform(delete(ENTITY_API_URL_ID, ods.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Ods> odsList = odsRepository.findAll();
        assertThat(odsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
