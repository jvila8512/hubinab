package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Noticias;
import com.mycompany.myapp.domain.Reto;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Noticias entity.
 */
@Repository
public interface NoticiasRepository extends JpaRepository<Noticias, Long> {
    @Query("select noticias from Noticias noticias where noticias.user.login = ?#{principal.username}")
    List<Noticias> findByUserIsCurrentUser();

    default Optional<Noticias> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Noticias> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default List<Noticias> findTodosByIdEcosistemabyIdUser(Long id, Long iduser) {
        return this.findAllByNoticiaByIdEcosistemabyIdUser(id, iduser);
    }

    default List<Noticias> findTodosByIdEcosistemabyIdUserSolo(Long id, Long iduser) {
        return this.findAllByNoticiaByIdEcosistemabyIdUserSolo(id, iduser);
    }

    default Page<Noticias> findTodosByIdEcosistemabyIdUser_Paginado(Pageable pageable, Long id) {
        return this.findAllByNoticiaByIdEcosistemabyIdUserPaginado(pageable, id);
    }

    default Long contarRE(Long id) {
        return this.contar(id);
    }

    @Query(value = "select count(distinct noticias) from Noticias noticias where noticias.ecosistema.id =:id")
    Long contar(@Param("id") Long id);

    default Page<Noticias> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct noticias from Noticias noticias left join fetch noticias.user left join fetch noticias.ecosistema left join fetch noticias.tipoNoticia",
        countQuery = "select count(distinct noticias) from Noticias noticias"
    )
    Page<Noticias> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct noticias from Noticias noticias left join fetch noticias.user left join fetch noticias.ecosistema left join fetch noticias.tipoNoticia"
    )
    List<Noticias> findAllWithToOneRelationships();

    @Query(
        "select noticias from Noticias noticias left join fetch noticias.user left join fetch noticias.ecosistema left join fetch noticias.tipoNoticia where noticias.id =:id"
    )
    Optional<Noticias> findOneWithToOneRelationships(@Param("id") Long id);

    @Query(
        "select noticias from Noticias noticias left join fetch noticias.user left join fetch noticias.ecosistema left join fetch noticias.tipoNoticia where noticias.publicar =true and noticias.publica = true"
    )
    List<Noticias> findOneWithToOneRelationshipsByPublicar();

    @Query(
        value = "select noticias from Noticias noticias left join fetch noticias.user left join fetch noticias.ecosistema left join fetch noticias.tipoNoticia where noticias.ecosistema.id =:id and noticias.publica =true ",
        countQuery = "select count(distinct noticias) from Noticias noticias"
    )
    Page<Noticias> findAllByNoticiaByIdEcosistemabyIdUserPaginado(Pageable pageable, @Param("id") Long id);

    @Query(
        "select noticias from Noticias noticias left join fetch noticias.user left join fetch noticias.ecosistema left join fetch noticias.tipoNoticia where noticias.ecosistema.id =:id and noticias.publica =true or (noticias.user.id =:iduser and noticias.ecosistema.id =:id)  "
    )
    List<Noticias> findAllByNoticiaByIdEcosistemabyIdUser(@Param("id") Long id, @Param("iduser") Long iduser);

    @Query(
        "select noticias from Noticias noticias left join fetch noticias.user left join fetch noticias.ecosistema left join fetch noticias.tipoNoticia where noticias.ecosistema.id =:id and noticias.user.id =:iduser order by noticias.fechaCreada desc"
    )
    List<Noticias> findAllByNoticiaByIdEcosistemabyIdUserSolo(@Param("id") Long id, @Param("iduser") Long iduser);

    @Query(
        "select noticias from Noticias noticias left join fetch noticias.user left join fetch noticias.ecosistema left join fetch noticias.tipoNoticia where noticias.ecosistema.id =:id  and noticias.publica = true"
    )
    List<Noticias> findAllWithToOneRelationshipsByPublicaByEcositema(@Param("id") Long id);

    List<Noticias> findAllByPublicaTrueAndEcosistemaIdInOrderByEcosistemaIdDesc(Collection<Long> ecosistemas);
}
