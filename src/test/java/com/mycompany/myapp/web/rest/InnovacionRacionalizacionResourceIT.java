package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.InnovacionRacionalizacion;
import com.mycompany.myapp.repository.InnovacionRacionalizacionRepository;
import com.mycompany.myapp.service.InnovacionRacionalizacionService;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link InnovacionRacionalizacionResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class InnovacionRacionalizacionResourceIT {

    private static final String DEFAULT_TEMATICA = "AAAAAAAAAA";
    private static final String UPDATED_TEMATICA = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_VP = 1;
    private static final Integer UPDATED_VP = 2;

    private static final String DEFAULT_AUTORES = "AAAAAAAAAA";
    private static final String UPDATED_AUTORES = "BBBBBBBBBB";

    private static final Long DEFAULT_NUMERO_IDENTIDAD = 1L;
    private static final Long UPDATED_NUMERO_IDENTIDAD = 2L;

    private static final String DEFAULT_OBSERVACION = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_APROBADA = false;
    private static final Boolean UPDATED_APROBADA = true;

    private static final Boolean DEFAULT_PUBLICO = false;
    private static final Boolean UPDATED_PUBLICO = true;

    private static final String ENTITY_API_URL = "/api/innovacion-racionalizacions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private InnovacionRacionalizacionRepository innovacionRacionalizacionRepository;

    @Mock
    private InnovacionRacionalizacionRepository innovacionRacionalizacionRepositoryMock;

    @Mock
    private InnovacionRacionalizacionService innovacionRacionalizacionServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInnovacionRacionalizacionMockMvc;

    private InnovacionRacionalizacion innovacionRacionalizacion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InnovacionRacionalizacion createEntity(EntityManager em) {
        InnovacionRacionalizacion innovacionRacionalizacion = new InnovacionRacionalizacion()
            .tematica(DEFAULT_TEMATICA)
            .fecha(DEFAULT_FECHA)
            .vp(DEFAULT_VP)
            .autores(DEFAULT_AUTORES)
            .numeroIdentidad(DEFAULT_NUMERO_IDENTIDAD)
            .observacion(DEFAULT_OBSERVACION)
            .aprobada(DEFAULT_APROBADA)
            .publico(DEFAULT_PUBLICO);
        return innovacionRacionalizacion;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InnovacionRacionalizacion createUpdatedEntity(EntityManager em) {
        InnovacionRacionalizacion innovacionRacionalizacion = new InnovacionRacionalizacion()
            .tematica(UPDATED_TEMATICA)
            .fecha(UPDATED_FECHA)
            .vp(UPDATED_VP)
            .autores(UPDATED_AUTORES)
            .numeroIdentidad(UPDATED_NUMERO_IDENTIDAD)
            .observacion(UPDATED_OBSERVACION)
            .aprobada(UPDATED_APROBADA)
            .publico(UPDATED_PUBLICO);
        return innovacionRacionalizacion;
    }

    @BeforeEach
    public void initTest() {
        innovacionRacionalizacion = createEntity(em);
    }

    @Test
    @Transactional
    void createInnovacionRacionalizacion() throws Exception {
        int databaseSizeBeforeCreate = innovacionRacionalizacionRepository.findAll().size();
        // Create the InnovacionRacionalizacion
        restInnovacionRacionalizacionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(innovacionRacionalizacion))
            )
            .andExpect(status().isCreated());

        // Validate the InnovacionRacionalizacion in the database
        List<InnovacionRacionalizacion> innovacionRacionalizacionList = innovacionRacionalizacionRepository.findAll();
        assertThat(innovacionRacionalizacionList).hasSize(databaseSizeBeforeCreate + 1);
        InnovacionRacionalizacion testInnovacionRacionalizacion = innovacionRacionalizacionList.get(
            innovacionRacionalizacionList.size() - 1
        );
        assertThat(testInnovacionRacionalizacion.getTematica()).isEqualTo(DEFAULT_TEMATICA);
        assertThat(testInnovacionRacionalizacion.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testInnovacionRacionalizacion.getVp()).isEqualTo(DEFAULT_VP);
        assertThat(testInnovacionRacionalizacion.getAutores()).isEqualTo(DEFAULT_AUTORES);
        assertThat(testInnovacionRacionalizacion.getNumeroIdentidad()).isEqualTo(DEFAULT_NUMERO_IDENTIDAD);
        assertThat(testInnovacionRacionalizacion.getObservacion()).isEqualTo(DEFAULT_OBSERVACION);
        assertThat(testInnovacionRacionalizacion.getAprobada()).isEqualTo(DEFAULT_APROBADA);
        assertThat(testInnovacionRacionalizacion.getPublico()).isEqualTo(DEFAULT_PUBLICO);
    }

    @Test
    @Transactional
    void createInnovacionRacionalizacionWithExistingId() throws Exception {
        // Create the InnovacionRacionalizacion with an existing ID
        innovacionRacionalizacion.setId(1L);

        int databaseSizeBeforeCreate = innovacionRacionalizacionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInnovacionRacionalizacionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(innovacionRacionalizacion))
            )
            .andExpect(status().isBadRequest());

        // Validate the InnovacionRacionalizacion in the database
        List<InnovacionRacionalizacion> innovacionRacionalizacionList = innovacionRacionalizacionRepository.findAll();
        assertThat(innovacionRacionalizacionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTematicaIsRequired() throws Exception {
        int databaseSizeBeforeTest = innovacionRacionalizacionRepository.findAll().size();
        // set the field null
        innovacionRacionalizacion.setTematica(null);

        // Create the InnovacionRacionalizacion, which fails.

        restInnovacionRacionalizacionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(innovacionRacionalizacion))
            )
            .andExpect(status().isBadRequest());

        List<InnovacionRacionalizacion> innovacionRacionalizacionList = innovacionRacionalizacionRepository.findAll();
        assertThat(innovacionRacionalizacionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = innovacionRacionalizacionRepository.findAll().size();
        // set the field null
        innovacionRacionalizacion.setFecha(null);

        // Create the InnovacionRacionalizacion, which fails.

        restInnovacionRacionalizacionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(innovacionRacionalizacion))
            )
            .andExpect(status().isBadRequest());

        List<InnovacionRacionalizacion> innovacionRacionalizacionList = innovacionRacionalizacionRepository.findAll();
        assertThat(innovacionRacionalizacionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVpIsRequired() throws Exception {
        int databaseSizeBeforeTest = innovacionRacionalizacionRepository.findAll().size();
        // set the field null
        innovacionRacionalizacion.setVp(null);

        // Create the InnovacionRacionalizacion, which fails.

        restInnovacionRacionalizacionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(innovacionRacionalizacion))
            )
            .andExpect(status().isBadRequest());

        List<InnovacionRacionalizacion> innovacionRacionalizacionList = innovacionRacionalizacionRepository.findAll();
        assertThat(innovacionRacionalizacionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAutoresIsRequired() throws Exception {
        int databaseSizeBeforeTest = innovacionRacionalizacionRepository.findAll().size();
        // set the field null
        innovacionRacionalizacion.setAutores(null);

        // Create the InnovacionRacionalizacion, which fails.

        restInnovacionRacionalizacionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(innovacionRacionalizacion))
            )
            .andExpect(status().isBadRequest());

        List<InnovacionRacionalizacion> innovacionRacionalizacionList = innovacionRacionalizacionRepository.findAll();
        assertThat(innovacionRacionalizacionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNumeroIdentidadIsRequired() throws Exception {
        int databaseSizeBeforeTest = innovacionRacionalizacionRepository.findAll().size();
        // set the field null
        innovacionRacionalizacion.setNumeroIdentidad(null);

        // Create the InnovacionRacionalizacion, which fails.

        restInnovacionRacionalizacionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(innovacionRacionalizacion))
            )
            .andExpect(status().isBadRequest());

        List<InnovacionRacionalizacion> innovacionRacionalizacionList = innovacionRacionalizacionRepository.findAll();
        assertThat(innovacionRacionalizacionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPublicoIsRequired() throws Exception {
        int databaseSizeBeforeTest = innovacionRacionalizacionRepository.findAll().size();
        // set the field null
        innovacionRacionalizacion.setPublico(null);

        // Create the InnovacionRacionalizacion, which fails.

        restInnovacionRacionalizacionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(innovacionRacionalizacion))
            )
            .andExpect(status().isBadRequest());

        List<InnovacionRacionalizacion> innovacionRacionalizacionList = innovacionRacionalizacionRepository.findAll();
        assertThat(innovacionRacionalizacionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllInnovacionRacionalizacions() throws Exception {
        // Initialize the database
        innovacionRacionalizacionRepository.saveAndFlush(innovacionRacionalizacion);

        // Get all the innovacionRacionalizacionList
        restInnovacionRacionalizacionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(innovacionRacionalizacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].tematica").value(hasItem(DEFAULT_TEMATICA)))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].vp").value(hasItem(DEFAULT_VP)))
            .andExpect(jsonPath("$.[*].autores").value(hasItem(DEFAULT_AUTORES)))
            .andExpect(jsonPath("$.[*].numeroIdentidad").value(hasItem(DEFAULT_NUMERO_IDENTIDAD.intValue())))
            .andExpect(jsonPath("$.[*].observacion").value(hasItem(DEFAULT_OBSERVACION.toString())))
            .andExpect(jsonPath("$.[*].aprobada").value(hasItem(DEFAULT_APROBADA.booleanValue())))
            .andExpect(jsonPath("$.[*].publico").value(hasItem(DEFAULT_PUBLICO.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllInnovacionRacionalizacionsWithEagerRelationshipsIsEnabled() throws Exception {
        when(innovacionRacionalizacionServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restInnovacionRacionalizacionMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(innovacionRacionalizacionServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllInnovacionRacionalizacionsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(innovacionRacionalizacionServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restInnovacionRacionalizacionMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(innovacionRacionalizacionServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getInnovacionRacionalizacion() throws Exception {
        // Initialize the database
        innovacionRacionalizacionRepository.saveAndFlush(innovacionRacionalizacion);

        // Get the innovacionRacionalizacion
        restInnovacionRacionalizacionMockMvc
            .perform(get(ENTITY_API_URL_ID, innovacionRacionalizacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(innovacionRacionalizacion.getId().intValue()))
            .andExpect(jsonPath("$.tematica").value(DEFAULT_TEMATICA))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.vp").value(DEFAULT_VP))
            .andExpect(jsonPath("$.autores").value(DEFAULT_AUTORES))
            .andExpect(jsonPath("$.numeroIdentidad").value(DEFAULT_NUMERO_IDENTIDAD.intValue()))
            .andExpect(jsonPath("$.observacion").value(DEFAULT_OBSERVACION.toString()))
            .andExpect(jsonPath("$.aprobada").value(DEFAULT_APROBADA.booleanValue()))
            .andExpect(jsonPath("$.publico").value(DEFAULT_PUBLICO.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingInnovacionRacionalizacion() throws Exception {
        // Get the innovacionRacionalizacion
        restInnovacionRacionalizacionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewInnovacionRacionalizacion() throws Exception {
        // Initialize the database
        innovacionRacionalizacionRepository.saveAndFlush(innovacionRacionalizacion);

        int databaseSizeBeforeUpdate = innovacionRacionalizacionRepository.findAll().size();

        // Update the innovacionRacionalizacion
        InnovacionRacionalizacion updatedInnovacionRacionalizacion = innovacionRacionalizacionRepository
            .findById(innovacionRacionalizacion.getId())
            .get();
        // Disconnect from session so that the updates on updatedInnovacionRacionalizacion are not directly saved in db
        em.detach(updatedInnovacionRacionalizacion);
        updatedInnovacionRacionalizacion
            .tematica(UPDATED_TEMATICA)
            .fecha(UPDATED_FECHA)
            .vp(UPDATED_VP)
            .autores(UPDATED_AUTORES)
            .numeroIdentidad(UPDATED_NUMERO_IDENTIDAD)
            .observacion(UPDATED_OBSERVACION)
            .aprobada(UPDATED_APROBADA)
            .publico(UPDATED_PUBLICO);

        restInnovacionRacionalizacionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedInnovacionRacionalizacion.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedInnovacionRacionalizacion))
            )
            .andExpect(status().isOk());

        // Validate the InnovacionRacionalizacion in the database
        List<InnovacionRacionalizacion> innovacionRacionalizacionList = innovacionRacionalizacionRepository.findAll();
        assertThat(innovacionRacionalizacionList).hasSize(databaseSizeBeforeUpdate);
        InnovacionRacionalizacion testInnovacionRacionalizacion = innovacionRacionalizacionList.get(
            innovacionRacionalizacionList.size() - 1
        );
        assertThat(testInnovacionRacionalizacion.getTematica()).isEqualTo(UPDATED_TEMATICA);
        assertThat(testInnovacionRacionalizacion.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testInnovacionRacionalizacion.getVp()).isEqualTo(UPDATED_VP);
        assertThat(testInnovacionRacionalizacion.getAutores()).isEqualTo(UPDATED_AUTORES);
        assertThat(testInnovacionRacionalizacion.getNumeroIdentidad()).isEqualTo(UPDATED_NUMERO_IDENTIDAD);
        assertThat(testInnovacionRacionalizacion.getObservacion()).isEqualTo(UPDATED_OBSERVACION);
        assertThat(testInnovacionRacionalizacion.getAprobada()).isEqualTo(UPDATED_APROBADA);
        assertThat(testInnovacionRacionalizacion.getPublico()).isEqualTo(UPDATED_PUBLICO);
    }

    @Test
    @Transactional
    void putNonExistingInnovacionRacionalizacion() throws Exception {
        int databaseSizeBeforeUpdate = innovacionRacionalizacionRepository.findAll().size();
        innovacionRacionalizacion.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInnovacionRacionalizacionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, innovacionRacionalizacion.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(innovacionRacionalizacion))
            )
            .andExpect(status().isBadRequest());

        // Validate the InnovacionRacionalizacion in the database
        List<InnovacionRacionalizacion> innovacionRacionalizacionList = innovacionRacionalizacionRepository.findAll();
        assertThat(innovacionRacionalizacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInnovacionRacionalizacion() throws Exception {
        int databaseSizeBeforeUpdate = innovacionRacionalizacionRepository.findAll().size();
        innovacionRacionalizacion.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInnovacionRacionalizacionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(innovacionRacionalizacion))
            )
            .andExpect(status().isBadRequest());

        // Validate the InnovacionRacionalizacion in the database
        List<InnovacionRacionalizacion> innovacionRacionalizacionList = innovacionRacionalizacionRepository.findAll();
        assertThat(innovacionRacionalizacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInnovacionRacionalizacion() throws Exception {
        int databaseSizeBeforeUpdate = innovacionRacionalizacionRepository.findAll().size();
        innovacionRacionalizacion.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInnovacionRacionalizacionMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(innovacionRacionalizacion))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the InnovacionRacionalizacion in the database
        List<InnovacionRacionalizacion> innovacionRacionalizacionList = innovacionRacionalizacionRepository.findAll();
        assertThat(innovacionRacionalizacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInnovacionRacionalizacionWithPatch() throws Exception {
        // Initialize the database
        innovacionRacionalizacionRepository.saveAndFlush(innovacionRacionalizacion);

        int databaseSizeBeforeUpdate = innovacionRacionalizacionRepository.findAll().size();

        // Update the innovacionRacionalizacion using partial update
        InnovacionRacionalizacion partialUpdatedInnovacionRacionalizacion = new InnovacionRacionalizacion();
        partialUpdatedInnovacionRacionalizacion.setId(innovacionRacionalizacion.getId());

        partialUpdatedInnovacionRacionalizacion
            .tematica(UPDATED_TEMATICA)
            .fecha(UPDATED_FECHA)
            .vp(UPDATED_VP)
            .autores(UPDATED_AUTORES)
            .observacion(UPDATED_OBSERVACION);

        restInnovacionRacionalizacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInnovacionRacionalizacion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInnovacionRacionalizacion))
            )
            .andExpect(status().isOk());

        // Validate the InnovacionRacionalizacion in the database
        List<InnovacionRacionalizacion> innovacionRacionalizacionList = innovacionRacionalizacionRepository.findAll();
        assertThat(innovacionRacionalizacionList).hasSize(databaseSizeBeforeUpdate);
        InnovacionRacionalizacion testInnovacionRacionalizacion = innovacionRacionalizacionList.get(
            innovacionRacionalizacionList.size() - 1
        );
        assertThat(testInnovacionRacionalizacion.getTematica()).isEqualTo(UPDATED_TEMATICA);
        assertThat(testInnovacionRacionalizacion.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testInnovacionRacionalizacion.getVp()).isEqualTo(UPDATED_VP);
        assertThat(testInnovacionRacionalizacion.getAutores()).isEqualTo(UPDATED_AUTORES);
        assertThat(testInnovacionRacionalizacion.getNumeroIdentidad()).isEqualTo(DEFAULT_NUMERO_IDENTIDAD);
        assertThat(testInnovacionRacionalizacion.getObservacion()).isEqualTo(UPDATED_OBSERVACION);
        assertThat(testInnovacionRacionalizacion.getAprobada()).isEqualTo(DEFAULT_APROBADA);
        assertThat(testInnovacionRacionalizacion.getPublico()).isEqualTo(DEFAULT_PUBLICO);
    }

    @Test
    @Transactional
    void fullUpdateInnovacionRacionalizacionWithPatch() throws Exception {
        // Initialize the database
        innovacionRacionalizacionRepository.saveAndFlush(innovacionRacionalizacion);

        int databaseSizeBeforeUpdate = innovacionRacionalizacionRepository.findAll().size();

        // Update the innovacionRacionalizacion using partial update
        InnovacionRacionalizacion partialUpdatedInnovacionRacionalizacion = new InnovacionRacionalizacion();
        partialUpdatedInnovacionRacionalizacion.setId(innovacionRacionalizacion.getId());

        partialUpdatedInnovacionRacionalizacion
            .tematica(UPDATED_TEMATICA)
            .fecha(UPDATED_FECHA)
            .vp(UPDATED_VP)
            .autores(UPDATED_AUTORES)
            .numeroIdentidad(UPDATED_NUMERO_IDENTIDAD)
            .observacion(UPDATED_OBSERVACION)
            .aprobada(UPDATED_APROBADA)
            .publico(UPDATED_PUBLICO);

        restInnovacionRacionalizacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInnovacionRacionalizacion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInnovacionRacionalizacion))
            )
            .andExpect(status().isOk());

        // Validate the InnovacionRacionalizacion in the database
        List<InnovacionRacionalizacion> innovacionRacionalizacionList = innovacionRacionalizacionRepository.findAll();
        assertThat(innovacionRacionalizacionList).hasSize(databaseSizeBeforeUpdate);
        InnovacionRacionalizacion testInnovacionRacionalizacion = innovacionRacionalizacionList.get(
            innovacionRacionalizacionList.size() - 1
        );
        assertThat(testInnovacionRacionalizacion.getTematica()).isEqualTo(UPDATED_TEMATICA);
        assertThat(testInnovacionRacionalizacion.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testInnovacionRacionalizacion.getVp()).isEqualTo(UPDATED_VP);
        assertThat(testInnovacionRacionalizacion.getAutores()).isEqualTo(UPDATED_AUTORES);
        assertThat(testInnovacionRacionalizacion.getNumeroIdentidad()).isEqualTo(UPDATED_NUMERO_IDENTIDAD);
        assertThat(testInnovacionRacionalizacion.getObservacion()).isEqualTo(UPDATED_OBSERVACION);
        assertThat(testInnovacionRacionalizacion.getAprobada()).isEqualTo(UPDATED_APROBADA);
        assertThat(testInnovacionRacionalizacion.getPublico()).isEqualTo(UPDATED_PUBLICO);
    }

    @Test
    @Transactional
    void patchNonExistingInnovacionRacionalizacion() throws Exception {
        int databaseSizeBeforeUpdate = innovacionRacionalizacionRepository.findAll().size();
        innovacionRacionalizacion.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInnovacionRacionalizacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, innovacionRacionalizacion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(innovacionRacionalizacion))
            )
            .andExpect(status().isBadRequest());

        // Validate the InnovacionRacionalizacion in the database
        List<InnovacionRacionalizacion> innovacionRacionalizacionList = innovacionRacionalizacionRepository.findAll();
        assertThat(innovacionRacionalizacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInnovacionRacionalizacion() throws Exception {
        int databaseSizeBeforeUpdate = innovacionRacionalizacionRepository.findAll().size();
        innovacionRacionalizacion.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInnovacionRacionalizacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(innovacionRacionalizacion))
            )
            .andExpect(status().isBadRequest());

        // Validate the InnovacionRacionalizacion in the database
        List<InnovacionRacionalizacion> innovacionRacionalizacionList = innovacionRacionalizacionRepository.findAll();
        assertThat(innovacionRacionalizacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInnovacionRacionalizacion() throws Exception {
        int databaseSizeBeforeUpdate = innovacionRacionalizacionRepository.findAll().size();
        innovacionRacionalizacion.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInnovacionRacionalizacionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(innovacionRacionalizacion))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the InnovacionRacionalizacion in the database
        List<InnovacionRacionalizacion> innovacionRacionalizacionList = innovacionRacionalizacionRepository.findAll();
        assertThat(innovacionRacionalizacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInnovacionRacionalizacion() throws Exception {
        // Initialize the database
        innovacionRacionalizacionRepository.saveAndFlush(innovacionRacionalizacion);

        int databaseSizeBeforeDelete = innovacionRacionalizacionRepository.findAll().size();

        // Delete the innovacionRacionalizacion
        restInnovacionRacionalizacionMockMvc
            .perform(delete(ENTITY_API_URL_ID, innovacionRacionalizacion.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InnovacionRacionalizacion> innovacionRacionalizacionList = innovacionRacionalizacionRepository.findAll();
        assertThat(innovacionRacionalizacionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
