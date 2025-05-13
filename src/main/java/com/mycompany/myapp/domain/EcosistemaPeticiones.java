package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A EcosistemaPeticiones.
 */
@Entity
@Table(name = "ecosistema_peticiones")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EcosistemaPeticiones implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "motivo", nullable = false)
    private String motivo;

    @NotNull
    @Column(name = "fecha_peticion", nullable = false)
    private LocalDate fechaPeticion;

    @Column(name = "aprobada")
    private Boolean aprobada;

    @ManyToOne
    private User user;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "retos",
            "proyectos",
            "ecosistemaPeticiones",
            "noticias",
            "redesUrls",
            "aniristas",
            "ecosistemaComponentes",
            "users",
            "ecosistemaRol",
            "usurioecosistemas",
        },
        allowSetters = true
    )
    private Ecosistema ecosistema;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EcosistemaPeticiones id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMotivo() {
        return this.motivo;
    }

    public EcosistemaPeticiones motivo(String motivo) {
        this.setMotivo(motivo);
        return this;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public LocalDate getFechaPeticion() {
        return this.fechaPeticion;
    }

    public EcosistemaPeticiones fechaPeticion(LocalDate fechaPeticion) {
        this.setFechaPeticion(fechaPeticion);
        return this;
    }

    public void setFechaPeticion(LocalDate fechaPeticion) {
        this.fechaPeticion = fechaPeticion;
    }

    public Boolean getAprobada() {
        return this.aprobada;
    }

    public EcosistemaPeticiones aprobada(Boolean aprobada) {
        this.setAprobada(aprobada);
        return this;
    }

    public void setAprobada(Boolean aprobada) {
        this.aprobada = aprobada;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public EcosistemaPeticiones user(User user) {
        this.setUser(user);
        return this;
    }

    public Ecosistema getEcosistema() {
        return this.ecosistema;
    }

    public void setEcosistema(Ecosistema ecosistema) {
        this.ecosistema = ecosistema;
    }

    public EcosistemaPeticiones ecosistema(Ecosistema ecosistema) {
        this.setEcosistema(ecosistema);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EcosistemaPeticiones)) {
            return false;
        }
        return id != null && id.equals(((EcosistemaPeticiones) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EcosistemaPeticiones{" +
            "id=" + getId() +
            ", motivo='" + getMotivo() + "'" +
            ", fechaPeticion='" + getFechaPeticion() + "'" +
            ", aprobada='" + getAprobada() + "'" +
            "}";
    }
}
