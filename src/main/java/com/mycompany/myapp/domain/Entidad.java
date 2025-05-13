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
 * A Entidad.
 */
@Entity
@Table(name = "entidad")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Entidad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "entidad", nullable = false)
    private String entidad;

    @OneToMany(mappedBy = "entidad")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "comentarios", "likes", "user", "reto", "tipoIdea", "entidad" }, allowSetters = true)
    private Set<Idea> ideas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Entidad id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEntidad() {
        return this.entidad;
    }

    public Entidad entidad(String entidad) {
        this.setEntidad(entidad);
        return this;
    }

    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }

    public Set<Idea> getIdeas() {
        return this.ideas;
    }

    public void setIdeas(Set<Idea> ideas) {
        if (this.ideas != null) {
            this.ideas.forEach(i -> i.setEntidad(null));
        }
        if (ideas != null) {
            ideas.forEach(i -> i.setEntidad(this));
        }
        this.ideas = ideas;
    }

    public Entidad ideas(Set<Idea> ideas) {
        this.setIdeas(ideas);
        return this;
    }

    public Entidad addIdea(Idea idea) {
        this.ideas.add(idea);
        idea.setEntidad(this);
        return this;
    }

    public Entidad removeIdea(Idea idea) {
        this.ideas.remove(idea);
        idea.setEntidad(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Entidad)) {
            return false;
        }
        return id != null && id.equals(((Entidad) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Entidad{" +
            "id=" + getId() +
            ", entidad='" + getEntidad() + "'" +
            "}";
    }
}
