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
 * A Ods.
 */
@Entity
@Table(name = "ods")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Ods implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "ods", nullable = false)
    private String ods;

    @ManyToMany(mappedBy = "ods")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "partipantes", "user", "sectors", "lineaInvestigacions", "ods", "ecosistema", "tipoProyecto" },
        allowSetters = true
    )
    private Set<Proyectos> proyectos = new HashSet<>();

    @ManyToMany(mappedBy = "ods")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "user", "sectors", "lineaInvestigacion", "ods" }, allowSetters = true)
    private Set<InnovacionRacionalizacion> innovacion = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Set<InnovacionRacionalizacion> getInnovacion() {
        return innovacion;
    }

    public Long getId() {
        return this.id;
    }

    public Ods id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOds() {
        return this.ods;
    }

    public Ods ods(String ods) {
        this.setOds(ods);
        return this;
    }

    public void setOds(String ods) {
        this.ods = ods;
    }

    public Set<Proyectos> getProyectos() {
        return this.proyectos;
    }

    public void setProyectos(Set<Proyectos> proyectos) {
        if (this.proyectos != null) {
            this.proyectos.forEach(i -> i.removeOds(this));
        }
        if (proyectos != null) {
            proyectos.forEach(i -> i.addOds(this));
        }
        this.proyectos = proyectos;
    }

    public Ods proyectos(Set<Proyectos> proyectos) {
        this.setProyectos(proyectos);
        return this;
    }

    public Ods addProyectos(Proyectos proyectos) {
        this.proyectos.add(proyectos);
        proyectos.getOds().add(this);
        return this;
    }

    public Ods removeProyectos(Proyectos proyectos) {
        this.proyectos.remove(proyectos);
        proyectos.getOds().remove(this);
        return this;
    }

    public Ods addInnovacion(InnovacionRacionalizacion innovacion) {
        this.innovacion.add(innovacion);
        innovacion.getOds().add(this);
        return this;
    }

    public Ods removeInnovacion(InnovacionRacionalizacion innovacion) {
        this.innovacion.remove(innovacion);
        innovacion.getLineaInvestigacions().remove(this);
        return this;
    }

    public void setInnovacion(Set<InnovacionRacionalizacion> innovacion) {
        if (this.innovacion != null) {
            this.innovacion.forEach(i -> i.removeOds(this));
        }
        if (innovacion != null) {
            innovacion.forEach(i -> i.addOds(this));
        }
        this.innovacion = innovacion;
    }

    public Ods innovacion(Set<InnovacionRacionalizacion> innovacion) {
        this.setInnovacion(innovacion);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ods)) {
            return false;
        }
        return id != null && id.equals(((Ods) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ods{" +
            "id=" + getId() +
            ", ods='" + getOds() + "'" +
            "}";
    }
}
