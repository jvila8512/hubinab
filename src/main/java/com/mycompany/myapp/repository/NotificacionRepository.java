package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Notificacion;
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
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
    @Query("select notificacion from Notificacion notificacion where notificacion.user.login = ?#{principal.username}")
    List<Notificacion> findByUserIsCurrentUser();

    default Optional<Notificacion> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Notificacion> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Notificacion> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    Optional<Notificacion> findOneByUserIdAndId(Long iduser, Long idnoti);

    default List<Notificacion> findTodasNotificaciones() {
        return this.findAllNotificaciones();
    }

    default List<Notificacion> findTodasNotificacionesByUser(Long iduser) {
        return this.findAllNotificacionesbyUser(iduser);
    }

    default List<Notificacion> findTodasNotificacionesByUserVista(Long iduser) {
        return this.findAllNotificacionesbyUserByVista(iduser);
    }

    default List<Notificacion> findTodasNotificacionesByUserNoVista(Long iduser) {
        return this.findAllNotificacionesbyUserByNoVista(iduser);
    }

    default List<Notificacion> findTodasNotificacionesByUserCreada(Long idusercreada) {
        return this.findAllNotificacionesbyUsercreada(idusercreada);
    }

    @Query(
        value = "select distinct notificacion from Notificacion notificacion left join fetch notificacion.user left join fetch notificacion.usercreada",
        countQuery = "select count(distinct notificacion) from Notificacion notificacion"
    )
    Page<Notificacion> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        value = "select distinct notificacion from Notificacion notificacion left join fetch notificacion.user left join fetch notificacion.usercreada where notificacion.user.id =:iduser order by notificacion.fecha desc",
        countQuery = "select count(distinct notificacion) from Notificacion notificacion"
    )
    Page<Notificacion> findAllNotificacionesByUser(Pageable pageable, @Param("id") Long iduser);

    @Query(
        "select distinct notificacion from Notificacion notificacion left join fetch notificacion.user left join fetch notificacion.usercreada"
    )
    List<Notificacion> findAllWithToOneRelationships();

    @Query(
        "select distinct notificacion from Notificacion notificacion left join fetch notificacion.user left join fetch notificacion.usercreada order by notificacion.fecha desc"
    )
    List<Notificacion> findAllNotificaciones();

    @Query(
        "select distinct notificacion from Notificacion notificacion left join fetch notificacion.user left join fetch notificacion.usercreada where notificacion.user.id =:iduser order by notificacion.fecha desc"
    )
    List<Notificacion> findAllNotificacionesbyUser(@Param("iduser") Long iduser);

    @Query(
        "select distinct notificacion from Notificacion notificacion left join fetch notificacion.user left join fetch notificacion.usercreada where notificacion.user.id =:iduser and notificacion.visto = true order by notificacion.fecha desc"
    )
    List<Notificacion> findAllNotificacionesbyUserByVista(@Param("iduser") Long iduser);

    @Query(
        "select distinct notificacion from Notificacion notificacion left join fetch notificacion.user left join fetch notificacion.usercreada where notificacion.user.id =:iduser and notificacion.visto = false order by notificacion.fecha desc"
    )
    List<Notificacion> findAllNotificacionesbyUserByNoVista(@Param("iduser") Long iduser);

    @Query(
        "select distinct notificacion from Notificacion notificacion left join fetch notificacion.user left join fetch notificacion.usercreada where notificacion.usercreada.id =:idusercreada order by notificacion.fecha desc"
    )
    List<Notificacion> findAllNotificacionesbyUsercreada(@Param("idusercreada") Long idusercreada);

    @Query(
        "select notificacion from Notificacion notificacion left join fetch notificacion.user left join fetch notificacion.usercreada where notificacion.id =:id"
    )
    Optional<Notificacion> findOneWithToOneRelationships(@Param("id") Long id);
}
