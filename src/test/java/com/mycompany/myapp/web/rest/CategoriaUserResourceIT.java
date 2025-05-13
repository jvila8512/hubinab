package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.CategoriaUser;
import com.mycompany.myapp.repository.CategoriaUserRepository;
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
 * Integration tests for the {@link CategoriaUserResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CategoriaUserResourceIT {

    private static final String DEFAULT_CATEGORIA_USER = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORIA_USER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/categoria-users";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CategoriaUserRepository categoriaUserRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCategoriaUserMockMvc;

    private CategoriaUser categoriaUser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategoriaUser createEntity(EntityManager em) {
        CategoriaUser categoriaUser = new CategoriaUser().categoriaUser(DEFAULT_CATEGORIA_USER);
        return categoriaUser;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategoriaUser createUpdatedEntity(EntityManager em) {
        CategoriaUser categoriaUser = new CategoriaUser().categoriaUser(UPDATED_CATEGORIA_USER);
        return categoriaUser;
    }

    @BeforeEach
    public void initTest() {
        categoriaUser = createEntity(em);
    }

    @Test
    @Transactional
    void createCategoriaUser() throws Exception {
        int databaseSizeBeforeCreate = categoriaUserRepository.findAll().size();
        // Create the CategoriaUser
        restCategoriaUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(categoriaUser)))
            .andExpect(status().isCreated());

        // Validate the CategoriaUser in the database
        List<CategoriaUser> categoriaUserList = categoriaUserRepository.findAll();
        assertThat(categoriaUserList).hasSize(databaseSizeBeforeCreate + 1);
        CategoriaUser testCategoriaUser = categoriaUserList.get(categoriaUserList.size() - 1);
        assertThat(testCategoriaUser.getCategoriaUser()).isEqualTo(DEFAULT_CATEGORIA_USER);
    }

    @Test
    @Transactional
    void createCategoriaUserWithExistingId() throws Exception {
        // Create the CategoriaUser with an existing ID
        categoriaUser.setId(1L);

        int databaseSizeBeforeCreate = categoriaUserRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategoriaUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(categoriaUser)))
            .andExpect(status().isBadRequest());

        // Validate the CategoriaUser in the database
        List<CategoriaUser> categoriaUserList = categoriaUserRepository.findAll();
        assertThat(categoriaUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCategoriaUserIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoriaUserRepository.findAll().size();
        // set the field null
        categoriaUser.setCategoriaUser(null);

        // Create the CategoriaUser, which fails.

        restCategoriaUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(categoriaUser)))
            .andExpect(status().isBadRequest());

        List<CategoriaUser> categoriaUserList = categoriaUserRepository.findAll();
        assertThat(categoriaUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCategoriaUsers() throws Exception {
        // Initialize the database
        categoriaUserRepository.saveAndFlush(categoriaUser);

        // Get all the categoriaUserList
        restCategoriaUserMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categoriaUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].categoriaUser").value(hasItem(DEFAULT_CATEGORIA_USER)));
    }

    @Test
    @Transactional
    void getCategoriaUser() throws Exception {
        // Initialize the database
        categoriaUserRepository.saveAndFlush(categoriaUser);

        // Get the categoriaUser
        restCategoriaUserMockMvc
            .perform(get(ENTITY_API_URL_ID, categoriaUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(categoriaUser.getId().intValue()))
            .andExpect(jsonPath("$.categoriaUser").value(DEFAULT_CATEGORIA_USER));
    }

    @Test
    @Transactional
    void getNonExistingCategoriaUser() throws Exception {
        // Get the categoriaUser
        restCategoriaUserMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCategoriaUser() throws Exception {
        // Initialize the database
        categoriaUserRepository.saveAndFlush(categoriaUser);

        int databaseSizeBeforeUpdate = categoriaUserRepository.findAll().size();

        // Update the categoriaUser
        CategoriaUser updatedCategoriaUser = categoriaUserRepository.findById(categoriaUser.getId()).get();
        // Disconnect from session so that the updates on updatedCategoriaUser are not directly saved in db
        em.detach(updatedCategoriaUser);
        updatedCategoriaUser.categoriaUser(UPDATED_CATEGORIA_USER);

        restCategoriaUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCategoriaUser.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCategoriaUser))
            )
            .andExpect(status().isOk());

        // Validate the CategoriaUser in the database
        List<CategoriaUser> categoriaUserList = categoriaUserRepository.findAll();
        assertThat(categoriaUserList).hasSize(databaseSizeBeforeUpdate);
        CategoriaUser testCategoriaUser = categoriaUserList.get(categoriaUserList.size() - 1);
        assertThat(testCategoriaUser.getCategoriaUser()).isEqualTo(UPDATED_CATEGORIA_USER);
    }

    @Test
    @Transactional
    void putNonExistingCategoriaUser() throws Exception {
        int databaseSizeBeforeUpdate = categoriaUserRepository.findAll().size();
        categoriaUser.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategoriaUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, categoriaUser.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categoriaUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategoriaUser in the database
        List<CategoriaUser> categoriaUserList = categoriaUserRepository.findAll();
        assertThat(categoriaUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCategoriaUser() throws Exception {
        int databaseSizeBeforeUpdate = categoriaUserRepository.findAll().size();
        categoriaUser.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoriaUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categoriaUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategoriaUser in the database
        List<CategoriaUser> categoriaUserList = categoriaUserRepository.findAll();
        assertThat(categoriaUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCategoriaUser() throws Exception {
        int databaseSizeBeforeUpdate = categoriaUserRepository.findAll().size();
        categoriaUser.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoriaUserMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(categoriaUser)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CategoriaUser in the database
        List<CategoriaUser> categoriaUserList = categoriaUserRepository.findAll();
        assertThat(categoriaUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCategoriaUserWithPatch() throws Exception {
        // Initialize the database
        categoriaUserRepository.saveAndFlush(categoriaUser);

        int databaseSizeBeforeUpdate = categoriaUserRepository.findAll().size();

        // Update the categoriaUser using partial update
        CategoriaUser partialUpdatedCategoriaUser = new CategoriaUser();
        partialUpdatedCategoriaUser.setId(categoriaUser.getId());

        partialUpdatedCategoriaUser.categoriaUser(UPDATED_CATEGORIA_USER);

        restCategoriaUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCategoriaUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCategoriaUser))
            )
            .andExpect(status().isOk());

        // Validate the CategoriaUser in the database
        List<CategoriaUser> categoriaUserList = categoriaUserRepository.findAll();
        assertThat(categoriaUserList).hasSize(databaseSizeBeforeUpdate);
        CategoriaUser testCategoriaUser = categoriaUserList.get(categoriaUserList.size() - 1);
        assertThat(testCategoriaUser.getCategoriaUser()).isEqualTo(UPDATED_CATEGORIA_USER);
    }

    @Test
    @Transactional
    void fullUpdateCategoriaUserWithPatch() throws Exception {
        // Initialize the database
        categoriaUserRepository.saveAndFlush(categoriaUser);

        int databaseSizeBeforeUpdate = categoriaUserRepository.findAll().size();

        // Update the categoriaUser using partial update
        CategoriaUser partialUpdatedCategoriaUser = new CategoriaUser();
        partialUpdatedCategoriaUser.setId(categoriaUser.getId());

        partialUpdatedCategoriaUser.categoriaUser(UPDATED_CATEGORIA_USER);

        restCategoriaUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCategoriaUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCategoriaUser))
            )
            .andExpect(status().isOk());

        // Validate the CategoriaUser in the database
        List<CategoriaUser> categoriaUserList = categoriaUserRepository.findAll();
        assertThat(categoriaUserList).hasSize(databaseSizeBeforeUpdate);
        CategoriaUser testCategoriaUser = categoriaUserList.get(categoriaUserList.size() - 1);
        assertThat(testCategoriaUser.getCategoriaUser()).isEqualTo(UPDATED_CATEGORIA_USER);
    }

    @Test
    @Transactional
    void patchNonExistingCategoriaUser() throws Exception {
        int databaseSizeBeforeUpdate = categoriaUserRepository.findAll().size();
        categoriaUser.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategoriaUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, categoriaUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(categoriaUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategoriaUser in the database
        List<CategoriaUser> categoriaUserList = categoriaUserRepository.findAll();
        assertThat(categoriaUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCategoriaUser() throws Exception {
        int databaseSizeBeforeUpdate = categoriaUserRepository.findAll().size();
        categoriaUser.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoriaUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(categoriaUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategoriaUser in the database
        List<CategoriaUser> categoriaUserList = categoriaUserRepository.findAll();
        assertThat(categoriaUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCategoriaUser() throws Exception {
        int databaseSizeBeforeUpdate = categoriaUserRepository.findAll().size();
        categoriaUser.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoriaUserMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(categoriaUser))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CategoriaUser in the database
        List<CategoriaUser> categoriaUserList = categoriaUserRepository.findAll();
        assertThat(categoriaUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCategoriaUser() throws Exception {
        // Initialize the database
        categoriaUserRepository.saveAndFlush(categoriaUser);

        int databaseSizeBeforeDelete = categoriaUserRepository.findAll().size();

        // Delete the categoriaUser
        restCategoriaUserMockMvc
            .perform(delete(ENTITY_API_URL_ID, categoriaUser.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CategoriaUser> categoriaUserList = categoriaUserRepository.findAll();
        assertThat(categoriaUserList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
