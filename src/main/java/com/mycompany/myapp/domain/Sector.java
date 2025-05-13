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
 * A Sector.
 */
@Entity
@Table(name = "sector")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Sector implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "sector", nullable = false)
    private String sector;

    @ManyToMany(mappedBy = "sectors")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "partipantes", "user", "sectors", "lineaInvestigacions", "ods", "ecosistema", "tipoProyecto" },
        allowSetters = true
    )
    private Set<Proyectos> proyectos = new HashSet<>();

    @ManyToMany(mappedBy = "sectors")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "user", "sectors", "lineaInvestigacion", "ods" }, allowSetters = true)
    private Set<InnovacionRacionalizacion> innovacion = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Set<InnovacionRacionalizacion> getInnovacion() {
        return innovacion;
    }

    public Sector id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSector() {
        return this.sector;
    }

    public Sector sector(String sector) {
        this.setSector(sector);
        return this;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public Set<Proyectos> getProyectos() {
        return this.proyectos;
    }

    public void setProyectos(Set<Proyectos> proyectos) {
        if (this.proyectos != null) {
            this.proyectos.forEach(i -> i.removeSector(this));
        }
        if (proyectos != null) {
            proyectos.forEach(i -> i.addSector(this));
        }
        this.proyectos = proyectos;
    }

    public Sector proyectos(Set<Proyectos> proyectos) {
        this.setProyectos(proyectos);
        return this;
    }

    public Sector addProyectos(Proyectos proyectos) {
        this.proyectos.add(proyectos);
        proyectos.getSectors().add(this);
        return this;
    }

    public Sector removeProyectos(Proyectos proyectos) {
        this.proyectos.remove(proyectos);
        proyectos.getSectors().remove(this);
        return this;
    }

    public Sector addInnovacion(InnovacionRacionalizacion innovacion) {
        this.innovacion.add(innovacion);
        innovacion.getSector().add(this);
        return this;
    }

    public Sector removeInnovacion(InnovacionRacionalizacion innovacion) {
        this.innovacion.remove(innovacion);
        innovacion.getLineaInvestigacions().remove(this);
        return this;
    }

    public void setInnovacion(Set<InnovacionRacionalizacion> innovacion) {
        if (this.innovacion != null) {
            this.innovacion.forEach(i -> i.removeSector(this));
        }
        if (innovacion != null) {
            innovacion.forEach(i -> i.addSector(this));
        }
        this.innovacion = innovacion;
    }

    public Sector innovacion(Set<InnovacionRacionalizacion> innovacion) {
        this.setInnovacion(innovacion);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sector)) {
            return false;
        }
        return id != null && id.equals(((Sector) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Sector{" +
            "id=" + getId() +
            ", sector='" + getSector() + "'" +
            "}";
    }
}
