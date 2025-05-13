package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TipoNoticia.
 */
@Entity
@Table(name = "tipo_notificacion")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TipoNotificacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "tipo_notificacion", nullable = false)
    private String tipoNotificacion;

    @NotNull
    @Column(name = "icono", nullable = false)
    private String icono;

    @OneToMany(mappedBy = "tipoNotificacion")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "user", "tipoNotificacion" }, allowSetters = true)
    private Set<Notificacion> notificacion = new HashSet<>();

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Notificacion> getNotificacion() {
        return notificacion;
    }

    public void setNotificacion(Set<Notificacion> notificacion) {
        this.notificacion = notificacion;
    }

    public String getTipoNotificacion() {
        return tipoNotificacion;
    }

    public void setTipoNotificacion(String tipoNotificacion) {
        this.tipoNotificacion = tipoNotificacion;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }
}
