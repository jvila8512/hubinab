package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.ChangeMacker;
import com.mycompany.myapp.repository.ChangeMackerRepository;
import com.mycompany.myapp.service.ChangeMackerService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.ChangeMacker}.
 */
@RestController
@RequestMapping("/api")
public class ChangeMackerResource {

    private final Logger log = LoggerFactory.getLogger(ChangeMackerResource.class);

    private static final String ENTITY_NAME = "changeMacker";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChangeMackerService changeMackerService;

    private final ChangeMackerRepository changeMackerRepository;

    public ChangeMackerResource(ChangeMackerService changeMackerService, ChangeMackerRepository changeMackerRepository) {
        this.changeMackerService = changeMackerService;
        this.changeMackerRepository = changeMackerRepository;
    }

    /**
     * {@code POST  /change-mackers} : Create a new changeMacker.
     *
     * @param changeMacker the changeMacker to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new changeMacker, or with status {@code 400 (Bad Request)} if the changeMacker has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/change-mackers")
    public ResponseEntity<ChangeMacker> createChangeMacker(@Valid @RequestBody ChangeMacker changeMacker) throws URISyntaxException {
        log.debug("REST request to save ChangeMacker : {}", changeMacker);
        if (changeMacker.getId() != null) {
            throw new BadRequestAlertException("A new changeMacker cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChangeMacker result = changeMackerService.save(changeMacker);
        return ResponseEntity
            .created(new URI("/api/change-mackers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /change-mackers/:id} : Updates an existing changeMacker.
     *
     * @param id the id of the changeMacker to save.
     * @param changeMacker the changeMacker to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated changeMacker,
     * or with status {@code 400 (Bad Request)} if the changeMacker is not valid,
     * or with status {@code 500 (Internal Server Error)} if the changeMacker couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/change-mackers/{id}")
    public ResponseEntity<ChangeMacker> updateChangeMacker(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ChangeMacker changeMacker
    ) throws URISyntaxException {
        log.debug("REST request to update ChangeMacker : {}, {}", id, changeMacker);
        if (changeMacker.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, changeMacker.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!changeMackerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ChangeMacker result = changeMackerService.update(changeMacker);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, changeMacker.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /change-mackers/:id} : Partial updates given fields of an existing changeMacker, field will ignore if it is null
     *
     * @param id the id of the changeMacker to save.
     * @param changeMacker the changeMacker to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated changeMacker,
     * or with status {@code 400 (Bad Request)} if the changeMacker is not valid,
     * or with status {@code 404 (Not Found)} if the changeMacker is not found,
     * or with status {@code 500 (Internal Server Error)} if the changeMacker couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/change-mackers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ChangeMacker> partialUpdateChangeMacker(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ChangeMacker changeMacker
    ) throws URISyntaxException {
        log.debug("REST request to partial update ChangeMacker partially : {}, {}", id, changeMacker);
        if (changeMacker.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, changeMacker.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!changeMackerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ChangeMacker> result = changeMackerService.partialUpdate(changeMacker);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, changeMacker.getId().toString())
        );
    }

    /**
     * {@code GET  /change-mackers} : get all the changeMackers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of changeMackers in body.
     */
    @GetMapping("/change-mackers")
    public List<ChangeMacker> getAllChangeMackers() {
        log.debug("REST request to get all ChangeMackers");
        return changeMackerService.findAll();
    }

    /**
     * {@code GET  /change-mackers/:id} : get the "id" changeMacker.
     *
     * @param id the id of the changeMacker to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the changeMacker, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/change-mackers/{id}")
    public ResponseEntity<ChangeMacker> getChangeMacker(@PathVariable Long id) {
        log.debug("REST request to get ChangeMacker : {}", id);
        Optional<ChangeMacker> changeMacker = changeMackerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(changeMacker);
    }

    /**
     * {@code DELETE  /change-mackers/:id} : delete the "id" changeMacker.
     *
     * @param id the id of the changeMacker to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/change-mackers/{id}")
    public ResponseEntity<Void> deleteChangeMacker(@PathVariable Long id) {
        log.debug("REST request to delete ChangeMacker : {}", id);
        changeMackerService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
