package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Servicios;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Servicios}.
 */
public interface ServiciosService {
    /**
     * Save a servicios.
     *
     * @param servicios the entity to save.
     * @return the persisted entity.
     */
    Servicios save(Servicios servicios);

    /**
     * Updates a servicios.
     *
     * @param servicios the entity to update.
     * @return the persisted entity.
     */
    Servicios update(Servicios servicios);

    /**
     * Partially updates a servicios.
     *
     * @param servicios the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Servicios> partialUpdate(Servicios servicios);

    /**
     * Get all the servicios.
     *
     * @return the list of entities.
     */
    List<Servicios> findAll();

    /**
     * Get all the servicios.
     *
     * @return the list of entities by Publicado.
     */
    List<Servicios> findAllByPublicado();

    /**
     * Get the "id" servicios.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Servicios> findOne(Long id);

    /**
     * Delete the "id" servicios.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
