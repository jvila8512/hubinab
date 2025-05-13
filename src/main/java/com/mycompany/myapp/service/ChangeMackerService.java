package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.ChangeMacker;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ChangeMacker}.
 */
public interface ChangeMackerService {
    /**
     * Save a changeMacker.
     *
     * @param changeMacker the entity to save.
     * @return the persisted entity.
     */
    ChangeMacker save(ChangeMacker changeMacker);

    /**
     * Updates a changeMacker.
     *
     * @param changeMacker the entity to update.
     * @return the persisted entity.
     */
    ChangeMacker update(ChangeMacker changeMacker);

    /**
     * Partially updates a changeMacker.
     *
     * @param changeMacker the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ChangeMacker> partialUpdate(ChangeMacker changeMacker);

    /**
     * Get all the changeMackers.
     *
     * @return the list of entities.
     */
    List<ChangeMacker> findAll();

    /**
     * Get the "id" changeMacker.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ChangeMacker> findOne(Long id);

    /**
     * Delete the "id" changeMacker.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
