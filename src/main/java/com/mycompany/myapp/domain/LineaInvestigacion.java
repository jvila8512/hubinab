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
 * A LineaInvestigacion.
 */
@Entity
@Table(name = "linea_investigacion")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LineaInvestigacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "linea", nullable = false)
    private String linea;

    @ManyToMany(mappedBy = "lineaInvestigacions")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "partipantes", "user", "sectors", "lineaInvestigacions", "ods", "ecosistema", "tipoProyecto" },
        allowSetters = true
    )
    private Set<Proyectos> proyectos = new HashSet<>();

    @ManyToMany(mappedBy = "lineaInvestigacion")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "user", "sectors", "lineaInvestigacion", "ods" }, allowSetters = true)
    private Set<InnovacionRacionalizacion> innovacion = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Set<InnovacionRacionalizacion> getInnovacion() {
        return innovacion;
    }

    public Long getId() {
        return this.id;
    }

    public LineaInvestigacion id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLinea() {
        return this.linea;
    }

    public LineaInvestigacion linea(String linea) {
        this.setLinea(linea);
        return this;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    public Set<Proyectos> getProyectos() {
        return this.proyectos;
    }

    public void setProyectos(Set<Proyectos> proyectos) {
        if (this.proyectos != null) {
            this.proyectos.forEach(i -> i.removeLineaInvestigacion(this));
        }
        if (proyectos != null) {
            proyectos.forEach(i -> i.addLineaInvestigacion(this));
        }
        this.proyectos = proyectos;
    }

    public LineaInvestigacion proyectos(Set<Proyectos> proyectos) {
        this.setProyectos(proyectos);
        return this;
    }

    public LineaInvestigacion addProyectos(Proyectos proyectos) {
        this.proyectos.add(proyectos);
        proyectos.getLineaInvestigacions().add(this);
        return this;
    }

    public LineaInvestigacion removeProyectos(Proyectos proyectos) {
        this.proyectos.remove(proyectos);
        proyectos.getLineaInvestigacions().remove(this);
        return this;
    }

    public LineaInvestigacion addInnovacion(InnovacionRacionalizacion innovacion) {
        this.innovacion.add(innovacion);
        innovacion.getLineaInvestigacions().add(this);
        return this;
    }

    public LineaInvestigacion removeInnovacion(InnovacionRacionalizacion innovacion) {
        this.innovacion.remove(innovacion);
        innovacion.getLineaInvestigacions().remove(this);
        return this;
    }

    public void setInnovacion(Set<InnovacionRacionalizacion> innovacion) {
        if (this.innovacion != null) {
            this.innovacion.forEach(i -> i.removeLineaInvestigacion(this));
        }
        if (innovacion != null) {
            innovacion.forEach(i -> i.addLineaInvestigacion(this));
        }
        this.innovacion = innovacion;
    }

    public LineaInvestigacion innovacion(Set<InnovacionRacionalizacion> innovacion) {
        this.setInnovacion(innovacion);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LineaInvestigacion)) {
            return false;
        }
        return id != null && id.equals(((LineaInvestigacion) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LineaInvestigacion{" +
            "id=" + getId() +
            ", linea='" + getLinea() + "'" +
            "}";
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }
}
