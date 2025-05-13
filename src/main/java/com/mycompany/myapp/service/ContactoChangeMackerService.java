package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.ContactoChangeMacker;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ContactoChangeMacker}.
 */
public interface ContactoChangeMackerService {
    /**
     * Save a contactoChangeMacker.
     *
     * @param contactoChangeMacker the entity to save.
     * @return the persisted entity.
     */
    ContactoChangeMacker save(ContactoChangeMacker contactoChangeMacker);

    /**
     * Updates a contactoChangeMacker.
     *
     * @param contactoChangeMacker the entity to update.
     * @return the persisted entity.
     */
    ContactoChangeMacker update(ContactoChangeMacker contactoChangeMacker);

    /**
     * Partially updates a contactoChangeMacker.
     *
     * @param contactoChangeMacker the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ContactoChangeMacker> partialUpdate(ContactoChangeMacker contactoChangeMacker);

    /**
     * Get all the contactoChangeMackers.
     *
     * @return the list of entities.
     */
    List<ContactoChangeMacker> findAll();

    /**
     * Get all the contactoChangeMackers with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ContactoChangeMacker> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" contactoChangeMacker.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ContactoChangeMacker> findOne(Long id);

    /**
     * Delete the "id" contactoChangeMacker.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
