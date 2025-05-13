package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.RedesSociales;
import com.mycompany.myapp.repository.RedesSocialesRepository;
import com.mycompany.myapp.service.RedesSocialesService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.RedesSociales}.
 */
@RestController
@RequestMapping("/api")
public class RedesSocialesResource {

    private final Logger log = LoggerFactory.getLogger(RedesSocialesResource.class);

    private static final String ENTITY_NAME = "redesSociales";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RedesSocialesService redesSocialesService;

    private final RedesSocialesRepository redesSocialesRepository;

    public RedesSocialesResource(RedesSocialesService redesSocialesService, RedesSocialesRepository redesSocialesRepository) {
        this.redesSocialesService = redesSocialesService;
        this.redesSocialesRepository = redesSocialesRepository;
    }

    /**
     * {@code POST  /redes-sociales} : Create a new redesSociales.
     *
     * @param redesSociales the redesSociales to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new redesSociales, or with status {@code 400 (Bad Request)} if the redesSociales has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/redes-sociales")
    public ResponseEntity<RedesSociales> createRedesSociales(@Valid @RequestBody RedesSociales redesSociales) throws URISyntaxException {
        log.debug("REST request to save RedesSociales : {}", redesSociales);
        if (redesSociales.getId() != null) {
            throw new BadRequestAlertException("A new redesSociales cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RedesSociales result = redesSocialesService.save(redesSociales);
        return ResponseEntity
            .created(new URI("/api/redes-sociales/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /redes-sociales/:id} : Updates an existing redesSociales.
     *
     * @param id the id of the redesSociales to save.
     * @param redesSociales the redesSociales to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated redesSociales,
     * or with status {@code 400 (Bad Request)} if the redesSociales is not valid,
     * or with status {@code 500 (Internal Server Error)} if the redesSociales couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/redes-sociales/{id}")
    public ResponseEntity<RedesSociales> updateRedesSociales(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RedesSociales redesSociales
    ) throws URISyntaxException {
        log.debug("REST request to update RedesSociales : {}, {}", id, redesSociales);
        if (redesSociales.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, redesSociales.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!redesSocialesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RedesSociales result = redesSocialesService.update(redesSociales);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, redesSociales.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /redes-sociales/:id} : Partial updates given fields of an existing redesSociales, field will ignore if it is null
     *
     * @param id the id of the redesSociales to save.
     * @param redesSociales the redesSociales to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated redesSociales,
     * or with status {@code 400 (Bad Request)} if the redesSociales is not valid,
     * or with status {@code 404 (Not Found)} if the redesSociales is not found,
     * or with status {@code 500 (Internal Server Error)} if the redesSociales couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/redes-sociales/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RedesSociales> partialUpdateRedesSociales(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RedesSociales redesSociales
    ) throws URISyntaxException {
        log.debug("REST request to partial update RedesSociales partially : {}, {}", id, redesSociales);
        if (redesSociales.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, redesSociales.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!redesSocialesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RedesSociales> result = redesSocialesService.partialUpdate(redesSociales);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, redesSociales.getId().toString())
        );
    }

    /**
     * {@code GET  /redes-sociales} : get all the redesSociales.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of redesSociales in body.
     */
    @GetMapping("/redes-sociales")
    public List<RedesSociales> getAllRedesSociales() {
        log.debug("REST request to get all RedesSociales");
        return redesSocialesService.findAll();
    }

    /**
     * {@code GET  /redes-sociales/:id} : get the "id" redesSociales.
     *
     * @param id the id of the redesSociales to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the redesSociales, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/redes-sociales/{id}")
    public ResponseEntity<RedesSociales> getRedesSociales(@PathVariable Long id) {
        log.debug("REST request to get RedesSociales : {}", id);
        Optional<RedesSociales> redesSociales = redesSocialesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(redesSociales);
    }

    /**
     * {@code DELETE  /redes-sociales/:id} : delete the "id" redesSociales.
     *
     * @param id the id of the redesSociales to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/redes-sociales/{id}")
    public ResponseEntity<Void> deleteRedesSociales(@PathVariable Long id) {
        log.debug("REST request to delete RedesSociales : {}", id);
        redesSocialesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
