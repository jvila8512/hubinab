package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Servicios;
import com.mycompany.myapp.repository.ServiciosRepository;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link ServiciosResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ServiciosResourceIT {

    private static final String DEFAULT_SERVICIO = "AAAAAAAAAA";
    private static final String UPDATED_SERVICIO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PUBLICADO = false;
    private static final Boolean UPDATED_PUBLICADO = true;

    private static final byte[] DEFAULT_FOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FOTO_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/servicios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ServiciosRepository serviciosRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServiciosMockMvc;

    private Servicios servicios;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Servicios createEntity(EntityManager em) {
        Servicios servicios = new Servicios()
            .servicio(DEFAULT_SERVICIO)
            .descripcion(DEFAULT_DESCRIPCION)
            .publicado(DEFAULT_PUBLICADO)
            .foto(DEFAULT_FOTO)
            .fotoContentType(DEFAULT_FOTO_CONTENT_TYPE);
        return servicios;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Servicios createUpdatedEntity(EntityManager em) {
        Servicios servicios = new Servicios()
            .servicio(UPDATED_SERVICIO)
            .descripcion(UPDATED_DESCRIPCION)
            .publicado(UPDATED_PUBLICADO)
            .foto(UPDATED_FOTO)
            .fotoContentType(UPDATED_FOTO_CONTENT_TYPE);
        return servicios;
    }

    @BeforeEach
    public void initTest() {
        servicios = createEntity(em);
    }

    @Test
    @Transactional
    void createServicios() throws Exception {
        int databaseSizeBeforeCreate = serviciosRepository.findAll().size();
        // Create the Servicios
        restServiciosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(servicios)))
            .andExpect(status().isCreated());

        // Validate the Servicios in the database
        List<Servicios> serviciosList = serviciosRepository.findAll();
        assertThat(serviciosList).hasSize(databaseSizeBeforeCreate + 1);
        Servicios testServicios = serviciosList.get(serviciosList.size() - 1);
        assertThat(testServicios.getServicio()).isEqualTo(DEFAULT_SERVICIO);
        assertThat(testServicios.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testServicios.getPublicado()).isEqualTo(DEFAULT_PUBLICADO);
        assertThat(testServicios.getFoto()).isEqualTo(DEFAULT_FOTO);
        assertThat(testServicios.getFotoContentType()).isEqualTo(DEFAULT_FOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createServiciosWithExistingId() throws Exception {
        // Create the Servicios with an existing ID
        servicios.setId(1L);

        int databaseSizeBeforeCreate = serviciosRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiciosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(servicios)))
            .andExpect(status().isBadRequest());

        // Validate the Servicios in the database
        List<Servicios> serviciosList = serviciosRepository.findAll();
        assertThat(serviciosList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkServicioIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviciosRepository.findAll().size();
        // set the field null
        servicios.setServicio(null);

        // Create the Servicios, which fails.

        restServiciosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(servicios)))
            .andExpect(status().isBadRequest());

        List<Servicios> serviciosList = serviciosRepository.findAll();
        assertThat(serviciosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllServicios() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList
        restServiciosMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(servicios.getId().intValue())))
            .andExpect(jsonPath("$.[*].servicio").value(hasItem(DEFAULT_SERVICIO)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].publicado").value(hasItem(DEFAULT_PUBLICADO.booleanValue())))
            .andExpect(jsonPath("$.[*].fotoContentType").value(hasItem(DEFAULT_FOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].foto").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTO))));
    }

    @Test
    @Transactional
    void getServicios() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get the servicios
        restServiciosMockMvc
            .perform(get(ENTITY_API_URL_ID, servicios.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(servicios.getId().intValue()))
            .andExpect(jsonPath("$.servicio").value(DEFAULT_SERVICIO))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.publicado").value(DEFAULT_PUBLICADO.booleanValue()))
            .andExpect(jsonPath("$.fotoContentType").value(DEFAULT_FOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.foto").value(Base64Utils.encodeToString(DEFAULT_FOTO)));
    }

    @Test
    @Transactional
    void getNonExistingServicios() throws Exception {
        // Get the servicios
        restServiciosMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewServicios() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        int databaseSizeBeforeUpdate = serviciosRepository.findAll().size();

        // Update the servicios
        Servicios updatedServicios = serviciosRepository.findById(servicios.getId()).get();
        // Disconnect from session so that the updates on updatedServicios are not directly saved in db
        em.detach(updatedServicios);
        updatedServicios
            .servicio(UPDATED_SERVICIO)
            .descripcion(UPDATED_DESCRIPCION)
            .publicado(UPDATED_PUBLICADO)
            .foto(UPDATED_FOTO)
            .fotoContentType(UPDATED_FOTO_CONTENT_TYPE);

        restServiciosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedServicios.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedServicios))
            )
            .andExpect(status().isOk());

        // Validate the Servicios in the database
        List<Servicios> serviciosList = serviciosRepository.findAll();
        assertThat(serviciosList).hasSize(databaseSizeBeforeUpdate);
        Servicios testServicios = serviciosList.get(serviciosList.size() - 1);
        assertThat(testServicios.getServicio()).isEqualTo(UPDATED_SERVICIO);
        assertThat(testServicios.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testServicios.getPublicado()).isEqualTo(UPDATED_PUBLICADO);
        assertThat(testServicios.getFoto()).isEqualTo(UPDATED_FOTO);
        assertThat(testServicios.getFotoContentType()).isEqualTo(UPDATED_FOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingServicios() throws Exception {
        int databaseSizeBeforeUpdate = serviciosRepository.findAll().size();
        servicios.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiciosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, servicios.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(servicios))
            )
            .andExpect(status().isBadRequest());

        // Validate the Servicios in the database
        List<Servicios> serviciosList = serviciosRepository.findAll();
        assertThat(serviciosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchServicios() throws Exception {
        int databaseSizeBeforeUpdate = serviciosRepository.findAll().size();
        servicios.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiciosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(servicios))
            )
            .andExpect(status().isBadRequest());

        // Validate the Servicios in the database
        List<Servicios> serviciosList = serviciosRepository.findAll();
        assertThat(serviciosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamServicios() throws Exception {
        int databaseSizeBeforeUpdate = serviciosRepository.findAll().size();
        servicios.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiciosMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(servicios)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Servicios in the database
        List<Servicios> serviciosList = serviciosRepository.findAll();
        assertThat(serviciosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateServiciosWithPatch() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        int databaseSizeBeforeUpdate = serviciosRepository.findAll().size();

        // Update the servicios using partial update
        Servicios partialUpdatedServicios = new Servicios();
        partialUpdatedServicios.setId(servicios.getId());

        partialUpdatedServicios.descripcion(UPDATED_DESCRIPCION).foto(UPDATED_FOTO).fotoContentType(UPDATED_FOTO_CONTENT_TYPE);

        restServiciosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServicios.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedServicios))
            )
            .andExpect(status().isOk());

        // Validate the Servicios in the database
        List<Servicios> serviciosList = serviciosRepository.findAll();
        assertThat(serviciosList).hasSize(databaseSizeBeforeUpdate);
        Servicios testServicios = serviciosList.get(serviciosList.size() - 1);
        assertThat(testServicios.getServicio()).isEqualTo(DEFAULT_SERVICIO);
        assertThat(testServicios.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testServicios.getPublicado()).isEqualTo(DEFAULT_PUBLICADO);
        assertThat(testServicios.getFoto()).isEqualTo(UPDATED_FOTO);
        assertThat(testServicios.getFotoContentType()).isEqualTo(UPDATED_FOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateServiciosWithPatch() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        int databaseSizeBeforeUpdate = serviciosRepository.findAll().size();

        // Update the servicios using partial update
        Servicios partialUpdatedServicios = new Servicios();
        partialUpdatedServicios.setId(servicios.getId());

        partialUpdatedServicios
            .servicio(UPDATED_SERVICIO)
            .descripcion(UPDATED_DESCRIPCION)
            .publicado(UPDATED_PUBLICADO)
            .foto(UPDATED_FOTO)
            .fotoContentType(UPDATED_FOTO_CONTENT_TYPE);

        restServiciosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServicios.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedServicios))
            )
            .andExpect(status().isOk());

        // Validate the Servicios in the database
        List<Servicios> serviciosList = serviciosRepository.findAll();
        assertThat(serviciosList).hasSize(databaseSizeBeforeUpdate);
        Servicios testServicios = serviciosList.get(serviciosList.size() - 1);
        assertThat(testServicios.getServicio()).isEqualTo(UPDATED_SERVICIO);
        assertThat(testServicios.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testServicios.getPublicado()).isEqualTo(UPDATED_PUBLICADO);
        assertThat(testServicios.getFoto()).isEqualTo(UPDATED_FOTO);
        assertThat(testServicios.getFotoContentType()).isEqualTo(UPDATED_FOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingServicios() throws Exception {
        int databaseSizeBeforeUpdate = serviciosRepository.findAll().size();
        servicios.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiciosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, servicios.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(servicios))
            )
            .andExpect(status().isBadRequest());

        // Validate the Servicios in the database
        List<Servicios> serviciosList = serviciosRepository.findAll();
        assertThat(serviciosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchServicios() throws Exception {
        int databaseSizeBeforeUpdate = serviciosRepository.findAll().size();
        servicios.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiciosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(servicios))
            )
            .andExpect(status().isBadRequest());

        // Validate the Servicios in the database
        List<Servicios> serviciosList = serviciosRepository.findAll();
        assertThat(serviciosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamServicios() throws Exception {
        int databaseSizeBeforeUpdate = serviciosRepository.findAll().size();
        servicios.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiciosMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(servicios))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Servicios in the database
        List<Servicios> serviciosList = serviciosRepository.findAll();
        assertThat(serviciosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteServicios() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        int databaseSizeBeforeDelete = serviciosRepository.findAll().size();

        // Delete the servicios
        restServiciosMockMvc
            .perform(delete(ENTITY_API_URL_ID, servicios.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Servicios> serviciosList = serviciosRepository.findAll();
        assertThat(serviciosList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
