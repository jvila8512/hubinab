package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Participantes;
import com.mycompany.myapp.repository.ParticipantesRepository;
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
 * Integration tests for the {@link ParticipantesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ParticipantesResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO = "BBBBBBBBBB";

    private static final String DEFAULT_CORREO = "AAAAAAAAAA";
    private static final String UPDATED_CORREO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/participantes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ParticipantesRepository participantesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restParticipantesMockMvc;

    private Participantes participantes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Participantes createEntity(EntityManager em) {
        Participantes participantes = new Participantes().nombre(DEFAULT_NOMBRE).telefono(DEFAULT_TELEFONO).correo(DEFAULT_CORREO);
        return participantes;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Participantes createUpdatedEntity(EntityManager em) {
        Participantes participantes = new Participantes().nombre(UPDATED_NOMBRE).telefono(UPDATED_TELEFONO).correo(UPDATED_CORREO);
        return participantes;
    }

    @BeforeEach
    public void initTest() {
        participantes = createEntity(em);
    }

    @Test
    @Transactional
    void createParticipantes() throws Exception {
        int databaseSizeBeforeCreate = participantesRepository.findAll().size();
        // Create the Participantes
        restParticipantesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(participantes)))
            .andExpect(status().isCreated());

        // Validate the Participantes in the database
        List<Participantes> participantesList = participantesRepository.findAll();
        assertThat(participantesList).hasSize(databaseSizeBeforeCreate + 1);
        Participantes testParticipantes = participantesList.get(participantesList.size() - 1);
        assertThat(testParticipantes.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testParticipantes.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testParticipantes.getCorreo()).isEqualTo(DEFAULT_CORREO);
    }

    @Test
    @Transactional
    void createParticipantesWithExistingId() throws Exception {
        // Create the Participantes with an existing ID
        participantes.setId(1L);

        int databaseSizeBeforeCreate = participantesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restParticipantesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(participantes)))
            .andExpect(status().isBadRequest());

        // Validate the Participantes in the database
        List<Participantes> participantesList = participantesRepository.findAll();
        assertThat(participantesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = participantesRepository.findAll().size();
        // set the field null
        participantes.setNombre(null);

        // Create the Participantes, which fails.

        restParticipantesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(participantes)))
            .andExpect(status().isBadRequest());

        List<Participantes> participantesList = participantesRepository.findAll();
        assertThat(participantesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCorreoIsRequired() throws Exception {
        int databaseSizeBeforeTest = participantesRepository.findAll().size();
        // set the field null
        participantes.setCorreo(null);

        // Create the Participantes, which fails.

        restParticipantesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(participantes)))
            .andExpect(status().isBadRequest());

        List<Participantes> participantesList = participantesRepository.findAll();
        assertThat(participantesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllParticipantes() throws Exception {
        // Initialize the database
        participantesRepository.saveAndFlush(participantes);

        // Get all the participantesList
        restParticipantesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(participantes.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)))
            .andExpect(jsonPath("$.[*].correo").value(hasItem(DEFAULT_CORREO)));
    }

    @Test
    @Transactional
    void getParticipantes() throws Exception {
        // Initialize the database
        participantesRepository.saveAndFlush(participantes);

        // Get the participantes
        restParticipantesMockMvc
            .perform(get(ENTITY_API_URL_ID, participantes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(participantes.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO))
            .andExpect(jsonPath("$.correo").value(DEFAULT_CORREO));
    }

    @Test
    @Transactional
    void getNonExistingParticipantes() throws Exception {
        // Get the participantes
        restParticipantesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewParticipantes() throws Exception {
        // Initialize the database
        participantesRepository.saveAndFlush(participantes);

        int databaseSizeBeforeUpdate = participantesRepository.findAll().size();

        // Update the participantes
        Participantes updatedParticipantes = participantesRepository.findById(participantes.getId()).get();
        // Disconnect from session so that the updates on updatedParticipantes are not directly saved in db
        em.detach(updatedParticipantes);
        updatedParticipantes.nombre(UPDATED_NOMBRE).telefono(UPDATED_TELEFONO).correo(UPDATED_CORREO);

        restParticipantesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedParticipantes.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedParticipantes))
            )
            .andExpect(status().isOk());

        // Validate the Participantes in the database
        List<Participantes> participantesList = participantesRepository.findAll();
        assertThat(participantesList).hasSize(databaseSizeBeforeUpdate);
        Participantes testParticipantes = participantesList.get(participantesList.size() - 1);
        assertThat(testParticipantes.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testParticipantes.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testParticipantes.getCorreo()).isEqualTo(UPDATED_CORREO);
    }

    @Test
    @Transactional
    void putNonExistingParticipantes() throws Exception {
        int databaseSizeBeforeUpdate = participantesRepository.findAll().size();
        participantes.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParticipantesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, participantes.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(participantes))
            )
            .andExpect(status().isBadRequest());

        // Validate the Participantes in the database
        List<Participantes> participantesList = participantesRepository.findAll();
        assertThat(participantesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchParticipantes() throws Exception {
        int databaseSizeBeforeUpdate = participantesRepository.findAll().size();
        participantes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParticipantesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(participantes))
            )
            .andExpect(status().isBadRequest());

        // Validate the Participantes in the database
        List<Participantes> participantesList = participantesRepository.findAll();
        assertThat(participantesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamParticipantes() throws Exception {
        int databaseSizeBeforeUpdate = participantesRepository.findAll().size();
        participantes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParticipantesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(participantes)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Participantes in the database
        List<Participantes> participantesList = participantesRepository.findAll();
        assertThat(participantesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateParticipantesWithPatch() throws Exception {
        // Initialize the database
        participantesRepository.saveAndFlush(participantes);

        int databaseSizeBeforeUpdate = participantesRepository.findAll().size();

        // Update the participantes using partial update
        Participantes partialUpdatedParticipantes = new Participantes();
        partialUpdatedParticipantes.setId(participantes.getId());

        partialUpdatedParticipantes.correo(UPDATED_CORREO);

        restParticipantesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedParticipantes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedParticipantes))
            )
            .andExpect(status().isOk());

        // Validate the Participantes in the database
        List<Participantes> participantesList = participantesRepository.findAll();
        assertThat(participantesList).hasSize(databaseSizeBeforeUpdate);
        Participantes testParticipantes = participantesList.get(participantesList.size() - 1);
        assertThat(testParticipantes.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testParticipantes.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testParticipantes.getCorreo()).isEqualTo(UPDATED_CORREO);
    }

    @Test
    @Transactional
    void fullUpdateParticipantesWithPatch() throws Exception {
        // Initialize the database
        participantesRepository.saveAndFlush(participantes);

        int databaseSizeBeforeUpdate = participantesRepository.findAll().size();

        // Update the participantes using partial update
        Participantes partialUpdatedParticipantes = new Participantes();
        partialUpdatedParticipantes.setId(participantes.getId());

        partialUpdatedParticipantes.nombre(UPDATED_NOMBRE).telefono(UPDATED_TELEFONO).correo(UPDATED_CORREO);

        restParticipantesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedParticipantes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedParticipantes))
            )
            .andExpect(status().isOk());

        // Validate the Participantes in the database
        List<Participantes> participantesList = participantesRepository.findAll();
        assertThat(participantesList).hasSize(databaseSizeBeforeUpdate);
        Participantes testParticipantes = participantesList.get(participantesList.size() - 1);
        assertThat(testParticipantes.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testParticipantes.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testParticipantes.getCorreo()).isEqualTo(UPDATED_CORREO);
    }

    @Test
    @Transactional
    void patchNonExistingParticipantes() throws Exception {
        int databaseSizeBeforeUpdate = participantesRepository.findAll().size();
        participantes.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParticipantesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, participantes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(participantes))
            )
            .andExpect(status().isBadRequest());

        // Validate the Participantes in the database
        List<Participantes> participantesList = participantesRepository.findAll();
        assertThat(participantesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchParticipantes() throws Exception {
        int databaseSizeBeforeUpdate = participantesRepository.findAll().size();
        participantes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParticipantesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(participantes))
            )
            .andExpect(status().isBadRequest());

        // Validate the Participantes in the database
        List<Participantes> participantesList = participantesRepository.findAll();
        assertThat(participantesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamParticipantes() throws Exception {
        int databaseSizeBeforeUpdate = participantesRepository.findAll().size();
        participantes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParticipantesMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(participantes))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Participantes in the database
        List<Participantes> participantesList = participantesRepository.findAll();
        assertThat(participantesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteParticipantes() throws Exception {
        // Initialize the database
        participantesRepository.saveAndFlush(participantes);

        int databaseSizeBeforeDelete = participantesRepository.findAll().size();

        // Delete the participantes
        restParticipantesMockMvc
            .perform(delete(ENTITY_API_URL_ID, participantes.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Participantes> participantesList = participantesRepository.findAll();
        assertThat(participantesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
