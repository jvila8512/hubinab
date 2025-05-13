package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.TipoProyecto;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link TipoProyecto}.
 */
public interface TipoProyectoService {
    /**
     * Save a tipoProyecto.
     *
     * @param tipoProyecto the entity to save.
     * @return the persisted entity.
     */
    TipoProyecto save(TipoProyecto tipoProyecto);

    /**
     * Updates a tipoProyecto.
     *
     * @param tipoProyecto the entity to update.
     * @return the persisted entity.
     */
    TipoProyecto update(TipoProyecto tipoProyecto);

    /**
     * Partially updates a tipoProyecto.
     *
     * @param tipoProyecto the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TipoProyecto> partialUpdate(TipoProyecto tipoProyecto);

    /**
     * Get all the tipoProyectos.
     *
     * @return the list of entities.
     */
    List<TipoProyecto> findAll();

    /**
     * Get the "id" tipoProyecto.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TipoProyecto> findOne(Long id);

    /**
     * Delete the "id" tipoProyecto.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
