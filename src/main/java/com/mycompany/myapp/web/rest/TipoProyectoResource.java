package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.TipoProyecto;
import com.mycompany.myapp.repository.TipoProyectoRepository;
import com.mycompany.myapp.service.TipoProyectoService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.TipoProyecto}.
 */
@RestController
@RequestMapping("/api")
public class TipoProyectoResource {

    private final Logger log = LoggerFactory.getLogger(TipoProyectoResource.class);

    private static final String ENTITY_NAME = "tipoProyecto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoProyectoService tipoProyectoService;

    private final TipoProyectoRepository tipoProyectoRepository;

    public TipoProyectoResource(TipoProyectoService tipoProyectoService, TipoProyectoRepository tipoProyectoRepository) {
        this.tipoProyectoService = tipoProyectoService;
        this.tipoProyectoRepository = tipoProyectoRepository;
    }

    /**
     * {@code POST  /tipo-proyectos} : Create a new tipoProyecto.
     *
     * @param tipoProyecto the tipoProyecto to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoProyecto, or with status {@code 400 (Bad Request)} if the tipoProyecto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-proyectos")
    public ResponseEntity<TipoProyecto> createTipoProyecto(@Valid @RequestBody TipoProyecto tipoProyecto) throws URISyntaxException {
        log.debug("REST request to save TipoProyecto : {}", tipoProyecto);
        if (tipoProyecto.getId() != null) {
            throw new BadRequestAlertException("A new tipoProyecto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoProyecto result = tipoProyectoService.save(tipoProyecto);
        return ResponseEntity
            .created(new URI("/api/tipo-proyectos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-proyectos/:id} : Updates an existing tipoProyecto.
     *
     * @param id the id of the tipoProyecto to save.
     * @param tipoProyecto the tipoProyecto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoProyecto,
     * or with status {@code 400 (Bad Request)} if the tipoProyecto is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoProyecto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-proyectos/{id}")
    public ResponseEntity<TipoProyecto> updateTipoProyecto(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TipoProyecto tipoProyecto
    ) throws URISyntaxException {
        log.debug("REST request to update TipoProyecto : {}, {}", id, tipoProyecto);
        if (tipoProyecto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tipoProyecto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tipoProyectoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TipoProyecto result = tipoProyectoService.update(tipoProyecto);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoProyecto.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tipo-proyectos/:id} : Partial updates given fields of an existing tipoProyecto, field will ignore if it is null
     *
     * @param id the id of the tipoProyecto to save.
     * @param tipoProyecto the tipoProyecto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoProyecto,
     * or with status {@code 400 (Bad Request)} if the tipoProyecto is not valid,
     * or with status {@code 404 (Not Found)} if the tipoProyecto is not found,
     * or with status {@code 500 (Internal Server Error)} if the tipoProyecto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tipo-proyectos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TipoProyecto> partialUpdateTipoProyecto(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TipoProyecto tipoProyecto
    ) throws URISyntaxException {
        log.debug("REST request to partial update TipoProyecto partially : {}, {}", id, tipoProyecto);
        if (tipoProyecto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tipoProyecto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tipoProyectoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TipoProyecto> result = tipoProyectoService.partialUpdate(tipoProyecto);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoProyecto.getId().toString())
        );
    }

    /**
     * {@code GET  /tipo-proyectos} : get all the tipoProyectos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoProyectos in body.
     */
    @GetMapping("/tipo-proyectos")
    public List<TipoProyecto> getAllTipoProyectos() {
        log.debug("REST request to get all TipoProyectos");
        return tipoProyectoService.findAll();
    }

    /**
     * {@code GET  /tipo-proyectos/:id} : get the "id" tipoProyecto.
     *
     * @param id the id of the tipoProyecto to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoProyecto, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-proyectos/{id}")
    public ResponseEntity<TipoProyecto> getTipoProyecto(@PathVariable Long id) {
        log.debug("REST request to get TipoProyecto : {}", id);
        Optional<TipoProyecto> tipoProyecto = tipoProyectoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoProyecto);
    }

    /**
     * {@code DELETE  /tipo-proyectos/:id} : delete the "id" tipoProyecto.
     *
     * @param id the id of the tipoProyecto to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-proyectos/{id}")
    public ResponseEntity<Void> deleteTipoProyecto(@PathVariable Long id) {
        log.debug("REST request to delete TipoProyecto : {}", id);
        tipoProyectoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
