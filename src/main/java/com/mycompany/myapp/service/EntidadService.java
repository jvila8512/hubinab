package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Entidad;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Entidad}.
 */
public interface EntidadService {
    /**
     * Save a entidad.
     *
     * @param entidad the entity to save.
     * @return the persisted entity.
     */
    Entidad save(Entidad entidad);

    /**
     * Updates a entidad.
     *
     * @param entidad the entity to update.
     * @return the persisted entity.
     */
    Entidad update(Entidad entidad);

    /**
     * Partially updates a entidad.
     *
     * @param entidad the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Entidad> partialUpdate(Entidad entidad);

    /**
     * Get all the entidads.
     *
     * @return the list of entities.
     */
    List<Entidad> findAll();

    /**
     * Get the "id" entidad.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Entidad> findOne(Long id);

    /**
     * Delete the "id" entidad.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
