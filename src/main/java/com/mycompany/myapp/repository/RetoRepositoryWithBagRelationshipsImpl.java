package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Reto;
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
public class RetoRepositoryWithBagRelationshipsImpl implements RetoRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Reto> fetchBagRelationships(Optional<Reto> usuarioEcosistema) {
        return usuarioEcosistema.map(this::fetchEcosistemas);
    }

    @Override
    public Page<Reto> fetchBagRelationships(Page<Reto> usuarioEcosistemas) {
        return new PageImpl<>(
            fetchBagRelationships(usuarioEcosistemas.getContent()),
            usuarioEcosistemas.getPageable(),
            usuarioEcosistemas.getTotalElements()
        );
    }

    @Override
    public List<Reto> fetchBagRelationships(List<Reto> usuarioEcosistemas) {
        return Optional.of(usuarioEcosistemas).map(this::fetchEcosistemas).orElse(Collections.emptyList());
    }

    Reto fetchEcosistemas(Reto result) {
        return entityManager
            .createQuery("select reto from Reto reto left join fetch reto.ideas where reto is :reto", Reto.class)
            .setParameter("reto", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Reto> fetchEcosistemas(List<Reto> reto) {
        return entityManager
            .createQuery("select distinct reto from Reto reto left join fetch reto.ideas where reto in :reto", Reto.class)
            .setParameter("reto", reto)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
    }
}
