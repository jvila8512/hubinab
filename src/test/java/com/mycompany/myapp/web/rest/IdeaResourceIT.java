package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Idea;
import com.mycompany.myapp.repository.IdeaRepository;
import com.mycompany.myapp.service.IdeaService;
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
 * Integration tests for the {@link IdeaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class IdeaResourceIT {

    private static final Integer DEFAULT_NUMERO_REGISTRO = 1;
    private static final Integer UPDATED_NUMERO_REGISTRO = 2;

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String DEFAULT_AUTOR = "AAAAAAAAAA";
    private static final String UPDATED_AUTOR = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_INSCRIPCION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_INSCRIPCION = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_VISTO = 1;
    private static final Integer UPDATED_VISTO = 2;

    private static final byte[] DEFAULT_FOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FOTO_CONTENT_TYPE = "image/png";

    private static final Boolean DEFAULT_ACEPTADA = false;
    private static final Boolean UPDATED_ACEPTADA = true;

    private static final Boolean DEFAULT_PUBLICA = false;
    private static final Boolean UPDATED_PUBLICA = true;

    private static final String ENTITY_API_URL = "/api/ideas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private IdeaRepository ideaRepository;

    @Mock
    private IdeaRepository ideaRepositoryMock;

    @Mock
    private IdeaService ideaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIdeaMockMvc;

    private Idea idea;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Idea createEntity(EntityManager em) {
        Idea idea = new Idea()
            .numeroRegistro(DEFAULT_NUMERO_REGISTRO)
            .titulo(DEFAULT_TITULO)
            .descripcion(DEFAULT_DESCRIPCION)
            .autor(DEFAULT_AUTOR)
            .fechaInscripcion(DEFAULT_FECHA_INSCRIPCION)
            .visto(DEFAULT_VISTO)
            .foto(DEFAULT_FOTO)
            .fotoContentType(DEFAULT_FOTO_CONTENT_TYPE)
            .aceptada(DEFAULT_ACEPTADA)
            .publica(DEFAULT_PUBLICA);
        return idea;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Idea createUpdatedEntity(EntityManager em) {
        Idea idea = new Idea()
            .numeroRegistro(UPDATED_NUMERO_REGISTRO)
            .titulo(UPDATED_TITULO)
            .descripcion(UPDATED_DESCRIPCION)
            .autor(UPDATED_AUTOR)
            .fechaInscripcion(UPDATED_FECHA_INSCRIPCION)
            .visto(UPDATED_VISTO)
            .foto(UPDATED_FOTO)
            .fotoContentType(UPDATED_FOTO_CONTENT_TYPE)
            .aceptada(UPDATED_ACEPTADA)
            .publica(UPDATED_PUBLICA);
        return idea;
    }

    @BeforeEach
    public void initTest() {
        idea = createEntity(em);
    }

    @Test
    @Transactional
    void createIdea() throws Exception {
        int databaseSizeBeforeCreate = ideaRepository.findAll().size();
        // Create the Idea
        restIdeaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(idea)))
            .andExpect(status().isCreated());

        // Validate the Idea in the database
        List<Idea> ideaList = ideaRepository.findAll();
        assertThat(ideaList).hasSize(databaseSizeBeforeCreate + 1);
        Idea testIdea = ideaList.get(ideaList.size() - 1);
        assertThat(testIdea.getNumeroRegistro()).isEqualTo(DEFAULT_NUMERO_REGISTRO);
        assertThat(testIdea.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testIdea.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testIdea.getAutor()).isEqualTo(DEFAULT_AUTOR);
        assertThat(testIdea.getFechaInscripcion()).isEqualTo(DEFAULT_FECHA_INSCRIPCION);
        assertThat(testIdea.getVisto()).isEqualTo(DEFAULT_VISTO);
        assertThat(testIdea.getFoto()).isEqualTo(DEFAULT_FOTO);
        assertThat(testIdea.getFotoContentType()).isEqualTo(DEFAULT_FOTO_CONTENT_TYPE);
        assertThat(testIdea.getAceptada()).isEqualTo(DEFAULT_ACEPTADA);
        assertThat(testIdea.getPublica()).isEqualTo(DEFAULT_PUBLICA);
    }

    @Test
    @Transactional
    void createIdeaWithExistingId() throws Exception {
        // Create the Idea with an existing ID
        idea.setId(1L);

        int databaseSizeBeforeCreate = ideaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIdeaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(idea)))
            .andExpect(status().isBadRequest());

        // Validate the Idea in the database
        List<Idea> ideaList = ideaRepository.findAll();
        assertThat(ideaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNumeroRegistroIsRequired() throws Exception {
        int databaseSizeBeforeTest = ideaRepository.findAll().size();
        // set the field null
        idea.setNumeroRegistro(null);

        // Create the Idea, which fails.

        restIdeaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(idea)))
            .andExpect(status().isBadRequest());

        List<Idea> ideaList = ideaRepository.findAll();
        assertThat(ideaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTituloIsRequired() throws Exception {
        int databaseSizeBeforeTest = ideaRepository.findAll().size();
        // set the field null
        idea.setTitulo(null);

        // Create the Idea, which fails.

        restIdeaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(idea)))
            .andExpect(status().isBadRequest());

        List<Idea> ideaList = ideaRepository.findAll();
        assertThat(ideaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAutorIsRequired() throws Exception {
        int databaseSizeBeforeTest = ideaRepository.findAll().size();
        // set the field null
        idea.setAutor(null);

        // Create the Idea, which fails.

        restIdeaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(idea)))
            .andExpect(status().isBadRequest());

        List<Idea> ideaList = ideaRepository.findAll();
        assertThat(ideaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFechaInscripcionIsRequired() throws Exception {
        int databaseSizeBeforeTest = ideaRepository.findAll().size();
        // set the field null
        idea.setFechaInscripcion(null);

        // Create the Idea, which fails.

        restIdeaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(idea)))
            .andExpect(status().isBadRequest());

        List<Idea> ideaList = ideaRepository.findAll();
        assertThat(ideaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllIdeas() throws Exception {
        // Initialize the database
        ideaRepository.saveAndFlush(idea);

        // Get all the ideaList
        restIdeaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(idea.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroRegistro").value(hasItem(DEFAULT_NUMERO_REGISTRO)))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].autor").value(hasItem(DEFAULT_AUTOR)))
            .andExpect(jsonPath("$.[*].fechaInscripcion").value(hasItem(DEFAULT_FECHA_INSCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].visto").value(hasItem(DEFAULT_VISTO)))
            .andExpect(jsonPath("$.[*].fotoContentType").value(hasItem(DEFAULT_FOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].foto").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTO))))
            .andExpect(jsonPath("$.[*].aceptada").value(hasItem(DEFAULT_ACEPTADA.booleanValue())))
            .andExpect(jsonPath("$.[*].publica").value(hasItem(DEFAULT_PUBLICA.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllIdeasWithEagerRelationshipsIsEnabled() throws Exception {
        when(ideaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restIdeaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(ideaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllIdeasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(ideaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restIdeaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(ideaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getIdea() throws Exception {
        // Initialize the database
        ideaRepository.saveAndFlush(idea);

        // Get the idea
        restIdeaMockMvc
            .perform(get(ENTITY_API_URL_ID, idea.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(idea.getId().intValue()))
            .andExpect(jsonPath("$.numeroRegistro").value(DEFAULT_NUMERO_REGISTRO))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.autor").value(DEFAULT_AUTOR))
            .andExpect(jsonPath("$.fechaInscripcion").value(DEFAULT_FECHA_INSCRIPCION.toString()))
            .andExpect(jsonPath("$.visto").value(DEFAULT_VISTO))
            .andExpect(jsonPath("$.fotoContentType").value(DEFAULT_FOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.foto").value(Base64Utils.encodeToString(DEFAULT_FOTO)))
            .andExpect(jsonPath("$.aceptada").value(DEFAULT_ACEPTADA.booleanValue()))
            .andExpect(jsonPath("$.publica").value(DEFAULT_PUBLICA.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingIdea() throws Exception {
        // Get the idea
        restIdeaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewIdea() throws Exception {
        // Initialize the database
        ideaRepository.saveAndFlush(idea);

        int databaseSizeBeforeUpdate = ideaRepository.findAll().size();

        // Update the idea
        Idea updatedIdea = ideaRepository.findById(idea.getId()).get();
        // Disconnect from session so that the updates on updatedIdea are not directly saved in db
        em.detach(updatedIdea);
        updatedIdea
            .numeroRegistro(UPDATED_NUMERO_REGISTRO)
            .titulo(UPDATED_TITULO)
            .descripcion(UPDATED_DESCRIPCION)
            .autor(UPDATED_AUTOR)
            .fechaInscripcion(UPDATED_FECHA_INSCRIPCION)
            .visto(UPDATED_VISTO)
            .foto(UPDATED_FOTO)
            .fotoContentType(UPDATED_FOTO_CONTENT_TYPE)
            .aceptada(UPDATED_ACEPTADA)
            .publica(UPDATED_PUBLICA);

        restIdeaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedIdea.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedIdea))
            )
            .andExpect(status().isOk());

        // Validate the Idea in the database
        List<Idea> ideaList = ideaRepository.findAll();
        assertThat(ideaList).hasSize(databaseSizeBeforeUpdate);
        Idea testIdea = ideaList.get(ideaList.size() - 1);
        assertThat(testIdea.getNumeroRegistro()).isEqualTo(UPDATED_NUMERO_REGISTRO);
        assertThat(testIdea.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testIdea.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testIdea.getAutor()).isEqualTo(UPDATED_AUTOR);
        assertThat(testIdea.getFechaInscripcion()).isEqualTo(UPDATED_FECHA_INSCRIPCION);
        assertThat(testIdea.getVisto()).isEqualTo(UPDATED_VISTO);
        assertThat(testIdea.getFoto()).isEqualTo(UPDATED_FOTO);
        assertThat(testIdea.getFotoContentType()).isEqualTo(UPDATED_FOTO_CONTENT_TYPE);
        assertThat(testIdea.getAceptada()).isEqualTo(UPDATED_ACEPTADA);
        assertThat(testIdea.getPublica()).isEqualTo(UPDATED_PUBLICA);
    }

    @Test
    @Transactional
    void putNonExistingIdea() throws Exception {
        int databaseSizeBeforeUpdate = ideaRepository.findAll().size();
        idea.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIdeaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, idea.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(idea))
            )
            .andExpect(status().isBadRequest());

        // Validate the Idea in the database
        List<Idea> ideaList = ideaRepository.findAll();
        assertThat(ideaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIdea() throws Exception {
        int databaseSizeBeforeUpdate = ideaRepository.findAll().size();
        idea.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIdeaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(idea))
            )
            .andExpect(status().isBadRequest());

        // Validate the Idea in the database
        List<Idea> ideaList = ideaRepository.findAll();
        assertThat(ideaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIdea() throws Exception {
        int databaseSizeBeforeUpdate = ideaRepository.findAll().size();
        idea.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIdeaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(idea)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Idea in the database
        List<Idea> ideaList = ideaRepository.findAll();
        assertThat(ideaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIdeaWithPatch() throws Exception {
        // Initialize the database
        ideaRepository.saveAndFlush(idea);

        int databaseSizeBeforeUpdate = ideaRepository.findAll().size();

        // Update the idea using partial update
        Idea partialUpdatedIdea = new Idea();
        partialUpdatedIdea.setId(idea.getId());

        partialUpdatedIdea
            .numeroRegistro(UPDATED_NUMERO_REGISTRO)
            .titulo(UPDATED_TITULO)
            .descripcion(UPDATED_DESCRIPCION)
            .autor(UPDATED_AUTOR)
            .visto(UPDATED_VISTO)
            .foto(UPDATED_FOTO)
            .fotoContentType(UPDATED_FOTO_CONTENT_TYPE);

        restIdeaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIdea.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIdea))
            )
            .andExpect(status().isOk());

        // Validate the Idea in the database
        List<Idea> ideaList = ideaRepository.findAll();
        assertThat(ideaList).hasSize(databaseSizeBeforeUpdate);
        Idea testIdea = ideaList.get(ideaList.size() - 1);
        assertThat(testIdea.getNumeroRegistro()).isEqualTo(UPDATED_NUMERO_REGISTRO);
        assertThat(testIdea.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testIdea.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testIdea.getAutor()).isEqualTo(UPDATED_AUTOR);
        assertThat(testIdea.getFechaInscripcion()).isEqualTo(DEFAULT_FECHA_INSCRIPCION);
        assertThat(testIdea.getVisto()).isEqualTo(UPDATED_VISTO);
        assertThat(testIdea.getFoto()).isEqualTo(UPDATED_FOTO);
        assertThat(testIdea.getFotoContentType()).isEqualTo(UPDATED_FOTO_CONTENT_TYPE);
        assertThat(testIdea.getAceptada()).isEqualTo(DEFAULT_ACEPTADA);
        assertThat(testIdea.getPublica()).isEqualTo(DEFAULT_PUBLICA);
    }

    @Test
    @Transactional
    void fullUpdateIdeaWithPatch() throws Exception {
        // Initialize the database
        ideaRepository.saveAndFlush(idea);

        int databaseSizeBeforeUpdate = ideaRepository.findAll().size();

        // Update the idea using partial update
        Idea partialUpdatedIdea = new Idea();
        partialUpdatedIdea.setId(idea.getId());

        partialUpdatedIdea
            .numeroRegistro(UPDATED_NUMERO_REGISTRO)
            .titulo(UPDATED_TITULO)
            .descripcion(UPDATED_DESCRIPCION)
            .autor(UPDATED_AUTOR)
            .fechaInscripcion(UPDATED_FECHA_INSCRIPCION)
            .visto(UPDATED_VISTO)
            .foto(UPDATED_FOTO)
            .fotoContentType(UPDATED_FOTO_CONTENT_TYPE)
            .aceptada(UPDATED_ACEPTADA)
            .publica(UPDATED_PUBLICA);

        restIdeaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIdea.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIdea))
            )
            .andExpect(status().isOk());

        // Validate the Idea in the database
        List<Idea> ideaList = ideaRepository.findAll();
        assertThat(ideaList).hasSize(databaseSizeBeforeUpdate);
        Idea testIdea = ideaList.get(ideaList.size() - 1);
        assertThat(testIdea.getNumeroRegistro()).isEqualTo(UPDATED_NUMERO_REGISTRO);
        assertThat(testIdea.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testIdea.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testIdea.getAutor()).isEqualTo(UPDATED_AUTOR);
        assertThat(testIdea.getFechaInscripcion()).isEqualTo(UPDATED_FECHA_INSCRIPCION);
        assertThat(testIdea.getVisto()).isEqualTo(UPDATED_VISTO);
        assertThat(testIdea.getFoto()).isEqualTo(UPDATED_FOTO);
        assertThat(testIdea.getFotoContentType()).isEqualTo(UPDATED_FOTO_CONTENT_TYPE);
        assertThat(testIdea.getAceptada()).isEqualTo(UPDATED_ACEPTADA);
        assertThat(testIdea.getPublica()).isEqualTo(UPDATED_PUBLICA);
    }

    @Test
    @Transactional
    void patchNonExistingIdea() throws Exception {
        int databaseSizeBeforeUpdate = ideaRepository.findAll().size();
        idea.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIdeaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, idea.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(idea))
            )
            .andExpect(status().isBadRequest());

        // Validate the Idea in the database
        List<Idea> ideaList = ideaRepository.findAll();
        assertThat(ideaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIdea() throws Exception {
        int databaseSizeBeforeUpdate = ideaRepository.findAll().size();
        idea.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIdeaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(idea))
            )
            .andExpect(status().isBadRequest());

        // Validate the Idea in the database
        List<Idea> ideaList = ideaRepository.findAll();
        assertThat(ideaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIdea() throws Exception {
        int databaseSizeBeforeUpdate = ideaRepository.findAll().size();
        idea.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIdeaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(idea)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Idea in the database
        List<Idea> ideaList = ideaRepository.findAll();
        assertThat(ideaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIdea() throws Exception {
        // Initialize the database
        ideaRepository.saveAndFlush(idea);

        int databaseSizeBeforeDelete = ideaRepository.findAll().size();

        // Delete the idea
        restIdeaMockMvc
            .perform(delete(ENTITY_API_URL_ID, idea.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Idea> ideaList = ideaRepository.findAll();
        assertThat(ideaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
