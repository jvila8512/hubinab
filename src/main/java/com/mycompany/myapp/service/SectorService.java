package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Sector;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Sector}.
 */
public interface SectorService {
    /**
     * Save a sector.
     *
     * @param sector the entity to save.
     * @return the persisted entity.
     */
    Sector save(Sector sector);

    /**
     * Updates a sector.
     *
     * @param sector the entity to update.
     * @return the persisted entity.
     */
    Sector update(Sector sector);

    /**
     * Partially updates a sector.
     *
     * @param sector the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Sector> partialUpdate(Sector sector);

    /**
     * Get all the sectors.
     *
     * @return the list of entities.
     */
    List<Sector> findAll();

    /**
     * Get the "id" sector.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Sector> findOne(Long id);

    /**
     * Delete the "id" sector.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
