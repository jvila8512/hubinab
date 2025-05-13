package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Notificacion;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Notificacion}.
 */
public interface NotificacionService {
    /**
     * Save a notificacion.
     *
     * @param notificacion the entity to save.
     * @return the persisted entity.
     */
    Notificacion save(Notificacion notificacion);

    /**
     * Updates a notificacion.
     *
     * @param notificacion the entity to update.
     * @return the persisted entity.
     */
    Notificacion update(Notificacion notificacion);

    /**
     * Partially updates a notificacion.
     *
     * @param notificacion the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Notificacion> partialUpdate(Notificacion notificacion);

    /**
     * Get all the notificacion.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Notificacion> findAll(Pageable pageable);

    /**
     * Get all the notis with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Notificacion> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" noti.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Notificacion> findOne(Long id);

    /**
     * Get the "id" noti.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Notificacion> findByUserIdByNotiId(Long iduser, Long idNoti);

    /**
     *
     * Get the "id" noti.
     *
     *
     * @return the entity.
     */
    List<Notificacion> findTodasNotificaciones();

    /**
     * Get the "id" noti.
     *
     * @param id the id of the entity.
     */
    List<Notificacion> findTodasNotificacionesbyUser(Long iduser);
    /**
     * Get the "id" noti.
     *
     * @param id the id of the entity.
     */
    List<Notificacion> findTodasNotificacionesbyUserVisto(Long iduser);
    /**
     * Get the "id" noti.
     *
     * @param id the id of the entity.
     */
    List<Notificacion> findTodasNotificacionesbyUserNoVisto(Long iduser);
    /**
     * Get the "id" noti.
     *
     * @param id the id of the entity.
     */
    List<Notificacion> findTodasNotificacionesbyUserCreada(Long idusercreada);

    /**
     * Delete the "id" noti.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    void save1(Notificacion element);
}
