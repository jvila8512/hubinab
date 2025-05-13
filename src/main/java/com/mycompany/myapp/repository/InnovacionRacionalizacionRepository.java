package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.InnovacionRacionalizacion;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the InnovacionRacionalizacion entity.
 */
@Repository
public interface InnovacionRacionalizacionRepository
    extends InnovacionRepositoryWithBagRelationships, JpaRepository<InnovacionRacionalizacion, Long> {
    default Optional<InnovacionRacionalizacion> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<InnovacionRacionalizacion> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships());
    }

    default List<InnovacionRacionalizacion> findAllbyPublica() {
        return this.fetchBagRelationships(this.findAllWithToOneRelationshipsByPublica());
    }

    default List<InnovacionRacionalizacion> findAllbyPublicaByUserid(Long iduser) {
        return this.fetchBagRelationships(this.findAllWithToOneRelationshipsByPublicaByUser(iduser));
    }

    default Page<InnovacionRacionalizacion> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct innovacionRacionalizacion from InnovacionRacionalizacion innovacionRacionalizacion left join fetch innovacionRacionalizacion.tipoIdea",
        countQuery = "select count(distinct innovacionRacionalizacion) from InnovacionRacionalizacion innovacionRacionalizacion"
    )
    Page<InnovacionRacionalizacion> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct innovacionRacionalizacion from InnovacionRacionalizacion innovacionRacionalizacion left join fetch innovacionRacionalizacion.tipoIdea"
    )
    List<InnovacionRacionalizacion> findAllWithToOneRelationships();

    @Query(
        "select distinct innovacionRacionalizacion from InnovacionRacionalizacion innovacionRacionalizacion left join fetch innovacionRacionalizacion.tipoIdea where innovacionRacionalizacion.publico =true "
    )
    List<InnovacionRacionalizacion> findAllWithToOneRelationshipsByPublica();

    @Query(
        "select distinct innovacionRacionalizacion from InnovacionRacionalizacion innovacionRacionalizacion left join fetch innovacionRacionalizacion.tipoIdea where innovacionRacionalizacion.publico =true  or  innovacionRacionalizacion.user.id=:iduser "
    )
    List<InnovacionRacionalizacion> findAllWithToOneRelationshipsByPublicaByUser(@Param("iduser") Long iduser);

    @Query(
        "select innovacionRacionalizacion from InnovacionRacionalizacion innovacionRacionalizacion left join fetch innovacionRacionalizacion.tipoIdea where innovacionRacionalizacion.id =:id"
    )
    Optional<InnovacionRacionalizacion> findOneWithToOneRelationships(@Param("id") Long id);
}
