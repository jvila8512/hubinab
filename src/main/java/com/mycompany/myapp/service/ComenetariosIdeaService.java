package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.ComenetariosIdea;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ComenetariosIdea}.
 */
public interface ComenetariosIdeaService {
    /**
     * Save a comenetariosIdea.
     *
     * @param comenetariosIdea the entity to save.
     * @return the persisted entity.
     */
    ComenetariosIdea save(ComenetariosIdea comenetariosIdea);

    /**
     * Updates a comenetariosIdea.
     *
     * @param comenetariosIdea the entity to update.
     * @return the persisted entity.
     */
    ComenetariosIdea update(ComenetariosIdea comenetariosIdea);

    /**
     * Partially updates a comenetariosIdea.
     *
     * @param comenetariosIdea the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ComenetariosIdea> partialUpdate(ComenetariosIdea comenetariosIdea);

    /**
     * Get all the comenetariosIdeas.
     *
     * @return the list of entities.
     */
    List<ComenetariosIdea> findAll();

    /**
     * Get all the comenetariosIdeas by Id Idea.
     *
     * @return the list of entities by Id Idea.
     */
    List<ComenetariosIdea> findAllByIdIdea(Long id);

    /**
     * Get all the comenetariosIdeas with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ComenetariosIdea> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" comenetariosIdea.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ComenetariosIdea> findOne(Long id);

    /**
     * Delete the "id" comenetariosIdea.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
