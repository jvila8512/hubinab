package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Reto;
import com.mycompany.myapp.repository.RetoRepository;
import com.mycompany.myapp.service.RetoService;
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
 * Integration tests for the {@link RetoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class RetoResourceIT {

    private static final String DEFAULT_RETO = "AAAAAAAAAA";
    private static final String UPDATED_RETO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String DEFAULT_MOTIVACION = "AAAAAAAAAA";
    private static final String UPDATED_MOTIVACION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_INICIO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_INICIO = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FECHA_FIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_FIN = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_ACTIVO = false;
    private static final Boolean UPDATED_ACTIVO = true;

    private static final Boolean DEFAULT_VALIDADO = false;
    private static final Boolean UPDATED_VALIDADO = true;

    private static final byte[] DEFAULT_URL_FOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_URL_FOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_URL_FOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_URL_FOTO_CONTENT_TYPE = "image/png";

    private static final Integer DEFAULT_VISTO = 1;
    private static final Integer UPDATED_VISTO = 2;

    private static final String ENTITY_API_URL = "/api/retos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RetoRepository retoRepository;

    @Mock
    private RetoRepository retoRepositoryMock;

    @Mock
    private RetoService retoServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRetoMockMvc;

    private Reto reto;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reto createEntity(EntityManager em) {
        Reto reto = new Reto()
            .reto(DEFAULT_RETO)
            .descripcion(DEFAULT_DESCRIPCION)
            .motivacion(DEFAULT_MOTIVACION)
            .fechaInicio(DEFAULT_FECHA_INICIO)
            .fechaFin(DEFAULT_FECHA_FIN)
            .activo(DEFAULT_ACTIVO)
            .validado(DEFAULT_VALIDADO)
            .urlFoto(DEFAULT_URL_FOTO)
            .urlFotoContentType(DEFAULT_URL_FOTO_CONTENT_TYPE)
            .visto(DEFAULT_VISTO);
        return reto;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reto createUpdatedEntity(EntityManager em) {
        Reto reto = new Reto()
            .reto(UPDATED_RETO)
            .descripcion(UPDATED_DESCRIPCION)
            .motivacion(UPDATED_MOTIVACION)
            .fechaInicio(UPDATED_FECHA_INICIO)
            .fechaFin(UPDATED_FECHA_FIN)
            .activo(UPDATED_ACTIVO)
            .validado(UPDATED_VALIDADO)
            .urlFoto(UPDATED_URL_FOTO)
            .urlFotoContentType(UPDATED_URL_FOTO_CONTENT_TYPE)
            .visto(UPDATED_VISTO);
        return reto;
    }

    @BeforeEach
    public void initTest() {
        reto = createEntity(em);
    }

    @Test
    @Transactional
    void createReto() throws Exception {
        int databaseSizeBeforeCreate = retoRepository.findAll().size();
        // Create the Reto
        restRetoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reto)))
            .andExpect(status().isCreated());

        // Validate the Reto in the database
        List<Reto> retoList = retoRepository.findAll();
        assertThat(retoList).hasSize(databaseSizeBeforeCreate + 1);
        Reto testReto = retoList.get(retoList.size() - 1);
        assertThat(testReto.getReto()).isEqualTo(DEFAULT_RETO);
        assertThat(testReto.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testReto.getMotivacion()).isEqualTo(DEFAULT_MOTIVACION);
        assertThat(testReto.getFechaInicio()).isEqualTo(DEFAULT_FECHA_INICIO);
        assertThat(testReto.getFechaFin()).isEqualTo(DEFAULT_FECHA_FIN);
        assertThat(testReto.getActivo()).isEqualTo(DEFAULT_ACTIVO);
        assertThat(testReto.getValidado()).isEqualTo(DEFAULT_VALIDADO);
        assertThat(testReto.getUrlFoto()).isEqualTo(DEFAULT_URL_FOTO);
        assertThat(testReto.getUrlFotoContentType()).isEqualTo(DEFAULT_URL_FOTO_CONTENT_TYPE);
        assertThat(testReto.getVisto()).isEqualTo(DEFAULT_VISTO);
    }

    @Test
    @Transactional
    void createRetoWithExistingId() throws Exception {
        // Create the Reto with an existing ID
        reto.setId(1L);

        int databaseSizeBeforeCreate = retoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRetoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reto)))
            .andExpect(status().isBadRequest());

        // Validate the Reto in the database
        List<Reto> retoList = retoRepository.findAll();
        assertThat(retoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkRetoIsRequired() throws Exception {
        int databaseSizeBeforeTest = retoRepository.findAll().size();
        // set the field null
        reto.setReto(null);

        // Create the Reto, which fails.

        restRetoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reto)))
            .andExpect(status().isBadRequest());

        List<Reto> retoList = retoRepository.findAll();
        assertThat(retoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMotivacionIsRequired() throws Exception {
        int databaseSizeBeforeTest = retoRepository.findAll().size();
        // set the field null
        reto.setMotivacion(null);

        // Create the Reto, which fails.

        restRetoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reto)))
            .andExpect(status().isBadRequest());

        List<Reto> retoList = retoRepository.findAll();
        assertThat(retoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFechaInicioIsRequired() throws Exception {
        int databaseSizeBeforeTest = retoRepository.findAll().size();
        // set the field null
        reto.setFechaInicio(null);

        // Create the Reto, which fails.

        restRetoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reto)))
            .andExpect(status().isBadRequest());

        List<Reto> retoList = retoRepository.findAll();
        assertThat(retoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFechaFinIsRequired() throws Exception {
        int databaseSizeBeforeTest = retoRepository.findAll().size();
        // set the field null
        reto.setFechaFin(null);

        // Create the Reto, which fails.

        restRetoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reto)))
            .andExpect(status().isBadRequest());

        List<Reto> retoList = retoRepository.findAll();
        assertThat(retoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRetos() throws Exception {
        // Initialize the database
        retoRepository.saveAndFlush(reto);

        // Get all the retoList
        restRetoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reto.getId().intValue())))
            .andExpect(jsonPath("$.[*].reto").value(hasItem(DEFAULT_RETO)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].motivacion").value(hasItem(DEFAULT_MOTIVACION)))
            .andExpect(jsonPath("$.[*].fechaInicio").value(hasItem(DEFAULT_FECHA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].fechaFin").value(hasItem(DEFAULT_FECHA_FIN.toString())))
            .andExpect(jsonPath("$.[*].activo").value(hasItem(DEFAULT_ACTIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].validado").value(hasItem(DEFAULT_VALIDADO.booleanValue())))
            .andExpect(jsonPath("$.[*].urlFotoContentType").value(hasItem(DEFAULT_URL_FOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].urlFoto").value(hasItem(Base64Utils.encodeToString(DEFAULT_URL_FOTO))))
            .andExpect(jsonPath("$.[*].visto").value(hasItem(DEFAULT_VISTO)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllRetosWithEagerRelationshipsIsEnabled() throws Exception {
        when(retoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restRetoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(retoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllRetosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(retoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restRetoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(retoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getReto() throws Exception {
        // Initialize the database
        retoRepository.saveAndFlush(reto);

        // Get the reto
        restRetoMockMvc
            .perform(get(ENTITY_API_URL_ID, reto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(reto.getId().intValue()))
            .andExpect(jsonPath("$.reto").value(DEFAULT_RETO))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.motivacion").value(DEFAULT_MOTIVACION))
            .andExpect(jsonPath("$.fechaInicio").value(DEFAULT_FECHA_INICIO.toString()))
            .andExpect(jsonPath("$.fechaFin").value(DEFAULT_FECHA_FIN.toString()))
            .andExpect(jsonPath("$.activo").value(DEFAULT_ACTIVO.booleanValue()))
            .andExpect(jsonPath("$.validado").value(DEFAULT_VALIDADO.booleanValue()))
            .andExpect(jsonPath("$.urlFotoContentType").value(DEFAULT_URL_FOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.urlFoto").value(Base64Utils.encodeToString(DEFAULT_URL_FOTO)))
            .andExpect(jsonPath("$.visto").value(DEFAULT_VISTO));
    }

    @Test
    @Transactional
    void getNonExistingReto() throws Exception {
        // Get the reto
        restRetoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewReto() throws Exception {
        // Initialize the database
        retoRepository.saveAndFlush(reto);

        int databaseSizeBeforeUpdate = retoRepository.findAll().size();

        // Update the reto
        Reto updatedReto = retoRepository.findById(reto.getId()).get();
        // Disconnect from session so that the updates on updatedReto are not directly saved in db
        em.detach(updatedReto);
        updatedReto
            .reto(UPDATED_RETO)
            .descripcion(UPDATED_DESCRIPCION)
            .motivacion(UPDATED_MOTIVACION)
            .fechaInicio(UPDATED_FECHA_INICIO)
            .fechaFin(UPDATED_FECHA_FIN)
            .activo(UPDATED_ACTIVO)
            .validado(UPDATED_VALIDADO)
            .urlFoto(UPDATED_URL_FOTO)
            .urlFotoContentType(UPDATED_URL_FOTO_CONTENT_TYPE)
            .visto(UPDATED_VISTO);

        restRetoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedReto.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedReto))
            )
            .andExpect(status().isOk());

        // Validate the Reto in the database
        List<Reto> retoList = retoRepository.findAll();
        assertThat(retoList).hasSize(databaseSizeBeforeUpdate);
        Reto testReto = retoList.get(retoList.size() - 1);
        assertThat(testReto.getReto()).isEqualTo(UPDATED_RETO);
        assertThat(testReto.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testReto.getMotivacion()).isEqualTo(UPDATED_MOTIVACION);
        assertThat(testReto.getFechaInicio()).isEqualTo(UPDATED_FECHA_INICIO);
        assertThat(testReto.getFechaFin()).isEqualTo(UPDATED_FECHA_FIN);
        assertThat(testReto.getActivo()).isEqualTo(UPDATED_ACTIVO);
        assertThat(testReto.getValidado()).isEqualTo(UPDATED_VALIDADO);
        assertThat(testReto.getUrlFoto()).isEqualTo(UPDATED_URL_FOTO);
        assertThat(testReto.getUrlFotoContentType()).isEqualTo(UPDATED_URL_FOTO_CONTENT_TYPE);
        assertThat(testReto.getVisto()).isEqualTo(UPDATED_VISTO);
    }

    @Test
    @Transactional
    void putNonExistingReto() throws Exception {
        int databaseSizeBeforeUpdate = retoRepository.findAll().size();
        reto.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRetoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, reto.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reto))
            )
            .andExpect(status().isBadRequest());

        // Validate the Reto in the database
        List<Reto> retoList = retoRepository.findAll();
        assertThat(retoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchReto() throws Exception {
        int databaseSizeBeforeUpdate = retoRepository.findAll().size();
        reto.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRetoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reto))
            )
            .andExpect(status().isBadRequest());

        // Validate the Reto in the database
        List<Reto> retoList = retoRepository.findAll();
        assertThat(retoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamReto() throws Exception {
        int databaseSizeBeforeUpdate = retoRepository.findAll().size();
        reto.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRetoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reto)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Reto in the database
        List<Reto> retoList = retoRepository.findAll();
        assertThat(retoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRetoWithPatch() throws Exception {
        // Initialize the database
        retoRepository.saveAndFlush(reto);

        int databaseSizeBeforeUpdate = retoRepository.findAll().size();

        // Update the reto using partial update
        Reto partialUpdatedReto = new Reto();
        partialUpdatedReto.setId(reto.getId());

        partialUpdatedReto
            .motivacion(UPDATED_MOTIVACION)
            .fechaInicio(UPDATED_FECHA_INICIO)
            .fechaFin(UPDATED_FECHA_FIN)
            .activo(UPDATED_ACTIVO)
            .validado(UPDATED_VALIDADO)
            .urlFoto(UPDATED_URL_FOTO)
            .urlFotoContentType(UPDATED_URL_FOTO_CONTENT_TYPE)
            .visto(UPDATED_VISTO);

        restRetoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReto))
            )
            .andExpect(status().isOk());

        // Validate the Reto in the database
        List<Reto> retoList = retoRepository.findAll();
        assertThat(retoList).hasSize(databaseSizeBeforeUpdate);
        Reto testReto = retoList.get(retoList.size() - 1);
        assertThat(testReto.getReto()).isEqualTo(DEFAULT_RETO);
        assertThat(testReto.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testReto.getMotivacion()).isEqualTo(UPDATED_MOTIVACION);
        assertThat(testReto.getFechaInicio()).isEqualTo(UPDATED_FECHA_INICIO);
        assertThat(testReto.getFechaFin()).isEqualTo(UPDATED_FECHA_FIN);
        assertThat(testReto.getActivo()).isEqualTo(UPDATED_ACTIVO);
        assertThat(testReto.getValidado()).isEqualTo(UPDATED_VALIDADO);
        assertThat(testReto.getUrlFoto()).isEqualTo(UPDATED_URL_FOTO);
        assertThat(testReto.getUrlFotoContentType()).isEqualTo(UPDATED_URL_FOTO_CONTENT_TYPE);
        assertThat(testReto.getVisto()).isEqualTo(UPDATED_VISTO);
    }

    @Test
    @Transactional
    void fullUpdateRetoWithPatch() throws Exception {
        // Initialize the database
        retoRepository.saveAndFlush(reto);

        int databaseSizeBeforeUpdate = retoRepository.findAll().size();

        // Update the reto using partial update
        Reto partialUpdatedReto = new Reto();
        partialUpdatedReto.setId(reto.getId());

        partialUpdatedReto
            .reto(UPDATED_RETO)
            .descripcion(UPDATED_DESCRIPCION)
            .motivacion(UPDATED_MOTIVACION)
            .fechaInicio(UPDATED_FECHA_INICIO)
            .fechaFin(UPDATED_FECHA_FIN)
            .activo(UPDATED_ACTIVO)
            .validado(UPDATED_VALIDADO)
            .urlFoto(UPDATED_URL_FOTO)
            .urlFotoContentType(UPDATED_URL_FOTO_CONTENT_TYPE)
            .visto(UPDATED_VISTO);

        restRetoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReto))
            )
            .andExpect(status().isOk());

        // Validate the Reto in the database
        List<Reto> retoList = retoRepository.findAll();
        assertThat(retoList).hasSize(databaseSizeBeforeUpdate);
        Reto testReto = retoList.get(retoList.size() - 1);
        assertThat(testReto.getReto()).isEqualTo(UPDATED_RETO);
        assertThat(testReto.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testReto.getMotivacion()).isEqualTo(UPDATED_MOTIVACION);
        assertThat(testReto.getFechaInicio()).isEqualTo(UPDATED_FECHA_INICIO);
        assertThat(testReto.getFechaFin()).isEqualTo(UPDATED_FECHA_FIN);
        assertThat(testReto.getActivo()).isEqualTo(UPDATED_ACTIVO);
        assertThat(testReto.getValidado()).isEqualTo(UPDATED_VALIDADO);
        assertThat(testReto.getUrlFoto()).isEqualTo(UPDATED_URL_FOTO);
        assertThat(testReto.getUrlFotoContentType()).isEqualTo(UPDATED_URL_FOTO_CONTENT_TYPE);
        assertThat(testReto.getVisto()).isEqualTo(UPDATED_VISTO);
    }

    @Test
    @Transactional
    void patchNonExistingReto() throws Exception {
        int databaseSizeBeforeUpdate = retoRepository.findAll().size();
        reto.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRetoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, reto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reto))
            )
            .andExpect(status().isBadRequest());

        // Validate the Reto in the database
        List<Reto> retoList = retoRepository.findAll();
        assertThat(retoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchReto() throws Exception {
        int databaseSizeBeforeUpdate = retoRepository.findAll().size();
        reto.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRetoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reto))
            )
            .andExpect(status().isBadRequest());

        // Validate the Reto in the database
        List<Reto> retoList = retoRepository.findAll();
        assertThat(retoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamReto() throws Exception {
        int databaseSizeBeforeUpdate = retoRepository.findAll().size();
        reto.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRetoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(reto)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Reto in the database
        List<Reto> retoList = retoRepository.findAll();
        assertThat(retoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteReto() throws Exception {
        // Initialize the database
        retoRepository.saveAndFlush(reto);

        int databaseSizeBeforeDelete = retoRepository.findAll().size();

        // Delete the reto
        restRetoMockMvc
            .perform(delete(ENTITY_API_URL_ID, reto.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Reto> retoList = retoRepository.findAll();
        assertThat(retoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
