package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Proyectos;
import com.mycompany.myapp.repository.ProyectosRepository;
import com.mycompany.myapp.service.ProyectosService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Proyectos}.
 */
@RestController
@RequestMapping("/api")
public class ProyectosResource {

    private final Logger log = LoggerFactory.getLogger(ProyectosResource.class);

    private static final String ENTITY_NAME = "proyectos";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProyectosService proyectosService;

    private final ProyectosRepository proyectosRepository;

    public ProyectosResource(ProyectosService proyectosService, ProyectosRepository proyectosRepository) {
        this.proyectosService = proyectosService;
        this.proyectosRepository = proyectosRepository;
    }

    /**
     * {@code POST  /proyectos} : Create a new proyectos.
     *
     * @param proyectos the proyectos to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new proyectos, or with status {@code 400 (Bad Request)} if the proyectos has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/proyectos")
    public ResponseEntity<Proyectos> createProyectos(@Valid @RequestBody Proyectos proyectos) throws URISyntaxException {
        log.debug("REST request to save Proyectos : {}", proyectos);
        if (proyectos.getId() != null) {
            throw new BadRequestAlertException("A new proyectos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Proyectos result = proyectosService.save(proyectos);
        return ResponseEntity
            .created(new URI("/api/proyectos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /proyectos/:id} : Updates an existing proyectos.
     *
     * @param id the id of the proyectos to save.
     * @param proyectos the proyectos to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated proyectos,
     * or with status {@code 400 (Bad Request)} if the proyectos is not valid,
     * or with status {@code 500 (Internal Server Error)} if the proyectos couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/proyectos/{id}")
    public ResponseEntity<Proyectos> updateProyectos(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Proyectos proyectos
    ) throws URISyntaxException {
        log.debug("REST request to update Proyectos : {}, {}", id, proyectos);
        if (proyectos.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, proyectos.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!proyectosRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Proyectos result = proyectosService.update(proyectos);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, proyectos.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /retos} : get all the retos by Id Ecositemas.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the Long with status {@code 200 (OK)} and the list of retos in body.
     */
    @GetMapping("/proyectos/contarEcosistemas/{id}")
    public Long contar(@PathVariable(value = "id", required = false) final Long id) {
        return proyectosService.contarEcosistemas(id);
    }

    /**
     * {@code PATCH  /proyectos/:id} : Partial updates given fields of an existing proyectos, field will ignore if it is null
     *
     * @param id the id of the proyectos to save.
     * @param proyectos the proyectos to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated proyectos,
     * or with status {@code 400 (Bad Request)} if the proyectos is not valid,
     * or with status {@code 404 (Not Found)} if the proyectos is not found,
     * or with status {@code 500 (Internal Server Error)} if the proyectos couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/proyectos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Proyectos> partialUpdateProyectos(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Proyectos proyectos
    ) throws URISyntaxException {
        log.debug("REST request to partial update Proyectos partially : {}, {}", id, proyectos);
        if (proyectos.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, proyectos.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!proyectosRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Proyectos> result = proyectosService.partialUpdate(proyectos);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, proyectos.getId().toString())
        );
    }

    /**
     * {@code GET  /proyectos} : get all the proyectos.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of proyectos in body.
     */
    @GetMapping("/proyectos")
    public ResponseEntity<List<Proyectos>> getAllProyectos(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of Proyectos");
        Page<Proyectos> page;
        if (eagerload) {
            page = proyectosService.findAllWithEagerRelationships(pageable);
        } else {
            page = proyectosService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /proyectos} : get all the proyectos.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of proyectos in body.
     */
    @GetMapping("/proyectos/byecosistema/{id}")
    public List<Proyectos> getAllProyectosByEcosistema(@PathVariable Long id) {
        return proyectosService.findAllWithEagerRelationshipsByEcosistema(id);
    }

    /**
     * {@code GET  /proyectos} : get all the proyectos.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of proyectos in body.
     */
    @GetMapping("/proyectos/byecosistemaTodos")
    public List<Proyectos> getAllProyectosByTodos() {
        return proyectosService.findAll();
    }

    /**
     * {@code GET  /proyectos/:id} : get the "id" proyectos.
     *
     * @param id the id of the proyectos to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the proyectos, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/proyectos/{id}")
    public ResponseEntity<Proyectos> getProyectos(@PathVariable Long id) {
        log.debug("REST request to get Proyectos : {}", id);
        Optional<Proyectos> proyectos = proyectosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(proyectos);
    }

    /**
     * {@code DELETE  /proyectos/:id} : delete the "id" proyectos.
     *
     * @param id the id of the proyectos to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/proyectos/{id}")
    public ResponseEntity<Void> deleteProyectos(@PathVariable Long id) {
        log.debug("REST request to delete Proyectos : {}", id);
        proyectosService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code GET  /retos} : get all the retos.
     *
     * @param pageable  the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is
     *                  applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of retos in body.
     */
    @GetMapping("/proyectos/proyectosTodosByEcosistemasId/{idecosistemas}")
    public List<Proyectos> getAllProyectosByEcosistemasID(
        @PathVariable(value = "idecosistemas", required = false) final List<Long> idecosistemas
    ) {
        // buscar los retos activos de todos los ecosistemas del ususario logueado

        return proyectosService.busquedaGeneralPorEcosistemasId(idecosistemas);
    }
}
