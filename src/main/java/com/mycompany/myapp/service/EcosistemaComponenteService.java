package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.EcosistemaComponente;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link EcosistemaComponente}.
 */
public interface EcosistemaComponenteService {
    /**
     * Save a ecosistemaComponente.
     *
     * @param ecosistemaComponente the entity to save.
     * @return the persisted entity.
     */
    EcosistemaComponente save(EcosistemaComponente ecosistemaComponente);

    /**
     * Updates a ecosistemaComponente.
     *
     * @param ecosistemaComponente the entity to update.
     * @return the persisted entity.
     */
    EcosistemaComponente update(EcosistemaComponente ecosistemaComponente);

    /**
     * Partially updates a ecosistemaComponente.
     *
     * @param ecosistemaComponente the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EcosistemaComponente> partialUpdate(EcosistemaComponente ecosistemaComponente);

    /**
     * Get all the ecosistemaComponentes.
     *
     * @return the list of entities.
     */
    List<EcosistemaComponente> findAll();

    /**
     * Get all the ecosistemaComponentes.
     *
     * @return the list of entities.
     */
    List<EcosistemaComponente> findAllComponentesbyEcosistema(Long id);

    /**
     * Get all the ecosistemaComponentes with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EcosistemaComponente> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" ecosistemaComponente.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EcosistemaComponente> findOne(Long id);

    /**
     * Delete the "id" ecosistemaComponente.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
