package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.InnovacionRacionalizacion;
import com.mycompany.myapp.repository.InnovacionRacionalizacionRepository;
import com.mycompany.myapp.service.InnovacionRacionalizacionService;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.InnovacionRacionalizacion}.
 */
@RestController
@RequestMapping("/api")
public class InnovacionRacionalizacionResource {

    private final Logger log = LoggerFactory.getLogger(InnovacionRacionalizacionResource.class);

    private static final String ENTITY_NAME = "innovacionRacionalizacion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InnovacionRacionalizacionService innovacionRacionalizacionService;

    private final InnovacionRacionalizacionRepository innovacionRacionalizacionRepository;

    public InnovacionRacionalizacionResource(
        InnovacionRacionalizacionService innovacionRacionalizacionService,
        InnovacionRacionalizacionRepository innovacionRacionalizacionRepository
    ) {
        this.innovacionRacionalizacionService = innovacionRacionalizacionService;
        this.innovacionRacionalizacionRepository = innovacionRacionalizacionRepository;
    }

    /**
     * {@code POST  /innovacion-racionalizacions} : Create a new innovacionRacionalizacion.
     *
     * @param innovacionRacionalizacion the innovacionRacionalizacion to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new innovacionRacionalizacion, or with status {@code 400 (Bad Request)} if the innovacionRacionalizacion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/innovacion-racionalizacions")
    public ResponseEntity<InnovacionRacionalizacion> createInnovacionRacionalizacion(
        @Valid @RequestBody InnovacionRacionalizacion innovacionRacionalizacion
    ) throws URISyntaxException {
        log.debug("REST request to save InnovacionRacionalizacion : {}", innovacionRacionalizacion);
        if (innovacionRacionalizacion.getId() != null) {
            throw new BadRequestAlertException("A new innovacionRacionalizacion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InnovacionRacionalizacion result = innovacionRacionalizacionService.save(innovacionRacionalizacion);
        return ResponseEntity
            .created(new URI("/api/innovacion-racionalizacions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /innovacion-racionalizacions/:id} : Updates an existing innovacionRacionalizacion.
     *
     * @param id the id of the innovacionRacionalizacion to save.
     * @param innovacionRacionalizacion the innovacionRacionalizacion to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated innovacionRacionalizacion,
     * or with status {@code 400 (Bad Request)} if the innovacionRacionalizacion is not valid,
     * or with status {@code 500 (Internal Server Error)} if the innovacionRacionalizacion couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/innovacion-racionalizacions/{id}")
    public ResponseEntity<InnovacionRacionalizacion> updateInnovacionRacionalizacion(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody InnovacionRacionalizacion innovacionRacionalizacion
    ) throws URISyntaxException {
        log.debug("REST request to update InnovacionRacionalizacion : {}, {}", id, innovacionRacionalizacion);
        if (innovacionRacionalizacion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, innovacionRacionalizacion.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!innovacionRacionalizacionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        InnovacionRacionalizacion result = innovacionRacionalizacionService.update(innovacionRacionalizacion);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, innovacionRacionalizacion.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /innovacion-racionalizacions/:id} : Partial updates given fields of an existing innovacionRacionalizacion, field will ignore if it is null
     *
     * @param id the id of the innovacionRacionalizacion to save.
     * @param innovacionRacionalizacion the innovacionRacionalizacion to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated innovacionRacionalizacion,
     * or with status {@code 400 (Bad Request)} if the innovacionRacionalizacion is not valid,
     * or with status {@code 404 (Not Found)} if the innovacionRacionalizacion is not found,
     * or with status {@code 500 (Internal Server Error)} if the innovacionRacionalizacion couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/innovacion-racionalizacions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<InnovacionRacionalizacion> partialUpdateInnovacionRacionalizacion(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody InnovacionRacionalizacion innovacionRacionalizacion
    ) throws URISyntaxException {
        log.debug("REST request to partial update InnovacionRacionalizacion partially : {}, {}", id, innovacionRacionalizacion);
        if (innovacionRacionalizacion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, innovacionRacionalizacion.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!innovacionRacionalizacionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<InnovacionRacionalizacion> result = innovacionRacionalizacionService.partialUpdate(innovacionRacionalizacion);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, innovacionRacionalizacion.getId().toString())
        );
    }

    /**
     * {@code GET  /innovacion-racionalizacions} : get all the innovacionRacionalizacions.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of innovacionRacionalizacions in body.
     */
    @GetMapping("/innovacion-racionalizacions")
    public ResponseEntity<List<InnovacionRacionalizacion>> getAllInnovacionRacionalizacions(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of InnovacionRacionalizacions");
        Page<InnovacionRacionalizacion> page;
        if (eagerload) {
            page = innovacionRacionalizacionService.findAllWithEagerRelationships(pageable);
        } else {
            page = innovacionRacionalizacionService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /innovacion-racionalizacions} : get all the innovacionRacionalizacions.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of innovacionRacionalizacions in body.
     */
    @GetMapping("/innovacion-racionalizacions/buscar")
    public List<InnovacionRacionalizacion> getAllInnovacionRacionalizacionsByPublica() {
        log.debug("REST request to get a page of InnovacionRacionalizacions");

        return innovacionRacionalizacionService.findAllbyPublico();
    }

    /**
     * {@code GET  /innovacion-racionalizacions} : get all the innovacionRacionalizacions.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of innovacionRacionalizacions in body.
     */
    @GetMapping("/innovacion-racionalizacions/buscar/{id}")
    public List<InnovacionRacionalizacion> getAllInnovacionRacionalizacionsByPublicaUserId(@PathVariable Long id) {
        log.debug("REST request to get a page of InnovacionRacionalizacions by publcas y insertadas por el user");

        return innovacionRacionalizacionService.findAllbyPublicoByUser_id(id);
    }

    /**
     * {@code GET  /innovacion-racionalizacions/:id} : get the "id" innovacionRacionalizacion.
     *
     * @param id the id of the innovacionRacionalizacion to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the innovacionRacionalizacion, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/innovacion-racionalizacions/{id}")
    public ResponseEntity<InnovacionRacionalizacion> getInnovacionRacionalizacion(@PathVariable Long id) {
        log.debug("REST request to get InnovacionRacionalizacion : {}", id);
        Optional<InnovacionRacionalizacion> innovacionRacionalizacion = innovacionRacionalizacionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(innovacionRacionalizacion);
    }

    /**
     * {@code DELETE  /innovacion-racionalizacions/:id} : delete the "id" innovacionRacionalizacion.
     *
     * @param id the id of the innovacionRacionalizacion to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/innovacion-racionalizacions/{id}")
    public ResponseEntity<Void> deleteInnovacionRacionalizacion(@PathVariable Long id) {
        log.debug("REST request to delete InnovacionRacionalizacion : {}", id);
        innovacionRacionalizacionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
