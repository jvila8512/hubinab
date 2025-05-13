package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Participantes;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Participantes}.
 */
public interface ParticipantesService {
    /**
     * Save a participantes.
     *
     * @param participantes the entity to save.
     * @return the persisted entity.
     */
    Participantes save(Participantes participantes);

    /**
     * Updates a participantes.
     *
     * @param participantes the entity to update.
     * @return the persisted entity.
     */
    Participantes update(Participantes participantes);

    /**
     * Partially updates a participantes.
     *
     * @param participantes the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Participantes> partialUpdate(Participantes participantes);

    /**
     * Get all the participantes.
     *
     * @return the list of entities.
     */
    List<Participantes> findAll();

    /**
     * Get the "id" participantes.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Participantes> findOne(Long id);

    /**
     * Delete the "id" participantes.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
