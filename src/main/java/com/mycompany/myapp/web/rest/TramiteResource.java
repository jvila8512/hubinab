package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Tramite;
import com.mycompany.myapp.repository.TramiteRepository;
import com.mycompany.myapp.service.TramiteService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Tramite}.
 */
@RestController
@RequestMapping("/api")
public class TramiteResource {

    private final Logger log = LoggerFactory.getLogger(TramiteResource.class);

    private static final String ENTITY_NAME = "tramite";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TramiteService tramiteService;

    private final TramiteRepository tramiteRepository;

    public TramiteResource(TramiteService tramiteService, TramiteRepository tramiteRepository) {
        this.tramiteService = tramiteService;
        this.tramiteRepository = tramiteRepository;
    }

    /**
     * {@code POST  /tramites} : Create a new tramite.
     *
     * @param tramite the tramite to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tramite, or with status {@code 400 (Bad Request)} if the tramite has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tramites")
    public ResponseEntity<Tramite> createTramite(@RequestBody Tramite tramite) throws URISyntaxException {
        log.debug("REST request to save Tramite : {}", tramite);
        if (tramite.getId() != null) {
            throw new BadRequestAlertException("A new tramite cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Tramite result = tramiteService.save(tramite);
        return ResponseEntity
            .created(new URI("/api/tramites/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tramites/:id} : Updates an existing tramite.
     *
     * @param id the id of the tramite to save.
     * @param tramite the tramite to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tramite,
     * or with status {@code 400 (Bad Request)} if the tramite is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tramite couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tramites/{id}")
    public ResponseEntity<Tramite> updateTramite(@PathVariable(value = "id", required = false) final Long id, @RequestBody Tramite tramite)
        throws URISyntaxException {
        log.debug("REST request to update Tramite : {}, {}", id, tramite);
        if (tramite.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tramite.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tramiteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Tramite result = tramiteService.update(tramite);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tramite.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tramites/:id} : Partial updates given fields of an existing tramite, field will ignore if it is null
     *
     * @param id the id of the tramite to save.
     * @param tramite the tramite to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tramite,
     * or with status {@code 400 (Bad Request)} if the tramite is not valid,
     * or with status {@code 404 (Not Found)} if the tramite is not found,
     * or with status {@code 500 (Internal Server Error)} if the tramite couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tramites/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Tramite> partialUpdateTramite(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Tramite tramite
    ) throws URISyntaxException {
        log.debug("REST request to partial update Tramite partially : {}, {}", id, tramite);
        if (tramite.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tramite.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tramiteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Tramite> result = tramiteService.partialUpdate(tramite);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tramite.getId().toString())
        );
    }

    /**
     * {@code GET  /tramites} : get all the tramites.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tramites in body.
     */
    @GetMapping("/tramites")
    public List<Tramite> getAllTramites() {
        log.debug("REST request to get all Tramites");
        return tramiteService.findAll();
    }

    /**
     * {@code GET  /tramites/:id} : get the "id" tramite.
     *
     * @param id the id of the tramite to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tramite, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tramites/{id}")
    public ResponseEntity<Tramite> getTramite(@PathVariable Long id) {
        log.debug("REST request to get Tramite : {}", id);
        Optional<Tramite> tramite = tramiteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tramite);
    }

    /**
     * {@code DELETE  /tramites/:id} : delete the "id" tramite.
     *
     * @param id the id of the tramite to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tramites/{id}")
    public ResponseEntity<Void> deleteTramite(@PathVariable Long id) {
        log.debug("REST request to delete Tramite : {}", id);
        tramiteService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
