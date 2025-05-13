package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.ComenetariosIdea;
import com.mycompany.myapp.repository.ComenetariosIdeaRepository;
import com.mycompany.myapp.service.ComenetariosIdeaService;
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
 * Integration tests for the {@link ComenetariosIdeaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ComenetariosIdeaResourceIT {

    private static final String DEFAULT_COMENTARIO = "AAAAAAAAAA";
    private static final String UPDATED_COMENTARIO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_INSCRIPCION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_INSCRIPCION = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/comenetarios-ideas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ComenetariosIdeaRepository comenetariosIdeaRepository;

    @Mock
    private ComenetariosIdeaRepository comenetariosIdeaRepositoryMock;

    @Mock
    private ComenetariosIdeaService comenetariosIdeaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restComenetariosIdeaMockMvc;

    private ComenetariosIdea comenetariosIdea;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ComenetariosIdea createEntity(EntityManager em) {
        ComenetariosIdea comenetariosIdea = new ComenetariosIdea()
            .comentario(DEFAULT_COMENTARIO)
            .fechaInscripcion(DEFAULT_FECHA_INSCRIPCION);
        return comenetariosIdea;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ComenetariosIdea createUpdatedEntity(EntityManager em) {
        ComenetariosIdea comenetariosIdea = new ComenetariosIdea()
            .comentario(UPDATED_COMENTARIO)
            .fechaInscripcion(UPDATED_FECHA_INSCRIPCION);
        return comenetariosIdea;
    }

    @BeforeEach
    public void initTest() {
        comenetariosIdea = createEntity(em);
    }

    @Test
    @Transactional
    void createComenetariosIdea() throws Exception {
        int databaseSizeBeforeCreate = comenetariosIdeaRepository.findAll().size();
        // Create the ComenetariosIdea
        restComenetariosIdeaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(comenetariosIdea))
            )
            .andExpect(status().isCreated());

        // Validate the ComenetariosIdea in the database
        List<ComenetariosIdea> comenetariosIdeaList = comenetariosIdeaRepository.findAll();
        assertThat(comenetariosIdeaList).hasSize(databaseSizeBeforeCreate + 1);
        ComenetariosIdea testComenetariosIdea = comenetariosIdeaList.get(comenetariosIdeaList.size() - 1);
        assertThat(testComenetariosIdea.getComentario()).isEqualTo(DEFAULT_COMENTARIO);
        assertThat(testComenetariosIdea.getFechaInscripcion()).isEqualTo(DEFAULT_FECHA_INSCRIPCION);
    }

    @Test
    @Transactional
    void createComenetariosIdeaWithExistingId() throws Exception {
        // Create the ComenetariosIdea with an existing ID
        comenetariosIdea.setId(1L);

        int databaseSizeBeforeCreate = comenetariosIdeaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restComenetariosIdeaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(comenetariosIdea))
            )
            .andExpect(status().isBadRequest());

        // Validate the ComenetariosIdea in the database
        List<ComenetariosIdea> comenetariosIdeaList = comenetariosIdeaRepository.findAll();
        assertThat(comenetariosIdeaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFechaInscripcionIsRequired() throws Exception {
        int databaseSizeBeforeTest = comenetariosIdeaRepository.findAll().size();
        // set the field null
        comenetariosIdea.setFechaInscripcion(null);

        // Create the ComenetariosIdea, which fails.

        restComenetariosIdeaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(comenetariosIdea))
            )
            .andExpect(status().isBadRequest());

        List<ComenetariosIdea> comenetariosIdeaList = comenetariosIdeaRepository.findAll();
        assertThat(comenetariosIdeaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllComenetariosIdeas() throws Exception {
        // Initialize the database
        comenetariosIdeaRepository.saveAndFlush(comenetariosIdea);

        // Get all the comenetariosIdeaList
        restComenetariosIdeaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comenetariosIdea.getId().intValue())))
            .andExpect(jsonPath("$.[*].comentario").value(hasItem(DEFAULT_COMENTARIO.toString())))
            .andExpect(jsonPath("$.[*].fechaInscripcion").value(hasItem(DEFAULT_FECHA_INSCRIPCION.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllComenetariosIdeasWithEagerRelationshipsIsEnabled() throws Exception {
        when(comenetariosIdeaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restComenetariosIdeaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(comenetariosIdeaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllComenetariosIdeasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(comenetariosIdeaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restComenetariosIdeaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(comenetariosIdeaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getComenetariosIdea() throws Exception {
        // Initialize the database
        comenetariosIdeaRepository.saveAndFlush(comenetariosIdea);

        // Get the comenetariosIdea
        restComenetariosIdeaMockMvc
            .perform(get(ENTITY_API_URL_ID, comenetariosIdea.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(comenetariosIdea.getId().intValue()))
            .andExpect(jsonPath("$.comentario").value(DEFAULT_COMENTARIO.toString()))
            .andExpect(jsonPath("$.fechaInscripcion").value(DEFAULT_FECHA_INSCRIPCION.toString()));
    }

    @Test
    @Transactional
    void getNonExistingComenetariosIdea() throws Exception {
        // Get the comenetariosIdea
        restComenetariosIdeaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewComenetariosIdea() throws Exception {
        // Initialize the database
        comenetariosIdeaRepository.saveAndFlush(comenetariosIdea);

        int databaseSizeBeforeUpdate = comenetariosIdeaRepository.findAll().size();

        // Update the comenetariosIdea
        ComenetariosIdea updatedComenetariosIdea = comenetariosIdeaRepository.findById(comenetariosIdea.getId()).get();
        // Disconnect from session so that the updates on updatedComenetariosIdea are not directly saved in db
        em.detach(updatedComenetariosIdea);
        updatedComenetariosIdea.comentario(UPDATED_COMENTARIO).fechaInscripcion(UPDATED_FECHA_INSCRIPCION);

        restComenetariosIdeaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedComenetariosIdea.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedComenetariosIdea))
            )
            .andExpect(status().isOk());

        // Validate the ComenetariosIdea in the database
        List<ComenetariosIdea> comenetariosIdeaList = comenetariosIdeaRepository.findAll();
        assertThat(comenetariosIdeaList).hasSize(databaseSizeBeforeUpdate);
        ComenetariosIdea testComenetariosIdea = comenetariosIdeaList.get(comenetariosIdeaList.size() - 1);
        assertThat(testComenetariosIdea.getComentario()).isEqualTo(UPDATED_COMENTARIO);
        assertThat(testComenetariosIdea.getFechaInscripcion()).isEqualTo(UPDATED_FECHA_INSCRIPCION);
    }

    @Test
    @Transactional
    void putNonExistingComenetariosIdea() throws Exception {
        int databaseSizeBeforeUpdate = comenetariosIdeaRepository.findAll().size();
        comenetariosIdea.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restComenetariosIdeaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, comenetariosIdea.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(comenetariosIdea))
            )
            .andExpect(status().isBadRequest());

        // Validate the ComenetariosIdea in the database
        List<ComenetariosIdea> comenetariosIdeaList = comenetariosIdeaRepository.findAll();
        assertThat(comenetariosIdeaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchComenetariosIdea() throws Exception {
        int databaseSizeBeforeUpdate = comenetariosIdeaRepository.findAll().size();
        comenetariosIdea.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restComenetariosIdeaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(comenetariosIdea))
            )
            .andExpect(status().isBadRequest());

        // Validate the ComenetariosIdea in the database
        List<ComenetariosIdea> comenetariosIdeaList = comenetariosIdeaRepository.findAll();
        assertThat(comenetariosIdeaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamComenetariosIdea() throws Exception {
        int databaseSizeBeforeUpdate = comenetariosIdeaRepository.findAll().size();
        comenetariosIdea.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restComenetariosIdeaMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(comenetariosIdea))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ComenetariosIdea in the database
        List<ComenetariosIdea> comenetariosIdeaList = comenetariosIdeaRepository.findAll();
        assertThat(comenetariosIdeaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateComenetariosIdeaWithPatch() throws Exception {
        // Initialize the database
        comenetariosIdeaRepository.saveAndFlush(comenetariosIdea);

        int databaseSizeBeforeUpdate = comenetariosIdeaRepository.findAll().size();

        // Update the comenetariosIdea using partial update
        ComenetariosIdea partialUpdatedComenetariosIdea = new ComenetariosIdea();
        partialUpdatedComenetariosIdea.setId(comenetariosIdea.getId());

        partialUpdatedComenetariosIdea.fechaInscripcion(UPDATED_FECHA_INSCRIPCION);

        restComenetariosIdeaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedComenetariosIdea.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedComenetariosIdea))
            )
            .andExpect(status().isOk());

        // Validate the ComenetariosIdea in the database
        List<ComenetariosIdea> comenetariosIdeaList = comenetariosIdeaRepository.findAll();
        assertThat(comenetariosIdeaList).hasSize(databaseSizeBeforeUpdate);
        ComenetariosIdea testComenetariosIdea = comenetariosIdeaList.get(comenetariosIdeaList.size() - 1);
        assertThat(testComenetariosIdea.getComentario()).isEqualTo(DEFAULT_COMENTARIO);
        assertThat(testComenetariosIdea.getFechaInscripcion()).isEqualTo(UPDATED_FECHA_INSCRIPCION);
    }

    @Test
    @Transactional
    void fullUpdateComenetariosIdeaWithPatch() throws Exception {
        // Initialize the database
        comenetariosIdeaRepository.saveAndFlush(comenetariosIdea);

        int databaseSizeBeforeUpdate = comenetariosIdeaRepository.findAll().size();

        // Update the comenetariosIdea using partial update
        ComenetariosIdea partialUpdatedComenetariosIdea = new ComenetariosIdea();
        partialUpdatedComenetariosIdea.setId(comenetariosIdea.getId());

        partialUpdatedComenetariosIdea.comentario(UPDATED_COMENTARIO).fechaInscripcion(UPDATED_FECHA_INSCRIPCION);

        restComenetariosIdeaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedComenetariosIdea.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedComenetariosIdea))
            )
            .andExpect(status().isOk());

        // Validate the ComenetariosIdea in the database
        List<ComenetariosIdea> comenetariosIdeaList = comenetariosIdeaRepository.findAll();
        assertThat(comenetariosIdeaList).hasSize(databaseSizeBeforeUpdate);
        ComenetariosIdea testComenetariosIdea = comenetariosIdeaList.get(comenetariosIdeaList.size() - 1);
        assertThat(testComenetariosIdea.getComentario()).isEqualTo(UPDATED_COMENTARIO);
        assertThat(testComenetariosIdea.getFechaInscripcion()).isEqualTo(UPDATED_FECHA_INSCRIPCION);
    }

    @Test
    @Transactional
    void patchNonExistingComenetariosIdea() throws Exception {
        int databaseSizeBeforeUpdate = comenetariosIdeaRepository.findAll().size();
        comenetariosIdea.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restComenetariosIdeaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, comenetariosIdea.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(comenetariosIdea))
            )
            .andExpect(status().isBadRequest());

        // Validate the ComenetariosIdea in the database
        List<ComenetariosIdea> comenetariosIdeaList = comenetariosIdeaRepository.findAll();
        assertThat(comenetariosIdeaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchComenetariosIdea() throws Exception {
        int databaseSizeBeforeUpdate = comenetariosIdeaRepository.findAll().size();
        comenetariosIdea.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restComenetariosIdeaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(comenetariosIdea))
            )
            .andExpect(status().isBadRequest());

        // Validate the ComenetariosIdea in the database
        List<ComenetariosIdea> comenetariosIdeaList = comenetariosIdeaRepository.findAll();
        assertThat(comenetariosIdeaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamComenetariosIdea() throws Exception {
        int databaseSizeBeforeUpdate = comenetariosIdeaRepository.findAll().size();
        comenetariosIdea.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restComenetariosIdeaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(comenetariosIdea))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ComenetariosIdea in the database
        List<ComenetariosIdea> comenetariosIdeaList = comenetariosIdeaRepository.findAll();
        assertThat(comenetariosIdeaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteComenetariosIdea() throws Exception {
        // Initialize the database
        comenetariosIdeaRepository.saveAndFlush(comenetariosIdea);

        int databaseSizeBeforeDelete = comenetariosIdeaRepository.findAll().size();

        // Delete the comenetariosIdea
        restComenetariosIdeaMockMvc
            .perform(delete(ENTITY_API_URL_ID, comenetariosIdea.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ComenetariosIdea> comenetariosIdeaList = comenetariosIdeaRepository.findAll();
        assertThat(comenetariosIdeaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
