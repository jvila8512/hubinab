package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.TipoNoticia;
import com.mycompany.myapp.repository.TipoNoticiaRepository;
import com.mycompany.myapp.service.TipoNoticiaService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.TipoNoticia}.
 */
@RestController
@RequestMapping("/api")
public class TipoNoticiaResource {

    private final Logger log = LoggerFactory.getLogger(TipoNoticiaResource.class);

    private static final String ENTITY_NAME = "tipoNoticia";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoNoticiaService tipoNoticiaService;

    private final TipoNoticiaRepository tipoNoticiaRepository;

    public TipoNoticiaResource(TipoNoticiaService tipoNoticiaService, TipoNoticiaRepository tipoNoticiaRepository) {
        this.tipoNoticiaService = tipoNoticiaService;
        this.tipoNoticiaRepository = tipoNoticiaRepository;
    }

    /**
     * {@code POST  /tipo-noticias} : Create a new tipoNoticia.
     *
     * @param tipoNoticia the tipoNoticia to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoNoticia, or with status {@code 400 (Bad Request)} if the tipoNoticia has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-noticias")
    public ResponseEntity<TipoNoticia> createTipoNoticia(@Valid @RequestBody TipoNoticia tipoNoticia) throws URISyntaxException {
        log.debug("REST request to save TipoNoticia : {}", tipoNoticia);
        if (tipoNoticia.getId() != null) {
            throw new BadRequestAlertException("A new tipoNoticia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoNoticia result = tipoNoticiaService.save(tipoNoticia);
        return ResponseEntity
            .created(new URI("/api/tipo-noticias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-noticias/:id} : Updates an existing tipoNoticia.
     *
     * @param id the id of the tipoNoticia to save.
     * @param tipoNoticia the tipoNoticia to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoNoticia,
     * or with status {@code 400 (Bad Request)} if the tipoNoticia is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoNoticia couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-noticias/{id}")
    public ResponseEntity<TipoNoticia> updateTipoNoticia(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TipoNoticia tipoNoticia
    ) throws URISyntaxException {
        log.debug("REST request to update TipoNoticia : {}, {}", id, tipoNoticia);
        if (tipoNoticia.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tipoNoticia.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tipoNoticiaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TipoNoticia result = tipoNoticiaService.update(tipoNoticia);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoNoticia.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tipo-noticias/:id} : Partial updates given fields of an existing tipoNoticia, field will ignore if it is null
     *
     * @param id the id of the tipoNoticia to save.
     * @param tipoNoticia the tipoNoticia to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoNoticia,
     * or with status {@code 400 (Bad Request)} if the tipoNoticia is not valid,
     * or with status {@code 404 (Not Found)} if the tipoNoticia is not found,
     * or with status {@code 500 (Internal Server Error)} if the tipoNoticia couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tipo-noticias/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TipoNoticia> partialUpdateTipoNoticia(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TipoNoticia tipoNoticia
    ) throws URISyntaxException {
        log.debug("REST request to partial update TipoNoticia partially : {}, {}", id, tipoNoticia);
        if (tipoNoticia.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tipoNoticia.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tipoNoticiaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TipoNoticia> result = tipoNoticiaService.partialUpdate(tipoNoticia);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoNoticia.getId().toString())
        );
    }

    /**
     * {@code GET  /tipo-noticias} : get all the tipoNoticias.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoNoticias in body.
     */
    @GetMapping("/tipo-noticias")
    public List<TipoNoticia> getAllTipoNoticias() {
        log.debug("REST request to get all TipoNoticias");
        return tipoNoticiaService.findAll();
    }

    /**
     * {@code GET  /tipo-noticias/:id} : get the "id" tipoNoticia.
     *
     * @param id the id of the tipoNoticia to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoNoticia, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-noticias/{id}")
    public ResponseEntity<TipoNoticia> getTipoNoticia(@PathVariable Long id) {
        log.debug("REST request to get TipoNoticia : {}", id);
        Optional<TipoNoticia> tipoNoticia = tipoNoticiaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoNoticia);
    }

    /**
     * {@code DELETE  /tipo-noticias/:id} : delete the "id" tipoNoticia.
     *
     * @param id the id of the tipoNoticia to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-noticias/{id}")
    public ResponseEntity<Void> deleteTipoNoticia(@PathVariable Long id) {
        log.debug("REST request to delete TipoNoticia : {}", id);
        tipoNoticiaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
