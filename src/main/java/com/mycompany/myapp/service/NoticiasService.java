package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Noticias;
import com.mycompany.myapp.domain.Reto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Noticias}.
 */
public interface NoticiasService {
    /**
     * Save a noticias.
     *
     * @param noticias the entity to save.
     * @return the persisted entity.
     */
    Noticias save(Noticias noticias);

    /**
     * Updates a noticias.
     *
     * @param noticias the entity to update.
     * @return the persisted entity.
     */
    Noticias update(Noticias noticias);

    /**
     * Partially updates a noticias.
     *
     * @param noticias the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Noticias> partialUpdate(Noticias noticias);

    /**
     * Get all the noticias.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Noticias> findAll(Pageable pageable);

    /**
     * Get all the noticias.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    List<Noticias> findAllByPublicar();

    /**
     * Get all the noticias.
     *
     *
     * @return the list of entities by Ecosistema Id by Publica
     */
    List<Noticias> findAllByPublicaByEcosistemaId(Long id);

    /**
     * Get all the noticias by Ecosistema by publica by idUser.
     *
     *
     * @return the list of entities by Ecosistema Id by Publica
     */
    List<Noticias> findAllByPublicaByEcosistemaIdByUserId(Long id, Long iduser);
    /**
     * Get all the noticias by Ecosistema by publica by idUser.
     *
     *
     * @return the list of entities by Ecosistema Id by Publica
     */
    List<Noticias> findAllByPublicaByEcosistemaIdByUserIdSolo(Long id, Long iduser);
    /**
     * Get all the retos with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities by Ecosistema Id by Publica
     */
    Page<Noticias> findAllByPublicaByEcosistemaIdByUserIdPaginado(Pageable pageable, Long id);

    /**
     * Get all the noticias with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Noticias> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" noticias.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Noticias> findOne(Long id);

    /**
     * Delete the "id" noticias.
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

    List<Noticias> busquedaGeneralPorEcosistemasId(List<Long> ecosistemas);
}
