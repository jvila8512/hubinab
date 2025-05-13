package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Contacto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Contacto}.
 */
public interface ContactoService {
    /**
     * Save a contacto.
     *
     * @param contacto the entity to save.
     * @return the persisted entity.
     */
    Contacto save(Contacto contacto);

    /**
     * Updates a contacto.
     *
     * @param contacto the entity to update.
     * @return the persisted entity.
     */
    Contacto update(Contacto contacto);

    /**
     * Partially updates a contacto.
     *
     * @param contacto the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Contacto> partialUpdate(Contacto contacto);

    /**
     * Get all the contactos.
     *
     * @return the list of entities.
     */
    List<Contacto> findAll();

    /**
     * Get all the contactos with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Contacto> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" contacto.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Contacto> findOne(Long id);

    /**
     * Delete the "id" contacto.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
