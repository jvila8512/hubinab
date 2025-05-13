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
 * A EcosistemaRol.
 */
@Entity
@Table(name = "ecosistema_rol")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EcosistemaRol implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "ecosistema_rol", nullable = false)
    private String ecosistemaRol;

    @Lob
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @OneToMany(mappedBy = "ecosistemaRol")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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
    private Set<Ecosistema> ecosistemas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EcosistemaRol id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEcosistemaRol() {
        return this.ecosistemaRol;
    }

    public EcosistemaRol ecosistemaRol(String ecosistemaRol) {
        this.setEcosistemaRol(ecosistemaRol);
        return this;
    }

    public void setEcosistemaRol(String ecosistemaRol) {
        this.ecosistemaRol = ecosistemaRol;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public EcosistemaRol descripcion(String descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<Ecosistema> getEcosistemas() {
        return this.ecosistemas;
    }

    public void setEcosistemas(Set<Ecosistema> ecosistemas) {
        if (this.ecosistemas != null) {
            this.ecosistemas.forEach(i -> i.setEcosistemaRol(null));
        }
        if (ecosistemas != null) {
            ecosistemas.forEach(i -> i.setEcosistemaRol(this));
        }
        this.ecosistemas = ecosistemas;
    }

    public EcosistemaRol ecosistemas(Set<Ecosistema> ecosistemas) {
        this.setEcosistemas(ecosistemas);
        return this;
    }

    public EcosistemaRol addEcosistema(Ecosistema ecosistema) {
        this.ecosistemas.add(ecosistema);
        ecosistema.setEcosistemaRol(this);
        return this;
    }

    public EcosistemaRol removeEcosistema(Ecosistema ecosistema) {
        this.ecosistemas.remove(ecosistema);
        ecosistema.setEcosistemaRol(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EcosistemaRol)) {
            return false;
        }
        return id != null && id.equals(((EcosistemaRol) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EcosistemaRol{" +
            "id=" + getId() +
            ", ecosistemaRol='" + getEcosistemaRol() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
