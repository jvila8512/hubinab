package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.TipoContacto;
import com.mycompany.myapp.repository.TipoContactoRepository;
import com.mycompany.myapp.service.TipoContactoService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.TipoContacto}.
 */
@RestController
@RequestMapping("/api")
public class TipoContactoResource {

    private final Logger log = LoggerFactory.getLogger(TipoContactoResource.class);

    private static final String ENTITY_NAME = "tipoContacto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoContactoService tipoContactoService;

    private final TipoContactoRepository tipoContactoRepository;

    public TipoContactoResource(TipoContactoService tipoContactoService, TipoContactoRepository tipoContactoRepository) {
        this.tipoContactoService = tipoContactoService;
        this.tipoContactoRepository = tipoContactoRepository;
    }

    /**
     * {@code POST  /tipo-contactos} : Create a new tipoContacto.
     *
     * @param tipoContacto the tipoContacto to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoContacto, or with status {@code 400 (Bad Request)} if the tipoContacto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-contactos")
    public ResponseEntity<TipoContacto> createTipoContacto(@Valid @RequestBody TipoContacto tipoContacto) throws URISyntaxException {
        log.debug("REST request to save TipoContacto : {}", tipoContacto);
        if (tipoContacto.getId() != null) {
            throw new BadRequestAlertException("A new tipoContacto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoContacto result = tipoContactoService.save(tipoContacto);
        return ResponseEntity
            .created(new URI("/api/tipo-contactos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-contactos/:id} : Updates an existing tipoContacto.
     *
     * @param id the id of the tipoContacto to save.
     * @param tipoContacto the tipoContacto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoContacto,
     * or with status {@code 400 (Bad Request)} if the tipoContacto is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoContacto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-contactos/{id}")
    public ResponseEntity<TipoContacto> updateTipoContacto(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TipoContacto tipoContacto
    ) throws URISyntaxException {
        log.debug("REST request to update TipoContacto : {}, {}", id, tipoContacto);
        if (tipoContacto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tipoContacto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tipoContactoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TipoContacto result = tipoContactoService.update(tipoContacto);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoContacto.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tipo-contactos/:id} : Partial updates given fields of an existing tipoContacto, field will ignore if it is null
     *
     * @param id the id of the tipoContacto to save.
     * @param tipoContacto the tipoContacto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoContacto,
     * or with status {@code 400 (Bad Request)} if the tipoContacto is not valid,
     * or with status {@code 404 (Not Found)} if the tipoContacto is not found,
     * or with status {@code 500 (Internal Server Error)} if the tipoContacto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tipo-contactos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TipoContacto> partialUpdateTipoContacto(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TipoContacto tipoContacto
    ) throws URISyntaxException {
        log.debug("REST request to partial update TipoContacto partially : {}, {}", id, tipoContacto);
        if (tipoContacto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tipoContacto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tipoContactoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TipoContacto> result = tipoContactoService.partialUpdate(tipoContacto);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoContacto.getId().toString())
        );
    }

    /**
     * {@code GET  /tipo-contactos} : get all the tipoContactos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoContactos in body.
     */
    @GetMapping("/tipo-contactos")
    public List<TipoContacto> getAllTipoContactos() {
        log.debug("REST request to get all TipoContactos");
        return tipoContactoService.findAll();
    }

    /**
     * {@code GET  /tipo-contactos/:id} : get the "id" tipoContacto.
     *
     * @param id the id of the tipoContacto to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoContacto, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-contactos/{id}")
    public ResponseEntity<TipoContacto> getTipoContacto(@PathVariable Long id) {
        log.debug("REST request to get TipoContacto : {}", id);
        Optional<TipoContacto> tipoContacto = tipoContactoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoContacto);
    }

    /**
     * {@code DELETE  /tipo-contactos/:id} : delete the "id" tipoContacto.
     *
     * @param id the id of the tipoContacto to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-contactos/{id}")
    public ResponseEntity<Void> deleteTipoContacto(@PathVariable Long id) {
        log.debug("REST request to delete TipoContacto : {}", id);
        tipoContactoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
