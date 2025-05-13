package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Idea;
import com.mycompany.myapp.domain.Reto;
import com.mycompany.myapp.repository.IdeaRepository;
import com.mycompany.myapp.service.IdeaService;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Idea}.
 */
@RestController
@RequestMapping("/api")
public class IdeaResource {

    private final Logger log = LoggerFactory.getLogger(IdeaResource.class);

    private static final String ENTITY_NAME = "idea";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IdeaService ideaService;

    private final IdeaRepository ideaRepository;

    public IdeaResource(IdeaService ideaService, IdeaRepository ideaRepository) {
        this.ideaService = ideaService;
        this.ideaRepository = ideaRepository;
    }

    /**
     * {@code POST  /ideas} : Create a new idea.
     *
     * @param idea the idea to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new idea, or with status {@code 400 (Bad Request)} if the idea has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ideas")
    public ResponseEntity<Idea> createIdea(@Valid @RequestBody Idea idea) throws URISyntaxException {
        log.debug("REST request to save Idea : {}", idea);
        if (idea.getId() != null) {
            throw new BadRequestAlertException("A new idea cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Idea result = ideaService.save(idea);
        return ResponseEntity
            .created(new URI("/api/ideas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ideas/:id} : Updates an existing idea.
     *
     * @param id the id of the idea to save.
     * @param idea the idea to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated idea,
     * or with status {@code 400 (Bad Request)} if the idea is not valid,
     * or with status {@code 500 (Internal Server Error)} if the idea couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ideas/{id}")
    public ResponseEntity<Idea> updateIdea(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Idea idea)
        throws URISyntaxException {
        log.debug("REST request to update Idea : {}, {}", id, idea);
        if (idea.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, idea.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ideaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Idea result = ideaService.update(idea);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, idea.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ideas/:id} : Updates an existing idea.
     *
     * @param id the id of the idea to save.
     * @param idea the idea to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated idea,
     * or with status {@code 400 (Bad Request)} if the idea is not valid,
     * or with status {@code 500 (Internal Server Error)} if the idea couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ideas/sinrespuesta/{id}")
    public ResponseEntity<Idea> updateIdeasinRespuesta(@PathVariable(value = "id", required = false) final Long id)
        throws URISyntaxException {
        Idea result = null;
        Optional<Idea> idea1 = ideaService.findOne(id);

        Idea ideaok = idea1.orElseThrow(() -> new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));

        if (ideaok != null) {
            ideaok.setVisto(ideaok.getVisto() + 1);
            result = ideaService.update(ideaok);
        }

        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code PATCH  /ideas/:id} : Partial updates given fields of an existing idea, field will ignore if it is null
     *
     * @param id the id of the idea to save.
     * @param idea the idea to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated idea,
     * or with status {@code 400 (Bad Request)} if the idea is not valid,
     * or with status {@code 404 (Not Found)} if the idea is not found,
     * or with status {@code 500 (Internal Server Error)} if the idea couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ideas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Idea> partialUpdateIdea(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Idea idea
    ) throws URISyntaxException {
        log.debug("REST request to partial update Idea partially : {}, {}", id, idea);
        if (idea.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, idea.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ideaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Idea> result = ideaService.partialUpdate(idea);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, idea.getId().toString())
        );
    }

    /**
     * {@code GET  /ideas} : get all the ideas.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ideas in body.
     */
    @GetMapping("/ideas")
    public ResponseEntity<List<Idea>> getAllIdeas(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of Ideas");
        Page<Idea> page;
        if (eagerload) {
            page = ideaService.findAllWithEagerRelationships(pageable);
        } else {
            page = ideaService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ideas} : get all the ideas by Retos ID.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ideas in body.
     */
    @GetMapping("/ideas/ideasbyretoid/{id}/{iduser}")
    public List<Idea> getAllIdeasByRetoId(
        @PathVariable(value = "id", required = false) final Long id,
        @PathVariable(value = "iduser", required = false) final Long iduser
    ) {
        return ideaService.findAllWithEagerRelationshipsByRetoId(id, iduser);
    }

    /**
     * {@code GET  /ideas} : get all the ideas by Retos ID.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ideas in body.
     */
    @GetMapping("/ideas/ideasbyretosolo/{id}")
    public List<Idea> getAllIdeasByRetoIdSolo(@PathVariable(value = "id", required = false) final Long id) {
        return ideaService.findAllWithEagerRelationshipsByRetoIdSolo(id);
    }

    /**
     * {@code GET  /ideas/:id} : get the "id" idea.
     *
     * @param id the id of the idea to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the idea, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ideas/{id}")
    public ResponseEntity<Idea> getIdea(@PathVariable Long id) {
        log.debug("REST request to get Idea : {}", id);
        Optional<Idea> idea = ideaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(idea);
    }

    /**
     * {@code DELETE  /ideas/:id} : delete the "id" idea.
     *
     * @param id the id of the idea to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ideas/{id}")
    public ResponseEntity<Void> deleteIdea(@PathVariable Long id) {
        log.debug("REST request to delete Idea : {}", id);
        ideaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
