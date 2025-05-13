package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Tramite;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Tramite}.
 */
public interface TramiteService {
    /**
     * Save a tramite.
     *
     * @param tramite the entity to save.
     * @return the persisted entity.
     */
    Tramite save(Tramite tramite);

    /**
     * Updates a tramite.
     *
     * @param tramite the entity to update.
     * @return the persisted entity.
     */
    Tramite update(Tramite tramite);

    /**
     * Partially updates a tramite.
     *
     * @param tramite the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Tramite> partialUpdate(Tramite tramite);

    /**
     * Get all the tramites.
     *
     * @return the list of entities.
     */
    List<Tramite> findAll();

    /**
     * Get the "id" tramite.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Tramite> findOne(Long id);

    /**
     * Delete the "id" tramite.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
