package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.ContactoChangeMacker;
import com.mycompany.myapp.repository.ContactoChangeMackerRepository;
import com.mycompany.myapp.service.ContactoChangeMackerService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.ContactoChangeMacker}.
 */
@RestController
@RequestMapping("/api")
public class ContactoChangeMackerResource {

    private final Logger log = LoggerFactory.getLogger(ContactoChangeMackerResource.class);

    private static final String ENTITY_NAME = "contactoChangeMacker";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContactoChangeMackerService contactoChangeMackerService;

    private final ContactoChangeMackerRepository contactoChangeMackerRepository;

    public ContactoChangeMackerResource(
        ContactoChangeMackerService contactoChangeMackerService,
        ContactoChangeMackerRepository contactoChangeMackerRepository
    ) {
        this.contactoChangeMackerService = contactoChangeMackerService;
        this.contactoChangeMackerRepository = contactoChangeMackerRepository;
    }

    /**
     * {@code POST  /contacto-change-mackers} : Create a new contactoChangeMacker.
     *
     * @param contactoChangeMacker the contactoChangeMacker to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contactoChangeMacker, or with status {@code 400 (Bad Request)} if the contactoChangeMacker has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/contacto-change-mackers")
    public ResponseEntity<ContactoChangeMacker> createContactoChangeMacker(@Valid @RequestBody ContactoChangeMacker contactoChangeMacker)
        throws URISyntaxException {
        log.debug("REST request to save ContactoChangeMacker : {}", contactoChangeMacker);
        if (contactoChangeMacker.getId() != null) {
            throw new BadRequestAlertException("A new contactoChangeMacker cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContactoChangeMacker result = contactoChangeMackerService.save(contactoChangeMacker);
        return ResponseEntity
            .created(new URI("/api/contacto-change-mackers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /contacto-change-mackers/:id} : Updates an existing contactoChangeMacker.
     *
     * @param id the id of the contactoChangeMacker to save.
     * @param contactoChangeMacker the contactoChangeMacker to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contactoChangeMacker,
     * or with status {@code 400 (Bad Request)} if the contactoChangeMacker is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contactoChangeMacker couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/contacto-change-mackers/{id}")
    public ResponseEntity<ContactoChangeMacker> updateContactoChangeMacker(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ContactoChangeMacker contactoChangeMacker
    ) throws URISyntaxException {
        log.debug("REST request to update ContactoChangeMacker : {}, {}", id, contactoChangeMacker);
        if (contactoChangeMacker.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contactoChangeMacker.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contactoChangeMackerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ContactoChangeMacker result = contactoChangeMackerService.update(contactoChangeMacker);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contactoChangeMacker.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /contacto-change-mackers/:id} : Partial updates given fields of an existing contactoChangeMacker, field will ignore if it is null
     *
     * @param id the id of the contactoChangeMacker to save.
     * @param contactoChangeMacker the contactoChangeMacker to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contactoChangeMacker,
     * or with status {@code 400 (Bad Request)} if the contactoChangeMacker is not valid,
     * or with status {@code 404 (Not Found)} if the contactoChangeMacker is not found,
     * or with status {@code 500 (Internal Server Error)} if the contactoChangeMacker couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/contacto-change-mackers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ContactoChangeMacker> partialUpdateContactoChangeMacker(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ContactoChangeMacker contactoChangeMacker
    ) throws URISyntaxException {
        log.debug("REST request to partial update ContactoChangeMacker partially : {}, {}", id, contactoChangeMacker);
        if (contactoChangeMacker.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contactoChangeMacker.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contactoChangeMackerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ContactoChangeMacker> result = contactoChangeMackerService.partialUpdate(contactoChangeMacker);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contactoChangeMacker.getId().toString())
        );
    }

    /**
     * {@code GET  /contacto-change-mackers} : get all the contactoChangeMackers.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contactoChangeMackers in body.
     */
    @GetMapping("/contacto-change-mackers")
    public List<ContactoChangeMacker> getAllContactoChangeMackers(
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get all ContactoChangeMackers");
        return contactoChangeMackerService.findAll();
    }

    /**
     * {@code GET  /contacto-change-mackers/:id} : get the "id" contactoChangeMacker.
     *
     * @param id the id of the contactoChangeMacker to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contactoChangeMacker, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/contacto-change-mackers/{id}")
    public ResponseEntity<ContactoChangeMacker> getContactoChangeMacker(@PathVariable Long id) {
        log.debug("REST request to get ContactoChangeMacker : {}", id);
        Optional<ContactoChangeMacker> contactoChangeMacker = contactoChangeMackerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contactoChangeMacker);
    }

    /**
     * {@code DELETE  /contacto-change-mackers/:id} : delete the "id" contactoChangeMacker.
     *
     * @param id the id of the contactoChangeMacker to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/contacto-change-mackers/{id}")
    public ResponseEntity<Void> deleteContactoChangeMacker(@PathVariable Long id) {
        log.debug("REST request to delete ContactoChangeMacker : {}", id);
        contactoChangeMackerService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
