package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.ContactoServicio;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ContactoServicio}.
 */
public interface ContactoServicioService {
    /**
     * Save a contactoServicio.
     *
     * @param contactoServicio the entity to save.
     * @return the persisted entity.
     */
    ContactoServicio save(ContactoServicio contactoServicio);

    /**
     * Updates a contactoServicio.
     *
     * @param contactoServicio the entity to update.
     * @return the persisted entity.
     */
    ContactoServicio update(ContactoServicio contactoServicio);

    /**
     * Partially updates a contactoServicio.
     *
     * @param contactoServicio the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ContactoServicio> partialUpdate(ContactoServicio contactoServicio);

    /**
     * Get all the contactoServicios.
     *
     * @return the list of entities.
     */
    List<ContactoServicio> findAll();

    /**
     * Get all the contactoServicios with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ContactoServicio> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" contactoServicio.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ContactoServicio> findOne(Long id);

    /**
     * Delete the "id" contactoServicio.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
