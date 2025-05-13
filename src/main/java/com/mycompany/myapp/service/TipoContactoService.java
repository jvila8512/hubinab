package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.TipoContacto;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link TipoContacto}.
 */
public interface TipoContactoService {
    /**
     * Save a tipoContacto.
     *
     * @param tipoContacto the entity to save.
     * @return the persisted entity.
     */
    TipoContacto save(TipoContacto tipoContacto);

    /**
     * Updates a tipoContacto.
     *
     * @param tipoContacto the entity to update.
     * @return the persisted entity.
     */
    TipoContacto update(TipoContacto tipoContacto);

    /**
     * Partially updates a tipoContacto.
     *
     * @param tipoContacto the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TipoContacto> partialUpdate(TipoContacto tipoContacto);

    /**
     * Get all the tipoContactos.
     *
     * @return the list of entities.
     */
    List<TipoContacto> findAll();

    /**
     * Get the "id" tipoContacto.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TipoContacto> findOne(Long id);

    /**
     * Delete the "id" tipoContacto.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
