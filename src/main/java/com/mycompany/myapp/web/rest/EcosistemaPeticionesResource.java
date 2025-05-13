package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.EcosistemaPeticiones;
import com.mycompany.myapp.repository.EcosistemaPeticionesRepository;
import com.mycompany.myapp.service.EcosistemaPeticionesService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.EcosistemaPeticiones}.
 */
@RestController
@RequestMapping("/api")
public class EcosistemaPeticionesResource {

    private final Logger log = LoggerFactory.getLogger(EcosistemaPeticionesResource.class);

    private static final String ENTITY_NAME = "ecosistemaPeticiones";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EcosistemaPeticionesService ecosistemaPeticionesService;

    private final EcosistemaPeticionesRepository ecosistemaPeticionesRepository;

    public EcosistemaPeticionesResource(
        EcosistemaPeticionesService ecosistemaPeticionesService,
        EcosistemaPeticionesRepository ecosistemaPeticionesRepository
    ) {
        this.ecosistemaPeticionesService = ecosistemaPeticionesService;
        this.ecosistemaPeticionesRepository = ecosistemaPeticionesRepository;
    }

    /**
     * {@code POST  /ecosistema-peticiones} : Create a new ecosistemaPeticiones.
     *
     * @param ecosistemaPeticiones the ecosistemaPeticiones to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ecosistemaPeticiones, or with status {@code 400 (Bad Request)} if the ecosistemaPeticiones has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ecosistema-peticiones")
    public ResponseEntity<EcosistemaPeticiones> createEcosistemaPeticiones(@Valid @RequestBody EcosistemaPeticiones ecosistemaPeticiones)
        throws URISyntaxException {
        log.debug("REST request to save EcosistemaPeticiones : {}", ecosistemaPeticiones);
        if (ecosistemaPeticiones.getId() != null) {
            throw new BadRequestAlertException("A new ecosistemaPeticiones cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EcosistemaPeticiones result = ecosistemaPeticionesService.save(ecosistemaPeticiones);
        return ResponseEntity
            .created(new URI("/api/ecosistema-peticiones/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ecosistema-peticiones/:id} : Updates an existing ecosistemaPeticiones.
     *
     * @param id the id of the ecosistemaPeticiones to save.
     * @param ecosistemaPeticiones the ecosistemaPeticiones to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ecosistemaPeticiones,
     * or with status {@code 400 (Bad Request)} if the ecosistemaPeticiones is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ecosistemaPeticiones couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ecosistema-peticiones/{id}")
    public ResponseEntity<EcosistemaPeticiones> updateEcosistemaPeticiones(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EcosistemaPeticiones ecosistemaPeticiones
    ) throws URISyntaxException {
        log.debug("REST request to update EcosistemaPeticiones : {}, {}", id, ecosistemaPeticiones);
        if (ecosistemaPeticiones.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ecosistemaPeticiones.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ecosistemaPeticionesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EcosistemaPeticiones result = ecosistemaPeticionesService.update(ecosistemaPeticiones);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ecosistemaPeticiones.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ecosistema-peticiones/:id} : Partial updates given fields of an existing ecosistemaPeticiones, field will ignore if it is null
     *
     * @param id the id of the ecosistemaPeticiones to save.
     * @param ecosistemaPeticiones the ecosistemaPeticiones to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ecosistemaPeticiones,
     * or with status {@code 400 (Bad Request)} if the ecosistemaPeticiones is not valid,
     * or with status {@code 404 (Not Found)} if the ecosistemaPeticiones is not found,
     * or with status {@code 500 (Internal Server Error)} if the ecosistemaPeticiones couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ecosistema-peticiones/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EcosistemaPeticiones> partialUpdateEcosistemaPeticiones(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EcosistemaPeticiones ecosistemaPeticiones
    ) throws URISyntaxException {
        log.debug("REST request to partial update EcosistemaPeticiones partially : {}, {}", id, ecosistemaPeticiones);
        if (ecosistemaPeticiones.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ecosistemaPeticiones.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ecosistemaPeticionesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EcosistemaPeticiones> result = ecosistemaPeticionesService.partialUpdate(ecosistemaPeticiones);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ecosistemaPeticiones.getId().toString())
        );
    }

    /**
     * {@code GET  /ecosistema-peticiones} : get all the ecosistemaPeticiones.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ecosistemaPeticiones in body.
     */
    @GetMapping("/ecosistema-peticiones")
    public List<EcosistemaPeticiones> getAllEcosistemaPeticiones(
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get all EcosistemaPeticiones");
        return ecosistemaPeticionesService.findAll();
    }

    /**
     * {@code GET  /ecosistema-peticiones/:id} : get the "id" ecosistemaPeticiones.
     *
     * @param id the id of the ecosistemaPeticiones to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ecosistemaPeticiones, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ecosistema-peticiones/{id}")
    public ResponseEntity<EcosistemaPeticiones> getEcosistemaPeticiones(@PathVariable Long id) {
        log.debug("REST request to get EcosistemaPeticiones : {}", id);
        Optional<EcosistemaPeticiones> ecosistemaPeticiones = ecosistemaPeticionesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ecosistemaPeticiones);
    }

    /**
     * {@code DELETE  /ecosistema-peticiones/:id} : delete the "id" ecosistemaPeticiones.
     *
     * @param id the id of the ecosistemaPeticiones to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ecosistema-peticiones/{id}")
    public ResponseEntity<Void> deleteEcosistemaPeticiones(@PathVariable Long id) {
        log.debug("REST request to delete EcosistemaPeticiones : {}", id);
        ecosistemaPeticionesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
