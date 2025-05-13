package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Proyectos;
import com.mycompany.myapp.domain.Reto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Proyectos}.
 */
public interface ProyectosService {
    /**
     * Save a proyectos.
     *
     * @param proyectos the entity to save.
     * @return the persisted entity.
     */
    Proyectos save(Proyectos proyectos);

    /**
     * Updates a proyectos.
     *
     * @param proyectos the entity to update.
     * @return the persisted entity.
     */
    Proyectos update(Proyectos proyectos);

    /**
     * Partially updates a proyectos.
     *
     * @param proyectos the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Proyectos> partialUpdate(Proyectos proyectos);

    /**
     * Get all the proyectos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Proyectos> findAll(Pageable pageable);
    /**
     * Get all the proyectos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    List<Proyectos> findAll();

    /**
     * Get all the proyectos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    List<Proyectos> findAllWithEagerRelationshipsByEcosistema(Long id);

    /**
     * Get all the proyectos with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Proyectos> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" proyectos.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Proyectos> findOne(Long id);

    /**
     * Delete the "id" proyectos.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Get the "id" reto.
     *
     * @param id the id of the entity.
     * @return the Long.
     */
    Long contarEcosistemas(Long id);

    List<Proyectos> busquedaGeneralPorEcosistemasId(List<Long> ecosistemas);
}
