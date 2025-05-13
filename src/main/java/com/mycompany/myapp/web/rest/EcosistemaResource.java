package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Ecosistema;
import com.mycompany.myapp.repository.EcosistemaRepository;
import com.mycompany.myapp.service.EcosistemaService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Year;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Ecosistema}.
 */
@RestController
@RequestMapping("/api")
public class EcosistemaResource {

    private final Logger log = LoggerFactory.getLogger(EcosistemaResource.class);

    private static final String ENTITY_NAME = "ecosistema";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EcosistemaService ecosistemaService;

    private final EcosistemaRepository ecosistemaRepository;

    public EcosistemaResource(EcosistemaService ecosistemaService, EcosistemaRepository ecosistemaRepository) {
        this.ecosistemaService = ecosistemaService;
        this.ecosistemaRepository = ecosistemaRepository;
    }

    /**
     * {@code POST  /ecosistemas} : Create a new ecosistema.
     *
     * @param ecosistema the ecosistema to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ecosistema, or with status {@code 400 (Bad Request)} if the ecosistema has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ecosistemas")
    public ResponseEntity<Ecosistema> createEcosistema(@Valid @RequestBody Ecosistema ecosistema) throws URISyntaxException {
        log.debug("REST request to save Ecosistema : {}", ecosistema);
        if (ecosistema.getId() != null) {
            throw new BadRequestAlertException("A new ecosistema cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Ecosistema result = ecosistemaService.save(ecosistema);
        return ResponseEntity
            .created(new URI("/api/ecosistemas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @GetMapping("/ecosistemas/system/year")
    public ResponseEntity<Integer> getCurrentYear() {
        return ResponseEntity.ok(Year.now().getValue());
    }

    /**
     * {@code POST  /ecosistemas} : Create a new ecosistema.
     *
     * @param ecosistema the ecosistema to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ecosistema, or with status {@code 400 (Bad Request)} if the ecosistema has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ecosistemas1")
    public ResponseEntity<Ecosistema> createEcosistema1(@Valid @RequestBody Ecosistema ecosistema) throws URISyntaxException {
        log.debug("REST request to save Ecosistema : {}", ecosistema);
        if (ecosistema.getId() != null) {
            throw new BadRequestAlertException("A new ecosistema cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Ecosistema result = ecosistemaService.save(ecosistema);
        return ResponseEntity
            .created(new URI("/api/ecosistemas/" + result.getId()))
            .headers(HeaderUtil.createAlert(applicationName, applicationName + ".ecosistema.solicitude", "Ecosistema"))
            .body(result);
    }

    /**
     * {@code PUT  /ecosistemas/:id} : Updates an existing ecosistema.
     *
     * @param id the id of the ecosistema to save.
     * @param ecosistema the ecosistema to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ecosistema,
     * or with status {@code 400 (Bad Request)} if the ecosistema is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ecosistema couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ecosistemas/{id}")
    public ResponseEntity<Ecosistema> updateEcosistema(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Ecosistema ecosistema
    ) throws URISyntaxException {
        log.debug("REST request to update Ecosistema : {}, {}", id, ecosistema);
        if (ecosistema.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ecosistema.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ecosistemaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Ecosistema result = ecosistemaService.update(ecosistema);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ecosistema.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ecosistemas/:id} : Partial updates given fields of an existing ecosistema, field will ignore if it is null
     *
     * @param id the id of the ecosistema to save.
     * @param ecosistema the ecosistema to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ecosistema,
     * or with status {@code 400 (Bad Request)} if the ecosistema is not valid,
     * or with status {@code 404 (Not Found)} if the ecosistema is not found,
     * or with status {@code 500 (Internal Server Error)} if the ecosistema couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ecosistemas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Ecosistema> partialUpdateEcosistema(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Ecosistema ecosistema
    ) throws URISyntaxException {
        log.debug("REST request to partial update Ecosistema partially : {}, {}", id, ecosistema);
        if (ecosistema.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ecosistema.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ecosistemaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Ecosistema> result = ecosistemaService.partialUpdate(ecosistema);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ecosistema.getId().toString())
        );
    }

    /**
     * {@code GET  /ecosistemas} : get all the ecosistemas.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ecosistemas in body.
     */
    @GetMapping("/ecosistemas")
    public List<Ecosistema> getAllEcosistemas(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Ecosistemas");
        return ecosistemaService.findAll();
    }

    @GetMapping("/ecosistemas/usuario-existe/{userId}")
    public ResponseEntity<Boolean> verificarUsuario(@PathVariable Long userId) {
        boolean existeEnEcosistemas = ecosistemaService.usuarioEnEcosistemas(userId);
        return ResponseEntity.ok(existeEnEcosistemas);
    }

    /**
     * {@code GET  /ecosistemas} : get all the ecosistemas.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ecosistemas in body.
     */
    @GetMapping("/ecosistemas/activos")
    public List<Ecosistema> getAllEcosistemasbyActivo() {
        log.debug("REST request to get all Ecosistemas by Activos");
        return ecosistemaService.findAllbyActivo();
    }

    /**
     * {@code GET  /ecosistemas/:id} : get the "id" ecosistema.
     *
     * @param id the id of the ecosistema to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ecosistema, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ecosistemas/{id}")
    public ResponseEntity<Ecosistema> getEcosistema(@PathVariable Long id) {
        log.debug("REST request to get Ecosistema : {}", id);
        Optional<Ecosistema> ecosistema = ecosistemaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ecosistema);
    }

    /**
     * {@code DELETE  /ecosistemas/:id} : delete the "id" ecosistema.
     *
     * @param id the id of the ecosistema to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ecosistemas/{id}")
    public ResponseEntity<Void> deleteEcosistema(@PathVariable Long id) {
        log.debug("REST request to delete Ecosistema : {}", id);
        ecosistemaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
