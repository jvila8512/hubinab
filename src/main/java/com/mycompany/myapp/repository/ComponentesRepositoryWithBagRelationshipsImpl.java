package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Componentes;
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
public class ComponentesRepositoryWithBagRelationshipsImpl implements ComponentesRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Componentes> fetchBagRelationships(Optional<Componentes> componentes) {
        return componentes.map(this::fetchEcosistemas);
    }

    @Override
    public Page<Componentes> fetchBagRelationships(Page<Componentes> componentes) {
        return new PageImpl<>(fetchBagRelationships(componentes.getContent()), componentes.getPageable(), componentes.getTotalElements());
    }

    @Override
    public List<Componentes> fetchBagRelationships(List<Componentes> componentes) {
        return Optional.of(componentes).map(this::fetchEcosistemas).orElse(Collections.emptyList());
    }

    Componentes fetchEcosistemas(Componentes result) {
        return entityManager
            .createQuery(
                "select componentes from Componentes componentes left join fetch componentes.ecosistemas where componentes is :componentes",
                Componentes.class
            )
            .setParameter("componentes", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Componentes> fetchEcosistemas(List<Componentes> componentes) {
        return entityManager
            .createQuery(
                "select distinct componentes from Componentes componentes left join fetch componentes.ecosistemas where componentes in :componentes",
                Componentes.class
            )
            .setParameter("componentes", componentes)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
    }
}
