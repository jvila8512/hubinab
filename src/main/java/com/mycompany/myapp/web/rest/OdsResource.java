package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Ods;
import com.mycompany.myapp.repository.OdsRepository;
import com.mycompany.myapp.service.OdsService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Ods}.
 */
@RestController
@RequestMapping("/api")
public class OdsResource {

    private final Logger log = LoggerFactory.getLogger(OdsResource.class);

    private static final String ENTITY_NAME = "ods";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OdsService odsService;

    private final OdsRepository odsRepository;

    public OdsResource(OdsService odsService, OdsRepository odsRepository) {
        this.odsService = odsService;
        this.odsRepository = odsRepository;
    }

    /**
     * {@code POST  /ods} : Create a new ods.
     *
     * @param ods the ods to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ods, or with status {@code 400 (Bad Request)} if the ods has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ods")
    public ResponseEntity<Ods> createOds(@Valid @RequestBody Ods ods) throws URISyntaxException {
        log.debug("REST request to save Ods : {}", ods);
        if (ods.getId() != null) {
            throw new BadRequestAlertException("A new ods cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Ods result = odsService.save(ods);
        return ResponseEntity
            .created(new URI("/api/ods/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ods/:id} : Updates an existing ods.
     *
     * @param id the id of the ods to save.
     * @param ods the ods to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ods,
     * or with status {@code 400 (Bad Request)} if the ods is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ods couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ods/{id}")
    public ResponseEntity<Ods> updateOds(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Ods ods)
        throws URISyntaxException {
        log.debug("REST request to update Ods : {}, {}", id, ods);
        if (ods.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ods.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!odsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Ods result = odsService.update(ods);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ods.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ods/:id} : Partial updates given fields of an existing ods, field will ignore if it is null
     *
     * @param id the id of the ods to save.
     * @param ods the ods to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ods,
     * or with status {@code 400 (Bad Request)} if the ods is not valid,
     * or with status {@code 404 (Not Found)} if the ods is not found,
     * or with status {@code 500 (Internal Server Error)} if the ods couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ods/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Ods> partialUpdateOds(@PathVariable(value = "id", required = false) final Long id, @NotNull @RequestBody Ods ods)
        throws URISyntaxException {
        log.debug("REST request to partial update Ods partially : {}, {}", id, ods);
        if (ods.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ods.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!odsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Ods> result = odsService.partialUpdate(ods);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ods.getId().toString())
        );
    }

    /**
     * {@code GET  /ods} : get all the ods.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ods in body.
     */
    @GetMapping("/ods")
    public List<Ods> getAllOds() {
        log.debug("REST request to get all Ods");
        return odsService.findAll();
    }

    /**
     * {@code GET  /ods/:id} : get the "id" ods.
     *
     * @param id the id of the ods to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ods, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ods/{id}")
    public ResponseEntity<Ods> getOds(@PathVariable Long id) {
        log.debug("REST request to get Ods : {}", id);
        Optional<Ods> ods = odsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ods);
    }

    /**
     * {@code DELETE  /ods/:id} : delete the "id" ods.
     *
     * @param id the id of the ods to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ods/{id}")
    public ResponseEntity<Void> deleteOds(@PathVariable Long id) {
        log.debug("REST request to delete Ods : {}", id);
        odsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
