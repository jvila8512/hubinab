package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Componentes;
import com.mycompany.myapp.repository.ComponentesRepository;
import com.mycompany.myapp.service.ComponentesService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Componentes}.
 */
@RestController
@RequestMapping("/api")
public class ComponentesResource {

    private final Logger log = LoggerFactory.getLogger(ComponentesResource.class);

    private static final String ENTITY_NAME = "componentes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ComponentesService componentesService;

    private final ComponentesRepository componentesRepository;

    public ComponentesResource(ComponentesService componentesService, ComponentesRepository componentesRepository) {
        this.componentesService = componentesService;
        this.componentesRepository = componentesRepository;
    }

    /**
     * {@code POST  /componentes} : Create a new componentes.
     *
     * @param componentes the componentes to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new componentes, or with status {@code 400 (Bad Request)} if the componentes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/componentes")
    public ResponseEntity<Componentes> createComponentes(@Valid @RequestBody Componentes componentes) throws URISyntaxException {
        log.debug("REST request to save Componentes : {}", componentes);
        if (componentes.getId() != null) {
            throw new BadRequestAlertException("A new componentes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Componentes result = componentesService.save(componentes);
        return ResponseEntity
            .created(new URI("/api/componentes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /componentes/:id} : Updates an existing componentes.
     *
     * @param id the id of the componentes to save.
     * @param componentes the componentes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated componentes,
     * or with status {@code 400 (Bad Request)} if the componentes is not valid,
     * or with status {@code 500 (Internal Server Error)} if the componentes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/componentes/{id}")
    public ResponseEntity<Componentes> updateComponentes(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Componentes componentes
    ) throws URISyntaxException {
        log.debug("REST request to update Componentes : {}, {}", id, componentes);
        if (componentes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, componentes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!componentesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Componentes result = componentesService.update(componentes);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, componentes.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /componentes/:id} : Partial updates given fields of an existing componentes, field will ignore if it is null
     *
     * @param id the id of the componentes to save.
     * @param componentes the componentes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated componentes,
     * or with status {@code 400 (Bad Request)} if the componentes is not valid,
     * or with status {@code 404 (Not Found)} if the componentes is not found,
     * or with status {@code 500 (Internal Server Error)} if the componentes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/componentes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Componentes> partialUpdateComponentes(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Componentes componentes
    ) throws URISyntaxException {
        log.debug("REST request to partial update Componentes partially : {}, {}", id, componentes);
        if (componentes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, componentes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!componentesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Componentes> result = componentesService.partialUpdate(componentes);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, componentes.getId().toString())
        );
    }

    /**
     * {@code GET  /componentes} : get all the componentes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of componentes in body.
     */
    @GetMapping("/componentes")
    public List<Componentes> getAllComponentes() {
        log.debug("REST request to get all Componentes");
        return componentesService.findAll();
    }

    /**
     * {@code GET  /componentes/:id} : get the "id" componentes.
     *
     * @param id the id of the componentes to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the componentes, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/componentes/{id}")
    public ResponseEntity<Componentes> getComponentes(@PathVariable Long id) {
        log.debug("REST request to get Componentes : {}", id);
        Optional<Componentes> componentes = componentesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(componentes);
    }

    /**
     * {@code DELETE  /componentes/:id} : delete the "id" componentes.
     *
     * @param id the id of the componentes to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/componentes/{id}")
    public ResponseEntity<Void> deleteComponentes(@PathVariable Long id) {
        log.debug("REST request to delete Componentes : {}", id);
        componentesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
