package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Reto;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Reto entity.
 */
@Repository
public interface RetoRepository extends RetoRepositoryWithBagRelationships, JpaRepository<Reto, Long> {
    @Query("select reto from Reto reto where reto.user.login = ?#{principal.username}")
    List<Reto> findByUserIsCurrentUser();

    default Optional<Reto> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Reto> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Reto> findAllWithEagerRelationshipsByIdEcosistemaIdeas1(Pageable pageable, Long id) {
        return this.fetchBagRelationships(this.findAllWithRelationshipsByIdEcosistemas1(pageable, id));
    }

    default List<Reto> findAllWithEagerRelationshipsByIdEcosistemaIdeas(Long id) {
        return this.fetchBagRelationships(this.findAllWithRelationshipsByIdEcosistemasByActivos(id));
    }

    default List<Reto> findAllWithEagerRelationshipsByIdEcosistemaIdeasTodos(Long id) {
        return this.fetchBagRelationships(this.findAllWithRelationshipsByIdEcosistemasTodos(id));
    }

    List<Reto> findAllByEcosistemaIdOrderByFechaInicioDescActivoDesc(Long id);

    List<Reto> findAllByEcosistemaIdAndPublicoTrueOrderByFechaInicioDesc(Long id);

    List<Reto> findAllByPublicoTrueOrderByFechaInicioDesc();

    List<Reto> findAllByActivoTrueAndEcosistemaIdInOrderByEcosistemaIdDesc(Collection<Long> ecosistemas);

    default List<Reto> findAllWithEagerRelationshipsByIdEcosistemaByIdUser(Long id, Long iduser) {
        return this.fetchBagRelationships(this.findAllWithRelationshipsByIdEcosistemasbyIdUser(id, iduser));
    }

    default Long contarRE(Long id) {
        return this.contar(id);
    }

    default Page<Reto> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct reto from Reto reto left join fetch reto.user left join fetch reto.ecosistema",
        countQuery = "select count(distinct reto) from Reto reto"
    )
    Page<Reto> findAllWithToOneRelationships(Pageable pageable);

    @Query(value = "select count(distinct reto) from Reto reto where reto.ecosistema.id =:id")
    Long contar(@Param("id") Long id);

    @Query("select distinct reto from Reto reto left join fetch reto.user left join fetch reto.ecosistema")
    List<Reto> findAllWithToOneRelationships();

    @Query("select reto from Reto reto left join fetch reto.user left join fetch reto.ecosistema where reto.id =:id")
    Optional<Reto> findOneWithToOneRelationships(@Param("id") Long id);

    @Query(
        value = "select reto from Reto reto left join fetch reto.user left join fetch reto.ecosistema where reto.ecosistema.id =:id and reto.activo=true",
        countQuery = "select count(distinct reto) from Reto reto where reto.ecosistema.id =:id"
    )
    Page<Reto> findAllWithRelationshipsByIdEcosistemas1(Pageable pageable, @Param("id") Long id);

    @Query(
        "select reto from Reto reto left join fetch reto.user left join fetch reto.ecosistema where reto.ecosistema.id =:id and (reto.activo = true or reto.publico= true) ORDER BY reto.fechaInicio DESC"
    )
    List<Reto> findAllWithRelationshipsByIdEcosistemasByActivos(@Param("id") Long id);

    @Query(
        "select reto from Reto reto left join fetch reto.user left join fetch reto.ecosistema where reto.ecosistema.id =:id  ORDER BY  reto.fechaInicio DESC"
    )
    List<Reto> findAllWithRelationshipsByIdEcosistemasTodos(@Param("id") Long id);

    @Query(
        "select reto from Reto reto left join fetch reto.user left join fetch reto.ecosistema where reto.ecosistema.id =:id and reto.user.id =:iduser  order by reto.fechaInicio asc"
    )
    List<Reto> findAllWithRelationshipsByIdEcosistemasbyIdUser(@Param("id") Long id, @Param("iduser") Long iduser);

    @Query("select reto from Reto reto left join fetch reto.user left join fetch reto.ecosistema where reto.user.id =:id")
    List<Reto> findAllWithRelationshipsByIdUsuario(@Param("id") Long id);
}
