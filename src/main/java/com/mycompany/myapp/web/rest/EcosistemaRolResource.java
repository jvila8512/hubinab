package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.EcosistemaRol;
import com.mycompany.myapp.repository.EcosistemaRolRepository;
import com.mycompany.myapp.service.EcosistemaRolService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.EcosistemaRol}.
 */
@RestController
@RequestMapping("/api")
public class EcosistemaRolResource {

    private final Logger log = LoggerFactory.getLogger(EcosistemaRolResource.class);

    private static final String ENTITY_NAME = "ecosistemaRol";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EcosistemaRolService ecosistemaRolService;

    private final EcosistemaRolRepository ecosistemaRolRepository;

    public EcosistemaRolResource(EcosistemaRolService ecosistemaRolService, EcosistemaRolRepository ecosistemaRolRepository) {
        this.ecosistemaRolService = ecosistemaRolService;
        this.ecosistemaRolRepository = ecosistemaRolRepository;
    }

    /**
     * {@code POST  /ecosistema-rols} : Create a new ecosistemaRol.
     *
     * @param ecosistemaRol the ecosistemaRol to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ecosistemaRol, or with status {@code 400 (Bad Request)} if the ecosistemaRol has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ecosistema-rols")
    public ResponseEntity<EcosistemaRol> createEcosistemaRol(@Valid @RequestBody EcosistemaRol ecosistemaRol) throws URISyntaxException {
        log.debug("REST request to save EcosistemaRol : {}", ecosistemaRol);
        if (ecosistemaRol.getId() != null) {
            throw new BadRequestAlertException("A new ecosistemaRol cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EcosistemaRol result = ecosistemaRolService.save(ecosistemaRol);
        return ResponseEntity
            .created(new URI("/api/ecosistema-rols/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ecosistema-rols/:id} : Updates an existing ecosistemaRol.
     *
     * @param id the id of the ecosistemaRol to save.
     * @param ecosistemaRol the ecosistemaRol to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ecosistemaRol,
     * or with status {@code 400 (Bad Request)} if the ecosistemaRol is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ecosistemaRol couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ecosistema-rols/{id}")
    public ResponseEntity<EcosistemaRol> updateEcosistemaRol(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EcosistemaRol ecosistemaRol
    ) throws URISyntaxException {
        log.debug("REST request to update EcosistemaRol : {}, {}", id, ecosistemaRol);
        if (ecosistemaRol.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ecosistemaRol.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ecosistemaRolRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EcosistemaRol result = ecosistemaRolService.update(ecosistemaRol);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ecosistemaRol.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ecosistema-rols/:id} : Partial updates given fields of an existing ecosistemaRol, field will ignore if it is null
     *
     * @param id the id of the ecosistemaRol to save.
     * @param ecosistemaRol the ecosistemaRol to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ecosistemaRol,
     * or with status {@code 400 (Bad Request)} if the ecosistemaRol is not valid,
     * or with status {@code 404 (Not Found)} if the ecosistemaRol is not found,
     * or with status {@code 500 (Internal Server Error)} if the ecosistemaRol couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ecosistema-rols/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EcosistemaRol> partialUpdateEcosistemaRol(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EcosistemaRol ecosistemaRol
    ) throws URISyntaxException {
        log.debug("REST request to partial update EcosistemaRol partially : {}, {}", id, ecosistemaRol);
        if (ecosistemaRol.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ecosistemaRol.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ecosistemaRolRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EcosistemaRol> result = ecosistemaRolService.partialUpdate(ecosistemaRol);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ecosistemaRol.getId().toString())
        );
    }

    /**
     * {@code GET  /ecosistema-rols} : get all the ecosistemaRols.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ecosistemaRols in body.
     */
    @GetMapping("/ecosistema-rols")
    public List<EcosistemaRol> getAllEcosistemaRols() {
        log.debug("REST request to get all EcosistemaRols");
        return ecosistemaRolService.findAll();
    }

    /**
     * {@code GET  /ecosistema-rols/:id} : get the "id" ecosistemaRol.
     *
     * @param id the id of the ecosistemaRol to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ecosistemaRol, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ecosistema-rols/{id}")
    public ResponseEntity<EcosistemaRol> getEcosistemaRol(@PathVariable Long id) {
        log.debug("REST request to get EcosistemaRol : {}", id);
        Optional<EcosistemaRol> ecosistemaRol = ecosistemaRolService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ecosistemaRol);
    }

    /**
     * {@code DELETE  /ecosistema-rols/:id} : delete the "id" ecosistemaRol.
     *
     * @param id the id of the ecosistemaRol to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ecosistema-rols/{id}")
    public ResponseEntity<Void> deleteEcosistemaRol(@PathVariable Long id) {
        log.debug("REST request to delete EcosistemaRol : {}", id);
        ecosistemaRolService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
