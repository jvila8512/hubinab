package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.UsuarioEcosistema;
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
public class UsuarioEcosistemaRepositoryWithBagRelationshipsImpl implements UsuarioEcosistemaRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<UsuarioEcosistema> fetchBagRelationships(Optional<UsuarioEcosistema> usuarioEcosistema) {
        return usuarioEcosistema.map(this::fetchEcosistemas);
    }

    @Override
    public Page<UsuarioEcosistema> fetchBagRelationships(Page<UsuarioEcosistema> usuarioEcosistemas) {
        return new PageImpl<>(
            fetchBagRelationships(usuarioEcosistemas.getContent()),
            usuarioEcosistemas.getPageable(),
            usuarioEcosistemas.getTotalElements()
        );
    }

    @Override
    public List<UsuarioEcosistema> fetchBagRelationships(List<UsuarioEcosistema> usuarioEcosistemas) {
        return Optional.of(usuarioEcosistemas).map(this::fetchEcosistemas).orElse(Collections.emptyList());
    }

    UsuarioEcosistema fetchEcosistemas(UsuarioEcosistema result) {
        return entityManager
            .createQuery(
                "select usuarioEcosistema from UsuarioEcosistema usuarioEcosistema left join fetch usuarioEcosistema.ecosistemas where usuarioEcosistema is :usuarioEcosistema",
                UsuarioEcosistema.class
            )
            .setParameter("usuarioEcosistema", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<UsuarioEcosistema> fetchEcosistemas(List<UsuarioEcosistema> usuarioEcosistemas) {
        return entityManager
            .createQuery(
                "select distinct usuarioEcosistema from UsuarioEcosistema usuarioEcosistema left join fetch usuarioEcosistema.ecosistemas where usuarioEcosistema in :usuarioEcosistemas",
                UsuarioEcosistema.class
            )
            .setParameter("usuarioEcosistemas", usuarioEcosistemas)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
    }
}
