package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.UsuarioEcosistema;
import com.mycompany.myapp.repository.UsuarioEcosistemaRepository;
import com.mycompany.myapp.service.UsuarioEcosistemaService;
import java.time.LocalDate;
import java.time.ZoneId;
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

/**
 * Integration tests for the {@link UsuarioEcosistemaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class UsuarioEcosistemaResourceIT {

    private static final LocalDate DEFAULT_FECHA_INGRESO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_INGRESO = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_BLOQUEADO = false;
    private static final Boolean UPDATED_BLOQUEADO = true;

    private static final String ENTITY_API_URL = "/api/usuario-ecosistemas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UsuarioEcosistemaRepository usuarioEcosistemaRepository;

    @Mock
    private UsuarioEcosistemaRepository usuarioEcosistemaRepositoryMock;

    @Mock
    private UsuarioEcosistemaService usuarioEcosistemaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUsuarioEcosistemaMockMvc;

    private UsuarioEcosistema usuarioEcosistema;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UsuarioEcosistema createEntity(EntityManager em) {
        UsuarioEcosistema usuarioEcosistema = new UsuarioEcosistema().fechaIngreso(DEFAULT_FECHA_INGRESO).bloqueado(DEFAULT_BLOQUEADO);
        return usuarioEcosistema;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UsuarioEcosistema createUpdatedEntity(EntityManager em) {
        UsuarioEcosistema usuarioEcosistema = new UsuarioEcosistema().fechaIngreso(UPDATED_FECHA_INGRESO).bloqueado(UPDATED_BLOQUEADO);
        return usuarioEcosistema;
    }

    @BeforeEach
    public void initTest() {
        usuarioEcosistema = createEntity(em);
    }

    @Test
    @Transactional
    void createUsuarioEcosistema() throws Exception {
        int databaseSizeBeforeCreate = usuarioEcosistemaRepository.findAll().size();
        // Create the UsuarioEcosistema
        restUsuarioEcosistemaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(usuarioEcosistema))
            )
            .andExpect(status().isCreated());

        // Validate the UsuarioEcosistema in the database
        List<UsuarioEcosistema> usuarioEcosistemaList = usuarioEcosistemaRepository.findAll();
        assertThat(usuarioEcosistemaList).hasSize(databaseSizeBeforeCreate + 1);
        UsuarioEcosistema testUsuarioEcosistema = usuarioEcosistemaList.get(usuarioEcosistemaList.size() - 1);
        assertThat(testUsuarioEcosistema.getFechaIngreso()).isEqualTo(DEFAULT_FECHA_INGRESO);
        assertThat(testUsuarioEcosistema.getBloqueado()).isEqualTo(DEFAULT_BLOQUEADO);
    }

    @Test
    @Transactional
    void createUsuarioEcosistemaWithExistingId() throws Exception {
        // Create the UsuarioEcosistema with an existing ID
        usuarioEcosistema.setId(1L);

        int databaseSizeBeforeCreate = usuarioEcosistemaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUsuarioEcosistemaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(usuarioEcosistema))
            )
            .andExpect(status().isBadRequest());

        // Validate the UsuarioEcosistema in the database
        List<UsuarioEcosistema> usuarioEcosistemaList = usuarioEcosistemaRepository.findAll();
        assertThat(usuarioEcosistemaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFechaIngresoIsRequired() throws Exception {
        int databaseSizeBeforeTest = usuarioEcosistemaRepository.findAll().size();
        // set the field null
        usuarioEcosistema.setFechaIngreso(null);

        // Create the UsuarioEcosistema, which fails.

        restUsuarioEcosistemaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(usuarioEcosistema))
            )
            .andExpect(status().isBadRequest());

        List<UsuarioEcosistema> usuarioEcosistemaList = usuarioEcosistemaRepository.findAll();
        assertThat(usuarioEcosistemaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllUsuarioEcosistemas() throws Exception {
        // Initialize the database
        usuarioEcosistemaRepository.saveAndFlush(usuarioEcosistema);

        // Get all the usuarioEcosistemaList
        restUsuarioEcosistemaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(usuarioEcosistema.getId().intValue())))
            .andExpect(jsonPath("$.[*].fechaIngreso").value(hasItem(DEFAULT_FECHA_INGRESO.toString())))
            .andExpect(jsonPath("$.[*].bloqueado").value(hasItem(DEFAULT_BLOQUEADO.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllUsuarioEcosistemasWithEagerRelationshipsIsEnabled() throws Exception {
        when(usuarioEcosistemaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restUsuarioEcosistemaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(usuarioEcosistemaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllUsuarioEcosistemasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(usuarioEcosistemaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restUsuarioEcosistemaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(usuarioEcosistemaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getUsuarioEcosistema() throws Exception {
        // Initialize the database
        usuarioEcosistemaRepository.saveAndFlush(usuarioEcosistema);

        // Get the usuarioEcosistema
        restUsuarioEcosistemaMockMvc
            .perform(get(ENTITY_API_URL_ID, usuarioEcosistema.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(usuarioEcosistema.getId().intValue()))
            .andExpect(jsonPath("$.fechaIngreso").value(DEFAULT_FECHA_INGRESO.toString()))
            .andExpect(jsonPath("$.bloqueado").value(DEFAULT_BLOQUEADO.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingUsuarioEcosistema() throws Exception {
        // Get the usuarioEcosistema
        restUsuarioEcosistemaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewUsuarioEcosistema() throws Exception {
        // Initialize the database
        usuarioEcosistemaRepository.saveAndFlush(usuarioEcosistema);

        int databaseSizeBeforeUpdate = usuarioEcosistemaRepository.findAll().size();

        // Update the usuarioEcosistema
        UsuarioEcosistema updatedUsuarioEcosistema = usuarioEcosistemaRepository.findById(usuarioEcosistema.getId()).get();
        // Disconnect from session so that the updates on updatedUsuarioEcosistema are not directly saved in db
        em.detach(updatedUsuarioEcosistema);
        updatedUsuarioEcosistema.fechaIngreso(UPDATED_FECHA_INGRESO).bloqueado(UPDATED_BLOQUEADO);

        restUsuarioEcosistemaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedUsuarioEcosistema.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedUsuarioEcosistema))
            )
            .andExpect(status().isOk());

        // Validate the UsuarioEcosistema in the database
        List<UsuarioEcosistema> usuarioEcosistemaList = usuarioEcosistemaRepository.findAll();
        assertThat(usuarioEcosistemaList).hasSize(databaseSizeBeforeUpdate);
        UsuarioEcosistema testUsuarioEcosistema = usuarioEcosistemaList.get(usuarioEcosistemaList.size() - 1);
        assertThat(testUsuarioEcosistema.getFechaIngreso()).isEqualTo(UPDATED_FECHA_INGRESO);
        assertThat(testUsuarioEcosistema.getBloqueado()).isEqualTo(UPDATED_BLOQUEADO);
    }

    @Test
    @Transactional
    void putNonExistingUsuarioEcosistema() throws Exception {
        int databaseSizeBeforeUpdate = usuarioEcosistemaRepository.findAll().size();
        usuarioEcosistema.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUsuarioEcosistemaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, usuarioEcosistema.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(usuarioEcosistema))
            )
            .andExpect(status().isBadRequest());

        // Validate the UsuarioEcosistema in the database
        List<UsuarioEcosistema> usuarioEcosistemaList = usuarioEcosistemaRepository.findAll();
        assertThat(usuarioEcosistemaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUsuarioEcosistema() throws Exception {
        int databaseSizeBeforeUpdate = usuarioEcosistemaRepository.findAll().size();
        usuarioEcosistema.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUsuarioEcosistemaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(usuarioEcosistema))
            )
            .andExpect(status().isBadRequest());

        // Validate the UsuarioEcosistema in the database
        List<UsuarioEcosistema> usuarioEcosistemaList = usuarioEcosistemaRepository.findAll();
        assertThat(usuarioEcosistemaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUsuarioEcosistema() throws Exception {
        int databaseSizeBeforeUpdate = usuarioEcosistemaRepository.findAll().size();
        usuarioEcosistema.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUsuarioEcosistemaMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(usuarioEcosistema))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UsuarioEcosistema in the database
        List<UsuarioEcosistema> usuarioEcosistemaList = usuarioEcosistemaRepository.findAll();
        assertThat(usuarioEcosistemaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUsuarioEcosistemaWithPatch() throws Exception {
        // Initialize the database
        usuarioEcosistemaRepository.saveAndFlush(usuarioEcosistema);

        int databaseSizeBeforeUpdate = usuarioEcosistemaRepository.findAll().size();

        // Update the usuarioEcosistema using partial update
        UsuarioEcosistema partialUpdatedUsuarioEcosistema = new UsuarioEcosistema();
        partialUpdatedUsuarioEcosistema.setId(usuarioEcosistema.getId());

        partialUpdatedUsuarioEcosistema.fechaIngreso(UPDATED_FECHA_INGRESO).bloqueado(UPDATED_BLOQUEADO);

        restUsuarioEcosistemaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUsuarioEcosistema.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUsuarioEcosistema))
            )
            .andExpect(status().isOk());

        // Validate the UsuarioEcosistema in the database
        List<UsuarioEcosistema> usuarioEcosistemaList = usuarioEcosistemaRepository.findAll();
        assertThat(usuarioEcosistemaList).hasSize(databaseSizeBeforeUpdate);
        UsuarioEcosistema testUsuarioEcosistema = usuarioEcosistemaList.get(usuarioEcosistemaList.size() - 1);
        assertThat(testUsuarioEcosistema.getFechaIngreso()).isEqualTo(UPDATED_FECHA_INGRESO);
        assertThat(testUsuarioEcosistema.getBloqueado()).isEqualTo(UPDATED_BLOQUEADO);
    }

    @Test
    @Transactional
    void fullUpdateUsuarioEcosistemaWithPatch() throws Exception {
        // Initialize the database
        usuarioEcosistemaRepository.saveAndFlush(usuarioEcosistema);

        int databaseSizeBeforeUpdate = usuarioEcosistemaRepository.findAll().size();

        // Update the usuarioEcosistema using partial update
        UsuarioEcosistema partialUpdatedUsuarioEcosistema = new UsuarioEcosistema();
        partialUpdatedUsuarioEcosistema.setId(usuarioEcosistema.getId());

        partialUpdatedUsuarioEcosistema.fechaIngreso(UPDATED_FECHA_INGRESO).bloqueado(UPDATED_BLOQUEADO);

        restUsuarioEcosistemaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUsuarioEcosistema.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUsuarioEcosistema))
            )
            .andExpect(status().isOk());

        // Validate the UsuarioEcosistema in the database
        List<UsuarioEcosistema> usuarioEcosistemaList = usuarioEcosistemaRepository.findAll();
        assertThat(usuarioEcosistemaList).hasSize(databaseSizeBeforeUpdate);
        UsuarioEcosistema testUsuarioEcosistema = usuarioEcosistemaList.get(usuarioEcosistemaList.size() - 1);
        assertThat(testUsuarioEcosistema.getFechaIngreso()).isEqualTo(UPDATED_FECHA_INGRESO);
        assertThat(testUsuarioEcosistema.getBloqueado()).isEqualTo(UPDATED_BLOQUEADO);
    }

    @Test
    @Transactional
    void patchNonExistingUsuarioEcosistema() throws Exception {
        int databaseSizeBeforeUpdate = usuarioEcosistemaRepository.findAll().size();
        usuarioEcosistema.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUsuarioEcosistemaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, usuarioEcosistema.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(usuarioEcosistema))
            )
            .andExpect(status().isBadRequest());

        // Validate the UsuarioEcosistema in the database
        List<UsuarioEcosistema> usuarioEcosistemaList = usuarioEcosistemaRepository.findAll();
        assertThat(usuarioEcosistemaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUsuarioEcosistema() throws Exception {
        int databaseSizeBeforeUpdate = usuarioEcosistemaRepository.findAll().size();
        usuarioEcosistema.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUsuarioEcosistemaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(usuarioEcosistema))
            )
            .andExpect(status().isBadRequest());

        // Validate the UsuarioEcosistema in the database
        List<UsuarioEcosistema> usuarioEcosistemaList = usuarioEcosistemaRepository.findAll();
        assertThat(usuarioEcosistemaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUsuarioEcosistema() throws Exception {
        int databaseSizeBeforeUpdate = usuarioEcosistemaRepository.findAll().size();
        usuarioEcosistema.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUsuarioEcosistemaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(usuarioEcosistema))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UsuarioEcosistema in the database
        List<UsuarioEcosistema> usuarioEcosistemaList = usuarioEcosistemaRepository.findAll();
        assertThat(usuarioEcosistemaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUsuarioEcosistema() throws Exception {
        // Initialize the database
        usuarioEcosistemaRepository.saveAndFlush(usuarioEcosistema);

        int databaseSizeBeforeDelete = usuarioEcosistemaRepository.findAll().size();

        // Delete the usuarioEcosistema
        restUsuarioEcosistemaMockMvc
            .perform(delete(ENTITY_API_URL_ID, usuarioEcosistema.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UsuarioEcosistema> usuarioEcosistemaList = usuarioEcosistemaRepository.findAll();
        assertThat(usuarioEcosistemaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
