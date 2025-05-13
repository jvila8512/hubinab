package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.ContactoChangeMacker;
import com.mycompany.myapp.repository.ContactoChangeMackerRepository;
import com.mycompany.myapp.service.ContactoChangeMackerService;
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
 * Integration tests for the {@link ContactoChangeMackerResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ContactoChangeMackerResourceIT {

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

    private static final String ENTITY_API_URL = "/api/contacto-change-mackers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ContactoChangeMackerRepository contactoChangeMackerRepository;

    @Mock
    private ContactoChangeMackerRepository contactoChangeMackerRepositoryMock;

    @Mock
    private ContactoChangeMackerService contactoChangeMackerServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContactoChangeMackerMockMvc;

    private ContactoChangeMacker contactoChangeMacker;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContactoChangeMacker createEntity(EntityManager em) {
        ContactoChangeMacker contactoChangeMacker = new ContactoChangeMacker()
            .nombre(DEFAULT_NOMBRE)
            .telefono(DEFAULT_TELEFONO)
            .correo(DEFAULT_CORREO)
            .mensaje(DEFAULT_MENSAJE)
            .fechaContacto(DEFAULT_FECHA_CONTACTO);
        return contactoChangeMacker;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContactoChangeMacker createUpdatedEntity(EntityManager em) {
        ContactoChangeMacker contactoChangeMacker = new ContactoChangeMacker()
            .nombre(UPDATED_NOMBRE)
            .telefono(UPDATED_TELEFONO)
            .correo(UPDATED_CORREO)
            .mensaje(UPDATED_MENSAJE)
            .fechaContacto(UPDATED_FECHA_CONTACTO);
        return contactoChangeMacker;
    }

    @BeforeEach
    public void initTest() {
        contactoChangeMacker = createEntity(em);
    }

    @Test
    @Transactional
    void createContactoChangeMacker() throws Exception {
        int databaseSizeBeforeCreate = contactoChangeMackerRepository.findAll().size();
        // Create the ContactoChangeMacker
        restContactoChangeMackerMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contactoChangeMacker))
            )
            .andExpect(status().isCreated());

        // Validate the ContactoChangeMacker in the database
        List<ContactoChangeMacker> contactoChangeMackerList = contactoChangeMackerRepository.findAll();
        assertThat(contactoChangeMackerList).hasSize(databaseSizeBeforeCreate + 1);
        ContactoChangeMacker testContactoChangeMacker = contactoChangeMackerList.get(contactoChangeMackerList.size() - 1);
        assertThat(testContactoChangeMacker.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testContactoChangeMacker.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testContactoChangeMacker.getCorreo()).isEqualTo(DEFAULT_CORREO);
        assertThat(testContactoChangeMacker.getMensaje()).isEqualTo(DEFAULT_MENSAJE);
        assertThat(testContactoChangeMacker.getFechaContacto()).isEqualTo(DEFAULT_FECHA_CONTACTO);
    }

    @Test
    @Transactional
    void createContactoChangeMackerWithExistingId() throws Exception {
        // Create the ContactoChangeMacker with an existing ID
        contactoChangeMacker.setId(1L);

        int databaseSizeBeforeCreate = contactoChangeMackerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restContactoChangeMackerMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contactoChangeMacker))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContactoChangeMacker in the database
        List<ContactoChangeMacker> contactoChangeMackerList = contactoChangeMackerRepository.findAll();
        assertThat(contactoChangeMackerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactoChangeMackerRepository.findAll().size();
        // set the field null
        contactoChangeMacker.setNombre(null);

        // Create the ContactoChangeMacker, which fails.

        restContactoChangeMackerMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contactoChangeMacker))
            )
            .andExpect(status().isBadRequest());

        List<ContactoChangeMacker> contactoChangeMackerList = contactoChangeMackerRepository.findAll();
        assertThat(contactoChangeMackerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTelefonoIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactoChangeMackerRepository.findAll().size();
        // set the field null
        contactoChangeMacker.setTelefono(null);

        // Create the ContactoChangeMacker, which fails.

        restContactoChangeMackerMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contactoChangeMacker))
            )
            .andExpect(status().isBadRequest());

        List<ContactoChangeMacker> contactoChangeMackerList = contactoChangeMackerRepository.findAll();
        assertThat(contactoChangeMackerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCorreoIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactoChangeMackerRepository.findAll().size();
        // set the field null
        contactoChangeMacker.setCorreo(null);

        // Create the ContactoChangeMacker, which fails.

        restContactoChangeMackerMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contactoChangeMacker))
            )
            .andExpect(status().isBadRequest());

        List<ContactoChangeMacker> contactoChangeMackerList = contactoChangeMackerRepository.findAll();
        assertThat(contactoChangeMackerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMensajeIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactoChangeMackerRepository.findAll().size();
        // set the field null
        contactoChangeMacker.setMensaje(null);

        // Create the ContactoChangeMacker, which fails.

        restContactoChangeMackerMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contactoChangeMacker))
            )
            .andExpect(status().isBadRequest());

        List<ContactoChangeMacker> contactoChangeMackerList = contactoChangeMackerRepository.findAll();
        assertThat(contactoChangeMackerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllContactoChangeMackers() throws Exception {
        // Initialize the database
        contactoChangeMackerRepository.saveAndFlush(contactoChangeMacker);

        // Get all the contactoChangeMackerList
        restContactoChangeMackerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contactoChangeMacker.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)))
            .andExpect(jsonPath("$.[*].correo").value(hasItem(DEFAULT_CORREO)))
            .andExpect(jsonPath("$.[*].mensaje").value(hasItem(DEFAULT_MENSAJE)))
            .andExpect(jsonPath("$.[*].fechaContacto").value(hasItem(DEFAULT_FECHA_CONTACTO.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllContactoChangeMackersWithEagerRelationshipsIsEnabled() throws Exception {
        when(contactoChangeMackerServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restContactoChangeMackerMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(contactoChangeMackerServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllContactoChangeMackersWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(contactoChangeMackerServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restContactoChangeMackerMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(contactoChangeMackerServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getContactoChangeMacker() throws Exception {
        // Initialize the database
        contactoChangeMackerRepository.saveAndFlush(contactoChangeMacker);

        // Get the contactoChangeMacker
        restContactoChangeMackerMockMvc
            .perform(get(ENTITY_API_URL_ID, contactoChangeMacker.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contactoChangeMacker.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO))
            .andExpect(jsonPath("$.correo").value(DEFAULT_CORREO))
            .andExpect(jsonPath("$.mensaje").value(DEFAULT_MENSAJE))
            .andExpect(jsonPath("$.fechaContacto").value(DEFAULT_FECHA_CONTACTO.toString()));
    }

    @Test
    @Transactional
    void getNonExistingContactoChangeMacker() throws Exception {
        // Get the contactoChangeMacker
        restContactoChangeMackerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewContactoChangeMacker() throws Exception {
        // Initialize the database
        contactoChangeMackerRepository.saveAndFlush(contactoChangeMacker);

        int databaseSizeBeforeUpdate = contactoChangeMackerRepository.findAll().size();

        // Update the contactoChangeMacker
        ContactoChangeMacker updatedContactoChangeMacker = contactoChangeMackerRepository.findById(contactoChangeMacker.getId()).get();
        // Disconnect from session so that the updates on updatedContactoChangeMacker are not directly saved in db
        em.detach(updatedContactoChangeMacker);
        updatedContactoChangeMacker
            .nombre(UPDATED_NOMBRE)
            .telefono(UPDATED_TELEFONO)
            .correo(UPDATED_CORREO)
            .mensaje(UPDATED_MENSAJE)
            .fechaContacto(UPDATED_FECHA_CONTACTO);

        restContactoChangeMackerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedContactoChangeMacker.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedContactoChangeMacker))
            )
            .andExpect(status().isOk());

        // Validate the ContactoChangeMacker in the database
        List<ContactoChangeMacker> contactoChangeMackerList = contactoChangeMackerRepository.findAll();
        assertThat(contactoChangeMackerList).hasSize(databaseSizeBeforeUpdate);
        ContactoChangeMacker testContactoChangeMacker = contactoChangeMackerList.get(contactoChangeMackerList.size() - 1);
        assertThat(testContactoChangeMacker.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testContactoChangeMacker.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testContactoChangeMacker.getCorreo()).isEqualTo(UPDATED_CORREO);
        assertThat(testContactoChangeMacker.getMensaje()).isEqualTo(UPDATED_MENSAJE);
        assertThat(testContactoChangeMacker.getFechaContacto()).isEqualTo(UPDATED_FECHA_CONTACTO);
    }

    @Test
    @Transactional
    void putNonExistingContactoChangeMacker() throws Exception {
        int databaseSizeBeforeUpdate = contactoChangeMackerRepository.findAll().size();
        contactoChangeMacker.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContactoChangeMackerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contactoChangeMacker.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contactoChangeMacker))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContactoChangeMacker in the database
        List<ContactoChangeMacker> contactoChangeMackerList = contactoChangeMackerRepository.findAll();
        assertThat(contactoChangeMackerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchContactoChangeMacker() throws Exception {
        int databaseSizeBeforeUpdate = contactoChangeMackerRepository.findAll().size();
        contactoChangeMacker.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactoChangeMackerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contactoChangeMacker))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContactoChangeMacker in the database
        List<ContactoChangeMacker> contactoChangeMackerList = contactoChangeMackerRepository.findAll();
        assertThat(contactoChangeMackerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamContactoChangeMacker() throws Exception {
        int databaseSizeBeforeUpdate = contactoChangeMackerRepository.findAll().size();
        contactoChangeMacker.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactoChangeMackerMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contactoChangeMacker))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ContactoChangeMacker in the database
        List<ContactoChangeMacker> contactoChangeMackerList = contactoChangeMackerRepository.findAll();
        assertThat(contactoChangeMackerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateContactoChangeMackerWithPatch() throws Exception {
        // Initialize the database
        contactoChangeMackerRepository.saveAndFlush(contactoChangeMacker);

        int databaseSizeBeforeUpdate = contactoChangeMackerRepository.findAll().size();

        // Update the contactoChangeMacker using partial update
        ContactoChangeMacker partialUpdatedContactoChangeMacker = new ContactoChangeMacker();
        partialUpdatedContactoChangeMacker.setId(contactoChangeMacker.getId());

        partialUpdatedContactoChangeMacker.telefono(UPDATED_TELEFONO).mensaje(UPDATED_MENSAJE);

        restContactoChangeMackerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContactoChangeMacker.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContactoChangeMacker))
            )
            .andExpect(status().isOk());

        // Validate the ContactoChangeMacker in the database
        List<ContactoChangeMacker> contactoChangeMackerList = contactoChangeMackerRepository.findAll();
        assertThat(contactoChangeMackerList).hasSize(databaseSizeBeforeUpdate);
        ContactoChangeMacker testContactoChangeMacker = contactoChangeMackerList.get(contactoChangeMackerList.size() - 1);
        assertThat(testContactoChangeMacker.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testContactoChangeMacker.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testContactoChangeMacker.getCorreo()).isEqualTo(DEFAULT_CORREO);
        assertThat(testContactoChangeMacker.getMensaje()).isEqualTo(UPDATED_MENSAJE);
        assertThat(testContactoChangeMacker.getFechaContacto()).isEqualTo(DEFAULT_FECHA_CONTACTO);
    }

    @Test
    @Transactional
    void fullUpdateContactoChangeMackerWithPatch() throws Exception {
        // Initialize the database
        contactoChangeMackerRepository.saveAndFlush(contactoChangeMacker);

        int databaseSizeBeforeUpdate = contactoChangeMackerRepository.findAll().size();

        // Update the contactoChangeMacker using partial update
        ContactoChangeMacker partialUpdatedContactoChangeMacker = new ContactoChangeMacker();
        partialUpdatedContactoChangeMacker.setId(contactoChangeMacker.getId());

        partialUpdatedContactoChangeMacker
            .nombre(UPDATED_NOMBRE)
            .telefono(UPDATED_TELEFONO)
            .correo(UPDATED_CORREO)
            .mensaje(UPDATED_MENSAJE)
            .fechaContacto(UPDATED_FECHA_CONTACTO);

        restContactoChangeMackerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContactoChangeMacker.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContactoChangeMacker))
            )
            .andExpect(status().isOk());

        // Validate the ContactoChangeMacker in the database
        List<ContactoChangeMacker> contactoChangeMackerList = contactoChangeMackerRepository.findAll();
        assertThat(contactoChangeMackerList).hasSize(databaseSizeBeforeUpdate);
        ContactoChangeMacker testContactoChangeMacker = contactoChangeMackerList.get(contactoChangeMackerList.size() - 1);
        assertThat(testContactoChangeMacker.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testContactoChangeMacker.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testContactoChangeMacker.getCorreo()).isEqualTo(UPDATED_CORREO);
        assertThat(testContactoChangeMacker.getMensaje()).isEqualTo(UPDATED_MENSAJE);
        assertThat(testContactoChangeMacker.getFechaContacto()).isEqualTo(UPDATED_FECHA_CONTACTO);
    }

    @Test
    @Transactional
    void patchNonExistingContactoChangeMacker() throws Exception {
        int databaseSizeBeforeUpdate = contactoChangeMackerRepository.findAll().size();
        contactoChangeMacker.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContactoChangeMackerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, contactoChangeMacker.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contactoChangeMacker))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContactoChangeMacker in the database
        List<ContactoChangeMacker> contactoChangeMackerList = contactoChangeMackerRepository.findAll();
        assertThat(contactoChangeMackerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchContactoChangeMacker() throws Exception {
        int databaseSizeBeforeUpdate = contactoChangeMackerRepository.findAll().size();
        contactoChangeMacker.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactoChangeMackerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contactoChangeMacker))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContactoChangeMacker in the database
        List<ContactoChangeMacker> contactoChangeMackerList = contactoChangeMackerRepository.findAll();
        assertThat(contactoChangeMackerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamContactoChangeMacker() throws Exception {
        int databaseSizeBeforeUpdate = contactoChangeMackerRepository.findAll().size();
        contactoChangeMacker.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactoChangeMackerMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contactoChangeMacker))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ContactoChangeMacker in the database
        List<ContactoChangeMacker> contactoChangeMackerList = contactoChangeMackerRepository.findAll();
        assertThat(contactoChangeMackerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteContactoChangeMacker() throws Exception {
        // Initialize the database
        contactoChangeMackerRepository.saveAndFlush(contactoChangeMacker);

        int databaseSizeBeforeDelete = contactoChangeMackerRepository.findAll().size();

        // Delete the contactoChangeMacker
        restContactoChangeMackerMockMvc
            .perform(delete(ENTITY_API_URL_ID, contactoChangeMacker.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ContactoChangeMacker> contactoChangeMackerList = contactoChangeMackerRepository.findAll();
        assertThat(contactoChangeMackerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
