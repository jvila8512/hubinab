package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Ecosistema;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Ecosistema}.
 */
public interface EcosistemaService {
    /**
     * Save a ecosistema.
     *
     * @param ecosistema the entity to save.
     * @return the persisted entity.
     */
    Ecosistema save(Ecosistema ecosistema);

    /**
     * Updates a ecosistema.
     *
     * @param ecosistema the entity to update.
     * @return the persisted entity.
     */
    Ecosistema update(Ecosistema ecosistema);

    /**
     * Partially updates a ecosistema.
     *
     * @param ecosistema the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Ecosistema> partialUpdate(Ecosistema ecosistema);

    /**
     * Get all the ecosistemas.
     *
     * @return the list of entities.
     */
    List<Ecosistema> findAll();

    /**
     * Get all the ecosistemas.
     *
     * @return the list of entities.
     */
    List<Ecosistema> findAllbyActivo();

    /**
     * Get all the ecosistemas with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Ecosistema> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" ecosistema.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Ecosistema> findOne(Long id);

    /**
     * Delete the "id" ecosistema.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    public boolean usuarioEnEcosistemas(Long userId);
}
