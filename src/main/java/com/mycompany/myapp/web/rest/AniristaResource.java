package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Anirista;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.domain.UsuarioEcosistema;
import com.mycompany.myapp.repository.AniristaRepository;
import com.mycompany.myapp.repository.UserRepository;
import com.mycompany.myapp.repository.UsuarioEcosistemaRepository;
import com.mycompany.myapp.service.AniristaService;
import com.mycompany.myapp.service.MailService;
import com.mycompany.myapp.service.UserService;
import com.mycompany.myapp.service.UsernameAlreadyUsedException;
import com.mycompany.myapp.service.UsuarioEcosistemaService;
import com.mycompany.myapp.service.dto.AdminUserDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.Normalizer;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Anirista}.
 */
@RestController
@RequestMapping("/api")
public class AniristaResource {

    private final Logger log = LoggerFactory.getLogger(AniristaResource.class);

    private static final String ENTITY_NAME = "anirista";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AniristaService aniristaService;

    private final AniristaRepository aniristaRepository;

    private final UserService userService;

    private final UserRepository userRepository;

    private final UsuarioEcosistemaService usuarioEcosistemaService;

    private final UsuarioEcosistemaRepository usuarioEcosistemaRepository;

    public AniristaResource(
        AniristaService aniristaService,
        AniristaRepository aniristaRepository,
        UserService userService,
        UserRepository userRepository,
        UsuarioEcosistemaService usuarioEcosistemaService,
        UsuarioEcosistemaRepository usuarioEcosistemaRepository
    ) {
        this.aniristaService = aniristaService;
        this.aniristaRepository = aniristaRepository;
        this.userService = userService;
        this.userRepository = userRepository;
        this.usuarioEcosistemaService = usuarioEcosistemaService;
        this.usuarioEcosistemaRepository = usuarioEcosistemaRepository;
    }

    /**
     * {@code POST  /aniristas} : Create a new anirista.
     *
     * @param anirista the anirista to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new anirista, or with status {@code 400 (Bad Request)} if the anirista has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/aniristas")
    public ResponseEntity<Anirista> createAnirista(@Valid @RequestBody Anirista anirista) throws URISyntaxException {
        log.debug("REST request to save Anirista : {}", anirista);
        if (anirista.getId() != null) {
            throw new BadRequestAlertException("A new anirista cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Anirista result = aniristaService.save(anirista);
        return ResponseEntity
            .created(new URI("/api/aniristas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /aniristas/:id} : Updates an existing anirista.
     *
     * @param id the id of the anirista to save.
     * @param anirista the anirista to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated anirista,
     * or with status {@code 400 (Bad Request)} if the anirista is not valid,
     * or with status {@code 500 (Internal Server Error)} if the anirista couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/aniristas/{id}")
    public ResponseEntity<Anirista> updateAnirista(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Anirista anirista
    ) throws URISyntaxException {
        log.debug("REST request to update Anirista : {}, {}", id, anirista);
        if (anirista.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, anirista.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!aniristaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Anirista result = aniristaService.update(anirista);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, anirista.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /aniristas/:id} : Partial updates given fields of an existing anirista, field will ignore if it is null
     *
     * @param id the id of the anirista to save.
     * @param anirista the anirista to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated anirista,
     * or with status {@code 400 (Bad Request)} if the anirista is not valid,
     * or with status {@code 404 (Not Found)} if the anirista is not found,
     * or with status {@code 500 (Internal Server Error)} if the anirista couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/aniristas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Anirista> partialUpdateAnirista(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Anirista anirista
    ) throws URISyntaxException {
        log.debug("REST request to partial update Anirista partially : {}, {}", id, anirista);
        if (anirista.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, anirista.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!aniristaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Anirista> result = aniristaService.partialUpdate(anirista);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, anirista.getId().toString())
        );
    }

    /**
     * {@code GET  /aniristas} : get all the aniristas.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aniristas in body.
     */
    @GetMapping("/aniristas")
    public List<Anirista> getAllAniristas(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Aniristas");
        return aniristaService.findAll();
    }

    /**
     * {@code GET  /aniristas/:id} : get the "id" anirista.
     *
     * @param id the id of the anirista to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the anirista, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/aniristas/{id}")
    public ResponseEntity<Anirista> getAnirista(@PathVariable Long id) {
        log.debug("REST request to get Anirista : {}", id);
        Optional<Anirista> anirista = aniristaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(anirista);
    }

    /**
     * {@code GET  /aniristas/:id} : get the "id" anirista.
     *
     * @param id the id of the anirista to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the anirista, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/aniristas/temporal")
    public void crearAniristaTemporal() {
        List<Anirista> lista = aniristaService.findAll();

        for (Anirista anirista : lista) {
            // Separamos el nombre completo en un array de palabras.
            String[] palabras = anirista.getNombre().split(" ");

            // Obtenemos el nombre de la persona.
            String nombre = palabras[0];

            String segundoNombre = "";

            String apellidos = "";

            // Obtenemos las iniciales de los apellidos de la persona.
            String inicialesApellidos = "";

            if (palabras.length == 3) {
                for (int i = 1; i < palabras.length; i++) {
                    inicialesApellidos += palabras[i].charAt(0);
                    apellidos += palabras[i] + " ";
                }
            } else if (palabras.length > 3) {
                segundoNombre = " " + palabras[1];
                for (int i = 2; i < palabras.length; i++) {
                    inicialesApellidos += palabras[i].charAt(0);
                    apellidos += palabras[i] + " ";
                }
            }
            Set<String> authorities = new HashSet<>();

            String loginjunto11 = nombre + "" + inicialesApellidos;
            String p = "";

            String login = loginjunto11
                .replace("á", "a")
                .replace("é", "e")
                .replace("í", "i")
                .replace("ó", "o")
                .replace("ú", "u")
                .replace("Á", "A")
                .replace("É", "E")
                .replace("Í", "I")
                .replace("Ó", "O")
                .replace("Ú", "U");

            authorities.add("ROLE_USER");

            Optional<User> usuarioencontrado = userRepository.findOneByLogin(login.toLowerCase());

            if (usuarioencontrado.isPresent()) p = "_";

            AdminUserDTO userDTO = new AdminUserDTO();
            userDTO.setLogin(login + p);
            userDTO.setFirstName(nombre + segundoNombre);
            userDTO.setLastName(apellidos);
            userDTO.setImageUrl("userDesconocido.png");
            userDTO.setCreatedBy("admin");
            userDTO.setCreatedDate(Instant.now());
            userDTO.setAuthorities(authorities);

            User newUser = userService.createUser(userDTO);

            UsuarioEcosistema nuevo = new UsuarioEcosistema();
            nuevo.setUser(newUser);
            nuevo.setFechaIngreso(LocalDate.now());

            UsuarioEcosistema result = usuarioEcosistemaService.save(nuevo);
        }
    }

    /**
     * {@code DELETE  /aniristas/:id} : delete the "id" anirista.
     *
     * @param id the id of the anirista to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/aniristas/{id}")
    public ResponseEntity<Void> deleteAnirista(@PathVariable Long id) {
        log.debug("REST request to delete Anirista : {}", id);
        aniristaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
