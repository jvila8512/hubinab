package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ComenetariosIdea;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ComenetariosIdea entity.
 */
@Repository
public interface ComenetariosIdeaRepository extends JpaRepository<ComenetariosIdea, Long> {
    @Query("select comenetariosIdea from ComenetariosIdea comenetariosIdea where comenetariosIdea.user.login = ?#{principal.username}")
    List<ComenetariosIdea> findByUserIsCurrentUser();

    default Optional<ComenetariosIdea> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<ComenetariosIdea> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default List<ComenetariosIdea> findAllWithEagerRelationshipsByIdIdea(Long id) {
        return this.findAllWithToOneRelationshipsByIdIdea(id);
    }

    default Page<ComenetariosIdea> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct comenetariosIdea from ComenetariosIdea comenetariosIdea left join fetch comenetariosIdea.user left join fetch comenetariosIdea.idea",
        countQuery = "select count(distinct comenetariosIdea) from ComenetariosIdea comenetariosIdea"
    )
    Page<ComenetariosIdea> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct comenetariosIdea from ComenetariosIdea comenetariosIdea left join fetch comenetariosIdea.user left join fetch comenetariosIdea.idea"
    )
    List<ComenetariosIdea> findAllWithToOneRelationships();

    @Query(
        "select distinct comenetariosIdea from ComenetariosIdea comenetariosIdea left join fetch comenetariosIdea.user left join fetch comenetariosIdea.idea where comenetariosIdea.idea.id =:id"
    )
    List<ComenetariosIdea> findAllWithToOneRelationshipsByIdIdea(@Param("id") Long id);

    @Query(
        "select comenetariosIdea from ComenetariosIdea comenetariosIdea left join fetch comenetariosIdea.user left join fetch comenetariosIdea.idea where comenetariosIdea.id =:id"
    )
    Optional<ComenetariosIdea> findOneWithToOneRelationships(@Param("id") Long id);
}
