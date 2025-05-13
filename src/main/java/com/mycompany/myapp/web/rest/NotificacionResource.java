package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Ecosistema;
import com.mycompany.myapp.domain.Notificacion;
import com.mycompany.myapp.domain.UsuarioEcosistema;
import com.mycompany.myapp.repository.NotificacionRepository;
import com.mycompany.myapp.repository.UsuarioEcosistemaRepository;
import com.mycompany.myapp.service.FileServiceAPI;
import com.mycompany.myapp.service.NotificacionService;
import com.mycompany.myapp.service.UsuarioEcosistemaService;
import com.mycompany.myapp.service.dto.AdminUserDTO;
import com.mycompany.myapp.service.dto.NotificacionDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Noti}.
 */
@RestController
@RequestMapping("/api")
public class NotificacionResource {

    private final Logger log = LoggerFactory.getLogger(NotiResource.class);

    private static final String ENTITY_NAME = "notificacion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NotificacionService notiService;

    private final NotificacionRepository notiRepository;

    @Autowired
    private FileServiceAPI fileServiceAPI;

    @Autowired
    private UsuarioEcosistemaRepository usuarioEcosistemaService;

    public NotificacionResource(NotificacionService notiService, NotificacionRepository notiRepository) {
        this.notiService = notiService;
        this.notiRepository = notiRepository;
    }

    /**
     * {@code POST  /notificacion} : Create a new notificacion.
     *
     * @param notificacion the notificacion to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new notificacion, or with status {@code 400 (Bad Request)} if the notificacion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/notificacion")
    public ResponseEntity<Notificacion> createNotificacion(@Valid @RequestBody Notificacion notificacion) throws URISyntaxException {
        log.debug("REST request to save Noti : {}", notificacion);
        if (notificacion.getId() != null) {
            throw new BadRequestAlertException("A new noti cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Notificacion result = notiService.save(notificacion);
        return ResponseEntity
            .created(new URI("/api/notificacion/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code POST  /notificacion} : Create a new notificacion.
     *
     * @param notificacion the notificacion to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new notificacion, or with status {@code 400 (Bad Request)} if the notificacion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/notificacion/SinMensaje")
    public Notificacion createNotificacionSinMensaje(@Valid @RequestBody Notificacion notificacion) throws URISyntaxException {
        log.debug("REST request to save Noti : {}", notificacion);
        if (notificacion.getId() != null) {
            throw new BadRequestAlertException("A new noti cannot already have an ID", ENTITY_NAME, "idexists");
        }

        Notificacion notificacion1 = new Notificacion();
        notificacion1.setDescripcion(notificacion.getDescripcion());
        notificacion1.setTipoNotificacion(notificacion.getTipoNotificacion());
        notificacion1.setUsercreada(notificacion.getUsercreada());
        notificacion1.setFecha(notificacion.getFecha());
        notificacion1.setUser(notificacion.getUser());
        notificacion1.setVisto(notificacion.getVisto());
        Notificacion result = notiService.save(notificacion);
        return result;
    }

    /**
     * {@code POST  /notificacion} : Create a new notificacion para todos los usuario de este ecosistema ademas de mandarles un correo.
     *
     * @param notificacion the notificacion to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new notificacion, or with status {@code 400 (Bad Request)} if the notificacion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/notificacion/SinMensajeParaTodos/{idecosistema}")
    public boolean createNotificacionSinMensaje(
        @PathVariable(value = "idecosistema", required = false) final Long idecosistema,
        @Valid @RequestBody Notificacion notificacion
    ) throws URISyntaxException {
        log.debug("REST request to save Notificacion para todos sin mensaje : {}", notificacion);
        if (notificacion.getId() != null) {
            throw new BadRequestAlertException("A new noti cannot already have an ID", ENTITY_NAME, "idexists");
        }

        Notificacion noti = notificacion;

        // Buscar todos los usuarios en la tabla user-ecosistema

        List<UsuarioEcosistema> userecosistemas = usuarioEcosistemaService.todos();
        // Recorro todos y busco a los ecosistemas que pertenecen si encontro el que es igual al que se pasa por parametro envio la notificacion
        for (UsuarioEcosistema usuarioEcosistema : userecosistemas) {
            Notificacion notificacion1 = new Notificacion();
            notificacion1.setDescripcion(noti.getDescripcion());
            notificacion1.setTipoNotificacion(noti.getTipoNotificacion());
            notificacion1.setUsercreada(noti.getUsercreada());
            notificacion1.setFecha(noti.getFecha());
            notificacion1.setUser(usuarioEcosistema.getUser());
            notificacion1.setVisto(noti.getVisto());

            Set<Ecosistema> list = usuarioEcosistema.getEcosistemas();
            List<Ecosistema> listEcosistemas = new ArrayList<>(list);

            if (listEcosistemas != null) for (Ecosistema ecosi : listEcosistemas) {
                if (ecosi.getId() == idecosistema) notiService.save(notificacion1);
            }
        }

        return true;
    }

    /**
     * {@code PUT  /notificacion/:id} : Updates an existing notificacion.
     *
     * @param id the id of the notificacion to save.
     * @param notificacion the notificacion to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated notificcacion,
     * or with status {@code 400 (Bad Request)} if the notifciacion is not valid,
     * or with status {@code 500 (Internal Server Error)} if the notificacion couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/notificacion/{id}")
    public ResponseEntity<Notificacion> updateNotificacion(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Notificacion notificacion
    ) throws URISyntaxException {
        log.debug("REST request to update Noti : {}, {}", id, notificacion);
        if (notificacion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, notificacion.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!notiRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Notificacion result = notiService.update(notificacion);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, notificacion.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /notificacion/:id} : Updates an existing notificacion.
     *
     * @param id the id of the notificacion to save.
     * @param notificacion the notificacion to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated noti,
     * or with status {@code 400 (Bad Request)} if the notifciacion is not valid,
     * or with status {@code 500 (Internal Server Error)} if the notificacion couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/notificacion/vista/{id}")
    public ResponseEntity<Notificacion> actializarvistaNotificacion(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Notificacion noti
    ) throws URISyntaxException {
        log.debug("REST request to update vista  Noti : {}, {}", id, noti);
        if (noti.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, noti.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!notiRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Notificacion result = notiService.update(noti);

        return ResponseEntity.ok().body(result);
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
    @PatchMapping(value = "/notificaciones/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Notificacion> partialUpdateNoti(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Notificacion noti
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

        Optional<Notificacion> result = notiService.partialUpdate(noti);

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
    @GetMapping("/notificacion")
    public ResponseEntity<List<Notificacion>> getAllNotis(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of Notis");
        Page<Notificacion> page;
        if (eagerload) {
            page = notiService.findAllWithEagerRelationships(pageable);
        } else {
            page = notiService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /notis} : get all the notis.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of notis in body.
     */
    @GetMapping("/notificacion/ByUserIdAndById/{id}/{iduser}")
    public ResponseEntity<Notificacion> getOneNotiByIdAndByUserId(@PathVariable Long id, @PathVariable Long iduser) {
        log.debug("REST request to get Noti : {}", id);
        Optional<Notificacion> noti = notiService.findByUserIdByNotiId(iduser, id);
        return ResponseUtil.wrapOrNotFound(noti);
    }

    /**
     * {@code GET  /notis/:id} : get the "id" noti.
     *
     * @param id the id of the noti to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the noti, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/notificacion/{id}")
    public ResponseEntity<Notificacion> getNoti(@PathVariable Long id) {
        log.debug("REST request to get Noti : {}", id);
        Optional<Notificacion> noti = notiService.findOne(id);
        return ResponseUtil.wrapOrNotFound(noti);
    }

    /**
     * {@code DELETE  /notis/:id} : delete the "id" noti.
     *
     * @param id the id of the noti to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/notificacion/{id}")
    public ResponseEntity<Void> deleteNoti(@PathVariable Long id) {
        log.debug("REST request to delete Noti : {}", id);
        notiService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code DELETE  /notis/:id} : delete the "id" noti.
     *
     * @param id the id of the noti to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/notificacion/deleteSinMensaje/{id}")
    public ResponseEntity<Void> deleteNotiSinmensaje(@PathVariable Long id) {
        log.debug("REST request to delete Noti : {}", id);
        notiService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * {@code GET  /notificacion/} : get notificacion.
     *
     *
     */
    @GetMapping("/notificacion/")
    public List<Notificacion> getTodasNotificaciones() {
        log.debug("REST request to get Notificaciones");
        return notiService.findTodasNotificaciones();
    }

    /**
     * {@code GET  /notificacion/:iduser} : get the "iduser" notificacion.
     *
     * @param id the id of the noti to retrieve.
     */
    @GetMapping("/notificacion/user/{iduser}")
    public List<NotificacionDTO> getTodasNotificacionesByUser(@PathVariable Long iduser) {
        log.debug("REST request to get Notificaciones by user");

        List<Notificacion> lista = notiService.findTodasNotificacionesbyUser(iduser);

        List<NotificacionDTO> listaDTO = (List<NotificacionDTO>) lista
            .stream()
            .map(noti -> {
                try {
                    return new NotificacionDTO(
                        noti,
                        fileServiceAPI.load(noti.getUsercreada().getImageUrl()),
                        fileServiceAPI.load(noti.getTipoNotificacion().getIcono())
                    );
                } catch (Exception e) {
                    return new NotificacionDTO(noti, "noImagen", "NoImagen");
                }
            })
            .collect(Collectors.toList());

        return listaDTO;
    }

    /**
     * {@code GET  /notificacion/:iduser} : get the "iduser" notificacion.
     *
     * @param id the id of the noti to retrieve.
     */
    @GetMapping("/notificacion/user-visto/{iduser}")
    public List<Notificacion> getTodasNotificacionesByUserVista(@PathVariable Long iduser) {
        log.debug("REST request to get Notificaciones bu user vistas");
        return notiService.findTodasNotificacionesbyUserVisto(iduser);
    }

    /**
     * {@code GET  /notificacion/:iduser} : get the "iduser" notificacion.
     *
     * @param id the id of the noti to retrieve.
     */
    @GetMapping("/notificacion/user-no-visto/{iduser}")
    public List<NotificacionDTO> getTodasNotificacionesByUserNoVista(@PathVariable Long iduser) {
        log.debug("REST request to get Notificaciones bu user vistas");
        List<Notificacion> lista = notiService.findTodasNotificacionesbyUserNoVisto(iduser);

        List<NotificacionDTO> listaDTO = (List<NotificacionDTO>) lista
            .stream()
            .map(noti -> {
                try {
                    return new NotificacionDTO(
                        noti,
                        fileServiceAPI.load(noti.getUsercreada().getImageUrl()),
                        fileServiceAPI.load(noti.getTipoNotificacion().getIcono())
                    );
                } catch (Exception e) {
                    return new NotificacionDTO(noti, "noImagen", "NoImagen");
                }
            })
            .collect(Collectors.toList());

        return listaDTO;
    }

    /**
     * {@code GET  /notificacion/:iduser} : get the "iduser" notificacion.
     *
     * @param id the id of the noti to retrieve.
     */
    @GetMapping("/notificacion/usercreada/{idusercreada}")
    public List<Notificacion> getTodasNotificacionesByUserCreada(@PathVariable Long idusercreada) {
        log.debug("REST request to get Notificaciones by user creado");
        return notiService.findTodasNotificacionesbyUserCreada(idusercreada);
    }
}
