package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.Notificacion;
import com.mycompany.myapp.domain.TipoNotificacion;
import com.mycompany.myapp.domain.User;
import java.time.Instant;

/**
 * A DTO representing a notificacion, with only the public attributes.
 */
public class NotificacionDTO {

    private Long id;

    private String descripcion;

    private Instant fecha = null;

    private Boolean visto;

    private User user;

    private User usercreada;

    private String imagenUserCreada;

    private TipoNotificacion tipoNotificacion;

    private String imagenTipoNotificacion;

    public NotificacionDTO() {}

    public NotificacionDTO(Notificacion notificacion, String imagenUserCreada, String imagenTipoNotificacion) {
        id = notificacion.getId();
        descripcion = notificacion.getDescripcion();
        fecha = notificacion.getFecha();
        visto = notificacion.getVisto();

        user = notificacion.getUser();

        usercreada = notificacion.getUsercreada();
        tipoNotificacion = notificacion.getTipoNotificacion();
        this.imagenUserCreada = imagenUserCreada;
        this.imagenTipoNotificacion = imagenTipoNotificacion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Instant getFecha() {
        return fecha;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public Boolean getVisto() {
        return visto;
    }

    public void setVisto(Boolean visto) {
        this.visto = visto;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUsercreada() {
        return usercreada;
    }

    public void setUsercreada(User usercreada) {
        this.usercreada = usercreada;
    }

    public String getImagenUserCreada() {
        return imagenUserCreada;
    }

    public void setImagenUserCreada(String imagenUserCreada) {
        this.imagenUserCreada = imagenUserCreada;
    }

    public TipoNotificacion getTipoNotificacion() {
        return tipoNotificacion;
    }

    public void setTipoNotificacion(TipoNotificacion tipoNotificacion) {
        this.tipoNotificacion = tipoNotificacion;
    }

    public String getImagenTipoNotificacion() {
        return imagenTipoNotificacion;
    }

    public void setImagenTipoNotificacion(String imagenTipoNotificacion) {
        this.imagenTipoNotificacion = imagenTipoNotificacion;
    }
}
