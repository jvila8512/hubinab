package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Noti;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Noti}.
 */
public interface NotiService {
    /**
     * Save a noti.
     *
     * @param noti the entity to save.
     * @return the persisted entity.
     */
    Noti save(Noti noti);

    /**
     * Updates a noti.
     *
     * @param noti the entity to update.
     * @return the persisted entity.
     */
    Noti update(Noti noti);

    /**
     * Partially updates a noti.
     *
     * @param noti the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Noti> partialUpdate(Noti noti);

    /**
     * Get all the notis.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Noti> findAll(Pageable pageable);

    /**
     * Get all the notis with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Noti> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" noti.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Noti> findOne(Long id);

    /**
     * Delete the "id" noti.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
