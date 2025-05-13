package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Anirista;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Anirista}.
 */
public interface AniristaService {
    /**
     * Save a anirista.
     *
     * @param anirista the entity to save.
     * @return the persisted entity.
     */
    Anirista save(Anirista anirista);

    /**
     * Updates a anirista.
     *
     * @param anirista the entity to update.
     * @return the persisted entity.
     */
    Anirista update(Anirista anirista);

    /**
     * Partially updates a anirista.
     *
     * @param anirista the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Anirista> partialUpdate(Anirista anirista);

    /**
     * Get all the aniristas.
     *
     * @return the list of entities.
     */
    List<Anirista> findAll();

    /**
     * Get all the aniristas with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Anirista> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" anirista.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Anirista> findOne(Long id);

    /**
     * Delete the "id" anirista.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
