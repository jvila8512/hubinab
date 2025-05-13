package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.TipoNotificacion;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link TipoNotificacion}.
 */
public interface TipoNotificacionService {
    /**
     * Save a tipoNotificacion.
     *
     * @param tipoNotificacion the entity to save.
     * @return the persisted entity.
     */
    TipoNotificacion save(TipoNotificacion tipoNotificacion);

    /**
     * Updates a tipoNotificacion.
     *
     * @param tipoNotificacion the entity to update.
     * @return the persisted entity.
     */
    TipoNotificacion update(TipoNotificacion tipoNotificacion);

    /**
     * Partially updates a tipoNotificacion.
     *
     * @param tipoNotificacion the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TipoNotificacion> partialUpdate(TipoNotificacion tipoNotificacion);

    /**
     * Get all the tipoNotificacions.
     *
     * @return the list of entities.
     */
    List<TipoNotificacion> findAll();

    /**
     * Get the "id" tipoNotificacion.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TipoNotificacion> findOne(Long id);

    /**
     * Delete the "id" tipoNotificacion.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
