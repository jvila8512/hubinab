package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.EcosistemaRol;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link EcosistemaRol}.
 */
public interface EcosistemaRolService {
    /**
     * Save a ecosistemaRol.
     *
     * @param ecosistemaRol the entity to save.
     * @return the persisted entity.
     */
    EcosistemaRol save(EcosistemaRol ecosistemaRol);

    /**
     * Updates a ecosistemaRol.
     *
     * @param ecosistemaRol the entity to update.
     * @return the persisted entity.
     */
    EcosistemaRol update(EcosistemaRol ecosistemaRol);

    /**
     * Partially updates a ecosistemaRol.
     *
     * @param ecosistemaRol the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EcosistemaRol> partialUpdate(EcosistemaRol ecosistemaRol);

    /**
     * Get all the ecosistemaRols.
     *
     * @return the list of entities.
     */
    List<EcosistemaRol> findAll();

    /**
     * Get the "id" ecosistemaRol.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EcosistemaRol> findOne(Long id);

    /**
     * Delete the "id" ecosistemaRol.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
