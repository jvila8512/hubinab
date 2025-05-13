package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Proyectos;
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
 * Spring Data SQL repository for the Proyectos entity.
 */
@Repository
public interface ProyectosRepository extends ProyectosRepositoryWithBagRelationships, JpaRepository<Proyectos, Long> {
    @Query("select proyectos from Proyectos proyectos where proyectos.user.login = ?#{principal.username}")
    List<Proyectos> findByUserIsCurrentUser();

    default Optional<Proyectos> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findOneWithToOneRelationships(id));
    }

    default List<Proyectos> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships());
    }

    default Long contarRE(Long id) {
        return this.contar(id);
    }

    default List<Proyectos> findAllTodos() {
        return this.fetchBagRelationships(this.findAll());
    }

    List<Proyectos> findAllByEcosistemaIdInOrderByEcosistemaIdDesc(Collection<Long> ecosistemas);

    default List<Proyectos> findAllWithEagerRelationshipsByEcosistema(@Param("id") Long id) {
        return this.fetchBagRelationships(this.findAllWithToOneRelationshipsByEcosistemaId(id));
    }

    default Page<Proyectos> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships(pageable));
    }

    @Query(
        value = "select distinct proyectos from Proyectos proyectos left join fetch proyectos.user left join fetch proyectos.ecosistema left join fetch proyectos.tipoProyecto",
        countQuery = "select count(distinct proyectos) from Proyectos proyectos"
    )
    Page<Proyectos> findAllWithToOneRelationships(Pageable pageable);

    @Query(value = "select count(distinct proyectos) from Proyectos proyectos where proyectos.ecosistema.id =:id")
    Long contar(@Param("id") Long id);

    @Query(
        "select distinct proyectos from Proyectos proyectos left join fetch proyectos.user left join fetch proyectos.ecosistema left join fetch proyectos.tipoProyecto"
    )
    List<Proyectos> findAllWithToOneRelationships();

    @Query(
        "select distinct proyectos from Proyectos proyectos left join fetch proyectos.user left join fetch proyectos.ecosistema left join fetch proyectos.tipoProyecto where proyectos.ecosistema.id =:id"
    )
    List<Proyectos> findAllWithToOneRelationshipsByEcosistemaId(@Param("id") Long id);

    @Query(
        "select proyectos from Proyectos proyectos left join fetch proyectos.user left join fetch proyectos.ecosistema left join fetch proyectos.tipoProyecto where proyectos.id =:id"
    )
    Optional<Proyectos> findOneWithToOneRelationships(@Param("id") Long id);
}
