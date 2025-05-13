package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Noticias;
import com.mycompany.myapp.domain.Reto;
import com.mycompany.myapp.repository.NoticiasRepository;
import com.mycompany.myapp.service.NoticiasService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Noticias}.
 */
@RestController
@RequestMapping("/api")
public class NoticiasResource {

    private final Logger log = LoggerFactory.getLogger(NoticiasResource.class);

    private static final String ENTITY_NAME = "noticias";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NoticiasService noticiasService;

    private final NoticiasRepository noticiasRepository;

    public NoticiasResource(NoticiasService noticiasService, NoticiasRepository noticiasRepository) {
        this.noticiasService = noticiasService;
        this.noticiasRepository = noticiasRepository;
    }

    /**
     * {@code POST  /noticias} : Create a new noticias.
     *
     * @param noticias the noticias to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new noticias, or with status {@code 400 (Bad Request)} if the noticias has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/noticias")
    public ResponseEntity<Noticias> createNoticias(@Valid @RequestBody Noticias noticias) throws URISyntaxException {
        log.debug("REST request to save Noticias : {}", noticias);
        if (noticias.getId() != null) {
            throw new BadRequestAlertException("A new noticias cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Noticias result = noticiasService.save(noticias);
        return ResponseEntity
            .created(new URI("/api/noticias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /noticias/:id} : Updates an existing noticias.
     *
     * @param id the id of the noticias to save.
     * @param noticias the noticias to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated noticias,
     * or with status {@code 400 (Bad Request)} if the noticias is not valid,
     * or with status {@code 500 (Internal Server Error)} if the noticias couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/noticias/{id}")
    public ResponseEntity<Noticias> updateNoticias(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Noticias noticias
    ) throws URISyntaxException {
        log.debug("REST request to update Noticias : {}, {}", id, noticias);
        if (noticias.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, noticias.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!noticiasRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Noticias result = noticiasService.update(noticias);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, noticias.getId().toString()))
            .body(result);
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
    @GetMapping("/noticias/noticiasTodosByEcosistemasId/{idecosistemas}")
    public List<Noticias> getAllRetosByEcosistemasID(
        @PathVariable(value = "idecosistemas", required = false) final List<Long> idecosistemas
    ) {
        // buscar los retos activos de todos los ecosistemas del ususario logueado

        return noticiasService.busquedaGeneralPorEcosistemasId(idecosistemas);
    }

    /**
     * {@code PATCH  /noticias/:id} : Partial updates given fields of an existing noticias, field will ignore if it is null
     *
     * @param id the id of the noticias to save.
     * @param noticias the noticias to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated noticias,
     * or with status {@code 400 (Bad Request)} if the noticias is not valid,
     * or with status {@code 404 (Not Found)} if the noticias is not found,
     * or with status {@code 500 (Internal Server Error)} if the noticias couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/noticias/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Noticias> partialUpdateNoticias(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Noticias noticias
    ) throws URISyntaxException {
        log.debug("REST request to partial update Noticias partially : {}, {}", id, noticias);
        if (noticias.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, noticias.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!noticiasRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Noticias> result = noticiasService.partialUpdate(noticias);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, noticias.getId().toString())
        );
    }

    /**
     * {@code GET  /noticias} : get all the noticias.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of noticias in body.
     */
    @GetMapping("/noticias")
    public ResponseEntity<List<Noticias>> getAllNoticias(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of Noticias");
        Page<Noticias> page;
        if (eagerload) {
            page = noticiasService.findAllWithEagerRelationships(pageable);
        } else {
            page = noticiasService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /noticias/:id} : get the "id" noticias.
     *
     * @param id the id of the noticias to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the noticias, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/noticias/{id}")
    public ResponseEntity<Noticias> getNoticias(@PathVariable Long id) {
        log.debug("REST request to get Noticias : {}", id);
        Optional<Noticias> noticias = noticiasService.findOne(id);
        return ResponseUtil.wrapOrNotFound(noticias);
    }

    /**
     * {@code GET  /noticias/:id} : get the "id" noticias.
     *
     * @param id the id of the noticias to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the noticias, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/noticias/publicar")
    public List<Noticias> getNoticiasByPublicar() {
        log.debug("REST request to get Noticias:");
        return noticiasService.findAllByPublicar();
    }

    /**
     * {@code GET  /noticias/:id} : get the "id" noticias.
     *
     * @param id the id of the noticias to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the noticias, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/noticias/noticiasbyecosistemabyuserpaginado/{id}")
    public ResponseEntity<List<Noticias>> getNoticiasByPublicaByEcosistemaByUserIdPaginado(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @PathVariable(value = "id", required = false) final Long id
    ) {
        log.debug("REST request to get Noticias by Publica by Ecosistema by UserId: prueba");
        Page<Noticias> page;
        page = noticiasService.findAllByPublicaByEcosistemaIdByUserIdPaginado(pageable, id);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /noticias/:id} : get the "id" noticias.
     *
     * @param id the id of the noticias to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the noticias, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/noticias/noticiasbyecosistemabyuser/{id}/{iduser}")
    public List<Noticias> getNoticiasByPublicaByEcosistemaByUserId(
        @PathVariable(value = "id", required = false) final Long id,
        @PathVariable(value = "iduser", required = false) final Long iduser
    ) {
        log.debug("REST request to get Noticias by Publica by Ecosistema by UserId: prueba");

        return noticiasService.findAllByPublicaByEcosistemaIdByUserId(id, iduser);
    }

    /**
     * {@code GET  /noticias/:id} : get the "id" noticias.
     *
     * @param id the id of the noticias to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the noticias, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/noticias/noticiasbyecosistemabyusersolo/{id}/{iduser}")
    public List<Noticias> getNoticiasByPublicaByEcosistemaByUserIdSolo(
        @PathVariable(value = "id", required = false) final Long id,
        @PathVariable(value = "iduser", required = false) final Long iduser
    ) {
        log.debug("REST request to get Noticias by Publica by Ecosistema by UserId: prueba");

        return noticiasService.findAllByPublicaByEcosistemaIdByUserIdSolo(id, iduser);
    }

    /**
     * {@code GET  /retos} : get all the retos by Id Ecositemas.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the Long with status {@code 200 (OK)} and the list of retos in body.
     */
    @GetMapping("/noticias/contarEcosistemas/{id}")
    public Long contar(@PathVariable(value = "id", required = false) final Long id) {
        return noticiasService.contarEcosistemas(id);
    }

    /**
     * {@code GET  /noticias/:id} : get the "id" noticias.
     *
     * @param id the id of the noticias to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the noticias, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/noticias/byecosistema/{id}")
    public List<Noticias> getNoticiasByPublicaByEcosistemaId(@PathVariable Long id) {
        log.debug("REST request to get Noticias by publicas by Ecosistema ID:");
        return noticiasService.findAllByPublicaByEcosistemaId(id);
    }

    /**
     * {@code DELETE  /noticias/:id} : delete the "id" noticias.
     *
     * @param id the id of the noticias to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/noticias/{id}")
    public ResponseEntity<Void> deleteNoticias(@PathVariable Long id) {
        log.debug("REST request to delete Noticias : {}", id);
        noticiasService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
