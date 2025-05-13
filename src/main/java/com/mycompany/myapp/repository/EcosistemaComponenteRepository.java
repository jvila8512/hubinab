package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.EcosistemaComponente;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the EcosistemaComponente entity.
 */
@Repository
public interface EcosistemaComponenteRepository extends JpaRepository<EcosistemaComponente, Long> {
    default Optional<EcosistemaComponente> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<EcosistemaComponente> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default List<EcosistemaComponente> findAllWithComponentesByEcosistema(Long id) {
        return this.findAllWithToComponentesByEcosistemas(id);
    }

    default Page<EcosistemaComponente> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct ecosistemaComponente from EcosistemaComponente ecosistemaComponente left join fetch ecosistemaComponente.componente left join fetch ecosistemaComponente.ecosistema",
        countQuery = "select count(distinct ecosistemaComponente) from EcosistemaComponente ecosistemaComponente"
    )
    Page<EcosistemaComponente> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct ecosistemaComponente from EcosistemaComponente ecosistemaComponente left join fetch ecosistemaComponente.componente left join fetch ecosistemaComponente.ecosistema"
    )
    List<EcosistemaComponente> findAllWithToOneRelationships();

    @Query(
        "select distinct ecosistemaComponente from EcosistemaComponente ecosistemaComponente left join fetch ecosistemaComponente.componente left join fetch ecosistemaComponente.ecosistema where ecosistemaComponente.ecosistema.id =:id"
    )
    List<EcosistemaComponente> findAllWithToComponentesByEcosistemas(@Param("id") Long id);

    @Query(
        "select ecosistemaComponente from EcosistemaComponente ecosistemaComponente left join fetch ecosistemaComponente.componente left join fetch ecosistemaComponente.ecosistema where ecosistemaComponente.id =:id"
    )
    Optional<EcosistemaComponente> findOneWithToOneRelationships(@Param("id") Long id);
}
