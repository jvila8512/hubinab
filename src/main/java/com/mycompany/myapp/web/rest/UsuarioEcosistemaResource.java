package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.UsuarioEcosistema;
import com.mycompany.myapp.repository.UsuarioEcosistemaRepository;
import com.mycompany.myapp.service.UsuarioEcosistemaService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.UsuarioEcosistema}.
 */
@RestController
@RequestMapping("/api")
public class UsuarioEcosistemaResource {

    private final Logger log = LoggerFactory.getLogger(UsuarioEcosistemaResource.class);

    private static final String ENTITY_NAME = "usuarioEcosistema";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UsuarioEcosistemaService usuarioEcosistemaService;

    private final UsuarioEcosistemaRepository usuarioEcosistemaRepository;

    public UsuarioEcosistemaResource(
        UsuarioEcosistemaService usuarioEcosistemaService,
        UsuarioEcosistemaRepository usuarioEcosistemaRepository
    ) {
        this.usuarioEcosistemaService = usuarioEcosistemaService;
        this.usuarioEcosistemaRepository = usuarioEcosistemaRepository;
    }

    /**
     * {@code POST  /usuario-ecosistemas} : Create a new usuarioEcosistema.
     *
     * @param usuarioEcosistema the usuarioEcosistema to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new usuarioEcosistema, or with status {@code 400 (Bad Request)} if the usuarioEcosistema has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/usuario-ecosistemas")
    public ResponseEntity<UsuarioEcosistema> createUsuarioEcosistema(@Valid @RequestBody UsuarioEcosistema usuarioEcosistema)
        throws URISyntaxException {
        log.debug("REST request to save UsuarioEcosistema : {}", usuarioEcosistema);
        if (usuarioEcosistema.getId() != null) {
            throw new BadRequestAlertException("A new usuarioEcosistema cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UsuarioEcosistema result = usuarioEcosistemaService.save(usuarioEcosistema);
        return ResponseEntity
            .created(new URI("/api/usuario-ecosistemas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /usuario-ecosistemas/:id} : Updates an existing usuarioEcosistema.
     *
     * @param id the id of the usuarioEcosistema to save.
     * @param usuarioEcosistema the usuarioEcosistema to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated usuarioEcosistema,
     * or with status {@code 400 (Bad Request)} if the usuarioEcosistema is not valid,
     * or with status {@code 500 (Internal Server Error)} if the usuarioEcosistema couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/usuario-ecosistemas/{id}")
    public ResponseEntity<UsuarioEcosistema> updateUsuarioEcosistema(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody UsuarioEcosistema usuarioEcosistema
    ) throws URISyntaxException {
        log.debug("REST request to update UsuarioEcosistema : {}, {}", id, usuarioEcosistema);
        if (usuarioEcosistema.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, usuarioEcosistema.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!usuarioEcosistemaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UsuarioEcosistema result = usuarioEcosistemaService.update(usuarioEcosistema);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, usuarioEcosistema.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /usuario-ecosistemas/:id} : Updates an existing usuarioEcosistema.
     *
     * @param id the id of the usuarioEcosistema to save.
     * @param usuarioEcosistema the usuarioEcosistema to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated usuarioEcosistema,
     * or with status {@code 400 (Bad Request)} if the usuarioEcosistema is not valid,
     * or with status {@code 500 (Internal Server Error)} if the usuarioEcosistema couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/usuario-ecosistemas1/{id}")
    public ResponseEntity<UsuarioEcosistema> updateUsuarioEcosistema1(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody UsuarioEcosistema usuarioEcosistema
    ) throws URISyntaxException {
        log.debug("REST request to update UsuarioEcosistema : {}, {}", id, usuarioEcosistema);
        if (usuarioEcosistema.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, usuarioEcosistema.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!usuarioEcosistemaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UsuarioEcosistema result = usuarioEcosistemaService.update(usuarioEcosistema);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createAlert(applicationName, applicationName + ".usuarioEcosistema.salirEcosistema", "UsuarioEcosistema"))
            .body(result);
    }

    /**
     * {@code PATCH  /usuario-ecosistemas/:id} : Partial updates given fields of an existing usuarioEcosistema, field will ignore if it is null
     *
     * @param id the id of the usuarioEcosistema to save.
     * @param usuarioEcosistema the usuarioEcosistema to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated usuarioEcosistema,
     * or with status {@code 400 (Bad Request)} if the usuarioEcosistema is not valid,
     * or with status {@code 404 (Not Found)} if the usuarioEcosistema is not found,
     * or with status {@code 500 (Internal Server Error)} if the usuarioEcosistema couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/usuario-ecosistemas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UsuarioEcosistema> partialUpdateUsuarioEcosistema(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody UsuarioEcosistema usuarioEcosistema
    ) throws URISyntaxException {
        log.debug("REST request to partial update UsuarioEcosistema partially : {}, {}", id, usuarioEcosistema);
        if (usuarioEcosistema.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, usuarioEcosistema.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!usuarioEcosistemaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UsuarioEcosistema> result = usuarioEcosistemaService.partialUpdate(usuarioEcosistema);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, usuarioEcosistema.getId().toString())
        );
    }

    /**
     * {@code GET  /usuario-ecosistemas} : get all the usuarioEcosistemas.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of usuarioEcosistemas in body.
     */
    @GetMapping("/usuario-ecosistemas")
    public List<UsuarioEcosistema> getAllUsuarioEcosistemas(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all UsuarioEcosistemas");
        return usuarioEcosistemaService.findAll();
    }

    /**
     * {@code GET  /usuario-ecosistemas/:id} : get the "id" usuarioEcosistema.
     *
     * @param id the id of the usuarioEcosistema to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the usuarioEcosistema, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/usuario-ecosistemas/{id}")
    public ResponseEntity<UsuarioEcosistema> getUsuarioEcosistema(@PathVariable Long id) {
        log.debug("REST request to get UsuarioEcosistema : {}", id);
        Optional<UsuarioEcosistema> usuarioEcosistema = usuarioEcosistemaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(usuarioEcosistema);
    }

    @GetMapping("/usuario-ecosistemas/user-id/{id}")
    public Optional<UsuarioEcosistema> getUserEcosistemaByUserid(@PathVariable Long id) {
        log.debug("REST request to get UsuarioEcosistema : {}", id);
        Optional<UsuarioEcosistema> usuarioEcosistema = usuarioEcosistemaService.buscarOneByUserId(id);
        return usuarioEcosistema;
    }

    /**
     * {@code DELETE  /usuario-ecosistemas/:id} : delete the "id" usuarioEcosistema.
     *
     * @param id the id of the usuarioEcosistema to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/usuario-ecosistemas/{id}")
    public ResponseEntity<Void> deleteUsuarioEcosistema(@PathVariable Long id) {
        log.debug("REST request to delete UsuarioEcosistema : {}", id);
        usuarioEcosistemaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
