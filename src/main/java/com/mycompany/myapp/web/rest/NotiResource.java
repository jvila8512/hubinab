package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Noti;
import com.mycompany.myapp.repository.NotiRepository;
import com.mycompany.myapp.service.NotiService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Noti}.
 */
@RestController
@RequestMapping("/api")
public class NotiResource {

    private final Logger log = LoggerFactory.getLogger(NotiResource.class);

    private static final String ENTITY_NAME = "noti";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NotiService notiService;

    private final NotiRepository notiRepository;

    public NotiResource(NotiService notiService, NotiRepository notiRepository) {
        this.notiService = notiService;
        this.notiRepository = notiRepository;
    }

    /**
     * {@code POST  /notis} : Create a new noti.
     *
     * @param noti the noti to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new noti, or with status {@code 400 (Bad Request)} if the noti has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/notis")
    public ResponseEntity<Noti> createNoti(@Valid @RequestBody Noti noti) throws URISyntaxException {
        log.debug("REST request to save Noti : {}", noti);
        if (noti.getId() != null) {
            throw new BadRequestAlertException("A new noti cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Noti result = notiService.save(noti);
        return ResponseEntity
            .created(new URI("/api/notis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /notis/:id} : Updates an existing noti.
     *
     * @param id the id of the noti to save.
     * @param noti the noti to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated noti,
     * or with status {@code 400 (Bad Request)} if the noti is not valid,
     * or with status {@code 500 (Internal Server Error)} if the noti couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/notis/{id}")
    public ResponseEntity<Noti> updateNoti(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Noti noti)
        throws URISyntaxException {
        log.debug("REST request to update Noti : {}, {}", id, noti);
        if (noti.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, noti.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!notiRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Noti result = notiService.update(noti);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, noti.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /notis/:id} : Partial updates given fields of an existing noti, field will ignore if it is null
     *
     * @param id the id of the noti to save.
     * @param noti the noti to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated noti,
     * or with status {@code 400 (Bad Request)} if the noti is not valid,
     * or with status {@code 404 (Not Found)} if the noti is not found,
     * or with status {@code 500 (Internal Server Error)} if the noti couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/notis/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Noti> partialUpdateNoti(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Noti noti
    ) throws URISyntaxException {
        log.debug("REST request to partial update Noti partially : {}, {}", id, noti);
        if (noti.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, noti.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!notiRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Noti> result = notiService.partialUpdate(noti);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, noti.getId().toString())
        );
    }

    /**
     * {@code GET  /notis} : get all the notis.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of notis in body.
     */
    @GetMapping("/notis")
    public ResponseEntity<List<Noti>> getAllNotis(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of Notis");
        Page<Noti> page;
        if (eagerload) {
            page = notiService.findAllWithEagerRelationships(pageable);
        } else {
            page = notiService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /notis/:id} : get the "id" noti.
     *
     * @param id the id of the noti to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the noti, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/notis/{id}")
    public ResponseEntity<Noti> getNoti(@PathVariable Long id) {
        log.debug("REST request to get Noti : {}", id);
        Optional<Noti> noti = notiService.findOne(id);
        return ResponseUtil.wrapOrNotFound(noti);
    }

    /**
     * {@code DELETE  /notis/:id} : delete the "id" noti.
     *
     * @param id the id of the noti to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/notis/{id}")
    public ResponseEntity<Void> deleteNoti(@PathVariable Long id) {
        log.debug("REST request to delete Noti : {}", id);
        notiService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
