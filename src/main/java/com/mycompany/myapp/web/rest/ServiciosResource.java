package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Servicios;
import com.mycompany.myapp.repository.ServiciosRepository;
import com.mycompany.myapp.service.ServiciosService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Servicios}.
 */
@RestController
@RequestMapping("/api")
public class ServiciosResource {

    private final Logger log = LoggerFactory.getLogger(ServiciosResource.class);

    private static final String ENTITY_NAME = "servicios";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiciosService serviciosService;

    private final ServiciosRepository serviciosRepository;

    public ServiciosResource(ServiciosService serviciosService, ServiciosRepository serviciosRepository) {
        this.serviciosService = serviciosService;
        this.serviciosRepository = serviciosRepository;
    }

    /**
     * {@code POST  /servicios} : Create a new servicios.
     *
     * @param servicios the servicios to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new servicios, or with status {@code 400 (Bad Request)} if the servicios has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/servicios")
    public ResponseEntity<Servicios> createServicios(@Valid @RequestBody Servicios servicios) throws URISyntaxException {
        log.debug("REST request to save Servicios : {}", servicios);
        if (servicios.getId() != null) {
            throw new BadRequestAlertException("A new servicios cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Servicios result = serviciosService.save(servicios);
        return ResponseEntity
            .created(new URI("/api/servicios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /servicios/:id} : Updates an existing servicios.
     *
     * @param id the id of the servicios to save.
     * @param servicios the servicios to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated servicios,
     * or with status {@code 400 (Bad Request)} if the servicios is not valid,
     * or with status {@code 500 (Internal Server Error)} if the servicios couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/servicios/{id}")
    public ResponseEntity<Servicios> updateServicios(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Servicios servicios
    ) throws URISyntaxException {
        log.debug("REST request to update Servicios : {}, {}", id, servicios);
        if (servicios.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, servicios.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!serviciosRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Servicios result = serviciosService.update(servicios);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, servicios.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /servicios/:id} : Partial updates given fields of an existing servicios, field will ignore if it is null
     *
     * @param id the id of the servicios to save.
     * @param servicios the servicios to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated servicios,
     * or with status {@code 400 (Bad Request)} if the servicios is not valid,
     * or with status {@code 404 (Not Found)} if the servicios is not found,
     * or with status {@code 500 (Internal Server Error)} if the servicios couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/servicios/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Servicios> partialUpdateServicios(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Servicios servicios
    ) throws URISyntaxException {
        log.debug("REST request to partial update Servicios partially : {}, {}", id, servicios);
        if (servicios.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, servicios.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!serviciosRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Servicios> result = serviciosService.partialUpdate(servicios);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, servicios.getId().toString())
        );
    }

    /**
     * {@code GET  /servicios} : get all the servicios.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of servicios in body.
     */
    @GetMapping("/servicios")
    public List<Servicios> getAllServicios() {
        log.debug("REST request to get all Servicios");
        return serviciosService.findAll();
    }

    /**
     * {@code GET  /servicios} : get all the servicios.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of servicios in body.
     */
    @GetMapping("/servicios/publicados")
    public List<Servicios> getAllServiciosByPublicado() {
        log.debug("REST request to get all Servicios Publicados");
        return serviciosService.findAllByPublicado();
    }

    /**
     * {@code GET  /servicios/:id} : get the "id" servicios.
     *
     * @param id the id of the servicios to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the servicios, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/servicios/{id}")
    public ResponseEntity<Servicios> getServicios(@PathVariable Long id) {
        log.debug("REST request to get Servicios : {}", id);
        Optional<Servicios> servicios = serviciosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(servicios);
    }

    /**
     * {@code DELETE  /servicios/:id} : delete the "id" servicios.
     *
     * @param id the id of the servicios to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/servicios/{id}")
    public ResponseEntity<Void> deleteServicios(@PathVariable Long id) {
        log.debug("REST request to delete Servicios : {}", id);
        serviciosService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
