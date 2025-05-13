package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ContactoChangeMacker;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ContactoChangeMacker entity.
 */
@Repository
public interface ContactoChangeMackerRepository extends JpaRepository<ContactoChangeMacker, Long> {
    default Optional<ContactoChangeMacker> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<ContactoChangeMacker> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<ContactoChangeMacker> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct contactoChangeMacker from ContactoChangeMacker contactoChangeMacker left join fetch contactoChangeMacker.changeMacker",
        countQuery = "select count(distinct contactoChangeMacker) from ContactoChangeMacker contactoChangeMacker"
    )
    Page<ContactoChangeMacker> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct contactoChangeMacker from ContactoChangeMacker contactoChangeMacker left join fetch contactoChangeMacker.changeMacker"
    )
    List<ContactoChangeMacker> findAllWithToOneRelationships();

    @Query(
        "select contactoChangeMacker from ContactoChangeMacker contactoChangeMacker left join fetch contactoChangeMacker.changeMacker where contactoChangeMacker.id =:id"
    )
    Optional<ContactoChangeMacker> findOneWithToOneRelationships(@Param("id") Long id);
}
