package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Ods;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Ods}.
 */
public interface OdsService {
    /**
     * Save a ods.
     *
     * @param ods the entity to save.
     * @return the persisted entity.
     */
    Ods save(Ods ods);

    /**
     * Updates a ods.
     *
     * @param ods the entity to update.
     * @return the persisted entity.
     */
    Ods update(Ods ods);

    /**
     * Partially updates a ods.
     *
     * @param ods the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Ods> partialUpdate(Ods ods);

    /**
     * Get all the ods.
     *
     * @return the list of entities.
     */
    List<Ods> findAll();

    /**
     * Get the "id" ods.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Ods> findOne(Long id);

    /**
     * Delete the "id" ods.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
