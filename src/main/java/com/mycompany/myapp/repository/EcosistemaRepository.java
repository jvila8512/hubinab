package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Ecosistema;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Ecosistema entity.
 */
@Repository
public interface EcosistemaRepository extends EcosistemaRepositoryWithBagRelationships, JpaRepository<Ecosistema, Long> {
    default Optional<Ecosistema> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findOneWithToOneRelationships(id));
    }

    default List<Ecosistema> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships());
    }

    default List<Ecosistema> findAllWithEagerRelationshipsByActivo() {
        return this.fetchBagRelationships(this.findAllWithToOneRelationshipsbyActivo());
    }

    default Page<Ecosistema> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships(pageable));
    }

    @Query(
        value = "select distinct ecosistema from Ecosistema ecosistema left join fetch ecosistema.ecosistemaRol",
        countQuery = "select count(distinct ecosistema) from Ecosistema ecosistema"
    )
    Page<Ecosistema> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct ecosistema from Ecosistema ecosistema left join fetch ecosistema.ecosistemaRol")
    List<Ecosistema> findAllWithToOneRelationships();

    @Query("select distinct ecosistema from Ecosistema ecosistema  left join fetch ecosistema.ecosistemaRol where ecosistema.activo = true")
    List<Ecosistema> findAllWithToOneRelationshipsbyActivo();

    @Query("select ecosistema from Ecosistema ecosistema left join fetch ecosistema.ecosistemaRol where ecosistema.id =:id")
    Optional<Ecosistema> findOneWithToOneRelationships(@Param("id") Long id);

    @Query(
        "SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM Ecosistema e LEFT JOIN e.users u WHERE u.id = :userId OR e.user.id = :userId"
    )
    boolean existsByUserOrInUsers(@Param("userId") Long userId);
}
