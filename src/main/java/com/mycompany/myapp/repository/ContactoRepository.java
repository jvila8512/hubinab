package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Contacto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Contacto entity.
 */
@Repository
public interface ContactoRepository extends JpaRepository<Contacto, Long> {
    default Optional<Contacto> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Contacto> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Contacto> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct contacto from Contacto contacto left join fetch contacto.tipoContacto",
        countQuery = "select count(distinct contacto) from Contacto contacto"
    )
    Page<Contacto> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct contacto from Contacto contacto left join fetch contacto.tipoContacto")
    List<Contacto> findAllWithToOneRelationships();

    @Query("select contacto from Contacto contacto left join fetch contacto.tipoContacto where contacto.id =:id")
    Optional<Contacto> findOneWithToOneRelationships(@Param("id") Long id);
}
