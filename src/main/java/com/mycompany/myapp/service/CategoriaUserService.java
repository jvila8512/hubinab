package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.CategoriaUser;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link CategoriaUser}.
 */
public interface CategoriaUserService {
    /**
     * Save a categoriaUser.
     *
     * @param categoriaUser the entity to save.
     * @return the persisted entity.
     */
    CategoriaUser save(CategoriaUser categoriaUser);

    /**
     * Updates a categoriaUser.
     *
     * @param categoriaUser the entity to update.
     * @return the persisted entity.
     */
    CategoriaUser update(CategoriaUser categoriaUser);

    /**
     * Partially updates a categoriaUser.
     *
     * @param categoriaUser the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CategoriaUser> partialUpdate(CategoriaUser categoriaUser);

    /**
     * Get all the categoriaUsers.
     *
     * @return the list of entities.
     */
    List<CategoriaUser> findAll();

    /**
     * Get the "id" categoriaUser.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CategoriaUser> findOne(Long id);

    /**
     * Delete the "id" categoriaUser.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
