package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.EcosistemaRol;
import com.mycompany.myapp.repository.EcosistemaRolRepository;
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
 * Integration tests for the {@link EcosistemaRolResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EcosistemaRolResourceIT {

    private static final String DEFAULT_ECOSISTEMA_ROL = "AAAAAAAAAA";
    private static final String UPDATED_ECOSISTEMA_ROL = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ecosistema-rols";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EcosistemaRolRepository ecosistemaRolRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEcosistemaRolMockMvc;

    private EcosistemaRol ecosistemaRol;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EcosistemaRol createEntity(EntityManager em) {
        EcosistemaRol ecosistemaRol = new EcosistemaRol().ecosistemaRol(DEFAULT_ECOSISTEMA_ROL).descripcion(DEFAULT_DESCRIPCION);
        return ecosistemaRol;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EcosistemaRol createUpdatedEntity(EntityManager em) {
        EcosistemaRol ecosistemaRol = new EcosistemaRol().ecosistemaRol(UPDATED_ECOSISTEMA_ROL).descripcion(UPDATED_DESCRIPCION);
        return ecosistemaRol;
    }

    @BeforeEach
    public void initTest() {
        ecosistemaRol = createEntity(em);
    }

    @Test
    @Transactional
    void createEcosistemaRol() throws Exception {
        int databaseSizeBeforeCreate = ecosistemaRolRepository.findAll().size();
        // Create the EcosistemaRol
        restEcosistemaRolMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ecosistemaRol)))
            .andExpect(status().isCreated());

        // Validate the EcosistemaRol in the database
        List<EcosistemaRol> ecosistemaRolList = ecosistemaRolRepository.findAll();
        assertThat(ecosistemaRolList).hasSize(databaseSizeBeforeCreate + 1);
        EcosistemaRol testEcosistemaRol = ecosistemaRolList.get(ecosistemaRolList.size() - 1);
        assertThat(testEcosistemaRol.getEcosistemaRol()).isEqualTo(DEFAULT_ECOSISTEMA_ROL);
        assertThat(testEcosistemaRol.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    void createEcosistemaRolWithExistingId() throws Exception {
        // Create the EcosistemaRol with an existing ID
        ecosistemaRol.setId(1L);

        int databaseSizeBeforeCreate = ecosistemaRolRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEcosistemaRolMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ecosistemaRol)))
            .andExpect(status().isBadRequest());

        // Validate the EcosistemaRol in the database
        List<EcosistemaRol> ecosistemaRolList = ecosistemaRolRepository.findAll();
        assertThat(ecosistemaRolList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkEcosistemaRolIsRequired() throws Exception {
        int databaseSizeBeforeTest = ecosistemaRolRepository.findAll().size();
        // set the field null
        ecosistemaRol.setEcosistemaRol(null);

        // Create the EcosistemaRol, which fails.

        restEcosistemaRolMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ecosistemaRol)))
            .andExpect(status().isBadRequest());

        List<EcosistemaRol> ecosistemaRolList = ecosistemaRolRepository.findAll();
        assertThat(ecosistemaRolList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEcosistemaRols() throws Exception {
        // Initialize the database
        ecosistemaRolRepository.saveAndFlush(ecosistemaRol);

        // Get all the ecosistemaRolList
        restEcosistemaRolMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ecosistemaRol.getId().intValue())))
            .andExpect(jsonPath("$.[*].ecosistemaRol").value(hasItem(DEFAULT_ECOSISTEMA_ROL)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())));
    }

    @Test
    @Transactional
    void getEcosistemaRol() throws Exception {
        // Initialize the database
        ecosistemaRolRepository.saveAndFlush(ecosistemaRol);

        // Get the ecosistemaRol
        restEcosistemaRolMockMvc
            .perform(get(ENTITY_API_URL_ID, ecosistemaRol.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ecosistemaRol.getId().intValue()))
            .andExpect(jsonPath("$.ecosistemaRol").value(DEFAULT_ECOSISTEMA_ROL))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()));
    }

    @Test
    @Transactional
    void getNonExistingEcosistemaRol() throws Exception {
        // Get the ecosistemaRol
        restEcosistemaRolMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEcosistemaRol() throws Exception {
        // Initialize the database
        ecosistemaRolRepository.saveAndFlush(ecosistemaRol);

        int databaseSizeBeforeUpdate = ecosistemaRolRepository.findAll().size();

        // Update the ecosistemaRol
        EcosistemaRol updatedEcosistemaRol = ecosistemaRolRepository.findById(ecosistemaRol.getId()).get();
        // Disconnect from session so that the updates on updatedEcosistemaRol are not directly saved in db
        em.detach(updatedEcosistemaRol);
        updatedEcosistemaRol.ecosistemaRol(UPDATED_ECOSISTEMA_ROL).descripcion(UPDATED_DESCRIPCION);

        restEcosistemaRolMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEcosistemaRol.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEcosistemaRol))
            )
            .andExpect(status().isOk());

        // Validate the EcosistemaRol in the database
        List<EcosistemaRol> ecosistemaRolList = ecosistemaRolRepository.findAll();
        assertThat(ecosistemaRolList).hasSize(databaseSizeBeforeUpdate);
        EcosistemaRol testEcosistemaRol = ecosistemaRolList.get(ecosistemaRolList.size() - 1);
        assertThat(testEcosistemaRol.getEcosistemaRol()).isEqualTo(UPDATED_ECOSISTEMA_ROL);
        assertThat(testEcosistemaRol.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void putNonExistingEcosistemaRol() throws Exception {
        int databaseSizeBeforeUpdate = ecosistemaRolRepository.findAll().size();
        ecosistemaRol.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEcosistemaRolMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ecosistemaRol.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ecosistemaRol))
            )
            .andExpect(status().isBadRequest());

        // Validate the EcosistemaRol in the database
        List<EcosistemaRol> ecosistemaRolList = ecosistemaRolRepository.findAll();
        assertThat(ecosistemaRolList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEcosistemaRol() throws Exception {
        int databaseSizeBeforeUpdate = ecosistemaRolRepository.findAll().size();
        ecosistemaRol.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEcosistemaRolMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ecosistemaRol))
            )
            .andExpect(status().isBadRequest());

        // Validate the EcosistemaRol in the database
        List<EcosistemaRol> ecosistemaRolList = ecosistemaRolRepository.findAll();
        assertThat(ecosistemaRolList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEcosistemaRol() throws Exception {
        int databaseSizeBeforeUpdate = ecosistemaRolRepository.findAll().size();
        ecosistemaRol.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEcosistemaRolMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ecosistemaRol)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EcosistemaRol in the database
        List<EcosistemaRol> ecosistemaRolList = ecosistemaRolRepository.findAll();
        assertThat(ecosistemaRolList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEcosistemaRolWithPatch() throws Exception {
        // Initialize the database
        ecosistemaRolRepository.saveAndFlush(ecosistemaRol);

        int databaseSizeBeforeUpdate = ecosistemaRolRepository.findAll().size();

        // Update the ecosistemaRol using partial update
        EcosistemaRol partialUpdatedEcosistemaRol = new EcosistemaRol();
        partialUpdatedEcosistemaRol.setId(ecosistemaRol.getId());

        partialUpdatedEcosistemaRol.ecosistemaRol(UPDATED_ECOSISTEMA_ROL);

        restEcosistemaRolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEcosistemaRol.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEcosistemaRol))
            )
            .andExpect(status().isOk());

        // Validate the EcosistemaRol in the database
        List<EcosistemaRol> ecosistemaRolList = ecosistemaRolRepository.findAll();
        assertThat(ecosistemaRolList).hasSize(databaseSizeBeforeUpdate);
        EcosistemaRol testEcosistemaRol = ecosistemaRolList.get(ecosistemaRolList.size() - 1);
        assertThat(testEcosistemaRol.getEcosistemaRol()).isEqualTo(UPDATED_ECOSISTEMA_ROL);
        assertThat(testEcosistemaRol.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    void fullUpdateEcosistemaRolWithPatch() throws Exception {
        // Initialize the database
        ecosistemaRolRepository.saveAndFlush(ecosistemaRol);

        int databaseSizeBeforeUpdate = ecosistemaRolRepository.findAll().size();

        // Update the ecosistemaRol using partial update
        EcosistemaRol partialUpdatedEcosistemaRol = new EcosistemaRol();
        partialUpdatedEcosistemaRol.setId(ecosistemaRol.getId());

        partialUpdatedEcosistemaRol.ecosistemaRol(UPDATED_ECOSISTEMA_ROL).descripcion(UPDATED_DESCRIPCION);

        restEcosistemaRolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEcosistemaRol.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEcosistemaRol))
            )
            .andExpect(status().isOk());

        // Validate the EcosistemaRol in the database
        List<EcosistemaRol> ecosistemaRolList = ecosistemaRolRepository.findAll();
        assertThat(ecosistemaRolList).hasSize(databaseSizeBeforeUpdate);
        EcosistemaRol testEcosistemaRol = ecosistemaRolList.get(ecosistemaRolList.size() - 1);
        assertThat(testEcosistemaRol.getEcosistemaRol()).isEqualTo(UPDATED_ECOSISTEMA_ROL);
        assertThat(testEcosistemaRol.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void patchNonExistingEcosistemaRol() throws Exception {
        int databaseSizeBeforeUpdate = ecosistemaRolRepository.findAll().size();
        ecosistemaRol.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEcosistemaRolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ecosistemaRol.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ecosistemaRol))
            )
            .andExpect(status().isBadRequest());

        // Validate the EcosistemaRol in the database
        List<EcosistemaRol> ecosistemaRolList = ecosistemaRolRepository.findAll();
        assertThat(ecosistemaRolList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEcosistemaRol() throws Exception {
        int databaseSizeBeforeUpdate = ecosistemaRolRepository.findAll().size();
        ecosistemaRol.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEcosistemaRolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ecosistemaRol))
            )
            .andExpect(status().isBadRequest());

        // Validate the EcosistemaRol in the database
        List<EcosistemaRol> ecosistemaRolList = ecosistemaRolRepository.findAll();
        assertThat(ecosistemaRolList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEcosistemaRol() throws Exception {
        int databaseSizeBeforeUpdate = ecosistemaRolRepository.findAll().size();
        ecosistemaRol.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEcosistemaRolMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(ecosistemaRol))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EcosistemaRol in the database
        List<EcosistemaRol> ecosistemaRolList = ecosistemaRolRepository.findAll();
        assertThat(ecosistemaRolList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEcosistemaRol() throws Exception {
        // Initialize the database
        ecosistemaRolRepository.saveAndFlush(ecosistemaRol);

        int databaseSizeBeforeDelete = ecosistemaRolRepository.findAll().size();

        // Delete the ecosistemaRol
        restEcosistemaRolMockMvc
            .perform(delete(ENTITY_API_URL_ID, ecosistemaRol.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EcosistemaRol> ecosistemaRolList = ecosistemaRolRepository.findAll();
        assertThat(ecosistemaRolList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
