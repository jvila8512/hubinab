package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Noticias;
import com.mycompany.myapp.repository.NoticiasRepository;
import com.mycompany.myapp.service.NoticiasService;
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
 * Integration tests for the {@link NoticiasResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class NoticiasResourceIT {

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_URL_FOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_URL_FOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_URL_FOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_URL_FOTO_CONTENT_TYPE = "image/png";

    private static final Boolean DEFAULT_PUBLICA = false;
    private static final Boolean UPDATED_PUBLICA = true;

    private static final Boolean DEFAULT_PUBLICAR = false;
    private static final Boolean UPDATED_PUBLICAR = true;

    private static final LocalDate DEFAULT_FECHA_CREADA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_CREADA = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/noticias";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NoticiasRepository noticiasRepository;

    @Mock
    private NoticiasRepository noticiasRepositoryMock;

    @Mock
    private NoticiasService noticiasServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNoticiasMockMvc;

    private Noticias noticias;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Noticias createEntity(EntityManager em) {
        Noticias noticias = new Noticias()
            .titulo(DEFAULT_TITULO)
            .descripcion(DEFAULT_DESCRIPCION)
            .urlFoto(DEFAULT_URL_FOTO)
            .urlFotoContentType(DEFAULT_URL_FOTO_CONTENT_TYPE)
            .publica(DEFAULT_PUBLICA)
            .publicar(DEFAULT_PUBLICAR)
            .fechaCreada(DEFAULT_FECHA_CREADA);
        return noticias;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Noticias createUpdatedEntity(EntityManager em) {
        Noticias noticias = new Noticias()
            .titulo(UPDATED_TITULO)
            .descripcion(UPDATED_DESCRIPCION)
            .urlFoto(UPDATED_URL_FOTO)
            .urlFotoContentType(UPDATED_URL_FOTO_CONTENT_TYPE)
            .publica(UPDATED_PUBLICA)
            .publicar(UPDATED_PUBLICAR)
            .fechaCreada(UPDATED_FECHA_CREADA);
        return noticias;
    }

    @BeforeEach
    public void initTest() {
        noticias = createEntity(em);
    }

    @Test
    @Transactional
    void createNoticias() throws Exception {
        int databaseSizeBeforeCreate = noticiasRepository.findAll().size();
        // Create the Noticias
        restNoticiasMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(noticias)))
            .andExpect(status().isCreated());

        // Validate the Noticias in the database
        List<Noticias> noticiasList = noticiasRepository.findAll();
        assertThat(noticiasList).hasSize(databaseSizeBeforeCreate + 1);
        Noticias testNoticias = noticiasList.get(noticiasList.size() - 1);
        assertThat(testNoticias.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testNoticias.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testNoticias.getUrlFoto()).isEqualTo(DEFAULT_URL_FOTO);
        assertThat(testNoticias.getUrlFotoContentType()).isEqualTo(DEFAULT_URL_FOTO_CONTENT_TYPE);
        assertThat(testNoticias.getPublica()).isEqualTo(DEFAULT_PUBLICA);
        assertThat(testNoticias.getPublicar()).isEqualTo(DEFAULT_PUBLICAR);
        assertThat(testNoticias.getFechaCreada()).isEqualTo(DEFAULT_FECHA_CREADA);
    }

    @Test
    @Transactional
    void createNoticiasWithExistingId() throws Exception {
        // Create the Noticias with an existing ID
        noticias.setId(1L);

        int databaseSizeBeforeCreate = noticiasRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNoticiasMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(noticias)))
            .andExpect(status().isBadRequest());

        // Validate the Noticias in the database
        List<Noticias> noticiasList = noticiasRepository.findAll();
        assertThat(noticiasList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTituloIsRequired() throws Exception {
        int databaseSizeBeforeTest = noticiasRepository.findAll().size();
        // set the field null
        noticias.setTitulo(null);

        // Create the Noticias, which fails.

        restNoticiasMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(noticias)))
            .andExpect(status().isBadRequest());

        List<Noticias> noticiasList = noticiasRepository.findAll();
        assertThat(noticiasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllNoticias() throws Exception {
        // Initialize the database
        noticiasRepository.saveAndFlush(noticias);

        // Get all the noticiasList
        restNoticiasMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(noticias.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].urlFotoContentType").value(hasItem(DEFAULT_URL_FOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].urlFoto").value(hasItem(Base64Utils.encodeToString(DEFAULT_URL_FOTO))))
            .andExpect(jsonPath("$.[*].publica").value(hasItem(DEFAULT_PUBLICA.booleanValue())))
            .andExpect(jsonPath("$.[*].publicar").value(hasItem(DEFAULT_PUBLICAR.booleanValue())))
            .andExpect(jsonPath("$.[*].fechaCreada").value(hasItem(DEFAULT_FECHA_CREADA.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllNoticiasWithEagerRelationshipsIsEnabled() throws Exception {
        when(noticiasServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restNoticiasMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(noticiasServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllNoticiasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(noticiasServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restNoticiasMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(noticiasServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getNoticias() throws Exception {
        // Initialize the database
        noticiasRepository.saveAndFlush(noticias);

        // Get the noticias
        restNoticiasMockMvc
            .perform(get(ENTITY_API_URL_ID, noticias.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(noticias.getId().intValue()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.urlFotoContentType").value(DEFAULT_URL_FOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.urlFoto").value(Base64Utils.encodeToString(DEFAULT_URL_FOTO)))
            .andExpect(jsonPath("$.publica").value(DEFAULT_PUBLICA.booleanValue()))
            .andExpect(jsonPath("$.publicar").value(DEFAULT_PUBLICAR.booleanValue()))
            .andExpect(jsonPath("$.fechaCreada").value(DEFAULT_FECHA_CREADA.toString()));
    }

    @Test
    @Transactional
    void getNonExistingNoticias() throws Exception {
        // Get the noticias
        restNoticiasMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewNoticias() throws Exception {
        // Initialize the database
        noticiasRepository.saveAndFlush(noticias);

        int databaseSizeBeforeUpdate = noticiasRepository.findAll().size();

        // Update the noticias
        Noticias updatedNoticias = noticiasRepository.findById(noticias.getId()).get();
        // Disconnect from session so that the updates on updatedNoticias are not directly saved in db
        em.detach(updatedNoticias);
        updatedNoticias
            .titulo(UPDATED_TITULO)
            .descripcion(UPDATED_DESCRIPCION)
            .urlFoto(UPDATED_URL_FOTO)
            .urlFotoContentType(UPDATED_URL_FOTO_CONTENT_TYPE)
            .publica(UPDATED_PUBLICA)
            .publicar(UPDATED_PUBLICAR)
            .fechaCreada(UPDATED_FECHA_CREADA);

        restNoticiasMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNoticias.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedNoticias))
            )
            .andExpect(status().isOk());

        // Validate the Noticias in the database
        List<Noticias> noticiasList = noticiasRepository.findAll();
        assertThat(noticiasList).hasSize(databaseSizeBeforeUpdate);
        Noticias testNoticias = noticiasList.get(noticiasList.size() - 1);
        assertThat(testNoticias.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testNoticias.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testNoticias.getUrlFoto()).isEqualTo(UPDATED_URL_FOTO);
        assertThat(testNoticias.getUrlFotoContentType()).isEqualTo(UPDATED_URL_FOTO_CONTENT_TYPE);
        assertThat(testNoticias.getPublica()).isEqualTo(UPDATED_PUBLICA);
        assertThat(testNoticias.getPublicar()).isEqualTo(UPDATED_PUBLICAR);
        assertThat(testNoticias.getFechaCreada()).isEqualTo(UPDATED_FECHA_CREADA);
    }

    @Test
    @Transactional
    void putNonExistingNoticias() throws Exception {
        int databaseSizeBeforeUpdate = noticiasRepository.findAll().size();
        noticias.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNoticiasMockMvc
            .perform(
                put(ENTITY_API_URL_ID, noticias.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(noticias))
            )
            .andExpect(status().isBadRequest());

        // Validate the Noticias in the database
        List<Noticias> noticiasList = noticiasRepository.findAll();
        assertThat(noticiasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNoticias() throws Exception {
        int databaseSizeBeforeUpdate = noticiasRepository.findAll().size();
        noticias.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNoticiasMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(noticias))
            )
            .andExpect(status().isBadRequest());

        // Validate the Noticias in the database
        List<Noticias> noticiasList = noticiasRepository.findAll();
        assertThat(noticiasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNoticias() throws Exception {
        int databaseSizeBeforeUpdate = noticiasRepository.findAll().size();
        noticias.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNoticiasMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(noticias)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Noticias in the database
        List<Noticias> noticiasList = noticiasRepository.findAll();
        assertThat(noticiasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNoticiasWithPatch() throws Exception {
        // Initialize the database
        noticiasRepository.saveAndFlush(noticias);

        int databaseSizeBeforeUpdate = noticiasRepository.findAll().size();

        // Update the noticias using partial update
        Noticias partialUpdatedNoticias = new Noticias();
        partialUpdatedNoticias.setId(noticias.getId());

        partialUpdatedNoticias.descripcion(UPDATED_DESCRIPCION).publica(UPDATED_PUBLICA);

        restNoticiasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNoticias.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNoticias))
            )
            .andExpect(status().isOk());

        // Validate the Noticias in the database
        List<Noticias> noticiasList = noticiasRepository.findAll();
        assertThat(noticiasList).hasSize(databaseSizeBeforeUpdate);
        Noticias testNoticias = noticiasList.get(noticiasList.size() - 1);
        assertThat(testNoticias.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testNoticias.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testNoticias.getUrlFoto()).isEqualTo(DEFAULT_URL_FOTO);
        assertThat(testNoticias.getUrlFotoContentType()).isEqualTo(DEFAULT_URL_FOTO_CONTENT_TYPE);
        assertThat(testNoticias.getPublica()).isEqualTo(UPDATED_PUBLICA);
        assertThat(testNoticias.getPublicar()).isEqualTo(DEFAULT_PUBLICAR);
        assertThat(testNoticias.getFechaCreada()).isEqualTo(DEFAULT_FECHA_CREADA);
    }

    @Test
    @Transactional
    void fullUpdateNoticiasWithPatch() throws Exception {
        // Initialize the database
        noticiasRepository.saveAndFlush(noticias);

        int databaseSizeBeforeUpdate = noticiasRepository.findAll().size();

        // Update the noticias using partial update
        Noticias partialUpdatedNoticias = new Noticias();
        partialUpdatedNoticias.setId(noticias.getId());

        partialUpdatedNoticias
            .titulo(UPDATED_TITULO)
            .descripcion(UPDATED_DESCRIPCION)
            .urlFoto(UPDATED_URL_FOTO)
            .urlFotoContentType(UPDATED_URL_FOTO_CONTENT_TYPE)
            .publica(UPDATED_PUBLICA)
            .publicar(UPDATED_PUBLICAR)
            .fechaCreada(UPDATED_FECHA_CREADA);

        restNoticiasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNoticias.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNoticias))
            )
            .andExpect(status().isOk());

        // Validate the Noticias in the database
        List<Noticias> noticiasList = noticiasRepository.findAll();
        assertThat(noticiasList).hasSize(databaseSizeBeforeUpdate);
        Noticias testNoticias = noticiasList.get(noticiasList.size() - 1);
        assertThat(testNoticias.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testNoticias.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testNoticias.getUrlFoto()).isEqualTo(UPDATED_URL_FOTO);
        assertThat(testNoticias.getUrlFotoContentType()).isEqualTo(UPDATED_URL_FOTO_CONTENT_TYPE);
        assertThat(testNoticias.getPublica()).isEqualTo(UPDATED_PUBLICA);
        assertThat(testNoticias.getPublicar()).isEqualTo(UPDATED_PUBLICAR);
        assertThat(testNoticias.getFechaCreada()).isEqualTo(UPDATED_FECHA_CREADA);
    }

    @Test
    @Transactional
    void patchNonExistingNoticias() throws Exception {
        int databaseSizeBeforeUpdate = noticiasRepository.findAll().size();
        noticias.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNoticiasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, noticias.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(noticias))
            )
            .andExpect(status().isBadRequest());

        // Validate the Noticias in the database
        List<Noticias> noticiasList = noticiasRepository.findAll();
        assertThat(noticiasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNoticias() throws Exception {
        int databaseSizeBeforeUpdate = noticiasRepository.findAll().size();
        noticias.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNoticiasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(noticias))
            )
            .andExpect(status().isBadRequest());

        // Validate the Noticias in the database
        List<Noticias> noticiasList = noticiasRepository.findAll();
        assertThat(noticiasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNoticias() throws Exception {
        int databaseSizeBeforeUpdate = noticiasRepository.findAll().size();
        noticias.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNoticiasMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(noticias)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Noticias in the database
        List<Noticias> noticiasList = noticiasRepository.findAll();
        assertThat(noticiasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNoticias() throws Exception {
        // Initialize the database
        noticiasRepository.saveAndFlush(noticias);

        int databaseSizeBeforeDelete = noticiasRepository.findAll().size();

        // Delete the noticias
        restNoticiasMockMvc
            .perform(delete(ENTITY_API_URL_ID, noticias.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Noticias> noticiasList = noticiasRepository.findAll();
        assertThat(noticiasList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
