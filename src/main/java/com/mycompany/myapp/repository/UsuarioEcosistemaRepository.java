package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.UsuarioEcosistema;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the UsuarioEcosistema entity.
 */
@Repository
public interface UsuarioEcosistemaRepository
    extends UsuarioEcosistemaRepositoryWithBagRelationships, JpaRepository<UsuarioEcosistema, Long> {
    @Query("select usuarioEcosistema from UsuarioEcosistema usuarioEcosistema where usuarioEcosistema.user.login = ?#{principal.username}")
    List<UsuarioEcosistema> findByUserIsCurrentUser();

    default Optional<UsuarioEcosistema> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findOneWithToOneRelationships(id));
    }

    Optional<UsuarioEcosistema> findByUserId(Long id);

    default List<UsuarioEcosistema> todos() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Optional<UsuarioEcosistema> findByUserIdWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findByUserId(id));
    }

    default List<UsuarioEcosistema> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships());
    }

    default Page<UsuarioEcosistema> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships(pageable));
    }

    @Query(
        value = "select distinct usuarioEcosistema from UsuarioEcosistema usuarioEcosistema left join fetch usuarioEcosistema.user",
        countQuery = "select count(distinct usuarioEcosistema) from UsuarioEcosistema usuarioEcosistema"
    )
    Page<UsuarioEcosistema> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct usuarioEcosistema from UsuarioEcosistema usuarioEcosistema left join fetch usuarioEcosistema.user")
    List<UsuarioEcosistema> findAllWithToOneRelationships();

    @Query(
        "select distinct usuarioEcosistema from UsuarioEcosistema usuarioEcosistema left join fetch usuarioEcosistema.user where usuarioEcosistema.user.id=: id"
    )
    List<UsuarioEcosistema> findAllWithToOneRelationshipsUSerId(@Param("id") Long id);

    @Query(
        "select usuarioEcosistema from UsuarioEcosistema usuarioEcosistema left join fetch usuarioEcosistema.user where usuarioEcosistema.id =:id"
    )
    Optional<UsuarioEcosistema> findOneWithToOneRelationships(@Param("id") Long id);
}
