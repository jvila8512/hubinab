package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.EcosistemaPeticiones;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the EcosistemaPeticiones entity.
 */
@Repository
public interface EcosistemaPeticionesRepository extends JpaRepository<EcosistemaPeticiones, Long> {
    @Query(
        "select ecosistemaPeticiones from EcosistemaPeticiones ecosistemaPeticiones where ecosistemaPeticiones.user.login = ?#{principal.username}"
    )
    List<EcosistemaPeticiones> findByUserIsCurrentUser();

    default Optional<EcosistemaPeticiones> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<EcosistemaPeticiones> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<EcosistemaPeticiones> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct ecosistemaPeticiones from EcosistemaPeticiones ecosistemaPeticiones left join fetch ecosistemaPeticiones.user left join fetch ecosistemaPeticiones.ecosistema",
        countQuery = "select count(distinct ecosistemaPeticiones) from EcosistemaPeticiones ecosistemaPeticiones"
    )
    Page<EcosistemaPeticiones> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct ecosistemaPeticiones from EcosistemaPeticiones ecosistemaPeticiones left join fetch ecosistemaPeticiones.user left join fetch ecosistemaPeticiones.ecosistema"
    )
    List<EcosistemaPeticiones> findAllWithToOneRelationships();

    @Query(
        "select ecosistemaPeticiones from EcosistemaPeticiones ecosistemaPeticiones left join fetch ecosistemaPeticiones.user left join fetch ecosistemaPeticiones.ecosistema where ecosistemaPeticiones.id =:id"
    )
    Optional<EcosistemaPeticiones> findOneWithToOneRelationships(@Param("id") Long id);
}
