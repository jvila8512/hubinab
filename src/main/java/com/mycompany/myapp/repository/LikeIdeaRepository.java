package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.LikeIdea;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the LikeIdea entity.
 */
@Repository
public interface LikeIdeaRepository extends JpaRepository<LikeIdea, Long> {
    @Query("select likeIdea from LikeIdea likeIdea where likeIdea.user.login = ?#{principal.username}")
    List<LikeIdea> findByUserIsCurrentUser();

    default Optional<LikeIdea> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<LikeIdea> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<LikeIdea> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct likeIdea from LikeIdea likeIdea left join fetch likeIdea.user left join fetch likeIdea.idea",
        countQuery = "select count(distinct likeIdea) from LikeIdea likeIdea"
    )
    Page<LikeIdea> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct likeIdea from LikeIdea likeIdea left join fetch likeIdea.user left join fetch likeIdea.idea")
    List<LikeIdea> findAllWithToOneRelationships();

    @Query(
        "select distinct likeIdea from LikeIdea likeIdea left join fetch likeIdea.user left join fetch likeIdea.idea where likeIdea.idea.id =:id "
    )
    List<LikeIdea> findAllByIdea(@Param("id") Long id);

    @Query("select likeIdea from LikeIdea likeIdea left join fetch likeIdea.user left join fetch likeIdea.idea where likeIdea.id =:id")
    Optional<LikeIdea> findOneWithToOneRelationships(@Param("id") Long id);
}
