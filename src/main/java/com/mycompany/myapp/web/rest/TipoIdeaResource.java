package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.TipoIdea;
import com.mycompany.myapp.repository.TipoIdeaRepository;
import com.mycompany.myapp.service.TipoIdeaService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.TipoIdea}.
 */
@RestController
@RequestMapping("/api")
public class TipoIdeaResource {

    private final Logger log = LoggerFactory.getLogger(TipoIdeaResource.class);

    private static final String ENTITY_NAME = "tipoIdea";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoIdeaService tipoIdeaService;

    private final TipoIdeaRepository tipoIdeaRepository;

    public TipoIdeaResource(TipoIdeaService tipoIdeaService, TipoIdeaRepository tipoIdeaRepository) {
        this.tipoIdeaService = tipoIdeaService;
        this.tipoIdeaRepository = tipoIdeaRepository;
    }

    /**
     * {@code POST  /tipo-ideas} : Create a new tipoIdea.
     *
     * @param tipoIdea the tipoIdea to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoIdea, or with status {@code 400 (Bad Request)} if the tipoIdea has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-ideas")
    public ResponseEntity<TipoIdea> createTipoIdea(@Valid @RequestBody TipoIdea tipoIdea) throws URISyntaxException {
        log.debug("REST request to save TipoIdea : {}", tipoIdea);
        if (tipoIdea.getId() != null) {
            throw new BadRequestAlertException("A new tipoIdea cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoIdea result = tipoIdeaService.save(tipoIdea);
        return ResponseEntity
            .created(new URI("/api/tipo-ideas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-ideas/:id} : Updates an existing tipoIdea.
     *
     * @param id the id of the tipoIdea to save.
     * @param tipoIdea the tipoIdea to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoIdea,
     * or with status {@code 400 (Bad Request)} if the tipoIdea is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoIdea couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-ideas/{id}")
    public ResponseEntity<TipoIdea> updateTipoIdea(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TipoIdea tipoIdea
    ) throws URISyntaxException {
        log.debug("REST request to update TipoIdea : {}, {}", id, tipoIdea);
        if (tipoIdea.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tipoIdea.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tipoIdeaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TipoIdea result = tipoIdeaService.update(tipoIdea);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoIdea.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tipo-ideas/:id} : Partial updates given fields of an existing tipoIdea, field will ignore if it is null
     *
     * @param id the id of the tipoIdea to save.
     * @param tipoIdea the tipoIdea to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoIdea,
     * or with status {@code 400 (Bad Request)} if the tipoIdea is not valid,
     * or with status {@code 404 (Not Found)} if the tipoIdea is not found,
     * or with status {@code 500 (Internal Server Error)} if the tipoIdea couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tipo-ideas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TipoIdea> partialUpdateTipoIdea(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TipoIdea tipoIdea
    ) throws URISyntaxException {
        log.debug("REST request to partial update TipoIdea partially : {}, {}", id, tipoIdea);
        if (tipoIdea.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tipoIdea.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tipoIdeaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TipoIdea> result = tipoIdeaService.partialUpdate(tipoIdea);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoIdea.getId().toString())
        );
    }

    /**
     * {@code GET  /tipo-ideas} : get all the tipoIdeas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoIdeas in body.
     */
    @GetMapping("/tipo-ideas")
    public List<TipoIdea> getAllTipoIdeas() {
        log.debug("REST request to get all TipoIdeas");
        return tipoIdeaService.findAll();
    }

    /**
     * {@code GET  /tipo-ideas/:id} : get the "id" tipoIdea.
     *
     * @param id the id of the tipoIdea to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoIdea, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-ideas/{id}")
    public ResponseEntity<TipoIdea> getTipoIdea(@PathVariable Long id) {
        log.debug("REST request to get TipoIdea : {}", id);
        Optional<TipoIdea> tipoIdea = tipoIdeaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoIdea);
    }

    /**
     * {@code DELETE  /tipo-ideas/:id} : delete the "id" tipoIdea.
     *
     * @param id the id of the tipoIdea to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-ideas/{id}")
    public ResponseEntity<Void> deleteTipoIdea(@PathVariable Long id) {
        log.debug("REST request to delete TipoIdea : {}", id);
        tipoIdeaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
