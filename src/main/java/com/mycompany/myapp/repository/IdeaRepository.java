package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Idea;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Idea entity.
 */
@Repository
public interface IdeaRepository extends IdeaRepositoryWithBagRelationships, JpaRepository<Idea, Long> {
    @Query("select idea from Idea idea where idea.user.login = ?#{principal.username}")
    List<Idea> findByUserIsCurrentUser();

    default Optional<Idea> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Idea> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Idea> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    default List<Idea> findAllWithEagerRelationshipsByRetoId(Long id, Long iduser) {
        return this.findAllWithToOneRelationshipsByRetoId(id, iduser);
    }

    default List<Idea> findAllTodosIdeasByRetos(long id) {
        return this.fetchBagRelationships(this.findAllWithToOneRelationshipsByRetoIdSoloReto(id));
    }

    @Query(
        value = "select distinct idea from Idea idea left join fetch idea.user left join fetch idea.reto left join fetch idea.tipoIdea left join fetch idea.entidad",
        countQuery = "select count(distinct idea) from Idea idea"
    )
    Page<Idea> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct idea from Idea idea left join fetch idea.user left join fetch idea.reto left join fetch idea.tipoIdea left join fetch idea.entidad"
    )
    List<Idea> findAllWithToOneRelationships();

    @Query(
        "select distinct idea from Idea idea left join fetch idea.user left join fetch idea.reto left join fetch idea.tipoIdea left join fetch idea.entidad where idea.reto.id =:id or ( idea.user.id=:iduser and idea.reto.id =:id) and idea.publica=true "
    )
    List<Idea> findAllWithToOneRelationshipsByRetoId(@Param("id") Long id, @Param("iduser") Long iduser);

    @Query(
        "select distinct idea from Idea idea left join fetch idea.user left join fetch idea.reto left join fetch idea.tipoIdea left join fetch idea.entidad where idea.reto.id =:id order by idea.id desc "
    )
    List<Idea> findAllWithToOneRelationshipsByRetoIdSoloReto(@Param("id") Long id);

    @Query(
        "select idea from Idea idea left join fetch idea.user left join fetch idea.reto left join fetch idea.tipoIdea left join fetch idea.entidad where idea.id =:id"
    )
    Optional<Idea> findOneWithToOneRelationships(@Param("id") Long id);
}
