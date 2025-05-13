package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Tramite;
import com.mycompany.myapp.repository.TramiteRepository;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link TramiteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TramiteResourceIT {

    private static final String DEFAULT_INSCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_INSCRIPCION = "BBBBBBBBBB";

    private static final String DEFAULT_PRUEBA_EXPERIMENTAL = "AAAAAAAAAA";
    private static final String UPDATED_PRUEBA_EXPERIMENTAL = "BBBBBBBBBB";

    private static final String DEFAULT_EXMANEN_EVALUACION = "AAAAAAAAAA";
    private static final String UPDATED_EXMANEN_EVALUACION = "BBBBBBBBBB";

    private static final String DEFAULT_DICTAMEN = "AAAAAAAAAA";
    private static final String UPDATED_DICTAMEN = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CONCESION = false;
    private static final Boolean UPDATED_CONCESION = true;

    private static final Boolean DEFAULT_DENEGADO = false;
    private static final Boolean UPDATED_DENEGADO = true;

    private static final Boolean DEFAULT_RECLAMACION = false;
    private static final Boolean UPDATED_RECLAMACION = true;

    private static final Boolean DEFAULT_ANULACION = false;
    private static final Boolean UPDATED_ANULACION = true;

    private static final LocalDate DEFAULT_FECHA_NOTIFICACION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_NOTIFICACION = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FECA_CERTIFICADO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECA_CERTIFICADO = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_OBSERVACION = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tramites";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TramiteRepository tramiteRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTramiteMockMvc;

    private Tramite tramite;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tramite createEntity(EntityManager em) {
        Tramite tramite = new Tramite()
            .inscripcion(DEFAULT_INSCRIPCION)
            .pruebaExperimental(DEFAULT_PRUEBA_EXPERIMENTAL)
            .exmanenEvaluacion(DEFAULT_EXMANEN_EVALUACION)
            .dictamen(DEFAULT_DICTAMEN)
            .concesion(DEFAULT_CONCESION)
            .denegado(DEFAULT_DENEGADO)
            .reclamacion(DEFAULT_RECLAMACION)
            .anulacion(DEFAULT_ANULACION)
            .fechaNotificacion(DEFAULT_FECHA_NOTIFICACION)
            .fecaCertificado(DEFAULT_FECA_CERTIFICADO)
            .observacion(DEFAULT_OBSERVACION);
        return tramite;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tramite createUpdatedEntity(EntityManager em) {
        Tramite tramite = new Tramite()
            .inscripcion(UPDATED_INSCRIPCION)
            .pruebaExperimental(UPDATED_PRUEBA_EXPERIMENTAL)
            .exmanenEvaluacion(UPDATED_EXMANEN_EVALUACION)
            .dictamen(UPDATED_DICTAMEN)
            .concesion(UPDATED_CONCESION)
            .denegado(UPDATED_DENEGADO)
            .reclamacion(UPDATED_RECLAMACION)
            .anulacion(UPDATED_ANULACION)
            .fechaNotificacion(UPDATED_FECHA_NOTIFICACION)
            .fecaCertificado(UPDATED_FECA_CERTIFICADO)
            .observacion(UPDATED_OBSERVACION);
        return tramite;
    }

    @BeforeEach
    public void initTest() {
        tramite = createEntity(em);
    }

    @Test
    @Transactional
    void createTramite() throws Exception {
        int databaseSizeBeforeCreate = tramiteRepository.findAll().size();
        // Create the Tramite
        restTramiteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tramite)))
            .andExpect(status().isCreated());

        // Validate the Tramite in the database
        List<Tramite> tramiteList = tramiteRepository.findAll();
        assertThat(tramiteList).hasSize(databaseSizeBeforeCreate + 1);
        Tramite testTramite = tramiteList.get(tramiteList.size() - 1);
        assertThat(testTramite.getInscripcion()).isEqualTo(DEFAULT_INSCRIPCION);
        assertThat(testTramite.getPruebaExperimental()).isEqualTo(DEFAULT_PRUEBA_EXPERIMENTAL);
        assertThat(testTramite.getExmanenEvaluacion()).isEqualTo(DEFAULT_EXMANEN_EVALUACION);
        assertThat(testTramite.getDictamen()).isEqualTo(DEFAULT_DICTAMEN);
        assertThat(testTramite.getConcesion()).isEqualTo(DEFAULT_CONCESION);
        assertThat(testTramite.getDenegado()).isEqualTo(DEFAULT_DENEGADO);
        assertThat(testTramite.getReclamacion()).isEqualTo(DEFAULT_RECLAMACION);
        assertThat(testTramite.getAnulacion()).isEqualTo(DEFAULT_ANULACION);
        assertThat(testTramite.getFechaNotificacion()).isEqualTo(DEFAULT_FECHA_NOTIFICACION);
        assertThat(testTramite.getFecaCertificado()).isEqualTo(DEFAULT_FECA_CERTIFICADO);
        assertThat(testTramite.getObservacion()).isEqualTo(DEFAULT_OBSERVACION);
    }

    @Test
    @Transactional
    void createTramiteWithExistingId() throws Exception {
        // Create the Tramite with an existing ID
        tramite.setId(1L);

        int databaseSizeBeforeCreate = tramiteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTramiteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tramite)))
            .andExpect(status().isBadRequest());

        // Validate the Tramite in the database
        List<Tramite> tramiteList = tramiteRepository.findAll();
        assertThat(tramiteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTramites() throws Exception {
        // Initialize the database
        tramiteRepository.saveAndFlush(tramite);

        // Get all the tramiteList
        restTramiteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tramite.getId().intValue())))
            .andExpect(jsonPath("$.[*].inscripcion").value(hasItem(DEFAULT_INSCRIPCION)))
            .andExpect(jsonPath("$.[*].pruebaExperimental").value(hasItem(DEFAULT_PRUEBA_EXPERIMENTAL)))
            .andExpect(jsonPath("$.[*].exmanenEvaluacion").value(hasItem(DEFAULT_EXMANEN_EVALUACION)))
            .andExpect(jsonPath("$.[*].dictamen").value(hasItem(DEFAULT_DICTAMEN)))
            .andExpect(jsonPath("$.[*].concesion").value(hasItem(DEFAULT_CONCESION.booleanValue())))
            .andExpect(jsonPath("$.[*].denegado").value(hasItem(DEFAULT_DENEGADO.booleanValue())))
            .andExpect(jsonPath("$.[*].reclamacion").value(hasItem(DEFAULT_RECLAMACION.booleanValue())))
            .andExpect(jsonPath("$.[*].anulacion").value(hasItem(DEFAULT_ANULACION.booleanValue())))
            .andExpect(jsonPath("$.[*].fechaNotificacion").value(hasItem(DEFAULT_FECHA_NOTIFICACION.toString())))
            .andExpect(jsonPath("$.[*].fecaCertificado").value(hasItem(DEFAULT_FECA_CERTIFICADO.toString())))
            .andExpect(jsonPath("$.[*].observacion").value(hasItem(DEFAULT_OBSERVACION)));
    }

    @Test
    @Transactional
    void getTramite() throws Exception {
        // Initialize the database
        tramiteRepository.saveAndFlush(tramite);

        // Get the tramite
        restTramiteMockMvc
            .perform(get(ENTITY_API_URL_ID, tramite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tramite.getId().intValue()))
            .andExpect(jsonPath("$.inscripcion").value(DEFAULT_INSCRIPCION))
            .andExpect(jsonPath("$.pruebaExperimental").value(DEFAULT_PRUEBA_EXPERIMENTAL))
            .andExpect(jsonPath("$.exmanenEvaluacion").value(DEFAULT_EXMANEN_EVALUACION))
            .andExpect(jsonPath("$.dictamen").value(DEFAULT_DICTAMEN))
            .andExpect(jsonPath("$.concesion").value(DEFAULT_CONCESION.booleanValue()))
            .andExpect(jsonPath("$.denegado").value(DEFAULT_DENEGADO.booleanValue()))
            .andExpect(jsonPath("$.reclamacion").value(DEFAULT_RECLAMACION.booleanValue()))
            .andExpect(jsonPath("$.anulacion").value(DEFAULT_ANULACION.booleanValue()))
            .andExpect(jsonPath("$.fechaNotificacion").value(DEFAULT_FECHA_NOTIFICACION.toString()))
            .andExpect(jsonPath("$.fecaCertificado").value(DEFAULT_FECA_CERTIFICADO.toString()))
            .andExpect(jsonPath("$.observacion").value(DEFAULT_OBSERVACION));
    }

    @Test
    @Transactional
    void getNonExistingTramite() throws Exception {
        // Get the tramite
        restTramiteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTramite() throws Exception {
        // Initialize the database
        tramiteRepository.saveAndFlush(tramite);

        int databaseSizeBeforeUpdate = tramiteRepository.findAll().size();

        // Update the tramite
        Tramite updatedTramite = tramiteRepository.findById(tramite.getId()).get();
        // Disconnect from session so that the updates on updatedTramite are not directly saved in db
        em.detach(updatedTramite);
        updatedTramite
            .inscripcion(UPDATED_INSCRIPCION)
            .pruebaExperimental(UPDATED_PRUEBA_EXPERIMENTAL)
            .exmanenEvaluacion(UPDATED_EXMANEN_EVALUACION)
            .dictamen(UPDATED_DICTAMEN)
            .concesion(UPDATED_CONCESION)
            .denegado(UPDATED_DENEGADO)
            .reclamacion(UPDATED_RECLAMACION)
            .anulacion(UPDATED_ANULACION)
            .fechaNotificacion(UPDATED_FECHA_NOTIFICACION)
            .fecaCertificado(UPDATED_FECA_CERTIFICADO)
            .observacion(UPDATED_OBSERVACION);

        restTramiteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTramite.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTramite))
            )
            .andExpect(status().isOk());

        // Validate the Tramite in the database
        List<Tramite> tramiteList = tramiteRepository.findAll();
        assertThat(tramiteList).hasSize(databaseSizeBeforeUpdate);
        Tramite testTramite = tramiteList.get(tramiteList.size() - 1);
        assertThat(testTramite.getInscripcion()).isEqualTo(UPDATED_INSCRIPCION);
        assertThat(testTramite.getPruebaExperimental()).isEqualTo(UPDATED_PRUEBA_EXPERIMENTAL);
        assertThat(testTramite.getExmanenEvaluacion()).isEqualTo(UPDATED_EXMANEN_EVALUACION);
        assertThat(testTramite.getDictamen()).isEqualTo(UPDATED_DICTAMEN);
        assertThat(testTramite.getConcesion()).isEqualTo(UPDATED_CONCESION);
        assertThat(testTramite.getDenegado()).isEqualTo(UPDATED_DENEGADO);
        assertThat(testTramite.getReclamacion()).isEqualTo(UPDATED_RECLAMACION);
        assertThat(testTramite.getAnulacion()).isEqualTo(UPDATED_ANULACION);
        assertThat(testTramite.getFechaNotificacion()).isEqualTo(UPDATED_FECHA_NOTIFICACION);
        assertThat(testTramite.getFecaCertificado()).isEqualTo(UPDATED_FECA_CERTIFICADO);
        assertThat(testTramite.getObservacion()).isEqualTo(UPDATED_OBSERVACION);
    }

    @Test
    @Transactional
    void putNonExistingTramite() throws Exception {
        int databaseSizeBeforeUpdate = tramiteRepository.findAll().size();
        tramite.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTramiteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tramite.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tramite))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tramite in the database
        List<Tramite> tramiteList = tramiteRepository.findAll();
        assertThat(tramiteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTramite() throws Exception {
        int databaseSizeBeforeUpdate = tramiteRepository.findAll().size();
        tramite.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTramiteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tramite))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tramite in the database
        List<Tramite> tramiteList = tramiteRepository.findAll();
        assertThat(tramiteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTramite() throws Exception {
        int databaseSizeBeforeUpdate = tramiteRepository.findAll().size();
        tramite.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTramiteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tramite)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tramite in the database
        List<Tramite> tramiteList = tramiteRepository.findAll();
        assertThat(tramiteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTramiteWithPatch() throws Exception {
        // Initialize the database
        tramiteRepository.saveAndFlush(tramite);

        int databaseSizeBeforeUpdate = tramiteRepository.findAll().size();

        // Update the tramite using partial update
        Tramite partialUpdatedTramite = new Tramite();
        partialUpdatedTramite.setId(tramite.getId());

        partialUpdatedTramite
            .inscripcion(UPDATED_INSCRIPCION)
            .pruebaExperimental(UPDATED_PRUEBA_EXPERIMENTAL)
            .dictamen(UPDATED_DICTAMEN)
            .concesion(UPDATED_CONCESION)
            .reclamacion(UPDATED_RECLAMACION)
            .anulacion(UPDATED_ANULACION)
            .fecaCertificado(UPDATED_FECA_CERTIFICADO);

        restTramiteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTramite.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTramite))
            )
            .andExpect(status().isOk());

        // Validate the Tramite in the database
        List<Tramite> tramiteList = tramiteRepository.findAll();
        assertThat(tramiteList).hasSize(databaseSizeBeforeUpdate);
        Tramite testTramite = tramiteList.get(tramiteList.size() - 1);
        assertThat(testTramite.getInscripcion()).isEqualTo(UPDATED_INSCRIPCION);
        assertThat(testTramite.getPruebaExperimental()).isEqualTo(UPDATED_PRUEBA_EXPERIMENTAL);
        assertThat(testTramite.getExmanenEvaluacion()).isEqualTo(DEFAULT_EXMANEN_EVALUACION);
        assertThat(testTramite.getDictamen()).isEqualTo(UPDATED_DICTAMEN);
        assertThat(testTramite.getConcesion()).isEqualTo(UPDATED_CONCESION);
        assertThat(testTramite.getDenegado()).isEqualTo(DEFAULT_DENEGADO);
        assertThat(testTramite.getReclamacion()).isEqualTo(UPDATED_RECLAMACION);
        assertThat(testTramite.getAnulacion()).isEqualTo(UPDATED_ANULACION);
        assertThat(testTramite.getFechaNotificacion()).isEqualTo(DEFAULT_FECHA_NOTIFICACION);
        assertThat(testTramite.getFecaCertificado()).isEqualTo(UPDATED_FECA_CERTIFICADO);
        assertThat(testTramite.getObservacion()).isEqualTo(DEFAULT_OBSERVACION);
    }

    @Test
    @Transactional
    void fullUpdateTramiteWithPatch() throws Exception {
        // Initialize the database
        tramiteRepository.saveAndFlush(tramite);

        int databaseSizeBeforeUpdate = tramiteRepository.findAll().size();

        // Update the tramite using partial update
        Tramite partialUpdatedTramite = new Tramite();
        partialUpdatedTramite.setId(tramite.getId());

        partialUpdatedTramite
            .inscripcion(UPDATED_INSCRIPCION)
            .pruebaExperimental(UPDATED_PRUEBA_EXPERIMENTAL)
            .exmanenEvaluacion(UPDATED_EXMANEN_EVALUACION)
            .dictamen(UPDATED_DICTAMEN)
            .concesion(UPDATED_CONCESION)
            .denegado(UPDATED_DENEGADO)
            .reclamacion(UPDATED_RECLAMACION)
            .anulacion(UPDATED_ANULACION)
            .fechaNotificacion(UPDATED_FECHA_NOTIFICACION)
            .fecaCertificado(UPDATED_FECA_CERTIFICADO)
            .observacion(UPDATED_OBSERVACION);

        restTramiteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTramite.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTramite))
            )
            .andExpect(status().isOk());

        // Validate the Tramite in the database
        List<Tramite> tramiteList = tramiteRepository.findAll();
        assertThat(tramiteList).hasSize(databaseSizeBeforeUpdate);
        Tramite testTramite = tramiteList.get(tramiteList.size() - 1);
        assertThat(testTramite.getInscripcion()).isEqualTo(UPDATED_INSCRIPCION);
        assertThat(testTramite.getPruebaExperimental()).isEqualTo(UPDATED_PRUEBA_EXPERIMENTAL);
        assertThat(testTramite.getExmanenEvaluacion()).isEqualTo(UPDATED_EXMANEN_EVALUACION);
        assertThat(testTramite.getDictamen()).isEqualTo(UPDATED_DICTAMEN);
        assertThat(testTramite.getConcesion()).isEqualTo(UPDATED_CONCESION);
        assertThat(testTramite.getDenegado()).isEqualTo(UPDATED_DENEGADO);
        assertThat(testTramite.getReclamacion()).isEqualTo(UPDATED_RECLAMACION);
        assertThat(testTramite.getAnulacion()).isEqualTo(UPDATED_ANULACION);
        assertThat(testTramite.getFechaNotificacion()).isEqualTo(UPDATED_FECHA_NOTIFICACION);
        assertThat(testTramite.getFecaCertificado()).isEqualTo(UPDATED_FECA_CERTIFICADO);
        assertThat(testTramite.getObservacion()).isEqualTo(UPDATED_OBSERVACION);
    }

    @Test
    @Transactional
    void patchNonExistingTramite() throws Exception {
        int databaseSizeBeforeUpdate = tramiteRepository.findAll().size();
        tramite.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTramiteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tramite.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tramite))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tramite in the database
        List<Tramite> tramiteList = tramiteRepository.findAll();
        assertThat(tramiteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTramite() throws Exception {
        int databaseSizeBeforeUpdate = tramiteRepository.findAll().size();
        tramite.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTramiteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tramite))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tramite in the database
        List<Tramite> tramiteList = tramiteRepository.findAll();
        assertThat(tramiteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTramite() throws Exception {
        int databaseSizeBeforeUpdate = tramiteRepository.findAll().size();
        tramite.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTramiteMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tramite)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tramite in the database
        List<Tramite> tramiteList = tramiteRepository.findAll();
        assertThat(tramiteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTramite() throws Exception {
        // Initialize the database
        tramiteRepository.saveAndFlush(tramite);

        int databaseSizeBeforeDelete = tramiteRepository.findAll().size();

        // Delete the tramite
        restTramiteMockMvc
            .perform(delete(ENTITY_API_URL_ID, tramite.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Tramite> tramiteList = tramiteRepository.findAll();
        assertThat(tramiteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
