package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.LikeIdea;
import com.mycompany.myapp.repository.LikeIdeaRepository;
import com.mycompany.myapp.service.LikeIdeaService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.LikeIdea}.
 */
@RestController
@RequestMapping("/api")
public class LikeIdeaResource {

    private final Logger log = LoggerFactory.getLogger(LikeIdeaResource.class);

    private static final String ENTITY_NAME = "likeIdea";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LikeIdeaService likeIdeaService;

    private final LikeIdeaRepository likeIdeaRepository;

    public LikeIdeaResource(LikeIdeaService likeIdeaService, LikeIdeaRepository likeIdeaRepository) {
        this.likeIdeaService = likeIdeaService;
        this.likeIdeaRepository = likeIdeaRepository;
    }

    /**
     * {@code POST  /like-ideas} : Create a new likeIdea.
     *
     * @param likeIdea the likeIdea to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new likeIdea, or with status {@code 400 (Bad Request)} if the likeIdea has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/like-ideas")
    public ResponseEntity<LikeIdea> createLikeIdea(@Valid @RequestBody LikeIdea likeIdea) throws URISyntaxException {
        log.debug("REST request to save LikeIdea : {}", likeIdea);
        if (likeIdea.getId() != null) {
            throw new BadRequestAlertException("A new likeIdea cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LikeIdea result = likeIdeaService.save(likeIdea);
        return ResponseEntity
            .created(new URI("/api/like-ideas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /like-ideas/:id} : Updates an existing likeIdea.
     *
     * @param id the id of the likeIdea to save.
     * @param likeIdea the likeIdea to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated likeIdea,
     * or with status {@code 400 (Bad Request)} if the likeIdea is not valid,
     * or with status {@code 500 (Internal Server Error)} if the likeIdea couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/like-ideas/{id}")
    public ResponseEntity<LikeIdea> updateLikeIdea(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LikeIdea likeIdea
    ) throws URISyntaxException {
        log.debug("REST request to update LikeIdea : {}, {}", id, likeIdea);
        if (likeIdea.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, likeIdea.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!likeIdeaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LikeIdea result = likeIdeaService.update(likeIdea);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, likeIdea.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /like-ideas/:id} : Partial updates given fields of an existing likeIdea, field will ignore if it is null
     *
     * @param id the id of the likeIdea to save.
     * @param likeIdea the likeIdea to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated likeIdea,
     * or with status {@code 400 (Bad Request)} if the likeIdea is not valid,
     * or with status {@code 404 (Not Found)} if the likeIdea is not found,
     * or with status {@code 500 (Internal Server Error)} if the likeIdea couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/like-ideas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LikeIdea> partialUpdateLikeIdea(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LikeIdea likeIdea
    ) throws URISyntaxException {
        log.debug("REST request to partial update LikeIdea partially : {}, {}", id, likeIdea);
        if (likeIdea.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, likeIdea.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!likeIdeaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LikeIdea> result = likeIdeaService.partialUpdate(likeIdea);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, likeIdea.getId().toString())
        );
    }

    /**
     * {@code GET  /like-ideas} : get all the likeIdeas.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of likeIdeas in body.
     */
    @GetMapping("/like-ideas")
    public List<LikeIdea> getAllLikeIdeas(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all LikeIdeas");
        return likeIdeaService.findAll();
    }

    /**
     * {@code GET  /like-ideas} : get all the likeIdeas by Id de Idea.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of likeIdeas in body.
     */
    @GetMapping("/like-ideas/byIdea/{id}")
    public List<LikeIdea> getAllLikeIdeasByIdea(@RequestParam(required = false, defaultValue = "false") Long id) {
        log.debug("REST request to get all LikeIdeas by Id de Idea");
        return likeIdeaService.findAllbyIdea(id);
    }

    /**
     * {@code GET  /like-ideas/:id} : get the "id" likeIdea.
     *
     * @param id the id of the likeIdea to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the likeIdea, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/like-ideas/{id}")
    public ResponseEntity<LikeIdea> getLikeIdea(@PathVariable Long id) {
        log.debug("REST request to get LikeIdea : {}", id);
        Optional<LikeIdea> likeIdea = likeIdeaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(likeIdea);
    }

    /**
     * {@code DELETE  /like-ideas/:id} : delete the "id" likeIdea.
     *
     * @param id the id of the likeIdea to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/like-ideas/{id}")
    public ResponseEntity<Void> deleteLikeIdea(@PathVariable Long id) {
        log.debug("REST request to delete LikeIdea : {}", id);
        likeIdeaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
