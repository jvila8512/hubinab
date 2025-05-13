package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.TipoNotificacion;
import com.mycompany.myapp.repository.TipoNotificacionRepository;
import com.mycompany.myapp.service.TipoNotificacionService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.TipoNotificacion}.
 */
@RestController
@RequestMapping("/api")
public class TipoNotificacionResource {

    private final Logger log = LoggerFactory.getLogger(TipoNotificacionResource.class);

    private static final String ENTITY_NAME = "tipoNotificacion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoNotificacionService tipoNotificacionService;

    private final TipoNotificacionRepository tipoNotificacionRepository;

    public TipoNotificacionResource(
        TipoNotificacionService tipoNotificacionService,
        TipoNotificacionRepository tipoNotificacionRepository
    ) {
        this.tipoNotificacionService = tipoNotificacionService;
        this.tipoNotificacionRepository = tipoNotificacionRepository;
    }

    /**
     * {@code POST  /tipo-notificacion} : Create a new tipoNotificacion.
     *
     * @param tipoNoticia the tipoNotificacion to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoNotificacion, or with status {@code 400 (Bad Request)} if the tipoNotificacion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-notificacion")
    public ResponseEntity<TipoNotificacion> createTipoNotificacion(@Valid @RequestBody TipoNotificacion tipoNotificacion)
        throws URISyntaxException {
        log.debug("REST request to save TipoNoticia : {}", tipoNotificacion);
        if (tipoNotificacion.getId() != null) {
            throw new BadRequestAlertException("A new tipoNotificacion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoNotificacion result = tipoNotificacionService.save(tipoNotificacion);
        return ResponseEntity
            .created(new URI("/api/tipo-notificacion/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-notificacion/:id} : Updates an existing tipoNotificacion.
     *
     * @param id the id of the tipoNotificacion to save.
     * @param tipoNoticia the tipoNotificacion to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoNotificacion,
     * or with status {@code 400 (Bad Request)} if the tipoNotificacion is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoNotificacion couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-notificacion/{id}")
    public ResponseEntity<TipoNotificacion> updateTipoNotificacion(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TipoNotificacion tipoNotificacion
    ) throws URISyntaxException {
        log.debug("REST request to update TipoNotificacion : {}, {}", id, tipoNotificacion);
        if (tipoNotificacion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tipoNotificacion.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tipoNotificacionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TipoNotificacion result = tipoNotificacionService.update(tipoNotificacion);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoNotificacion.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tipo-notificacion/:id} : Partial updates given fields of an existing tipoNotificacion, field will ignore if it is null
     *
     * @param id the id of the tipoNotificacion to save.
     * @param tipoNoticia the tipoNotificacion to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoNotificacion,
     * or with status {@code 400 (Bad Request)} if the tipoNotificacion is not valid,
     * or with status {@code 404 (Not Found)} if the tipoNotificacion is not found,
     * or with status {@code 500 (Internal Server Error)} if the tipoNotificacion couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tipo-notificacion/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TipoNotificacion> partialUpdateTipoNotificacion(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TipoNotificacion tipoNotificacion
    ) throws URISyntaxException {
        log.debug("REST request to partial update TipoNotificacion partially : {}, {}", id, tipoNotificacion);
        if (tipoNotificacion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tipoNotificacion.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tipoNotificacionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TipoNotificacion> result = tipoNotificacionService.partialUpdate(tipoNotificacion);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoNotificacion.getId().toString())
        );
    }

    /**
     * {@code GET  /tipo-notificacion} : get all the tipoNotificacion.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoNotificacion in body.
     */
    @GetMapping("/tipo-notificacion")
    public List<TipoNotificacion> getAllTipoNotificacion() {
        log.debug("REST request to get all TipoNotificacion");
        return tipoNotificacionService.findAll();
    }

    /**
     * {@code GET  /tipo-notificacion/:id} : get the "id" tipoNotificacion.
     *
     * @param id the id of the tipoNotificacion to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoNotificacion, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-notificacion/{id}")
    public ResponseEntity<TipoNotificacion> getTipoNotificacion(@PathVariable Long id) {
        log.debug("REST request to get TipoNotificacion : {}", id);
        Optional<TipoNotificacion> tipoNotificacion = tipoNotificacionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoNotificacion);
    }

    /**
     * {@code DELETE  /tipo-notificacion/:id} : delete the "id" tipoNotificacion.
     *
     * @param id the id of the tipoNotificacion to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-notificacion/{id}")
    public ResponseEntity<Void> deleteTipoNoticia(@PathVariable Long id) {
        log.debug("REST request to delete TipoNotificacion : {}", id);
        tipoNotificacionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
