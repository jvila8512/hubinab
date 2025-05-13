package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.LineaInvestigacion;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link LineaInvestigacion}.
 */
public interface LineaInvestigacionService {
    /**
     * Save a lineaInvestigacion.
     *
     * @param lineaInvestigacion the entity to save.
     * @return the persisted entity.
     */
    LineaInvestigacion save(LineaInvestigacion lineaInvestigacion);

    /**
     * Updates a lineaInvestigacion.
     *
     * @param lineaInvestigacion the entity to update.
     * @return the persisted entity.
     */
    LineaInvestigacion update(LineaInvestigacion lineaInvestigacion);

    /**
     * Partially updates a lineaInvestigacion.
     *
     * @param lineaInvestigacion the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LineaInvestigacion> partialUpdate(LineaInvestigacion lineaInvestigacion);

    /**
     * Get all the lineaInvestigacions.
     *
     * @return the list of entities.
     */
    List<LineaInvestigacion> findAll();

    /**
     * Get the "id" lineaInvestigacion.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LineaInvestigacion> findOne(Long id);

    /**
     * Delete the "id" lineaInvestigacion.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
