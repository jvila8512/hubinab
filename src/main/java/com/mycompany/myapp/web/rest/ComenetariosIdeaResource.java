package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.ComenetariosIdea;
import com.mycompany.myapp.repository.ComenetariosIdeaRepository;
import com.mycompany.myapp.service.ComenetariosIdeaService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.ComenetariosIdea}.
 */
@RestController
@RequestMapping("/api")
public class ComenetariosIdeaResource {

    private final Logger log = LoggerFactory.getLogger(ComenetariosIdeaResource.class);

    private static final String ENTITY_NAME = "comenetariosIdea";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ComenetariosIdeaService comenetariosIdeaService;

    private final ComenetariosIdeaRepository comenetariosIdeaRepository;

    public ComenetariosIdeaResource(
        ComenetariosIdeaService comenetariosIdeaService,
        ComenetariosIdeaRepository comenetariosIdeaRepository
    ) {
        this.comenetariosIdeaService = comenetariosIdeaService;
        this.comenetariosIdeaRepository = comenetariosIdeaRepository;
    }

    /**
     * {@code POST  /comenetarios-ideas} : Create a new comenetariosIdea.
     *
     * @param comenetariosIdea the comenetariosIdea to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new comenetariosIdea, or with status {@code 400 (Bad Request)} if the comenetariosIdea has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/comenetarios-ideas")
    public ResponseEntity<ComenetariosIdea> createComenetariosIdea(@Valid @RequestBody ComenetariosIdea comenetariosIdea)
        throws URISyntaxException {
        log.debug("REST request to save ComenetariosIdea : {}", comenetariosIdea);
        if (comenetariosIdea.getId() != null) {
            throw new BadRequestAlertException("A new comenetariosIdea cannot already have an ID", ENTITY_NAME, "idexists");
        }
        comenetariosIdea.setFechaInscripcion(LocalDate.now());
        ComenetariosIdea result = comenetariosIdeaService.save(comenetariosIdea);
        return ResponseEntity
            .created(new URI("/api/comenetarios-ideas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /comenetarios-ideas/:id} : Updates an existing comenetariosIdea.
     *
     * @param id the id of the comenetariosIdea to save.
     * @param comenetariosIdea the comenetariosIdea to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated comenetariosIdea,
     * or with status {@code 400 (Bad Request)} if the comenetariosIdea is not valid,
     * or with status {@code 500 (Internal Server Error)} if the comenetariosIdea couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/comenetarios-ideas/{id}")
    public ResponseEntity<ComenetariosIdea> updateComenetariosIdea(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ComenetariosIdea comenetariosIdea
    ) throws URISyntaxException {
        log.debug("REST request to update ComenetariosIdea : {}, {}", id, comenetariosIdea);
        if (comenetariosIdea.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, comenetariosIdea.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!comenetariosIdeaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ComenetariosIdea result = comenetariosIdeaService.update(comenetariosIdea);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, comenetariosIdea.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /comenetarios-ideas/:id} : Partial updates given fields of an existing comenetariosIdea, field will ignore if it is null
     *
     * @param id the id of the comenetariosIdea to save.
     * @param comenetariosIdea the comenetariosIdea to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated comenetariosIdea,
     * or with status {@code 400 (Bad Request)} if the comenetariosIdea is not valid,
     * or with status {@code 404 (Not Found)} if the comenetariosIdea is not found,
     * or with status {@code 500 (Internal Server Error)} if the comenetariosIdea couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/comenetarios-ideas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ComenetariosIdea> partialUpdateComenetariosIdea(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ComenetariosIdea comenetariosIdea
    ) throws URISyntaxException {
        log.debug("REST request to partial update ComenetariosIdea partially : {}, {}", id, comenetariosIdea);
        if (comenetariosIdea.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, comenetariosIdea.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!comenetariosIdeaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ComenetariosIdea> result = comenetariosIdeaService.partialUpdate(comenetariosIdea);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, comenetariosIdea.getId().toString())
        );
    }

    /**
     * {@code GET  /comenetarios-ideas} : get all the comenetariosIdeas.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of comenetariosIdeas in body.
     */
    @GetMapping("/comenetarios-ideas")
    public List<ComenetariosIdea> getAllComenetariosIdeas(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all ComenetariosIdeas");
        return comenetariosIdeaService.findAll();
    }

    /**
     * {@code GET  /comenetarios-ideas} : get all the comenetariosIdeas.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of comenetariosIdeas in body.
     */
    @GetMapping("/comenetarios-ideas/ideas/{id}")
    public List<ComenetariosIdea> getAllComenetariosIdeasByIdIdea(@PathVariable Long id) {
        log.debug("REST request to get all ComenetariosIdeas by Id Ideas");
        return comenetariosIdeaService.findAllByIdIdea(id);
    }

    /**
     * {@code GET  /comenetarios-ideas/:id} : get the "id" comenetariosIdea.
     *
     * @param id the id of the comenetariosIdea to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the comenetariosIdea, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/comenetarios-ideas/{id}")
    public ResponseEntity<ComenetariosIdea> getComenetariosIdea(@PathVariable Long id) {
        log.debug("REST request to get ComenetariosIdea : {}", id);
        Optional<ComenetariosIdea> comenetariosIdea = comenetariosIdeaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(comenetariosIdea);
    }

    /**
     * {@code DELETE  /comenetarios-ideas/:id} : delete the "id" comenetariosIdea.
     *
     * @param id the id of the comenetariosIdea to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/comenetarios-ideas/{id}")
    public ResponseEntity<Void> deleteComenetariosIdea(@PathVariable Long id) {
        log.debug("REST request to delete ComenetariosIdea : {}", id);
        comenetariosIdeaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
