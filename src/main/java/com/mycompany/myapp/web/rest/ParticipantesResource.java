package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Participantes;
import com.mycompany.myapp.repository.ParticipantesRepository;
import com.mycompany.myapp.service.ParticipantesService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Participantes}.
 */
@RestController
@RequestMapping("/api")
public class ParticipantesResource {

    private final Logger log = LoggerFactory.getLogger(ParticipantesResource.class);

    private static final String ENTITY_NAME = "participantes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ParticipantesService participantesService;

    private final ParticipantesRepository participantesRepository;

    public ParticipantesResource(ParticipantesService participantesService, ParticipantesRepository participantesRepository) {
        this.participantesService = participantesService;
        this.participantesRepository = participantesRepository;
    }

    /**
     * {@code POST  /participantes} : Create a new participantes.
     *
     * @param participantes the participantes to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new participantes, or with status {@code 400 (Bad Request)} if the participantes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/participantes")
    public ResponseEntity<Participantes> createParticipantes(@Valid @RequestBody Participantes participantes) throws URISyntaxException {
        log.debug("REST request to save Participantes : {}", participantes);
        if (participantes.getId() != null) {
            throw new BadRequestAlertException("A new participantes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Participantes result = participantesService.save(participantes);
        return ResponseEntity
            .created(new URI("/api/participantes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /participantes/:id} : Updates an existing participantes.
     *
     * @param id the id of the participantes to save.
     * @param participantes the participantes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated participantes,
     * or with status {@code 400 (Bad Request)} if the participantes is not valid,
     * or with status {@code 500 (Internal Server Error)} if the participantes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/participantes/{id}")
    public ResponseEntity<Participantes> updateParticipantes(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Participantes participantes
    ) throws URISyntaxException {
        log.debug("REST request to update Participantes : {}, {}", id, participantes);
        if (participantes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, participantes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!participantesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Participantes result = participantesService.update(participantes);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, participantes.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /participantes/:id} : Partial updates given fields of an existing participantes, field will ignore if it is null
     *
     * @param id the id of the participantes to save.
     * @param participantes the participantes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated participantes,
     * or with status {@code 400 (Bad Request)} if the participantes is not valid,
     * or with status {@code 404 (Not Found)} if the participantes is not found,
     * or with status {@code 500 (Internal Server Error)} if the participantes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/participantes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Participantes> partialUpdateParticipantes(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Participantes participantes
    ) throws URISyntaxException {
        log.debug("REST request to partial update Participantes partially : {}, {}", id, participantes);
        if (participantes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, participantes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!participantesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Participantes> result = participantesService.partialUpdate(participantes);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, participantes.getId().toString())
        );
    }

    /**
     * {@code GET  /participantes} : get all the participantes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of participantes in body.
     */
    @GetMapping("/participantes")
    public List<Participantes> getAllParticipantes() {
        log.debug("REST request to get all Participantes");
        return participantesService.findAll();
    }

    /**
     * {@code GET  /participantes/:id} : get the "id" participantes.
     *
     * @param id the id of the participantes to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the participantes, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/participantes/{id}")
    public ResponseEntity<Participantes> getParticipantes(@PathVariable Long id) {
        log.debug("REST request to get Participantes : {}", id);
        Optional<Participantes> participantes = participantesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(participantes);
    }

    /**
     * {@code DELETE  /participantes/:id} : delete the "id" participantes.
     *
     * @param id the id of the participantes to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/participantes/{id}")
    public ResponseEntity<Void> deleteParticipantes(@PathVariable Long id) {
        log.debug("REST request to delete Participantes : {}", id);
        participantesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
