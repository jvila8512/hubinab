package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.EcosistemaComponente;
import com.mycompany.myapp.repository.EcosistemaComponenteRepository;
import com.mycompany.myapp.service.EcosistemaComponenteService;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link EcosistemaComponenteResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class EcosistemaComponenteResourceIT {

    private static final String DEFAULT_LINK = "AAAAAAAAAA";
    private static final String UPDATED_LINK = "BBBBBBBBBB";

    private static final byte[] DEFAULT_DOCUMENTO_URL = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DOCUMENTO_URL = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DOCUMENTO_URL_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DOCUMENTO_URL_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/ecosistema-componentes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EcosistemaComponenteRepository ecosistemaComponenteRepository;

    @Mock
    private EcosistemaComponenteRepository ecosistemaComponenteRepositoryMock;

    @Mock
    private EcosistemaComponenteService ecosistemaComponenteServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEcosistemaComponenteMockMvc;

    private EcosistemaComponente ecosistemaComponente;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EcosistemaComponente createEntity(EntityManager em) {
        EcosistemaComponente ecosistemaComponente = new EcosistemaComponente()
            .link(DEFAULT_LINK)
            .documentoUrl(DEFAULT_DOCUMENTO_URL)
            .documentoUrlContentType(DEFAULT_DOCUMENTO_URL_CONTENT_TYPE);
        return ecosistemaComponente;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EcosistemaComponente createUpdatedEntity(EntityManager em) {
        EcosistemaComponente ecosistemaComponente = new EcosistemaComponente()
            .link(UPDATED_LINK)
            .documentoUrl(UPDATED_DOCUMENTO_URL)
            .documentoUrlContentType(UPDATED_DOCUMENTO_URL_CONTENT_TYPE);
        return ecosistemaComponente;
    }

    @BeforeEach
    public void initTest() {
        ecosistemaComponente = createEntity(em);
    }

    @Test
    @Transactional
    void createEcosistemaComponente() throws Exception {
        int databaseSizeBeforeCreate = ecosistemaComponenteRepository.findAll().size();
        // Create the EcosistemaComponente
        restEcosistemaComponenteMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ecosistemaComponente))
            )
            .andExpect(status().isCreated());

        // Validate the EcosistemaComponente in the database
        List<EcosistemaComponente> ecosistemaComponenteList = ecosistemaComponenteRepository.findAll();
        assertThat(ecosistemaComponenteList).hasSize(databaseSizeBeforeCreate + 1);
        EcosistemaComponente testEcosistemaComponente = ecosistemaComponenteList.get(ecosistemaComponenteList.size() - 1);
        assertThat(testEcosistemaComponente.getLink()).isEqualTo(DEFAULT_LINK);
        assertThat(testEcosistemaComponente.getDocumentoUrl()).isEqualTo(DEFAULT_DOCUMENTO_URL);
        assertThat(testEcosistemaComponente.getDocumentoUrlContentType()).isEqualTo(DEFAULT_DOCUMENTO_URL_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createEcosistemaComponenteWithExistingId() throws Exception {
        // Create the EcosistemaComponente with an existing ID
        ecosistemaComponente.setId(1L);

        int databaseSizeBeforeCreate = ecosistemaComponenteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEcosistemaComponenteMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ecosistemaComponente))
            )
            .andExpect(status().isBadRequest());

        // Validate the EcosistemaComponente in the database
        List<EcosistemaComponente> ecosistemaComponenteList = ecosistemaComponenteRepository.findAll();
        assertThat(ecosistemaComponenteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEcosistemaComponentes() throws Exception {
        // Initialize the database
        ecosistemaComponenteRepository.saveAndFlush(ecosistemaComponente);

        // Get all the ecosistemaComponenteList
        restEcosistemaComponenteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ecosistemaComponente.getId().intValue())))
            .andExpect(jsonPath("$.[*].link").value(hasItem(DEFAULT_LINK)))
            .andExpect(jsonPath("$.[*].documentoUrlContentType").value(hasItem(DEFAULT_DOCUMENTO_URL_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].documentoUrl").value(hasItem(Base64Utils.encodeToString(DEFAULT_DOCUMENTO_URL))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEcosistemaComponentesWithEagerRelationshipsIsEnabled() throws Exception {
        when(ecosistemaComponenteServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEcosistemaComponenteMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(ecosistemaComponenteServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEcosistemaComponentesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(ecosistemaComponenteServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEcosistemaComponenteMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(ecosistemaComponenteServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getEcosistemaComponente() throws Exception {
        // Initialize the database
        ecosistemaComponenteRepository.saveAndFlush(ecosistemaComponente);

        // Get the ecosistemaComponente
        restEcosistemaComponenteMockMvc
            .perform(get(ENTITY_API_URL_ID, ecosistemaComponente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ecosistemaComponente.getId().intValue()))
            .andExpect(jsonPath("$.link").value(DEFAULT_LINK))
            .andExpect(jsonPath("$.documentoUrlContentType").value(DEFAULT_DOCUMENTO_URL_CONTENT_TYPE))
            .andExpect(jsonPath("$.documentoUrl").value(Base64Utils.encodeToString(DEFAULT_DOCUMENTO_URL)));
    }

    @Test
    @Transactional
    void getNonExistingEcosistemaComponente() throws Exception {
        // Get the ecosistemaComponente
        restEcosistemaComponenteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEcosistemaComponente() throws Exception {
        // Initialize the database
        ecosistemaComponenteRepository.saveAndFlush(ecosistemaComponente);

        int databaseSizeBeforeUpdate = ecosistemaComponenteRepository.findAll().size();

        // Update the ecosistemaComponente
        EcosistemaComponente updatedEcosistemaComponente = ecosistemaComponenteRepository.findById(ecosistemaComponente.getId()).get();
        // Disconnect from session so that the updates on updatedEcosistemaComponente are not directly saved in db
        em.detach(updatedEcosistemaComponente);
        updatedEcosistemaComponente
            .link(UPDATED_LINK)
            .documentoUrl(UPDATED_DOCUMENTO_URL)
            .documentoUrlContentType(UPDATED_DOCUMENTO_URL_CONTENT_TYPE);

        restEcosistemaComponenteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEcosistemaComponente.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEcosistemaComponente))
            )
            .andExpect(status().isOk());

        // Validate the EcosistemaComponente in the database
        List<EcosistemaComponente> ecosistemaComponenteList = ecosistemaComponenteRepository.findAll();
        assertThat(ecosistemaComponenteList).hasSize(databaseSizeBeforeUpdate);
        EcosistemaComponente testEcosistemaComponente = ecosistemaComponenteList.get(ecosistemaComponenteList.size() - 1);
        assertThat(testEcosistemaComponente.getLink()).isEqualTo(UPDATED_LINK);
        assertThat(testEcosistemaComponente.getDocumentoUrl()).isEqualTo(UPDATED_DOCUMENTO_URL);
        assertThat(testEcosistemaComponente.getDocumentoUrlContentType()).isEqualTo(UPDATED_DOCUMENTO_URL_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingEcosistemaComponente() throws Exception {
        int databaseSizeBeforeUpdate = ecosistemaComponenteRepository.findAll().size();
        ecosistemaComponente.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEcosistemaComponenteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ecosistemaComponente.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ecosistemaComponente))
            )
            .andExpect(status().isBadRequest());

        // Validate the EcosistemaComponente in the database
        List<EcosistemaComponente> ecosistemaComponenteList = ecosistemaComponenteRepository.findAll();
        assertThat(ecosistemaComponenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEcosistemaComponente() throws Exception {
        int databaseSizeBeforeUpdate = ecosistemaComponenteRepository.findAll().size();
        ecosistemaComponente.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEcosistemaComponenteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ecosistemaComponente))
            )
            .andExpect(status().isBadRequest());

        // Validate the EcosistemaComponente in the database
        List<EcosistemaComponente> ecosistemaComponenteList = ecosistemaComponenteRepository.findAll();
        assertThat(ecosistemaComponenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEcosistemaComponente() throws Exception {
        int databaseSizeBeforeUpdate = ecosistemaComponenteRepository.findAll().size();
        ecosistemaComponente.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEcosistemaComponenteMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ecosistemaComponente))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EcosistemaComponente in the database
        List<EcosistemaComponente> ecosistemaComponenteList = ecosistemaComponenteRepository.findAll();
        assertThat(ecosistemaComponenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEcosistemaComponenteWithPatch() throws Exception {
        // Initialize the database
        ecosistemaComponenteRepository.saveAndFlush(ecosistemaComponente);

        int databaseSizeBeforeUpdate = ecosistemaComponenteRepository.findAll().size();

        // Update the ecosistemaComponente using partial update
        EcosistemaComponente partialUpdatedEcosistemaComponente = new EcosistemaComponente();
        partialUpdatedEcosistemaComponente.setId(ecosistemaComponente.getId());

        partialUpdatedEcosistemaComponente.documentoUrl(UPDATED_DOCUMENTO_URL).documentoUrlContentType(UPDATED_DOCUMENTO_URL_CONTENT_TYPE);

        restEcosistemaComponenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEcosistemaComponente.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEcosistemaComponente))
            )
            .andExpect(status().isOk());

        // Validate the EcosistemaComponente in the database
        List<EcosistemaComponente> ecosistemaComponenteList = ecosistemaComponenteRepository.findAll();
        assertThat(ecosistemaComponenteList).hasSize(databaseSizeBeforeUpdate);
        EcosistemaComponente testEcosistemaComponente = ecosistemaComponenteList.get(ecosistemaComponenteList.size() - 1);
        assertThat(testEcosistemaComponente.getLink()).isEqualTo(DEFAULT_LINK);
        assertThat(testEcosistemaComponente.getDocumentoUrl()).isEqualTo(UPDATED_DOCUMENTO_URL);
        assertThat(testEcosistemaComponente.getDocumentoUrlContentType()).isEqualTo(UPDATED_DOCUMENTO_URL_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateEcosistemaComponenteWithPatch() throws Exception {
        // Initialize the database
        ecosistemaComponenteRepository.saveAndFlush(ecosistemaComponente);

        int databaseSizeBeforeUpdate = ecosistemaComponenteRepository.findAll().size();

        // Update the ecosistemaComponente using partial update
        EcosistemaComponente partialUpdatedEcosistemaComponente = new EcosistemaComponente();
        partialUpdatedEcosistemaComponente.setId(ecosistemaComponente.getId());

        partialUpdatedEcosistemaComponente
            .link(UPDATED_LINK)
            .documentoUrl(UPDATED_DOCUMENTO_URL)
            .documentoUrlContentType(UPDATED_DOCUMENTO_URL_CONTENT_TYPE);

        restEcosistemaComponenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEcosistemaComponente.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEcosistemaComponente))
            )
            .andExpect(status().isOk());

        // Validate the EcosistemaComponente in the database
        List<EcosistemaComponente> ecosistemaComponenteList = ecosistemaComponenteRepository.findAll();
        assertThat(ecosistemaComponenteList).hasSize(databaseSizeBeforeUpdate);
        EcosistemaComponente testEcosistemaComponente = ecosistemaComponenteList.get(ecosistemaComponenteList.size() - 1);
        assertThat(testEcosistemaComponente.getLink()).isEqualTo(UPDATED_LINK);
        assertThat(testEcosistemaComponente.getDocumentoUrl()).isEqualTo(UPDATED_DOCUMENTO_URL);
        assertThat(testEcosistemaComponente.getDocumentoUrlContentType()).isEqualTo(UPDATED_DOCUMENTO_URL_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingEcosistemaComponente() throws Exception {
        int databaseSizeBeforeUpdate = ecosistemaComponenteRepository.findAll().size();
        ecosistemaComponente.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEcosistemaComponenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ecosistemaComponente.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ecosistemaComponente))
            )
            .andExpect(status().isBadRequest());

        // Validate the EcosistemaComponente in the database
        List<EcosistemaComponente> ecosistemaComponenteList = ecosistemaComponenteRepository.findAll();
        assertThat(ecosistemaComponenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEcosistemaComponente() throws Exception {
        int databaseSizeBeforeUpdate = ecosistemaComponenteRepository.findAll().size();
        ecosistemaComponente.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEcosistemaComponenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ecosistemaComponente))
            )
            .andExpect(status().isBadRequest());

        // Validate the EcosistemaComponente in the database
        List<EcosistemaComponente> ecosistemaComponenteList = ecosistemaComponenteRepository.findAll();
        assertThat(ecosistemaComponenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEcosistemaComponente() throws Exception {
        int databaseSizeBeforeUpdate = ecosistemaComponenteRepository.findAll().size();
        ecosistemaComponente.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEcosistemaComponenteMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ecosistemaComponente))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EcosistemaComponente in the database
        List<EcosistemaComponente> ecosistemaComponenteList = ecosistemaComponenteRepository.findAll();
        assertThat(ecosistemaComponenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEcosistemaComponente() throws Exception {
        // Initialize the database
        ecosistemaComponenteRepository.saveAndFlush(ecosistemaComponente);

        int databaseSizeBeforeDelete = ecosistemaComponenteRepository.findAll().size();

        // Delete the ecosistemaComponente
        restEcosistemaComponenteMockMvc
            .perform(delete(ENTITY_API_URL_ID, ecosistemaComponente.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EcosistemaComponente> ecosistemaComponenteList = ecosistemaComponenteRepository.findAll();
        assertThat(ecosistemaComponenteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
