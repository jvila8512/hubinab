package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.RedesSociales;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link RedesSociales}.
 */
public interface RedesSocialesService {
    /**
     * Save a redesSociales.
     *
     * @param redesSociales the entity to save.
     * @return the persisted entity.
     */
    RedesSociales save(RedesSociales redesSociales);

    /**
     * Updates a redesSociales.
     *
     * @param redesSociales the entity to update.
     * @return the persisted entity.
     */
    RedesSociales update(RedesSociales redesSociales);

    /**
     * Partially updates a redesSociales.
     *
     * @param redesSociales the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RedesSociales> partialUpdate(RedesSociales redesSociales);

    /**
     * Get all the redesSociales.
     *
     * @return the list of entities.
     */
    List<RedesSociales> findAll();

    /**
     * Get the "id" redesSociales.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RedesSociales> findOne(Long id);

    /**
     * Delete the "id" redesSociales.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
