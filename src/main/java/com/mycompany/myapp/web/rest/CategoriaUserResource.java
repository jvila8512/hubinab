package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.CategoriaUser;
import com.mycompany.myapp.repository.CategoriaUserRepository;
import com.mycompany.myapp.service.CategoriaUserService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.CategoriaUser}.
 */
@RestController
@RequestMapping("/api")
public class CategoriaUserResource {

    private final Logger log = LoggerFactory.getLogger(CategoriaUserResource.class);

    private static final String ENTITY_NAME = "categoriaUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CategoriaUserService categoriaUserService;

    private final CategoriaUserRepository categoriaUserRepository;

    public CategoriaUserResource(CategoriaUserService categoriaUserService, CategoriaUserRepository categoriaUserRepository) {
        this.categoriaUserService = categoriaUserService;
        this.categoriaUserRepository = categoriaUserRepository;
    }

    /**
     * {@code POST  /categoria-users} : Create a new categoriaUser.
     *
     * @param categoriaUser the categoriaUser to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new categoriaUser, or with status {@code 400 (Bad Request)} if the categoriaUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/categoria-users")
    public ResponseEntity<CategoriaUser> createCategoriaUser(@Valid @RequestBody CategoriaUser categoriaUser) throws URISyntaxException {
        log.debug("REST request to save CategoriaUser : {}", categoriaUser);
        if (categoriaUser.getId() != null) {
            throw new BadRequestAlertException("A new categoriaUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CategoriaUser result = categoriaUserService.save(categoriaUser);
        return ResponseEntity
            .created(new URI("/api/categoria-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /categoria-users/:id} : Updates an existing categoriaUser.
     *
     * @param id the id of the categoriaUser to save.
     * @param categoriaUser the categoriaUser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categoriaUser,
     * or with status {@code 400 (Bad Request)} if the categoriaUser is not valid,
     * or with status {@code 500 (Internal Server Error)} if the categoriaUser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/categoria-users/{id}")
    public ResponseEntity<CategoriaUser> updateCategoriaUser(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CategoriaUser categoriaUser
    ) throws URISyntaxException {
        log.debug("REST request to update CategoriaUser : {}, {}", id, categoriaUser);
        if (categoriaUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, categoriaUser.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!categoriaUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CategoriaUser result = categoriaUserService.update(categoriaUser);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, categoriaUser.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /categoria-users/:id} : Partial updates given fields of an existing categoriaUser, field will ignore if it is null
     *
     * @param id the id of the categoriaUser to save.
     * @param categoriaUser the categoriaUser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categoriaUser,
     * or with status {@code 400 (Bad Request)} if the categoriaUser is not valid,
     * or with status {@code 404 (Not Found)} if the categoriaUser is not found,
     * or with status {@code 500 (Internal Server Error)} if the categoriaUser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/categoria-users/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CategoriaUser> partialUpdateCategoriaUser(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CategoriaUser categoriaUser
    ) throws URISyntaxException {
        log.debug("REST request to partial update CategoriaUser partially : {}, {}", id, categoriaUser);
        if (categoriaUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, categoriaUser.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!categoriaUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CategoriaUser> result = categoriaUserService.partialUpdate(categoriaUser);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, categoriaUser.getId().toString())
        );
    }

    /**
     * {@code GET  /categoria-users} : get all the categoriaUsers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categoriaUsers in body.
     */
    @GetMapping("/categoria-users")
    public List<CategoriaUser> getAllCategoriaUsers() {
        log.debug("REST request to get all CategoriaUsers");
        return categoriaUserService.findAll();
    }

    /**
     * {@code GET  /categoria-users/:id} : get the "id" categoriaUser.
     *
     * @param id the id of the categoriaUser to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the categoriaUser, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/categoria-users/{id}")
    public ResponseEntity<CategoriaUser> getCategoriaUser(@PathVariable Long id) {
        log.debug("REST request to get CategoriaUser : {}", id);
        Optional<CategoriaUser> categoriaUser = categoriaUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(categoriaUser);
    }

    /**
     * {@code DELETE  /categoria-users/:id} : delete the "id" categoriaUser.
     *
     * @param id the id of the categoriaUser to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/categoria-users/{id}")
    public ResponseEntity<Void> deleteCategoriaUser(@PathVariable Long id) {
        log.debug("REST request to delete CategoriaUser : {}", id);
        categoriaUserService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
