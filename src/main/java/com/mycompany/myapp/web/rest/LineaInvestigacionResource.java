package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.LineaInvestigacion;
import com.mycompany.myapp.repository.LineaInvestigacionRepository;
import com.mycompany.myapp.service.LineaInvestigacionService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.LineaInvestigacion}.
 */
@RestController
@RequestMapping("/api")
public class LineaInvestigacionResource {

    private final Logger log = LoggerFactory.getLogger(LineaInvestigacionResource.class);

    private static final String ENTITY_NAME = "lineaInvestigacion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LineaInvestigacionService lineaInvestigacionService;

    private final LineaInvestigacionRepository lineaInvestigacionRepository;

    public LineaInvestigacionResource(
        LineaInvestigacionService lineaInvestigacionService,
        LineaInvestigacionRepository lineaInvestigacionRepository
    ) {
        this.lineaInvestigacionService = lineaInvestigacionService;
        this.lineaInvestigacionRepository = lineaInvestigacionRepository;
    }

    /**
     * {@code POST  /linea-investigacions} : Create a new lineaInvestigacion.
     *
     * @param lineaInvestigacion the lineaInvestigacion to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new lineaInvestigacion, or with status {@code 400 (Bad Request)} if the lineaInvestigacion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/linea-investigacions")
    public ResponseEntity<LineaInvestigacion> createLineaInvestigacion(@Valid @RequestBody LineaInvestigacion lineaInvestigacion)
        throws URISyntaxException {
        log.debug("REST request to save LineaInvestigacion : {}", lineaInvestigacion);
        if (lineaInvestigacion.getId() != null) {
            throw new BadRequestAlertException("A new lineaInvestigacion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LineaInvestigacion result = lineaInvestigacionService.save(lineaInvestigacion);
        return ResponseEntity
            .created(new URI("/api/linea-investigacions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /linea-investigacions/:id} : Updates an existing lineaInvestigacion.
     *
     * @param id the id of the lineaInvestigacion to save.
     * @param lineaInvestigacion the lineaInvestigacion to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lineaInvestigacion,
     * or with status {@code 400 (Bad Request)} if the lineaInvestigacion is not valid,
     * or with status {@code 500 (Internal Server Error)} if the lineaInvestigacion couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/linea-investigacions/{id}")
    public ResponseEntity<LineaInvestigacion> updateLineaInvestigacion(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LineaInvestigacion lineaInvestigacion
    ) throws URISyntaxException {
        log.debug("REST request to update LineaInvestigacion : {}, {}", id, lineaInvestigacion);
        if (lineaInvestigacion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, lineaInvestigacion.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!lineaInvestigacionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LineaInvestigacion result = lineaInvestigacionService.update(lineaInvestigacion);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, lineaInvestigacion.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /linea-investigacions/:id} : Partial updates given fields of an existing lineaInvestigacion, field will ignore if it is null
     *
     * @param id the id of the lineaInvestigacion to save.
     * @param lineaInvestigacion the lineaInvestigacion to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lineaInvestigacion,
     * or with status {@code 400 (Bad Request)} if the lineaInvestigacion is not valid,
     * or with status {@code 404 (Not Found)} if the lineaInvestigacion is not found,
     * or with status {@code 500 (Internal Server Error)} if the lineaInvestigacion couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/linea-investigacions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LineaInvestigacion> partialUpdateLineaInvestigacion(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LineaInvestigacion lineaInvestigacion
    ) throws URISyntaxException {
        log.debug("REST request to partial update LineaInvestigacion partially : {}, {}", id, lineaInvestigacion);
        if (lineaInvestigacion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, lineaInvestigacion.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!lineaInvestigacionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LineaInvestigacion> result = lineaInvestigacionService.partialUpdate(lineaInvestigacion);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, lineaInvestigacion.getId().toString())
        );
    }

    /**
     * {@code GET  /linea-investigacions} : get all the lineaInvestigacions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lineaInvestigacions in body.
     */
    @GetMapping("/linea-investigacions")
    public List<LineaInvestigacion> getAllLineaInvestigacions() {
        log.debug("REST request to get all LineaInvestigacions");
        return lineaInvestigacionService.findAll();
    }

    /**
     * {@code GET  /linea-investigacions/:id} : get the "id" lineaInvestigacion.
     *
     * @param id the id of the lineaInvestigacion to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the lineaInvestigacion, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/linea-investigacions/{id}")
    public ResponseEntity<LineaInvestigacion> getLineaInvestigacion(@PathVariable Long id) {
        log.debug("REST request to get LineaInvestigacion : {}", id);
        Optional<LineaInvestigacion> lineaInvestigacion = lineaInvestigacionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(lineaInvestigacion);
    }

    /**
     * {@code DELETE  /linea-investigacions/:id} : delete the "id" lineaInvestigacion.
     *
     * @param id the id of the lineaInvestigacion to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/linea-investigacions/{id}")
    public ResponseEntity<Void> deleteLineaInvestigacion(@PathVariable Long id) {
        log.debug("REST request to delete LineaInvestigacion : {}", id);
        lineaInvestigacionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
