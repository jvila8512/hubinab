package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Ecosistema;
import com.mycompany.myapp.domain.Reto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Reto}.
 */
public interface RetoService {
    /**
     * Save a reto.
     *
     * @param reto the entity to save.
     * @return the persisted entity.
     */
    Reto save(Reto reto);

    /**
     * Updates a reto.
     *
     * @param reto the entity to update.
     * @return the persisted entity.
     */
    Reto update(Reto reto);

    /**
     * Partially updates a reto.
     *
     * @param reto the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Reto> partialUpdate(Reto reto);

    /**
     * Get all the retos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Reto> findAll(Pageable pageable);

    /**
     * Get all the retos with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Reto> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get all the retos with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    List<Reto> findAllWithEagerRelationshipsByIdEcosistema(Long id);
    /**
     * Get all the retos with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    List<Reto> findAllWithEagerRelationshipsByIdEcosistemaTodas(Long id);

    /**
     * Get all the retos with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    List<Reto> findAllTodosPublicosByEcosistemaId(Long id);

    /**
     * Get all the retos with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Reto> findAllWithEagerRelationshipsByIdEcosistema1(Pageable pageable, Long id);

    /**
     * Get all the retos with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    List<Reto> findAllWithEagerRelationshipsByIdUsuario(Long id);
    /**
     * Get all the retos with eager load of many-to-many relationships.
     *
     * @param id the id ecositema.
     * @param iduser el id del usuario.
     * @return the list of entities.
     */
    List<Reto> findAllWithEagerRelationshipsByIdUsuariobyIdUser(Long id, Long iduser);

    /**
     * Get the "id" reto.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Reto> findOne(Long id);
    /**
     * Get the "id" reto.
     *
     * @param id the id of the entity.
     * @return the Long.
     */
    Long contarEcosistemas(Long id);

    /**
     * Delete the "id" reto.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<Reto> busquedaGeneralPorEcosistemasId(List<Long> ecosistemas);
}
