package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.TipoNoticia;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link TipoNoticia}.
 */
public interface TipoNoticiaService {
    /**
     * Save a tipoNoticia.
     *
     * @param tipoNoticia the entity to save.
     * @return the persisted entity.
     */
    TipoNoticia save(TipoNoticia tipoNoticia);

    /**
     * Updates a tipoNoticia.
     *
     * @param tipoNoticia the entity to update.
     * @return the persisted entity.
     */
    TipoNoticia update(TipoNoticia tipoNoticia);

    /**
     * Partially updates a tipoNoticia.
     *
     * @param tipoNoticia the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TipoNoticia> partialUpdate(TipoNoticia tipoNoticia);

    /**
     * Get all the tipoNoticias.
     *
     * @return the list of entities.
     */
    List<TipoNoticia> findAll();

    /**
     * Get the "id" tipoNoticia.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TipoNoticia> findOne(Long id);

    /**
     * Delete the "id" tipoNoticia.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
