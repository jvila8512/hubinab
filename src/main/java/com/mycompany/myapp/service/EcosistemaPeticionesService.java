package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.EcosistemaPeticiones;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link EcosistemaPeticiones}.
 */
public interface EcosistemaPeticionesService {
    /**
     * Save a ecosistemaPeticiones.
     *
     * @param ecosistemaPeticiones the entity to save.
     * @return the persisted entity.
     */
    EcosistemaPeticiones save(EcosistemaPeticiones ecosistemaPeticiones);

    /**
     * Updates a ecosistemaPeticiones.
     *
     * @param ecosistemaPeticiones the entity to update.
     * @return the persisted entity.
     */
    EcosistemaPeticiones update(EcosistemaPeticiones ecosistemaPeticiones);

    /**
     * Partially updates a ecosistemaPeticiones.
     *
     * @param ecosistemaPeticiones the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EcosistemaPeticiones> partialUpdate(EcosistemaPeticiones ecosistemaPeticiones);

    /**
     * Get all the ecosistemaPeticiones.
     *
     * @return the list of entities.
     */
    List<EcosistemaPeticiones> findAll();

    /**
     * Get all the ecosistemaPeticiones with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EcosistemaPeticiones> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" ecosistemaPeticiones.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EcosistemaPeticiones> findOne(Long id);

    /**
     * Delete the "id" ecosistemaPeticiones.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
