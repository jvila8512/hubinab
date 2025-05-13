package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.UsuarioEcosistema;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link UsuarioEcosistema}.
 */
public interface UsuarioEcosistemaService {
    /**
     * Save a usuarioEcosistema.
     *
     * @param usuarioEcosistema the entity to save.
     * @return the persisted entity.
     */
    UsuarioEcosistema save(UsuarioEcosistema usuarioEcosistema);

    /**
     * Updates a usuarioEcosistema.
     *
     * @param usuarioEcosistema the entity to update.
     * @return the persisted entity.
     */
    UsuarioEcosistema update(UsuarioEcosistema usuarioEcosistema);

    /**
     * Partially updates a usuarioEcosistema.
     *
     * @param usuarioEcosistema the entity to update partially.
     * @return the persisted entity.
     */
    Optional<UsuarioEcosistema> partialUpdate(UsuarioEcosistema usuarioEcosistema);

    /**
     * Get all the usuarioEcosistemas.
     *
     * @return the list of entities.
     */
    List<UsuarioEcosistema> findAll();

    /**
     * Get all the usuarioEcosistemas with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UsuarioEcosistema> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" usuarioEcosistema.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UsuarioEcosistema> findOne(Long id);

    /**
     * Delete the "id" usuarioEcosistema.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    Optional<UsuarioEcosistema> buscarOneByUserId(Long id);
}
