package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.TipoIdea;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link TipoIdea}.
 */
public interface TipoIdeaService {
    /**
     * Save a tipoIdea.
     *
     * @param tipoIdea the entity to save.
     * @return the persisted entity.
     */
    TipoIdea save(TipoIdea tipoIdea);

    /**
     * Updates a tipoIdea.
     *
     * @param tipoIdea the entity to update.
     * @return the persisted entity.
     */
    TipoIdea update(TipoIdea tipoIdea);

    /**
     * Partially updates a tipoIdea.
     *
     * @param tipoIdea the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TipoIdea> partialUpdate(TipoIdea tipoIdea);

    /**
     * Get all the tipoIdeas.
     *
     * @return the list of entities.
     */
    List<TipoIdea> findAll();

    /**
     * Get the "id" tipoIdea.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TipoIdea> findOne(Long id);

    /**
     * Delete the "id" tipoIdea.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
