package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Ecosistema;
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
public class EcosistemaRepositoryWithBagRelationshipsImpl implements EcosistemaRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Ecosistema> fetchBagRelationships(Optional<Ecosistema> ecosistema) {
        return ecosistema.map(this::fetchUsers);
    }

    @Override
    public Page<Ecosistema> fetchBagRelationships(Page<Ecosistema> ecosistemas) {
        return new PageImpl<>(fetchBagRelationships(ecosistemas.getContent()), ecosistemas.getPageable(), ecosistemas.getTotalElements());
    }

    @Override
    public List<Ecosistema> fetchBagRelationships(List<Ecosistema> ecosistemas) {
        return Optional.of(ecosistemas).map(this::fetchUsers).orElse(Collections.emptyList());
    }

    Ecosistema fetchUsers(Ecosistema result) {
        return entityManager
            .createQuery(
                "select ecosistema from Ecosistema ecosistema left join fetch ecosistema.users where ecosistema is :ecosistema",
                Ecosistema.class
            )
            .setParameter("ecosistema", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Ecosistema> fetchUsers(List<Ecosistema> ecosistemas) {
        return entityManager
            .createQuery(
                "select distinct ecosistema from Ecosistema ecosistema left join fetch ecosistema.users where ecosistema in :ecosistemas",
                Ecosistema.class
            )
            .setParameter("ecosistemas", ecosistemas)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
    }
}
