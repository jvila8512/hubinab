package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.InnovacionRacionalizacion;
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
public class InnovacionRepositoryWithBagRelationshipsImpl implements InnovacionRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<InnovacionRacionalizacion> fetchBagRelationships(Optional<InnovacionRacionalizacion> proyectos) {
        return proyectos.map(this::fetchSectors).map(this::fetchLineaInvestigacions).map(this::fetchOds);
    }

    @Override
    public Page<InnovacionRacionalizacion> fetchBagRelationships(Page<InnovacionRacionalizacion> proyectos) {
        return new PageImpl<>(fetchBagRelationships(proyectos.getContent()), proyectos.getPageable(), proyectos.getTotalElements());
    }

    @Override
    public List<InnovacionRacionalizacion> fetchBagRelationships(List<InnovacionRacionalizacion> proyectos) {
        return Optional
            .of(proyectos)
            .map(this::fetchSectors)
            .map(this::fetchLineaInvestigacions)
            .map(this::fetchOds)
            .orElse(Collections.emptyList());
    }

    InnovacionRacionalizacion fetchSectors(InnovacionRacionalizacion result) {
        return entityManager
            .createQuery(
                "select innovacionRacionalizacion from InnovacionRacionalizacion  innovacionRacionalizacion left join fetch innovacionRacionalizacion.sectors where InnovacionRacionalizacion is :innovacionRacionalizacion",
                InnovacionRacionalizacion.class
            )
            .setParameter("innovacionRacionalizacion", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<InnovacionRacionalizacion> fetchSectors(List<InnovacionRacionalizacion> proyectos) {
        return entityManager
            .createQuery(
                "select distinct innovacionRacionalizacion from InnovacionRacionalizacion innovacionRacionalizacion left join fetch innovacionRacionalizacion.sectors where innovacionRacionalizacion in :innovacionRacionalizacion",
                InnovacionRacionalizacion.class
            )
            .setParameter("innovacionRacionalizacion", proyectos)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
    }

    InnovacionRacionalizacion fetchLineaInvestigacions(InnovacionRacionalizacion result) {
        return entityManager
            .createQuery(
                "select innovacionRacionalizacion from InnovacionRacionalizacion innovacionRacionalizacion left join fetch innovacionRacionalizacion.lineaInvestigacion where innovacionRacionalizacion is :innovacionRacionalizacion",
                InnovacionRacionalizacion.class
            )
            .setParameter("innovacionRacionalizacion", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<InnovacionRacionalizacion> fetchLineaInvestigacions(List<InnovacionRacionalizacion> proyectos) {
        return entityManager
            .createQuery(
                "select distinct innovacionRacionalizacion from InnovacionRacionalizacion innovacionRacionalizacion left join fetch innovacionRacionalizacion.lineaInvestigacion where innovacionRacionalizacion in :innovacionRacionalizacion",
                InnovacionRacionalizacion.class
            )
            .setParameter("innovacionRacionalizacion", proyectos)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
    }

    InnovacionRacionalizacion fetchOds(InnovacionRacionalizacion result) {
        return entityManager
            .createQuery(
                "select innovacionRacionalizacion from Proyectos innovacionRacionalizacion left join fetch innovacionRacionalizacion.ods where innovacionRacionalizacion is :innovacionRacionalizacion",
                InnovacionRacionalizacion.class
            )
            .setParameter("innovacionRacionalizacion", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<InnovacionRacionalizacion> fetchOds(List<InnovacionRacionalizacion> proyectos) {
        return entityManager
            .createQuery(
                "select distinct innovacionRacionalizacion from InnovacionRacionalizacion innovacionRacionalizacion left join fetch innovacionRacionalizacion.ods where innovacionRacionalizacion in :innovacionRacionalizacion",
                InnovacionRacionalizacion.class
            )
            .setParameter("innovacionRacionalizacion", proyectos)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
    }
}
