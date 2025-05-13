package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.LikeIdea;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link LikeIdea}.
 */
public interface LikeIdeaService {
    /**
     * Save a likeIdea.
     *
     * @param likeIdea the entity to save.
     * @return the persisted entity.
     */
    LikeIdea save(LikeIdea likeIdea);

    /**
     * Updates a likeIdea.
     *
     * @param likeIdea the entity to update.
     * @return the persisted entity.
     */
    LikeIdea update(LikeIdea likeIdea);

    /**
     * Partially updates a likeIdea.
     *
     * @param likeIdea the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LikeIdea> partialUpdate(LikeIdea likeIdea);

    /**
     * Get all the likeIdeas.
     *
     * @return the list of entities.
     */
    List<LikeIdea> findAll();

    /**
     * Get all the likeIdeas.
     * @param id the id of the entity Idea.
     * @return the list of entities.
     */
    List<LikeIdea> findAllbyIdea(Long id);

    /**
     * Get all the likeIdeas with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LikeIdea> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" likeIdea.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LikeIdea> findOne(Long id);

    /**
     * Delete the "id" likeIdea.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
