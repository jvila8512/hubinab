package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Noti;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Noti entity.
 */
@Repository
public interface NotiRepository extends JpaRepository<Noti, Long> {
    @Query("select noti from Noti noti where noti.user.login = ?#{principal.username}")
    List<Noti> findByUserIsCurrentUser();

    default Optional<Noti> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Noti> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Noti> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct noti from Noti noti left join fetch noti.user",
        countQuery = "select count(distinct noti) from Noti noti"
    )
    Page<Noti> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct noti from Noti noti left join fetch noti.user")
    List<Noti> findAllWithToOneRelationships();

    @Query("select noti from Noti noti left join fetch noti.user where noti.id =:id")
    Optional<Noti> findOneWithToOneRelationships(@Param("id") Long id);
}
