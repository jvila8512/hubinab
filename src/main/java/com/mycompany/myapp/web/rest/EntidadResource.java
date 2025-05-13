package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Entidad;
import com.mycompany.myapp.repository.EntidadRepository;
import com.mycompany.myapp.service.EntidadService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Entidad}.
 */
@RestController
@RequestMapping("/api")
public class EntidadResource {

    private final Logger log = LoggerFactory.getLogger(EntidadResource.class);

    private static final String ENTITY_NAME = "entidad";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EntidadService entidadService;

    private final EntidadRepository entidadRepository;

    public EntidadResource(EntidadService entidadService, EntidadRepository entidadRepository) {
        this.entidadService = entidadService;
        this.entidadRepository = entidadRepository;
    }

    /**
     * {@code POST  /entidads} : Create a new entidad.
     *
     * @param entidad the entidad to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new entidad, or with status {@code 400 (Bad Request)} if the entidad has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/entidads")
    public ResponseEntity<Entidad> createEntidad(@Valid @RequestBody Entidad entidad) throws URISyntaxException {
        log.debug("REST request to save Entidad : {}", entidad);
        if (entidad.getId() != null) {
            throw new BadRequestAlertException("A new entidad cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Entidad result = entidadService.save(entidad);
        return ResponseEntity
            .created(new URI("/api/entidads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /entidads/:id} : Updates an existing entidad.
     *
     * @param id the id of the entidad to save.
     * @param entidad the entidad to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated entidad,
     * or with status {@code 400 (Bad Request)} if the entidad is not valid,
     * or with status {@code 500 (Internal Server Error)} if the entidad couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/entidads/{id}")
    public ResponseEntity<Entidad> updateEntidad(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Entidad entidad
    ) throws URISyntaxException {
        log.debug("REST request to update Entidad : {}, {}", id, entidad);
        if (entidad.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, entidad.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!entidadRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Entidad result = entidadService.update(entidad);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, entidad.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /entidads/:id} : Partial updates given fields of an existing entidad, field will ignore if it is null
     *
     * @param id the id of the entidad to save.
     * @param entidad the entidad to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated entidad,
     * or with status {@code 400 (Bad Request)} if the entidad is not valid,
     * or with status {@code 404 (Not Found)} if the entidad is not found,
     * or with status {@code 500 (Internal Server Error)} if the entidad couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/entidads/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Entidad> partialUpdateEntidad(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Entidad entidad
    ) throws URISyntaxException {
        log.debug("REST request to partial update Entidad partially : {}, {}", id, entidad);
        if (entidad.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, entidad.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!entidadRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Entidad> result = entidadService.partialUpdate(entidad);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, entidad.getId().toString())
        );
    }

    /**
     * {@code GET  /entidads} : get all the entidads.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entidads in body.
     */
    @GetMapping("/entidads")
    public List<Entidad> getAllEntidads() {
        log.debug("REST request to get all Entidads");
        return entidadService.findAll();
    }

    /**
     * {@code GET  /entidads/:id} : get the "id" entidad.
     *
     * @param id the id of the entidad to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the entidad, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/entidads/{id}")
    public ResponseEntity<Entidad> getEntidad(@PathVariable Long id) {
        log.debug("REST request to get Entidad : {}", id);
        Optional<Entidad> entidad = entidadService.findOne(id);
        return ResponseUtil.wrapOrNotFound(entidad);
    }

    /**
     * {@code DELETE  /entidads/:id} : delete the "id" entidad.
     *
     * @param id the id of the entidad to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/entidads/{id}")
    public ResponseEntity<Void> deleteEntidad(@PathVariable Long id) {
        log.debug("REST request to delete Entidad : {}", id);
        entidadService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
