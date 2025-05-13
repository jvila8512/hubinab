package com.mycompany.myapp.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Comunidad.
 */
@Entity
@Table(name = "comunidad")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Comunidad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "comunidad", nullable = false)
    private String comunidad;

    @Lob
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Column(name = "link")
    private String link;

    @Column(name = "activo")
    private Boolean activo;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Comunidad id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComunidad() {
        return this.comunidad;
    }

    public Comunidad comunidad(String comunidad) {
        this.setComunidad(comunidad);
        return this;
    }

    public void setComunidad(String comunidad) {
        this.comunidad = comunidad;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public Comunidad descripcion(String descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getActivo() {
        return this.activo;
    }

    public Comunidad activo(Boolean activo) {
        this.setActivo(activo);
        return this;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Comunidad link(String link) {
        this.setLink(link);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Comunidad)) {
            return false;
        }
        return id != null && id.equals(((Comunidad) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }
}
