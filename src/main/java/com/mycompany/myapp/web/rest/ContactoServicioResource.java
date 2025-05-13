package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.ContactoServicio;
import com.mycompany.myapp.repository.ContactoServicioRepository;
import com.mycompany.myapp.service.ContactoServicioService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.ContactoServicio}.
 */
@RestController
@RequestMapping("/api")
public class ContactoServicioResource {

    private final Logger log = LoggerFactory.getLogger(ContactoServicioResource.class);

    private static final String ENTITY_NAME = "contactoServicio";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContactoServicioService contactoServicioService;

    private final ContactoServicioRepository contactoServicioRepository;

    public ContactoServicioResource(
        ContactoServicioService contactoServicioService,
        ContactoServicioRepository contactoServicioRepository
    ) {
        this.contactoServicioService = contactoServicioService;
        this.contactoServicioRepository = contactoServicioRepository;
    }

    /**
     * {@code POST  /contacto-servicios} : Create a new contactoServicio.
     *
     * @param contactoServicio the contactoServicio to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contactoServicio, or with status {@code 400 (Bad Request)} if the contactoServicio has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/contacto-servicios")
    public ResponseEntity<ContactoServicio> createContactoServicio(@Valid @RequestBody ContactoServicio contactoServicio)
        throws URISyntaxException {
        log.debug("REST request to save ContactoServicio : {}", contactoServicio);
        if (contactoServicio.getId() != null) {
            throw new BadRequestAlertException("A new contactoServicio cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContactoServicio result = contactoServicioService.save(contactoServicio);
        return ResponseEntity
            .created(new URI("/api/contacto-servicios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /contacto-servicios/:id} : Updates an existing contactoServicio.
     *
     * @param id the id of the contactoServicio to save.
     * @param contactoServicio the contactoServicio to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contactoServicio,
     * or with status {@code 400 (Bad Request)} if the contactoServicio is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contactoServicio couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/contacto-servicios/{id}")
    public ResponseEntity<ContactoServicio> updateContactoServicio(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ContactoServicio contactoServicio
    ) throws URISyntaxException {
        log.debug("REST request to update ContactoServicio : {}, {}", id, contactoServicio);
        if (contactoServicio.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contactoServicio.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contactoServicioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ContactoServicio result = contactoServicioService.update(contactoServicio);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contactoServicio.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /contacto-servicios/:id} : Partial updates given fields of an existing contactoServicio, field will ignore if it is null
     *
     * @param id the id of the contactoServicio to save.
     * @param contactoServicio the contactoServicio to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contactoServicio,
     * or with status {@code 400 (Bad Request)} if the contactoServicio is not valid,
     * or with status {@code 404 (Not Found)} if the contactoServicio is not found,
     * or with status {@code 500 (Internal Server Error)} if the contactoServicio couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/contacto-servicios/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ContactoServicio> partialUpdateContactoServicio(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ContactoServicio contactoServicio
    ) throws URISyntaxException {
        log.debug("REST request to partial update ContactoServicio partially : {}, {}", id, contactoServicio);
        if (contactoServicio.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contactoServicio.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contactoServicioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ContactoServicio> result = contactoServicioService.partialUpdate(contactoServicio);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contactoServicio.getId().toString())
        );
    }

    /**
     * {@code GET  /contacto-servicios} : get all the contactoServicios.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contactoServicios in body.
     */
    @GetMapping("/contacto-servicios")
    public List<ContactoServicio> getAllContactoServicios(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all ContactoServicios");
        return contactoServicioService.findAll();
    }

    /**
     * {@code GET  /contacto-servicios/:id} : get the "id" contactoServicio.
     *
     * @param id the id of the contactoServicio to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contactoServicio, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/contacto-servicios/{id}")
    public ResponseEntity<ContactoServicio> getContactoServicio(@PathVariable Long id) {
        log.debug("REST request to get ContactoServicio : {}", id);
        Optional<ContactoServicio> contactoServicio = contactoServicioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contactoServicio);
    }

    /**
     * {@code DELETE  /contacto-servicios/:id} : delete the "id" contactoServicio.
     *
     * @param id the id of the contactoServicio to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/contacto-servicios/{id}")
    public ResponseEntity<Void> deleteContactoServicio(@PathVariable Long id) {
        log.debug("REST request to delete ContactoServicio : {}", id);
        contactoServicioService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
