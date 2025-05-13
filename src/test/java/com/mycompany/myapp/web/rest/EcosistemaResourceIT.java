package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Ecosistema;
import com.mycompany.myapp.repository.EcosistemaRepository;
import com.mycompany.myapp.service.EcosistemaService;
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
 * Integration tests for the {@link EcosistemaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class EcosistemaResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_TEMATICA = "AAAAAAAAAA";
    private static final String UPDATED_TEMATICA = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVO = false;
    private static final Boolean UPDATED_ACTIVO = true;

    private static final byte[] DEFAULT_LOGO_URL = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_LOGO_URL = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_LOGO_URL_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_LOGO_URL_CONTENT_TYPE = "image/png";

    private static final Integer DEFAULT_RANKING = 1;
    private static final Integer UPDATED_RANKING = 2;

    private static final Integer DEFAULT_USUARIOS_CANT = 1;
    private static final Integer UPDATED_USUARIOS_CANT = 2;

    private static final Integer DEFAULT_RETOS_CANT = 1;
    private static final Integer UPDATED_RETOS_CANT = 2;

    private static final Integer DEFAULT_IDEAS_CANT = 1;
    private static final Integer UPDATED_IDEAS_CANT = 2;

    private static final String ENTITY_API_URL = "/api/ecosistemas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EcosistemaRepository ecosistemaRepository;

    @Mock
    private EcosistemaRepository ecosistemaRepositoryMock;

    @Mock
    private EcosistemaService ecosistemaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEcosistemaMockMvc;

    private Ecosistema ecosistema;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ecosistema createEntity(EntityManager em) {
        Ecosistema ecosistema = new Ecosistema()
            .nombre(DEFAULT_NOMBRE)
            .tematica(DEFAULT_TEMATICA)
            .activo(DEFAULT_ACTIVO)
            .logoUrl(DEFAULT_LOGO_URL)
            .logoUrlContentType(DEFAULT_LOGO_URL_CONTENT_TYPE)
            .ranking(DEFAULT_RANKING)
            .usuariosCant(DEFAULT_USUARIOS_CANT)
            .retosCant(DEFAULT_RETOS_CANT)
            .ideasCant(DEFAULT_IDEAS_CANT);
        return ecosistema;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ecosistema createUpdatedEntity(EntityManager em) {
        Ecosistema ecosistema = new Ecosistema()
            .nombre(UPDATED_NOMBRE)
            .tematica(UPDATED_TEMATICA)
            .activo(UPDATED_ACTIVO)
            .logoUrl(UPDATED_LOGO_URL)
            .logoUrlContentType(UPDATED_LOGO_URL_CONTENT_TYPE)
            .ranking(UPDATED_RANKING)
            .usuariosCant(UPDATED_USUARIOS_CANT)
            .retosCant(UPDATED_RETOS_CANT)
            .ideasCant(UPDATED_IDEAS_CANT);
        return ecosistema;
    }

    @BeforeEach
    public void initTest() {
        ecosistema = createEntity(em);
    }

    @Test
    @Transactional
    void createEcosistema() throws Exception {
        int databaseSizeBeforeCreate = ecosistemaRepository.findAll().size();
        // Create the Ecosistema
        restEcosistemaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ecosistema)))
            .andExpect(status().isCreated());

        // Validate the Ecosistema in the database
        List<Ecosistema> ecosistemaList = ecosistemaRepository.findAll();
        assertThat(ecosistemaList).hasSize(databaseSizeBeforeCreate + 1);
        Ecosistema testEcosistema = ecosistemaList.get(ecosistemaList.size() - 1);
        assertThat(testEcosistema.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testEcosistema.getTematica()).isEqualTo(DEFAULT_TEMATICA);
        assertThat(testEcosistema.getActivo()).isEqualTo(DEFAULT_ACTIVO);
        assertThat(testEcosistema.getLogoUrl()).isEqualTo(DEFAULT_LOGO_URL);
        assertThat(testEcosistema.getLogoUrlContentType()).isEqualTo(DEFAULT_LOGO_URL_CONTENT_TYPE);
        assertThat(testEcosistema.getRanking()).isEqualTo(DEFAULT_RANKING);
        assertThat(testEcosistema.getUsuariosCant()).isEqualTo(DEFAULT_USUARIOS_CANT);
        assertThat(testEcosistema.getRetosCant()).isEqualTo(DEFAULT_RETOS_CANT);
        assertThat(testEcosistema.getIdeasCant()).isEqualTo(DEFAULT_IDEAS_CANT);
    }

    @Test
    @Transactional
    void createEcosistemaWithExistingId() throws Exception {
        // Create the Ecosistema with an existing ID
        ecosistema.setId(1L);

        int databaseSizeBeforeCreate = ecosistemaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEcosistemaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ecosistema)))
            .andExpect(status().isBadRequest());

        // Validate the Ecosistema in the database
        List<Ecosistema> ecosistemaList = ecosistemaRepository.findAll();
        assertThat(ecosistemaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = ecosistemaRepository.findAll().size();
        // set the field null
        ecosistema.setNombre(null);

        // Create the Ecosistema, which fails.

        restEcosistemaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ecosistema)))
            .andExpect(status().isBadRequest());

        List<Ecosistema> ecosistemaList = ecosistemaRepository.findAll();
        assertThat(ecosistemaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTematicaIsRequired() throws Exception {
        int databaseSizeBeforeTest = ecosistemaRepository.findAll().size();
        // set the field null
        ecosistema.setTematica(null);

        // Create the Ecosistema, which fails.

        restEcosistemaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ecosistema)))
            .andExpect(status().isBadRequest());

        List<Ecosistema> ecosistemaList = ecosistemaRepository.findAll();
        assertThat(ecosistemaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEcosistemas() throws Exception {
        // Initialize the database
        ecosistemaRepository.saveAndFlush(ecosistema);

        // Get all the ecosistemaList
        restEcosistemaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ecosistema.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].tematica").value(hasItem(DEFAULT_TEMATICA)))
            .andExpect(jsonPath("$.[*].activo").value(hasItem(DEFAULT_ACTIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].logoUrlContentType").value(hasItem(DEFAULT_LOGO_URL_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].logoUrl").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOGO_URL))))
            .andExpect(jsonPath("$.[*].ranking").value(hasItem(DEFAULT_RANKING)))
            .andExpect(jsonPath("$.[*].usuariosCant").value(hasItem(DEFAULT_USUARIOS_CANT)))
            .andExpect(jsonPath("$.[*].retosCant").value(hasItem(DEFAULT_RETOS_CANT)))
            .andExpect(jsonPath("$.[*].ideasCant").value(hasItem(DEFAULT_IDEAS_CANT)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEcosistemasWithEagerRelationshipsIsEnabled() throws Exception {
        when(ecosistemaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEcosistemaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(ecosistemaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEcosistemasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(ecosistemaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEcosistemaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(ecosistemaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getEcosistema() throws Exception {
        // Initialize the database
        ecosistemaRepository.saveAndFlush(ecosistema);

        // Get the ecosistema
        restEcosistemaMockMvc
            .perform(get(ENTITY_API_URL_ID, ecosistema.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ecosistema.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.tematica").value(DEFAULT_TEMATICA))
            .andExpect(jsonPath("$.activo").value(DEFAULT_ACTIVO.booleanValue()))
            .andExpect(jsonPath("$.logoUrlContentType").value(DEFAULT_LOGO_URL_CONTENT_TYPE))
            .andExpect(jsonPath("$.logoUrl").value(Base64Utils.encodeToString(DEFAULT_LOGO_URL)))
            .andExpect(jsonPath("$.ranking").value(DEFAULT_RANKING))
            .andExpect(jsonPath("$.usuariosCant").value(DEFAULT_USUARIOS_CANT))
            .andExpect(jsonPath("$.retosCant").value(DEFAULT_RETOS_CANT))
            .andExpect(jsonPath("$.ideasCant").value(DEFAULT_IDEAS_CANT));
    }

    @Test
    @Transactional
    void getNonExistingEcosistema() throws Exception {
        // Get the ecosistema
        restEcosistemaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEcosistema() throws Exception {
        // Initialize the database
        ecosistemaRepository.saveAndFlush(ecosistema);

        int databaseSizeBeforeUpdate = ecosistemaRepository.findAll().size();

        // Update the ecosistema
        Ecosistema updatedEcosistema = ecosistemaRepository.findById(ecosistema.getId()).get();
        // Disconnect from session so that the updates on updatedEcosistema are not directly saved in db
        em.detach(updatedEcosistema);
        updatedEcosistema
            .nombre(UPDATED_NOMBRE)
            .tematica(UPDATED_TEMATICA)
            .activo(UPDATED_ACTIVO)
            .logoUrl(UPDATED_LOGO_URL)
            .logoUrlContentType(UPDATED_LOGO_URL_CONTENT_TYPE)
            .ranking(UPDATED_RANKING)
            .usuariosCant(UPDATED_USUARIOS_CANT)
            .retosCant(UPDATED_RETOS_CANT)
            .ideasCant(UPDATED_IDEAS_CANT);

        restEcosistemaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEcosistema.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEcosistema))
            )
            .andExpect(status().isOk());

        // Validate the Ecosistema in the database
        List<Ecosistema> ecosistemaList = ecosistemaRepository.findAll();
        assertThat(ecosistemaList).hasSize(databaseSizeBeforeUpdate);
        Ecosistema testEcosistema = ecosistemaList.get(ecosistemaList.size() - 1);
        assertThat(testEcosistema.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testEcosistema.getTematica()).isEqualTo(UPDATED_TEMATICA);
        assertThat(testEcosistema.getActivo()).isEqualTo(UPDATED_ACTIVO);
        assertThat(testEcosistema.getLogoUrl()).isEqualTo(UPDATED_LOGO_URL);
        assertThat(testEcosistema.getLogoUrlContentType()).isEqualTo(UPDATED_LOGO_URL_CONTENT_TYPE);
        assertThat(testEcosistema.getRanking()).isEqualTo(UPDATED_RANKING);
        assertThat(testEcosistema.getUsuariosCant()).isEqualTo(UPDATED_USUARIOS_CANT);
        assertThat(testEcosistema.getRetosCant()).isEqualTo(UPDATED_RETOS_CANT);
        assertThat(testEcosistema.getIdeasCant()).isEqualTo(UPDATED_IDEAS_CANT);
    }

    @Test
    @Transactional
    void putNonExistingEcosistema() throws Exception {
        int databaseSizeBeforeUpdate = ecosistemaRepository.findAll().size();
        ecosistema.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEcosistemaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ecosistema.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ecosistema))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ecosistema in the database
        List<Ecosistema> ecosistemaList = ecosistemaRepository.findAll();
        assertThat(ecosistemaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEcosistema() throws Exception {
        int databaseSizeBeforeUpdate = ecosistemaRepository.findAll().size();
        ecosistema.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEcosistemaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ecosistema))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ecosistema in the database
        List<Ecosistema> ecosistemaList = ecosistemaRepository.findAll();
        assertThat(ecosistemaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEcosistema() throws Exception {
        int databaseSizeBeforeUpdate = ecosistemaRepository.findAll().size();
        ecosistema.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEcosistemaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ecosistema)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ecosistema in the database
        List<Ecosistema> ecosistemaList = ecosistemaRepository.findAll();
        assertThat(ecosistemaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEcosistemaWithPatch() throws Exception {
        // Initialize the database
        ecosistemaRepository.saveAndFlush(ecosistema);

        int databaseSizeBeforeUpdate = ecosistemaRepository.findAll().size();

        // Update the ecosistema using partial update
        Ecosistema partialUpdatedEcosistema = new Ecosistema();
        partialUpdatedEcosistema.setId(ecosistema.getId());

        partialUpdatedEcosistema.nombre(UPDATED_NOMBRE).activo(UPDATED_ACTIVO).ranking(UPDATED_RANKING);

        restEcosistemaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEcosistema.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEcosistema))
            )
            .andExpect(status().isOk());

        // Validate the Ecosistema in the database
        List<Ecosistema> ecosistemaList = ecosistemaRepository.findAll();
        assertThat(ecosistemaList).hasSize(databaseSizeBeforeUpdate);
        Ecosistema testEcosistema = ecosistemaList.get(ecosistemaList.size() - 1);
        assertThat(testEcosistema.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testEcosistema.getTematica()).isEqualTo(DEFAULT_TEMATICA);
        assertThat(testEcosistema.getActivo()).isEqualTo(UPDATED_ACTIVO);
        assertThat(testEcosistema.getLogoUrl()).isEqualTo(DEFAULT_LOGO_URL);
        assertThat(testEcosistema.getLogoUrlContentType()).isEqualTo(DEFAULT_LOGO_URL_CONTENT_TYPE);
        assertThat(testEcosistema.getRanking()).isEqualTo(UPDATED_RANKING);
        assertThat(testEcosistema.getUsuariosCant()).isEqualTo(DEFAULT_USUARIOS_CANT);
        assertThat(testEcosistema.getRetosCant()).isEqualTo(DEFAULT_RETOS_CANT);
        assertThat(testEcosistema.getIdeasCant()).isEqualTo(DEFAULT_IDEAS_CANT);
    }

    @Test
    @Transactional
    void fullUpdateEcosistemaWithPatch() throws Exception {
        // Initialize the database
        ecosistemaRepository.saveAndFlush(ecosistema);

        int databaseSizeBeforeUpdate = ecosistemaRepository.findAll().size();

        // Update the ecosistema using partial update
        Ecosistema partialUpdatedEcosistema = new Ecosistema();
        partialUpdatedEcosistema.setId(ecosistema.getId());

        partialUpdatedEcosistema
            .nombre(UPDATED_NOMBRE)
            .tematica(UPDATED_TEMATICA)
            .activo(UPDATED_ACTIVO)
            .logoUrl(UPDATED_LOGO_URL)
            .logoUrlContentType(UPDATED_LOGO_URL_CONTENT_TYPE)
            .ranking(UPDATED_RANKING)
            .usuariosCant(UPDATED_USUARIOS_CANT)
            .retosCant(UPDATED_RETOS_CANT)
            .ideasCant(UPDATED_IDEAS_CANT);

        restEcosistemaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEcosistema.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEcosistema))
            )
            .andExpect(status().isOk());

        // Validate the Ecosistema in the database
        List<Ecosistema> ecosistemaList = ecosistemaRepository.findAll();
        assertThat(ecosistemaList).hasSize(databaseSizeBeforeUpdate);
        Ecosistema testEcosistema = ecosistemaList.get(ecosistemaList.size() - 1);
        assertThat(testEcosistema.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testEcosistema.getTematica()).isEqualTo(UPDATED_TEMATICA);
        assertThat(testEcosistema.getActivo()).isEqualTo(UPDATED_ACTIVO);
        assertThat(testEcosistema.getLogoUrl()).isEqualTo(UPDATED_LOGO_URL);
        assertThat(testEcosistema.getLogoUrlContentType()).isEqualTo(UPDATED_LOGO_URL_CONTENT_TYPE);
        assertThat(testEcosistema.getRanking()).isEqualTo(UPDATED_RANKING);
        assertThat(testEcosistema.getUsuariosCant()).isEqualTo(UPDATED_USUARIOS_CANT);
        assertThat(testEcosistema.getRetosCant()).isEqualTo(UPDATED_RETOS_CANT);
        assertThat(testEcosistema.getIdeasCant()).isEqualTo(UPDATED_IDEAS_CANT);
    }

    @Test
    @Transactional
    void patchNonExistingEcosistema() throws Exception {
        int databaseSizeBeforeUpdate = ecosistemaRepository.findAll().size();
        ecosistema.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEcosistemaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ecosistema.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ecosistema))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ecosistema in the database
        List<Ecosistema> ecosistemaList = ecosistemaRepository.findAll();
        assertThat(ecosistemaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEcosistema() throws Exception {
        int databaseSizeBeforeUpdate = ecosistemaRepository.findAll().size();
        ecosistema.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEcosistemaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ecosistema))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ecosistema in the database
        List<Ecosistema> ecosistemaList = ecosistemaRepository.findAll();
        assertThat(ecosistemaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEcosistema() throws Exception {
        int databaseSizeBeforeUpdate = ecosistemaRepository.findAll().size();
        ecosistema.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEcosistemaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(ecosistema))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ecosistema in the database
        List<Ecosistema> ecosistemaList = ecosistemaRepository.findAll();
        assertThat(ecosistemaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEcosistema() throws Exception {
        // Initialize the database
        ecosistemaRepository.saveAndFlush(ecosistema);

        int databaseSizeBeforeDelete = ecosistemaRepository.findAll().size();

        // Delete the ecosistema
        restEcosistemaMockMvc
            .perform(delete(ENTITY_API_URL_ID, ecosistema.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Ecosistema> ecosistemaList = ecosistemaRepository.findAll();
        assertThat(ecosistemaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
