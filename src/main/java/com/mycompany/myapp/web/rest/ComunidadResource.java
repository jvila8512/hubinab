package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Comunidad;
import com.mycompany.myapp.repository.ComunidadRepository;
import com.mycompany.myapp.service.ComunidadService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Comunidad}.
 */
@RestController
@RequestMapping("/api")
public class ComunidadResource {

    private final Logger log = LoggerFactory.getLogger(ComunidadResource.class);

    private static final String ENTITY_NAME = "comunidad";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ComunidadService ComunidadService;

    private final ComunidadRepository ComunidadRepository;

    public ComunidadResource(ComunidadService ComunidadService, ComunidadRepository ComunidadRepository) {
        this.ComunidadService = ComunidadService;
        this.ComunidadRepository = ComunidadRepository;
    }

    /**
     * {@code POST  /comunidad} : Create a new comunidad.
     *
     * @param comunidad the comunidad to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new comunidad, or with status {@code 400 (Bad Request)} if the comunidad has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/comunidad")
    public ResponseEntity<Comunidad> createcomunidad(@Valid @RequestBody Comunidad comunidad) throws URISyntaxException {
        log.debug("REST request to save comunidad : {}", comunidad);
        if (comunidad.getId() != null) {
            throw new BadRequestAlertException("A new comunidad cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Comunidad result = ComunidadService.save(comunidad);
        return ResponseEntity
            .created(new URI("/api/comunidad/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /comunidad/:id} : Updates an existing comunidad.
     *
     * @param id the id of the comunidad to save.
     * @param comunidad the comunidad to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated comunidad,
     * or with status {@code 400 (Bad Request)} if the comunidad is not valid,
     * or with status {@code 500 (Internal Server Error)} if the comunidad couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/comunidad/{id}")
    public ResponseEntity<Comunidad> updatecomunidad(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Comunidad comunidad
    ) throws URISyntaxException {
        log.debug("REST request to update comunidad : {}, {}", id, comunidad);
        if (comunidad.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, comunidad.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ComunidadRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Comunidad result = ComunidadService.update(comunidad);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, comunidad.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /comunidad/:id} : Partial updates given fields of an existing comunidad, field will ignore if it is null
     *
     * @param id the id of the comunidad to save.
     * @param comunidad the comunidad to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated comunidad,
     * or with status {@code 400 (Bad Request)} if the comunidad is not valid,
     * or with status {@code 404 (Not Found)} if the comunidad is not found,
     * or with status {@code 500 (Internal Server Error)} if the comunidad couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/comunidad/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Comunidad> partialUpdatecomunidad(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Comunidad comunidad
    ) throws URISyntaxException {
        log.debug("REST request to partial update comunidad partially : {}, {}", id, comunidad);
        if (comunidad.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, comunidad.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ComunidadRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Comunidad> result = ComunidadService.partialUpdate(comunidad);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, comunidad.getId().toString())
        );
    }

    /**
     * {@code GET  /comunidad} : get all the comunidad.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of comunidad in body.
     */
    @GetMapping("/comunidad")
    public List<Comunidad> getAllcomunidad() {
        log.debug("REST request to get all comunidad");
        return ComunidadService.findAll();
    }

    /**
     * {@code GET  /comunidad} : get all the comunidad by activo.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of comunidad in body.
     */
    @GetMapping("/comunidadActiva")
    public List<Comunidad> getAllcomunidadByActivo() {
        log.debug("REST request to get all comunidad");
        return ComunidadService.findAllByActivo();
    }

    /**
     * {@code GET  /comunidad/:id} : get the "id" comunidad.
     *
     * @param id the id of the comunidad to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the comunidad, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/comunidad/{id}")
    public ResponseEntity<Comunidad> getcomunidad(@PathVariable Long id) {
        log.debug("REST request to get comunidad : {}", id);
        Optional<Comunidad> comunidad = ComunidadService.findOne(id);
        return ResponseUtil.wrapOrNotFound(comunidad);
    }

    /**
     * {@code DELETE  /comunidad/:id} : delete the "id" comunidad.
     *
     * @param id the id of the comunidad to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/comunidad/{id}")
    public ResponseEntity<Void> deletecomunidad(@PathVariable Long id) {
        log.debug("REST request to delete comunidad : {}", id);
        ComunidadService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
