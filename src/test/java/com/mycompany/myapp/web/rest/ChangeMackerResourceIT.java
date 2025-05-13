package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.ChangeMacker;
import com.mycompany.myapp.repository.ChangeMackerRepository;
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
 * Integration tests for the {@link ChangeMackerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ChangeMackerResourceIT {

    private static final byte[] DEFAULT_FOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FOTO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_TEMA = "AAAAAAAAAA";
    private static final String UPDATED_TEMA = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/change-mackers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ChangeMackerRepository changeMackerRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restChangeMackerMockMvc;

    private ChangeMacker changeMacker;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChangeMacker createEntity(EntityManager em) {
        ChangeMacker changeMacker = new ChangeMacker()
            .foto(DEFAULT_FOTO)
            .fotoContentType(DEFAULT_FOTO_CONTENT_TYPE)
            .nombre(DEFAULT_NOMBRE)
            .tema(DEFAULT_TEMA)
            .descripcion(DEFAULT_DESCRIPCION);
        return changeMacker;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChangeMacker createUpdatedEntity(EntityManager em) {
        ChangeMacker changeMacker = new ChangeMacker()
            .foto(UPDATED_FOTO)
            .fotoContentType(UPDATED_FOTO_CONTENT_TYPE)
            .nombre(UPDATED_NOMBRE)
            .tema(UPDATED_TEMA)
            .descripcion(UPDATED_DESCRIPCION);
        return changeMacker;
    }

    @BeforeEach
    public void initTest() {
        changeMacker = createEntity(em);
    }

    @Test
    @Transactional
    void createChangeMacker() throws Exception {
        int databaseSizeBeforeCreate = changeMackerRepository.findAll().size();
        // Create the ChangeMacker
        restChangeMackerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(changeMacker)))
            .andExpect(status().isCreated());

        // Validate the ChangeMacker in the database
        List<ChangeMacker> changeMackerList = changeMackerRepository.findAll();
        assertThat(changeMackerList).hasSize(databaseSizeBeforeCreate + 1);
        ChangeMacker testChangeMacker = changeMackerList.get(changeMackerList.size() - 1);
        assertThat(testChangeMacker.getFoto()).isEqualTo(DEFAULT_FOTO);
        assertThat(testChangeMacker.getFotoContentType()).isEqualTo(DEFAULT_FOTO_CONTENT_TYPE);
        assertThat(testChangeMacker.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testChangeMacker.getTema()).isEqualTo(DEFAULT_TEMA);
        assertThat(testChangeMacker.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    void createChangeMackerWithExistingId() throws Exception {
        // Create the ChangeMacker with an existing ID
        changeMacker.setId(1L);

        int databaseSizeBeforeCreate = changeMackerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restChangeMackerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(changeMacker)))
            .andExpect(status().isBadRequest());

        // Validate the ChangeMacker in the database
        List<ChangeMacker> changeMackerList = changeMackerRepository.findAll();
        assertThat(changeMackerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = changeMackerRepository.findAll().size();
        // set the field null
        changeMacker.setNombre(null);

        // Create the ChangeMacker, which fails.

        restChangeMackerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(changeMacker)))
            .andExpect(status().isBadRequest());

        List<ChangeMacker> changeMackerList = changeMackerRepository.findAll();
        assertThat(changeMackerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTemaIsRequired() throws Exception {
        int databaseSizeBeforeTest = changeMackerRepository.findAll().size();
        // set the field null
        changeMacker.setTema(null);

        // Create the ChangeMacker, which fails.

        restChangeMackerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(changeMacker)))
            .andExpect(status().isBadRequest());

        List<ChangeMacker> changeMackerList = changeMackerRepository.findAll();
        assertThat(changeMackerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllChangeMackers() throws Exception {
        // Initialize the database
        changeMackerRepository.saveAndFlush(changeMacker);

        // Get all the changeMackerList
        restChangeMackerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(changeMacker.getId().intValue())))
            .andExpect(jsonPath("$.[*].fotoContentType").value(hasItem(DEFAULT_FOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].foto").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTO))))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].tema").value(hasItem(DEFAULT_TEMA)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())));
    }

    @Test
    @Transactional
    void getChangeMacker() throws Exception {
        // Initialize the database
        changeMackerRepository.saveAndFlush(changeMacker);

        // Get the changeMacker
        restChangeMackerMockMvc
            .perform(get(ENTITY_API_URL_ID, changeMacker.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(changeMacker.getId().intValue()))
            .andExpect(jsonPath("$.fotoContentType").value(DEFAULT_FOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.foto").value(Base64Utils.encodeToString(DEFAULT_FOTO)))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.tema").value(DEFAULT_TEMA))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()));
    }

    @Test
    @Transactional
    void getNonExistingChangeMacker() throws Exception {
        // Get the changeMacker
        restChangeMackerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewChangeMacker() throws Exception {
        // Initialize the database
        changeMackerRepository.saveAndFlush(changeMacker);

        int databaseSizeBeforeUpdate = changeMackerRepository.findAll().size();

        // Update the changeMacker
        ChangeMacker updatedChangeMacker = changeMackerRepository.findById(changeMacker.getId()).get();
        // Disconnect from session so that the updates on updatedChangeMacker are not directly saved in db
        em.detach(updatedChangeMacker);
        updatedChangeMacker
            .foto(UPDATED_FOTO)
            .fotoContentType(UPDATED_FOTO_CONTENT_TYPE)
            .nombre(UPDATED_NOMBRE)
            .tema(UPDATED_TEMA)
            .descripcion(UPDATED_DESCRIPCION);

        restChangeMackerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedChangeMacker.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedChangeMacker))
            )
            .andExpect(status().isOk());

        // Validate the ChangeMacker in the database
        List<ChangeMacker> changeMackerList = changeMackerRepository.findAll();
        assertThat(changeMackerList).hasSize(databaseSizeBeforeUpdate);
        ChangeMacker testChangeMacker = changeMackerList.get(changeMackerList.size() - 1);
        assertThat(testChangeMacker.getFoto()).isEqualTo(UPDATED_FOTO);
        assertThat(testChangeMacker.getFotoContentType()).isEqualTo(UPDATED_FOTO_CONTENT_TYPE);
        assertThat(testChangeMacker.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testChangeMacker.getTema()).isEqualTo(UPDATED_TEMA);
        assertThat(testChangeMacker.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void putNonExistingChangeMacker() throws Exception {
        int databaseSizeBeforeUpdate = changeMackerRepository.findAll().size();
        changeMacker.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChangeMackerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, changeMacker.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(changeMacker))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChangeMacker in the database
        List<ChangeMacker> changeMackerList = changeMackerRepository.findAll();
        assertThat(changeMackerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchChangeMacker() throws Exception {
        int databaseSizeBeforeUpdate = changeMackerRepository.findAll().size();
        changeMacker.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChangeMackerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(changeMacker))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChangeMacker in the database
        List<ChangeMacker> changeMackerList = changeMackerRepository.findAll();
        assertThat(changeMackerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamChangeMacker() throws Exception {
        int databaseSizeBeforeUpdate = changeMackerRepository.findAll().size();
        changeMacker.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChangeMackerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(changeMacker)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ChangeMacker in the database
        List<ChangeMacker> changeMackerList = changeMackerRepository.findAll();
        assertThat(changeMackerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateChangeMackerWithPatch() throws Exception {
        // Initialize the database
        changeMackerRepository.saveAndFlush(changeMacker);

        int databaseSizeBeforeUpdate = changeMackerRepository.findAll().size();

        // Update the changeMacker using partial update
        ChangeMacker partialUpdatedChangeMacker = new ChangeMacker();
        partialUpdatedChangeMacker.setId(changeMacker.getId());

        partialUpdatedChangeMacker
            .foto(UPDATED_FOTO)
            .fotoContentType(UPDATED_FOTO_CONTENT_TYPE)
            .tema(UPDATED_TEMA)
            .descripcion(UPDATED_DESCRIPCION);

        restChangeMackerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChangeMacker.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedChangeMacker))
            )
            .andExpect(status().isOk());

        // Validate the ChangeMacker in the database
        List<ChangeMacker> changeMackerList = changeMackerRepository.findAll();
        assertThat(changeMackerList).hasSize(databaseSizeBeforeUpdate);
        ChangeMacker testChangeMacker = changeMackerList.get(changeMackerList.size() - 1);
        assertThat(testChangeMacker.getFoto()).isEqualTo(UPDATED_FOTO);
        assertThat(testChangeMacker.getFotoContentType()).isEqualTo(UPDATED_FOTO_CONTENT_TYPE);
        assertThat(testChangeMacker.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testChangeMacker.getTema()).isEqualTo(UPDATED_TEMA);
        assertThat(testChangeMacker.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void fullUpdateChangeMackerWithPatch() throws Exception {
        // Initialize the database
        changeMackerRepository.saveAndFlush(changeMacker);

        int databaseSizeBeforeUpdate = changeMackerRepository.findAll().size();

        // Update the changeMacker using partial update
        ChangeMacker partialUpdatedChangeMacker = new ChangeMacker();
        partialUpdatedChangeMacker.setId(changeMacker.getId());

        partialUpdatedChangeMacker
            .foto(UPDATED_FOTO)
            .fotoContentType(UPDATED_FOTO_CONTENT_TYPE)
            .nombre(UPDATED_NOMBRE)
            .tema(UPDATED_TEMA)
            .descripcion(UPDATED_DESCRIPCION);

        restChangeMackerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChangeMacker.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedChangeMacker))
            )
            .andExpect(status().isOk());

        // Validate the ChangeMacker in the database
        List<ChangeMacker> changeMackerList = changeMackerRepository.findAll();
        assertThat(changeMackerList).hasSize(databaseSizeBeforeUpdate);
        ChangeMacker testChangeMacker = changeMackerList.get(changeMackerList.size() - 1);
        assertThat(testChangeMacker.getFoto()).isEqualTo(UPDATED_FOTO);
        assertThat(testChangeMacker.getFotoContentType()).isEqualTo(UPDATED_FOTO_CONTENT_TYPE);
        assertThat(testChangeMacker.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testChangeMacker.getTema()).isEqualTo(UPDATED_TEMA);
        assertThat(testChangeMacker.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void patchNonExistingChangeMacker() throws Exception {
        int databaseSizeBeforeUpdate = changeMackerRepository.findAll().size();
        changeMacker.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChangeMackerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, changeMacker.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(changeMacker))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChangeMacker in the database
        List<ChangeMacker> changeMackerList = changeMackerRepository.findAll();
        assertThat(changeMackerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchChangeMacker() throws Exception {
        int databaseSizeBeforeUpdate = changeMackerRepository.findAll().size();
        changeMacker.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChangeMackerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(changeMacker))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChangeMacker in the database
        List<ChangeMacker> changeMackerList = changeMackerRepository.findAll();
        assertThat(changeMackerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamChangeMacker() throws Exception {
        int databaseSizeBeforeUpdate = changeMackerRepository.findAll().size();
        changeMacker.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChangeMackerMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(changeMacker))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ChangeMacker in the database
        List<ChangeMacker> changeMackerList = changeMackerRepository.findAll();
        assertThat(changeMackerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteChangeMacker() throws Exception {
        // Initialize the database
        changeMackerRepository.saveAndFlush(changeMacker);

        int databaseSizeBeforeDelete = changeMackerRepository.findAll().size();

        // Delete the changeMacker
        restChangeMackerMockMvc
            .perform(delete(ENTITY_API_URL_ID, changeMacker.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ChangeMacker> changeMackerList = changeMackerRepository.findAll();
        assertThat(changeMackerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
