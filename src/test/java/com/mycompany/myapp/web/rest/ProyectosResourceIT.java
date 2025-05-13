package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Proyectos;
import com.mycompany.myapp.repository.ProyectosRepository;
import com.mycompany.myapp.service.ProyectosService;
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
 * Integration tests for the {@link ProyectosResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProyectosResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String DEFAULT_AUTOR = "AAAAAAAAAA";
    private static final String UPDATED_AUTOR = "BBBBBBBBBB";

    private static final String DEFAULT_NECESIDAD = "AAAAAAAAAA";
    private static final String UPDATED_NECESIDAD = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_INICIO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_INICIO = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FECHA_FIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_FIN = LocalDate.now(ZoneId.systemDefault());

    private static final byte[] DEFAULT_LOGO_URL = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_LOGO_URL = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_LOGO_URL_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_LOGO_URL_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/proyectos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProyectosRepository proyectosRepository;

    @Mock
    private ProyectosRepository proyectosRepositoryMock;

    @Mock
    private ProyectosService proyectosServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProyectosMockMvc;

    private Proyectos proyectos;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Proyectos createEntity(EntityManager em) {
        Proyectos proyectos = new Proyectos()
            .nombre(DEFAULT_NOMBRE)
            .descripcion(DEFAULT_DESCRIPCION)
            .autor(DEFAULT_AUTOR)
            .necesidad(DEFAULT_NECESIDAD)
            .fechaInicio(DEFAULT_FECHA_INICIO)
            .fechaFin(DEFAULT_FECHA_FIN)
            .logoUrl(DEFAULT_LOGO_URL)
            .logoUrlContentType(DEFAULT_LOGO_URL_CONTENT_TYPE);
        return proyectos;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Proyectos createUpdatedEntity(EntityManager em) {
        Proyectos proyectos = new Proyectos()
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .autor(UPDATED_AUTOR)
            .necesidad(UPDATED_NECESIDAD)
            .fechaInicio(UPDATED_FECHA_INICIO)
            .fechaFin(UPDATED_FECHA_FIN)
            .logoUrl(UPDATED_LOGO_URL)
            .logoUrlContentType(UPDATED_LOGO_URL_CONTENT_TYPE);
        return proyectos;
    }

    @BeforeEach
    public void initTest() {
        proyectos = createEntity(em);
    }

    @Test
    @Transactional
    void createProyectos() throws Exception {
        int databaseSizeBeforeCreate = proyectosRepository.findAll().size();
        // Create the Proyectos
        restProyectosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(proyectos)))
            .andExpect(status().isCreated());

        // Validate the Proyectos in the database
        List<Proyectos> proyectosList = proyectosRepository.findAll();
        assertThat(proyectosList).hasSize(databaseSizeBeforeCreate + 1);
        Proyectos testProyectos = proyectosList.get(proyectosList.size() - 1);
        assertThat(testProyectos.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testProyectos.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testProyectos.getAutor()).isEqualTo(DEFAULT_AUTOR);
        assertThat(testProyectos.getNecesidad()).isEqualTo(DEFAULT_NECESIDAD);
        assertThat(testProyectos.getFechaInicio()).isEqualTo(DEFAULT_FECHA_INICIO);
        assertThat(testProyectos.getFechaFin()).isEqualTo(DEFAULT_FECHA_FIN);
        assertThat(testProyectos.getLogoUrl()).isEqualTo(DEFAULT_LOGO_URL);
        assertThat(testProyectos.getLogoUrlContentType()).isEqualTo(DEFAULT_LOGO_URL_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createProyectosWithExistingId() throws Exception {
        // Create the Proyectos with an existing ID
        proyectos.setId(1L);

        int databaseSizeBeforeCreate = proyectosRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProyectosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(proyectos)))
            .andExpect(status().isBadRequest());

        // Validate the Proyectos in the database
        List<Proyectos> proyectosList = proyectosRepository.findAll();
        assertThat(proyectosList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = proyectosRepository.findAll().size();
        // set the field null
        proyectos.setNombre(null);

        // Create the Proyectos, which fails.

        restProyectosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(proyectos)))
            .andExpect(status().isBadRequest());

        List<Proyectos> proyectosList = proyectosRepository.findAll();
        assertThat(proyectosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAutorIsRequired() throws Exception {
        int databaseSizeBeforeTest = proyectosRepository.findAll().size();
        // set the field null
        proyectos.setAutor(null);

        // Create the Proyectos, which fails.

        restProyectosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(proyectos)))
            .andExpect(status().isBadRequest());

        List<Proyectos> proyectosList = proyectosRepository.findAll();
        assertThat(proyectosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNecesidadIsRequired() throws Exception {
        int databaseSizeBeforeTest = proyectosRepository.findAll().size();
        // set the field null
        proyectos.setNecesidad(null);

        // Create the Proyectos, which fails.

        restProyectosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(proyectos)))
            .andExpect(status().isBadRequest());

        List<Proyectos> proyectosList = proyectosRepository.findAll();
        assertThat(proyectosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFechaInicioIsRequired() throws Exception {
        int databaseSizeBeforeTest = proyectosRepository.findAll().size();
        // set the field null
        proyectos.setFechaInicio(null);

        // Create the Proyectos, which fails.

        restProyectosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(proyectos)))
            .andExpect(status().isBadRequest());

        List<Proyectos> proyectosList = proyectosRepository.findAll();
        assertThat(proyectosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFechaFinIsRequired() throws Exception {
        int databaseSizeBeforeTest = proyectosRepository.findAll().size();
        // set the field null
        proyectos.setFechaFin(null);

        // Create the Proyectos, which fails.

        restProyectosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(proyectos)))
            .andExpect(status().isBadRequest());

        List<Proyectos> proyectosList = proyectosRepository.findAll();
        assertThat(proyectosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProyectos() throws Exception {
        // Initialize the database
        proyectosRepository.saveAndFlush(proyectos);

        // Get all the proyectosList
        restProyectosMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(proyectos.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].autor").value(hasItem(DEFAULT_AUTOR)))
            .andExpect(jsonPath("$.[*].necesidad").value(hasItem(DEFAULT_NECESIDAD)))
            .andExpect(jsonPath("$.[*].fechaInicio").value(hasItem(DEFAULT_FECHA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].fechaFin").value(hasItem(DEFAULT_FECHA_FIN.toString())))
            .andExpect(jsonPath("$.[*].logoUrlContentType").value(hasItem(DEFAULT_LOGO_URL_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].logoUrl").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOGO_URL))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllProyectosWithEagerRelationshipsIsEnabled() throws Exception {
        when(proyectosServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProyectosMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(proyectosServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllProyectosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(proyectosServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProyectosMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(proyectosServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getProyectos() throws Exception {
        // Initialize the database
        proyectosRepository.saveAndFlush(proyectos);

        // Get the proyectos
        restProyectosMockMvc
            .perform(get(ENTITY_API_URL_ID, proyectos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(proyectos.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.autor").value(DEFAULT_AUTOR))
            .andExpect(jsonPath("$.necesidad").value(DEFAULT_NECESIDAD))
            .andExpect(jsonPath("$.fechaInicio").value(DEFAULT_FECHA_INICIO.toString()))
            .andExpect(jsonPath("$.fechaFin").value(DEFAULT_FECHA_FIN.toString()))
            .andExpect(jsonPath("$.logoUrlContentType").value(DEFAULT_LOGO_URL_CONTENT_TYPE))
            .andExpect(jsonPath("$.logoUrl").value(Base64Utils.encodeToString(DEFAULT_LOGO_URL)));
    }

    @Test
    @Transactional
    void getNonExistingProyectos() throws Exception {
        // Get the proyectos
        restProyectosMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProyectos() throws Exception {
        // Initialize the database
        proyectosRepository.saveAndFlush(proyectos);

        int databaseSizeBeforeUpdate = proyectosRepository.findAll().size();

        // Update the proyectos
        Proyectos updatedProyectos = proyectosRepository.findById(proyectos.getId()).get();
        // Disconnect from session so that the updates on updatedProyectos are not directly saved in db
        em.detach(updatedProyectos);
        updatedProyectos
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .autor(UPDATED_AUTOR)
            .necesidad(UPDATED_NECESIDAD)
            .fechaInicio(UPDATED_FECHA_INICIO)
            .fechaFin(UPDATED_FECHA_FIN)
            .logoUrl(UPDATED_LOGO_URL)
            .logoUrlContentType(UPDATED_LOGO_URL_CONTENT_TYPE);

        restProyectosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProyectos.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedProyectos))
            )
            .andExpect(status().isOk());

        // Validate the Proyectos in the database
        List<Proyectos> proyectosList = proyectosRepository.findAll();
        assertThat(proyectosList).hasSize(databaseSizeBeforeUpdate);
        Proyectos testProyectos = proyectosList.get(proyectosList.size() - 1);
        assertThat(testProyectos.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testProyectos.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testProyectos.getAutor()).isEqualTo(UPDATED_AUTOR);
        assertThat(testProyectos.getNecesidad()).isEqualTo(UPDATED_NECESIDAD);
        assertThat(testProyectos.getFechaInicio()).isEqualTo(UPDATED_FECHA_INICIO);
        assertThat(testProyectos.getFechaFin()).isEqualTo(UPDATED_FECHA_FIN);
        assertThat(testProyectos.getLogoUrl()).isEqualTo(UPDATED_LOGO_URL);
        assertThat(testProyectos.getLogoUrlContentType()).isEqualTo(UPDATED_LOGO_URL_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingProyectos() throws Exception {
        int databaseSizeBeforeUpdate = proyectosRepository.findAll().size();
        proyectos.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProyectosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, proyectos.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(proyectos))
            )
            .andExpect(status().isBadRequest());

        // Validate the Proyectos in the database
        List<Proyectos> proyectosList = proyectosRepository.findAll();
        assertThat(proyectosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProyectos() throws Exception {
        int databaseSizeBeforeUpdate = proyectosRepository.findAll().size();
        proyectos.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProyectosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(proyectos))
            )
            .andExpect(status().isBadRequest());

        // Validate the Proyectos in the database
        List<Proyectos> proyectosList = proyectosRepository.findAll();
        assertThat(proyectosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProyectos() throws Exception {
        int databaseSizeBeforeUpdate = proyectosRepository.findAll().size();
        proyectos.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProyectosMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(proyectos)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Proyectos in the database
        List<Proyectos> proyectosList = proyectosRepository.findAll();
        assertThat(proyectosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProyectosWithPatch() throws Exception {
        // Initialize the database
        proyectosRepository.saveAndFlush(proyectos);

        int databaseSizeBeforeUpdate = proyectosRepository.findAll().size();

        // Update the proyectos using partial update
        Proyectos partialUpdatedProyectos = new Proyectos();
        partialUpdatedProyectos.setId(proyectos.getId());

        partialUpdatedProyectos
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .autor(UPDATED_AUTOR)
            .fechaInicio(UPDATED_FECHA_INICIO)
            .fechaFin(UPDATED_FECHA_FIN);

        restProyectosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProyectos.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProyectos))
            )
            .andExpect(status().isOk());

        // Validate the Proyectos in the database
        List<Proyectos> proyectosList = proyectosRepository.findAll();
        assertThat(proyectosList).hasSize(databaseSizeBeforeUpdate);
        Proyectos testProyectos = proyectosList.get(proyectosList.size() - 1);
        assertThat(testProyectos.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testProyectos.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testProyectos.getAutor()).isEqualTo(UPDATED_AUTOR);
        assertThat(testProyectos.getNecesidad()).isEqualTo(DEFAULT_NECESIDAD);
        assertThat(testProyectos.getFechaInicio()).isEqualTo(UPDATED_FECHA_INICIO);
        assertThat(testProyectos.getFechaFin()).isEqualTo(UPDATED_FECHA_FIN);
        assertThat(testProyectos.getLogoUrl()).isEqualTo(DEFAULT_LOGO_URL);
        assertThat(testProyectos.getLogoUrlContentType()).isEqualTo(DEFAULT_LOGO_URL_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateProyectosWithPatch() throws Exception {
        // Initialize the database
        proyectosRepository.saveAndFlush(proyectos);

        int databaseSizeBeforeUpdate = proyectosRepository.findAll().size();

        // Update the proyectos using partial update
        Proyectos partialUpdatedProyectos = new Proyectos();
        partialUpdatedProyectos.setId(proyectos.getId());

        partialUpdatedProyectos
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .autor(UPDATED_AUTOR)
            .necesidad(UPDATED_NECESIDAD)
            .fechaInicio(UPDATED_FECHA_INICIO)
            .fechaFin(UPDATED_FECHA_FIN)
            .logoUrl(UPDATED_LOGO_URL)
            .logoUrlContentType(UPDATED_LOGO_URL_CONTENT_TYPE);

        restProyectosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProyectos.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProyectos))
            )
            .andExpect(status().isOk());

        // Validate the Proyectos in the database
        List<Proyectos> proyectosList = proyectosRepository.findAll();
        assertThat(proyectosList).hasSize(databaseSizeBeforeUpdate);
        Proyectos testProyectos = proyectosList.get(proyectosList.size() - 1);
        assertThat(testProyectos.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testProyectos.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testProyectos.getAutor()).isEqualTo(UPDATED_AUTOR);
        assertThat(testProyectos.getNecesidad()).isEqualTo(UPDATED_NECESIDAD);
        assertThat(testProyectos.getFechaInicio()).isEqualTo(UPDATED_FECHA_INICIO);
        assertThat(testProyectos.getFechaFin()).isEqualTo(UPDATED_FECHA_FIN);
        assertThat(testProyectos.getLogoUrl()).isEqualTo(UPDATED_LOGO_URL);
        assertThat(testProyectos.getLogoUrlContentType()).isEqualTo(UPDATED_LOGO_URL_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingProyectos() throws Exception {
        int databaseSizeBeforeUpdate = proyectosRepository.findAll().size();
        proyectos.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProyectosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, proyectos.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(proyectos))
            )
            .andExpect(status().isBadRequest());

        // Validate the Proyectos in the database
        List<Proyectos> proyectosList = proyectosRepository.findAll();
        assertThat(proyectosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProyectos() throws Exception {
        int databaseSizeBeforeUpdate = proyectosRepository.findAll().size();
        proyectos.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProyectosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(proyectos))
            )
            .andExpect(status().isBadRequest());

        // Validate the Proyectos in the database
        List<Proyectos> proyectosList = proyectosRepository.findAll();
        assertThat(proyectosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProyectos() throws Exception {
        int databaseSizeBeforeUpdate = proyectosRepository.findAll().size();
        proyectos.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProyectosMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(proyectos))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Proyectos in the database
        List<Proyectos> proyectosList = proyectosRepository.findAll();
        assertThat(proyectosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProyectos() throws Exception {
        // Initialize the database
        proyectosRepository.saveAndFlush(proyectos);

        int databaseSizeBeforeDelete = proyectosRepository.findAll().size();

        // Delete the proyectos
        restProyectosMockMvc
            .perform(delete(ENTITY_API_URL_ID, proyectos.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Proyectos> proyectosList = proyectosRepository.findAll();
        assertThat(proyectosList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
