package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.ContactoServicio;
import com.mycompany.myapp.repository.ContactoServicioRepository;
import com.mycompany.myapp.service.ContactoServicioService;
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
 * Integration tests for the {@link ContactoServicioResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ContactoServicioResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO = "BBBBBBBBBB";

    private static final String DEFAULT_CORREO = "AAAAAAAAAA";
    private static final String UPDATED_CORREO = "BBBBBBBBBB";

    private static final String DEFAULT_MENSAJE = "AAAAAAAAAA";
    private static final String UPDATED_MENSAJE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_CONTACTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_CONTACTO = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/contacto-servicios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ContactoServicioRepository contactoServicioRepository;

    @Mock
    private ContactoServicioRepository contactoServicioRepositoryMock;

    @Mock
    private ContactoServicioService contactoServicioServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContactoServicioMockMvc;

    private ContactoServicio contactoServicio;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContactoServicio createEntity(EntityManager em) {
        ContactoServicio contactoServicio = new ContactoServicio()
            .nombre(DEFAULT_NOMBRE)
            .telefono(DEFAULT_TELEFONO)
            .correo(DEFAULT_CORREO)
            .mensaje(DEFAULT_MENSAJE)
            .fechaContacto(DEFAULT_FECHA_CONTACTO);
        return contactoServicio;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContactoServicio createUpdatedEntity(EntityManager em) {
        ContactoServicio contactoServicio = new ContactoServicio()
            .nombre(UPDATED_NOMBRE)
            .telefono(UPDATED_TELEFONO)
            .correo(UPDATED_CORREO)
            .mensaje(UPDATED_MENSAJE)
            .fechaContacto(UPDATED_FECHA_CONTACTO);
        return contactoServicio;
    }

    @BeforeEach
    public void initTest() {
        contactoServicio = createEntity(em);
    }

    @Test
    @Transactional
    void createContactoServicio() throws Exception {
        int databaseSizeBeforeCreate = contactoServicioRepository.findAll().size();
        // Create the ContactoServicio
        restContactoServicioMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contactoServicio))
            )
            .andExpect(status().isCreated());

        // Validate the ContactoServicio in the database
        List<ContactoServicio> contactoServicioList = contactoServicioRepository.findAll();
        assertThat(contactoServicioList).hasSize(databaseSizeBeforeCreate + 1);
        ContactoServicio testContactoServicio = contactoServicioList.get(contactoServicioList.size() - 1);
        assertThat(testContactoServicio.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testContactoServicio.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testContactoServicio.getCorreo()).isEqualTo(DEFAULT_CORREO);
        assertThat(testContactoServicio.getMensaje()).isEqualTo(DEFAULT_MENSAJE);
        assertThat(testContactoServicio.getFechaContacto()).isEqualTo(DEFAULT_FECHA_CONTACTO);
    }

    @Test
    @Transactional
    void createContactoServicioWithExistingId() throws Exception {
        // Create the ContactoServicio with an existing ID
        contactoServicio.setId(1L);

        int databaseSizeBeforeCreate = contactoServicioRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restContactoServicioMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contactoServicio))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContactoServicio in the database
        List<ContactoServicio> contactoServicioList = contactoServicioRepository.findAll();
        assertThat(contactoServicioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactoServicioRepository.findAll().size();
        // set the field null
        contactoServicio.setNombre(null);

        // Create the ContactoServicio, which fails.

        restContactoServicioMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contactoServicio))
            )
            .andExpect(status().isBadRequest());

        List<ContactoServicio> contactoServicioList = contactoServicioRepository.findAll();
        assertThat(contactoServicioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTelefonoIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactoServicioRepository.findAll().size();
        // set the field null
        contactoServicio.setTelefono(null);

        // Create the ContactoServicio, which fails.

        restContactoServicioMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contactoServicio))
            )
            .andExpect(status().isBadRequest());

        List<ContactoServicio> contactoServicioList = contactoServicioRepository.findAll();
        assertThat(contactoServicioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCorreoIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactoServicioRepository.findAll().size();
        // set the field null
        contactoServicio.setCorreo(null);

        // Create the ContactoServicio, which fails.

        restContactoServicioMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contactoServicio))
            )
            .andExpect(status().isBadRequest());

        List<ContactoServicio> contactoServicioList = contactoServicioRepository.findAll();
        assertThat(contactoServicioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMensajeIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactoServicioRepository.findAll().size();
        // set the field null
        contactoServicio.setMensaje(null);

        // Create the ContactoServicio, which fails.

        restContactoServicioMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contactoServicio))
            )
            .andExpect(status().isBadRequest());

        List<ContactoServicio> contactoServicioList = contactoServicioRepository.findAll();
        assertThat(contactoServicioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllContactoServicios() throws Exception {
        // Initialize the database
        contactoServicioRepository.saveAndFlush(contactoServicio);

        // Get all the contactoServicioList
        restContactoServicioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contactoServicio.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)))
            .andExpect(jsonPath("$.[*].correo").value(hasItem(DEFAULT_CORREO)))
            .andExpect(jsonPath("$.[*].mensaje").value(hasItem(DEFAULT_MENSAJE)))
            .andExpect(jsonPath("$.[*].fechaContacto").value(hasItem(DEFAULT_FECHA_CONTACTO.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllContactoServiciosWithEagerRelationshipsIsEnabled() throws Exception {
        when(contactoServicioServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restContactoServicioMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(contactoServicioServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllContactoServiciosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(contactoServicioServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restContactoServicioMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(contactoServicioServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getContactoServicio() throws Exception {
        // Initialize the database
        contactoServicioRepository.saveAndFlush(contactoServicio);

        // Get the contactoServicio
        restContactoServicioMockMvc
            .perform(get(ENTITY_API_URL_ID, contactoServicio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contactoServicio.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO))
            .andExpect(jsonPath("$.correo").value(DEFAULT_CORREO))
            .andExpect(jsonPath("$.mensaje").value(DEFAULT_MENSAJE))
            .andExpect(jsonPath("$.fechaContacto").value(DEFAULT_FECHA_CONTACTO.toString()));
    }

    @Test
    @Transactional
    void getNonExistingContactoServicio() throws Exception {
        // Get the contactoServicio
        restContactoServicioMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewContactoServicio() throws Exception {
        // Initialize the database
        contactoServicioRepository.saveAndFlush(contactoServicio);

        int databaseSizeBeforeUpdate = contactoServicioRepository.findAll().size();

        // Update the contactoServicio
        ContactoServicio updatedContactoServicio = contactoServicioRepository.findById(contactoServicio.getId()).get();
        // Disconnect from session so that the updates on updatedContactoServicio are not directly saved in db
        em.detach(updatedContactoServicio);
        updatedContactoServicio
            .nombre(UPDATED_NOMBRE)
            .telefono(UPDATED_TELEFONO)
            .correo(UPDATED_CORREO)
            .mensaje(UPDATED_MENSAJE)
            .fechaContacto(UPDATED_FECHA_CONTACTO);

        restContactoServicioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedContactoServicio.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedContactoServicio))
            )
            .andExpect(status().isOk());

        // Validate the ContactoServicio in the database
        List<ContactoServicio> contactoServicioList = contactoServicioRepository.findAll();
        assertThat(contactoServicioList).hasSize(databaseSizeBeforeUpdate);
        ContactoServicio testContactoServicio = contactoServicioList.get(contactoServicioList.size() - 1);
        assertThat(testContactoServicio.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testContactoServicio.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testContactoServicio.getCorreo()).isEqualTo(UPDATED_CORREO);
        assertThat(testContactoServicio.getMensaje()).isEqualTo(UPDATED_MENSAJE);
        assertThat(testContactoServicio.getFechaContacto()).isEqualTo(UPDATED_FECHA_CONTACTO);
    }

    @Test
    @Transactional
    void putNonExistingContactoServicio() throws Exception {
        int databaseSizeBeforeUpdate = contactoServicioRepository.findAll().size();
        contactoServicio.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContactoServicioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contactoServicio.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contactoServicio))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContactoServicio in the database
        List<ContactoServicio> contactoServicioList = contactoServicioRepository.findAll();
        assertThat(contactoServicioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchContactoServicio() throws Exception {
        int databaseSizeBeforeUpdate = contactoServicioRepository.findAll().size();
        contactoServicio.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactoServicioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contactoServicio))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContactoServicio in the database
        List<ContactoServicio> contactoServicioList = contactoServicioRepository.findAll();
        assertThat(contactoServicioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamContactoServicio() throws Exception {
        int databaseSizeBeforeUpdate = contactoServicioRepository.findAll().size();
        contactoServicio.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactoServicioMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contactoServicio))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ContactoServicio in the database
        List<ContactoServicio> contactoServicioList = contactoServicioRepository.findAll();
        assertThat(contactoServicioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateContactoServicioWithPatch() throws Exception {
        // Initialize the database
        contactoServicioRepository.saveAndFlush(contactoServicio);

        int databaseSizeBeforeUpdate = contactoServicioRepository.findAll().size();

        // Update the contactoServicio using partial update
        ContactoServicio partialUpdatedContactoServicio = new ContactoServicio();
        partialUpdatedContactoServicio.setId(contactoServicio.getId());

        partialUpdatedContactoServicio.nombre(UPDATED_NOMBRE).telefono(UPDATED_TELEFONO).mensaje(UPDATED_MENSAJE);

        restContactoServicioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContactoServicio.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContactoServicio))
            )
            .andExpect(status().isOk());

        // Validate the ContactoServicio in the database
        List<ContactoServicio> contactoServicioList = contactoServicioRepository.findAll();
        assertThat(contactoServicioList).hasSize(databaseSizeBeforeUpdate);
        ContactoServicio testContactoServicio = contactoServicioList.get(contactoServicioList.size() - 1);
        assertThat(testContactoServicio.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testContactoServicio.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testContactoServicio.getCorreo()).isEqualTo(DEFAULT_CORREO);
        assertThat(testContactoServicio.getMensaje()).isEqualTo(UPDATED_MENSAJE);
        assertThat(testContactoServicio.getFechaContacto()).isEqualTo(DEFAULT_FECHA_CONTACTO);
    }

    @Test
    @Transactional
    void fullUpdateContactoServicioWithPatch() throws Exception {
        // Initialize the database
        contactoServicioRepository.saveAndFlush(contactoServicio);

        int databaseSizeBeforeUpdate = contactoServicioRepository.findAll().size();

        // Update the contactoServicio using partial update
        ContactoServicio partialUpdatedContactoServicio = new ContactoServicio();
        partialUpdatedContactoServicio.setId(contactoServicio.getId());

        partialUpdatedContactoServicio
            .nombre(UPDATED_NOMBRE)
            .telefono(UPDATED_TELEFONO)
            .correo(UPDATED_CORREO)
            .mensaje(UPDATED_MENSAJE)
            .fechaContacto(UPDATED_FECHA_CONTACTO);

        restContactoServicioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContactoServicio.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContactoServicio))
            )
            .andExpect(status().isOk());

        // Validate the ContactoServicio in the database
        List<ContactoServicio> contactoServicioList = contactoServicioRepository.findAll();
        assertThat(contactoServicioList).hasSize(databaseSizeBeforeUpdate);
        ContactoServicio testContactoServicio = contactoServicioList.get(contactoServicioList.size() - 1);
        assertThat(testContactoServicio.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testContactoServicio.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testContactoServicio.getCorreo()).isEqualTo(UPDATED_CORREO);
        assertThat(testContactoServicio.getMensaje()).isEqualTo(UPDATED_MENSAJE);
        assertThat(testContactoServicio.getFechaContacto()).isEqualTo(UPDATED_FECHA_CONTACTO);
    }

    @Test
    @Transactional
    void patchNonExistingContactoServicio() throws Exception {
        int databaseSizeBeforeUpdate = contactoServicioRepository.findAll().size();
        contactoServicio.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContactoServicioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, contactoServicio.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contactoServicio))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContactoServicio in the database
        List<ContactoServicio> contactoServicioList = contactoServicioRepository.findAll();
        assertThat(contactoServicioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchContactoServicio() throws Exception {
        int databaseSizeBeforeUpdate = contactoServicioRepository.findAll().size();
        contactoServicio.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactoServicioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contactoServicio))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContactoServicio in the database
        List<ContactoServicio> contactoServicioList = contactoServicioRepository.findAll();
        assertThat(contactoServicioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamContactoServicio() throws Exception {
        int databaseSizeBeforeUpdate = contactoServicioRepository.findAll().size();
        contactoServicio.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactoServicioMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contactoServicio))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ContactoServicio in the database
        List<ContactoServicio> contactoServicioList = contactoServicioRepository.findAll();
        assertThat(contactoServicioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteContactoServicio() throws Exception {
        // Initialize the database
        contactoServicioRepository.saveAndFlush(contactoServicio);

        int databaseSizeBeforeDelete = contactoServicioRepository.findAll().size();

        // Delete the contactoServicio
        restContactoServicioMockMvc
            .perform(delete(ENTITY_API_URL_ID, contactoServicio.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ContactoServicio> contactoServicioList = contactoServicioRepository.findAll();
        assertThat(contactoServicioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
