package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Componentes;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Componentes}.
 */
public interface ComponentesService {
    /**
     * Save a componentes.
     *
     * @param componentes the entity to save.
     * @return the persisted entity.
     */
    Componentes save(Componentes componentes);

    /**
     * Updates a componentes.
     *
     * @param componentes the entity to update.
     * @return the persisted entity.
     */
    Componentes update(Componentes componentes);

    /**
     * Partially updates a componentes.
     *
     * @param componentes the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Componentes> partialUpdate(Componentes componentes);

    /**
     * Get all the componentes.
     *
     * @return the list of entities.
     */
    List<Componentes> findAll();

    /**
     * Get the "id" componentes.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Componentes> findOne(Long id);

    /**
     * Delete the "id" componentes.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
