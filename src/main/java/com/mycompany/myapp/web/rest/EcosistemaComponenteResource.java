package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.EcosistemaComponente;
import com.mycompany.myapp.repository.EcosistemaComponenteRepository;
import com.mycompany.myapp.service.EcosistemaComponenteService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.EcosistemaComponente}.
 */
@RestController
@RequestMapping("/api")
public class EcosistemaComponenteResource {

    private final Logger log = LoggerFactory.getLogger(EcosistemaComponenteResource.class);

    private static final String ENTITY_NAME = "ecosistemaComponente";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EcosistemaComponenteService ecosistemaComponenteService;

    private final EcosistemaComponenteRepository ecosistemaComponenteRepository;

    public EcosistemaComponenteResource(
        EcosistemaComponenteService ecosistemaComponenteService,
        EcosistemaComponenteRepository ecosistemaComponenteRepository
    ) {
        this.ecosistemaComponenteService = ecosistemaComponenteService;
        this.ecosistemaComponenteRepository = ecosistemaComponenteRepository;
    }

    /**
     * {@code POST  /ecosistema-componentes} : Create a new ecosistemaComponente.
     *
     * @param ecosistemaComponente the ecosistemaComponente to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ecosistemaComponente, or with status {@code 400 (Bad Request)} if the ecosistemaComponente has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ecosistema-componentes")
    public ResponseEntity<EcosistemaComponente> createEcosistemaComponente(@RequestBody EcosistemaComponente ecosistemaComponente)
        throws URISyntaxException {
        log.debug("REST request to save EcosistemaComponente : {}", ecosistemaComponente);
        if (ecosistemaComponente.getId() != null) {
            throw new BadRequestAlertException("A new ecosistemaComponente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EcosistemaComponente result = ecosistemaComponenteService.save(ecosistemaComponente);
        return ResponseEntity
            .created(new URI("/api/ecosistema-componentes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ecosistema-componentes/:id} : Updates an existing ecosistemaComponente.
     *
     * @param id the id of the ecosistemaComponente to save.
     * @param ecosistemaComponente the ecosistemaComponente to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ecosistemaComponente,
     * or with status {@code 400 (Bad Request)} if the ecosistemaComponente is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ecosistemaComponente couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ecosistema-componentes/{id}")
    public ResponseEntity<EcosistemaComponente> updateEcosistemaComponente(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EcosistemaComponente ecosistemaComponente
    ) throws URISyntaxException {
        log.debug("REST request to update EcosistemaComponente : {}, {}", id, ecosistemaComponente);
        if (ecosistemaComponente.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ecosistemaComponente.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ecosistemaComponenteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EcosistemaComponente result = ecosistemaComponenteService.update(ecosistemaComponente);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ecosistemaComponente.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ecosistema-componentes/:id} : Partial updates given fields of an existing ecosistemaComponente, field will ignore if it is null
     *
     * @param id the id of the ecosistemaComponente to save.
     * @param ecosistemaComponente the ecosistemaComponente to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ecosistemaComponente,
     * or with status {@code 400 (Bad Request)} if the ecosistemaComponente is not valid,
     * or with status {@code 404 (Not Found)} if the ecosistemaComponente is not found,
     * or with status {@code 500 (Internal Server Error)} if the ecosistemaComponente couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ecosistema-componentes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EcosistemaComponente> partialUpdateEcosistemaComponente(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EcosistemaComponente ecosistemaComponente
    ) throws URISyntaxException {
        log.debug("REST request to partial update EcosistemaComponente partially : {}, {}", id, ecosistemaComponente);
        if (ecosistemaComponente.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ecosistemaComponente.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ecosistemaComponenteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EcosistemaComponente> result = ecosistemaComponenteService.partialUpdate(ecosistemaComponente);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ecosistemaComponente.getId().toString())
        );
    }

    /**
     * {@code GET  /ecosistema-componentes} : get all the ecosistemaComponentes.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ecosistemaComponentes in body.
     */
    @GetMapping("/ecosistema-componentes")
    public List<EcosistemaComponente> getAllEcosistemaComponentes(
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get all EcosistemaComponentes");
        return ecosistemaComponenteService.findAll();
    }

    /**
     * {@code GET  /ecosistema-componentes/componentebyecosistema/:id} : get the "id" ecosistemaComponente.
     *
     * @param id the id of the ecosistemaComponente to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ecosistemaComponentes in body.
     */
    @GetMapping("/ecosistema-componentes/componentebyecosistema/{id}")
    public List<EcosistemaComponente> getAllComponentesbyEcosistema(@PathVariable Long id) {
        log.debug("REST request to get all EcosistemaComponentes by Ecosistema");
        return ecosistemaComponenteService.findAllComponentesbyEcosistema(id);
    }

    /**
     * {@code GET  /ecosistema-componentes/:id} : get the "id" ecosistemaComponente.
     *
     * @param id the id of the ecosistemaComponente to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ecosistemaComponente, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ecosistema-componentes/{id}")
    public ResponseEntity<EcosistemaComponente> getEcosistemaComponente(@PathVariable Long id) {
        log.debug("REST request to get EcosistemaComponente : {}", id);
        Optional<EcosistemaComponente> ecosistemaComponente = ecosistemaComponenteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ecosistemaComponente);
    }

    /**
     * {@code DELETE  /ecosistema-componentes/:id} : delete the "id" ecosistemaComponente.
     *
     * @param id the id of the ecosistemaComponente to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ecosistema-componentes/{id}")
    public ResponseEntity<Void> deleteEcosistemaComponente(@PathVariable Long id) {
        log.debug("REST request to delete EcosistemaComponente : {}", id);
        ecosistemaComponenteService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
