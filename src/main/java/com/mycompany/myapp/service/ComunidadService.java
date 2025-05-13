package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Comunidad;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Comunidad}.
 */
public interface ComunidadService {
    /**
     * Save a comunidad.
     *
     * @param comunidad the entity to save.
     * @return the persisted entity.
     */
    Comunidad save(Comunidad comunidad);

    /**
     * Updates a comunidad.
     *
     * @param comunidad the entity to update.
     * @return the persisted entity.
     */
    Comunidad update(Comunidad comunidad);

    /**
     * Partially updates a comunidad.
     *
     * @param comunidad the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Comunidad> partialUpdate(Comunidad comunidad);
    /**
     * Get all the comunidad.
     *
     * @return the list of entities.
     */
    List<Comunidad> findAll();
    /**
     * Get all the comunidad.
     *
     * @return the list of entities by active.
     */
    List<Comunidad> findAllByActivo();

    /**
     * Get the "id" comunidad.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Comunidad> findOne(Long id);

    /**
     * Delete the "id" comunidad.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
