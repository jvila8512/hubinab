package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Proyectos;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class ProyectosRepositoryWithBagRelationshipsImpl implements ProyectosRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Proyectos> fetchBagRelationships(Optional<Proyectos> proyectos) {
        return proyectos.map(this::fetchSectors).map(this::fetchLineaInvestigacions).map(this::fetchOds);
    }

    @Override
    public Page<Proyectos> fetchBagRelationships(Page<Proyectos> proyectos) {
        return new PageImpl<>(fetchBagRelationships(proyectos.getContent()), proyectos.getPageable(), proyectos.getTotalElements());
    }

    @Override
    public List<Proyectos> fetchBagRelationships(List<Proyectos> proyectos) {
        return Optional
            .of(proyectos)
            .map(this::fetchSectors)
            .map(this::fetchLineaInvestigacions)
            .map(this::fetchOds)
            .orElse(Collections.emptyList());
    }

    Proyectos fetchSectors(Proyectos result) {
        return entityManager
            .createQuery(
                "select proyectos from Proyectos proyectos left join fetch proyectos.sectors where proyectos is :proyectos",
                Proyectos.class
            )
            .setParameter("proyectos", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Proyectos> fetchSectors(List<Proyectos> proyectos) {
        return entityManager
            .createQuery(
                "select distinct proyectos from Proyectos proyectos left join fetch proyectos.sectors where proyectos in :proyectos",
                Proyectos.class
            )
            .setParameter("proyectos", proyectos)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
    }

    Proyectos fetchLineaInvestigacions(Proyectos result) {
        return entityManager
            .createQuery(
                "select proyectos from Proyectos proyectos left join fetch proyectos.lineaInvestigacions where proyectos is :proyectos",
                Proyectos.class
            )
            .setParameter("proyectos", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Proyectos> fetchLineaInvestigacions(List<Proyectos> proyectos) {
        return entityManager
            .createQuery(
                "select distinct proyectos from Proyectos proyectos left join fetch proyectos.lineaInvestigacions where proyectos in :proyectos",
                Proyectos.class
            )
            .setParameter("proyectos", proyectos)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
    }

    Proyectos fetchOds(Proyectos result) {
        return entityManager
            .createQuery(
                "select proyectos from Proyectos proyectos left join fetch proyectos.ods where proyectos is :proyectos",
                Proyectos.class
            )
            .setParameter("proyectos", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Proyectos> fetchOds(List<Proyectos> proyectos) {
        return entityManager
            .createQuery(
                "select distinct proyectos from Proyectos proyectos left join fetch proyectos.ods where proyectos in :proyectos",
                Proyectos.class
            )
            .setParameter("proyectos", proyectos)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
    }
}
