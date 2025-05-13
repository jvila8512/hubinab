package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Participantes;
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
public class ParticipantesRepositoryWithBagRelationshipsImpl implements ParticipantesRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Participantes> fetchBagRelationships(Optional<Participantes> participantes) {
        return participantes.map(this::fetchProyectos);
    }

    @Override
    public Page<Participantes> fetchBagRelationships(Page<Participantes> participantes) {
        return new PageImpl<>(
            fetchBagRelationships(participantes.getContent()),
            participantes.getPageable(),
            participantes.getTotalElements()
        );
    }

    @Override
    public List<Participantes> fetchBagRelationships(List<Participantes> participantes) {
        return Optional.of(participantes).map(this::fetchProyectos).orElse(Collections.emptyList());
    }

    Participantes fetchProyectos(Participantes result) {
        return entityManager
            .createQuery(
                "select participantes from Participantes participantes left join fetch participantes.proyectos where participantes is :participantes",
                Participantes.class
            )
            .setParameter("participantes", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Participantes> fetchProyectos(List<Participantes> participantes) {
        return entityManager
            .createQuery(
                "select distinct participantes from Participantes participantes left join fetch participantes.proyectos where participantes in :participantes",
                Participantes.class
            )
            .setParameter("participantes", participantes)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
    }
}
