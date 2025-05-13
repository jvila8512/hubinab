package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.FooterSection;
import com.mycompany.myapp.domain.Idea;
import com.mycompany.myapp.domain.Reto;
import com.mycompany.myapp.repository.FooterSectionRepository;
import com.mycompany.myapp.repository.IdeaRepository;
import com.mycompany.myapp.service.FooterSectionService;
import com.mycompany.myapp.service.IdeaService;
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

@RestController
@RequestMapping("/api")
public class FooterSectionResource {

    private final Logger log = LoggerFactory.getLogger(IdeaResource.class);

    private static final String ENTITY_NAME = "footerSection";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FooterSectionService footerSectionService;

    private final FooterSectionRepository footerSectionRepository;

    public FooterSectionResource(FooterSectionService footerSectionService, FooterSectionRepository f) {
        this.footerSectionService = footerSectionService;
        this.footerSectionRepository = f;
    }

    @PostMapping("/footer-sections")
    public ResponseEntity<FooterSection> createFooterSection(@Valid @RequestBody FooterSection footerSection) throws URISyntaxException {
        log.debug("REST request to save FooterSection : {}", footerSection);

        // Validación 1: No debe tener ID
        if (footerSection.getId() != null) {
            throw new BadRequestAlertException("A new footerSection cannot already have an ID", ENTITY_NAME, "idexists");
        }

        // Validación 2: Título único
        if (footerSectionService.titleExists(footerSection)) {
            throw new BadRequestAlertException("Title already exists", ENTITY_NAME, "titleexists");
        }

        FooterSection result = footerSectionService.create(footerSection);

        return ResponseEntity
            .created(new URI("/api/footer-sections/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/footer-sections/{id}")
    public ResponseEntity<FooterSection> updateFooterSection(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FooterSection footerSection
    ) throws URISyntaxException {
        log.debug("REST request to update FooterSection : {}, {}", id, footerSection);

        // Validación 1: ID nulo
        if (footerSection.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        // Validación 2: ID de path no coincide con body
        if (!Objects.equals(id, footerSection.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        // Validación 3: Entidad no existe en BD
        if (!footerSectionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        // Validación 4: Título duplicado (solo si el título cambió)
        if (!footerSection.getTitle().equals(footerSectionRepository.findById(id).get().getTitle())) {
            if (footerSectionService.titleExists(footerSection)) {
                throw new BadRequestAlertException("Title already exists", ENTITY_NAME, "titleexists");
            }
        }

        FooterSection result = footerSectionService.update(footerSection);

        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, footerSection.getId().toString()))
            .body(result);
    }

    @GetMapping("/footer-sections")
    public ResponseEntity<List<FooterSection>> getAllFooterSections() {
        return ResponseEntity.ok(footerSectionService.findAll());
    }

    @GetMapping("/footer-sections/active")
    public ResponseEntity<List<FooterSection>> getActiveFooterSections() {
        return ResponseEntity.ok(footerSectionService.findActiveSections());
    }

    @GetMapping("/footer-sections/{id}")
    public ResponseEntity<FooterSection> getFooterSection(@PathVariable Long id) {
        return footerSectionService.findOne(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/footer-sections/{id}")
    public ResponseEntity<Void> deleteFooterSection(@PathVariable Long id) {
        footerSectionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
