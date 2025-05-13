package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Anirista;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Anirista entity.
 */
@Repository
public interface AniristaRepository extends JpaRepository<Anirista, Long> {
    default Optional<Anirista> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Anirista> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Anirista> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct anirista from Anirista anirista left join fetch anirista.ecosistema",
        countQuery = "select count(distinct anirista) from Anirista anirista"
    )
    Page<Anirista> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct anirista from Anirista anirista left join fetch anirista.ecosistema")
    List<Anirista> findAllWithToOneRelationships();

    @Query("select anirista from Anirista anirista left join fetch anirista.ecosistema where anirista.id =:id")
    Optional<Anirista> findOneWithToOneRelationships(@Param("id") Long id);
}
