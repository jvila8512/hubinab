package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Idea;
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
public class IdeaRepositoryWithBagRelationshipsImpl implements IdeaRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Idea> fetchBagRelationships(Optional<Idea> componentes) {
        return componentes.map(this::fetchEcosistemas);
    }

    @Override
    public Page<Idea> fetchBagRelationships(Page<Idea> componentes) {
        return new PageImpl<>(fetchBagRelationships(componentes.getContent()), componentes.getPageable(), componentes.getTotalElements());
    }

    @Override
    public List<Idea> fetchBagRelationships(List<Idea> componentes) {
        return Optional.of(componentes).map(this::fetchEcosistemas).orElse(Collections.emptyList());
    }

    Idea fetchEcosistemas(Idea result) {
        return entityManager
            .createQuery("select idea from Idea idea left join fetch idea.likes where idea is :idea", Idea.class)
            .setParameter("idea", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Idea> fetchEcosistemas(List<Idea> componentes) {
        return entityManager
            .createQuery("select distinct idea from Idea idea left join fetch idea.likes where idea in :idea", Idea.class)
            .setParameter("idea", componentes)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
    }
}
