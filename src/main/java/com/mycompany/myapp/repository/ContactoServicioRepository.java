package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ContactoServicio;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ContactoServicio entity.
 */
@Repository
public interface ContactoServicioRepository extends JpaRepository<ContactoServicio, Long> {
    default Optional<ContactoServicio> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<ContactoServicio> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<ContactoServicio> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct contactoServicio from ContactoServicio contactoServicio left join fetch contactoServicio.servicios",
        countQuery = "select count(distinct contactoServicio) from ContactoServicio contactoServicio"
    )
    Page<ContactoServicio> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct contactoServicio from ContactoServicio contactoServicio left join fetch contactoServicio.servicios")
    List<ContactoServicio> findAllWithToOneRelationships();

    @Query(
        "select contactoServicio from ContactoServicio contactoServicio left join fetch contactoServicio.servicios where contactoServicio.id =:id"
    )
    Optional<ContactoServicio> findOneWithToOneRelationships(@Param("id") Long id);
}
