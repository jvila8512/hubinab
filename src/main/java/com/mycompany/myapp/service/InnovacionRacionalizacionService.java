package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.InnovacionRacionalizacion;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link InnovacionRacionalizacion}.
 */
public interface InnovacionRacionalizacionService {
    /**
     * Save a innovacionRacionalizacion.
     *
     * @param innovacionRacionalizacion the entity to save.
     * @return the persisted entity.
     */
    InnovacionRacionalizacion save(InnovacionRacionalizacion innovacionRacionalizacion);

    /**
     * Updates a innovacionRacionalizacion.
     *
     * @param innovacionRacionalizacion the entity to update.
     * @return the persisted entity.
     */
    InnovacionRacionalizacion update(InnovacionRacionalizacion innovacionRacionalizacion);

    /**
     * Partially updates a innovacionRacionalizacion.
     *
     * @param innovacionRacionalizacion the entity to update partially.
     * @return the persisted entity.
     */
    Optional<InnovacionRacionalizacion> partialUpdate(InnovacionRacionalizacion innovacionRacionalizacion);

    /**
     * Get all the innovacionRacionalizacions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<InnovacionRacionalizacion> findAll(Pageable pageable);

    /**
     * Buscar solo las publicas
     *
     * @param innovacionRacionalizacion buscar solo las publicas.
     * @return the list of entities
     */
    List<InnovacionRacionalizacion> findAllbyPublico();

    /**
     * Buscar y devolver  las no publicas que son del usuario
     *
     * @param innovacionRacionalizacion buscar solo no solo las publicas sino tambien la que son creadas por el usuario
     * @return the list of entities
     */
    List<InnovacionRacionalizacion> findAllbyPublicoByUser_id(Long iduser);

    /**
     * Get all the innovacionRacionalizacions with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<InnovacionRacionalizacion> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" innovacionRacionalizacion.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<InnovacionRacionalizacion> findOne(Long id);

    /**
     * Delete the "id" innovacionRacionalizacion.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
