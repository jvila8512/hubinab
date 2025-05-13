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
 * A TipoProyecto.
 */
@Entity
@Table(name = "tipo_proyecto")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TipoProyecto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "tipo_proyecto", nullable = false)
    private String tipoProyecto;

    @OneToMany(mappedBy = "tipoProyecto")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "partipantes", "user", "sectors", "lineaInvestigacions", "ods", "ecosistema", "tipoProyecto" },
        allowSetters = true
    )
    private Set<Proyectos> proyectos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TipoProyecto id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoProyecto() {
        return this.tipoProyecto;
    }

    public TipoProyecto tipoProyecto(String tipoProyecto) {
        this.setTipoProyecto(tipoProyecto);
        return this;
    }

    public void setTipoProyecto(String tipoProyecto) {
        this.tipoProyecto = tipoProyecto;
    }

    public Set<Proyectos> getProyectos() {
        return this.proyectos;
    }

    public void setProyectos(Set<Proyectos> proyectos) {
        if (this.proyectos != null) {
            this.proyectos.forEach(i -> i.setTipoProyecto(null));
        }
        if (proyectos != null) {
            proyectos.forEach(i -> i.setTipoProyecto(this));
        }
        this.proyectos = proyectos;
    }

    public TipoProyecto proyectos(Set<Proyectos> proyectos) {
        this.setProyectos(proyectos);
        return this;
    }

    public TipoProyecto addProyectos(Proyectos proyectos) {
        this.proyectos.add(proyectos);
        proyectos.setTipoProyecto(this);
        return this;
    }

    public TipoProyecto removeProyectos(Proyectos proyectos) {
        this.proyectos.remove(proyectos);
        proyectos.setTipoProyecto(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TipoProyecto)) {
            return false;
        }
        return id != null && id.equals(((TipoProyecto) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TipoProyecto{" +
            "id=" + getId() +
            ", tipoProyecto='" + getTipoProyecto() + "'" +
            "}";
    }
}
