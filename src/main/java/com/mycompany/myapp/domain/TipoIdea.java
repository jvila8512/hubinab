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
 * A TipoIdea.
 */
@Entity
@Table(name = "tipo_idea")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TipoIdea implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "tipo_idea", nullable = false)
    private String tipoIdea;

    @OneToMany(mappedBy = "tipoIdea")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "comentarios", "likes", "user", "reto", "tipoIdea", "entidad" }, allowSetters = true)
    private Set<Idea> ideas = new HashSet<>();

    @OneToMany(mappedBy = "tipoIdea")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tipoIdea" }, allowSetters = true)
    private Set<InnovacionRacionalizacion> innovacionRacionalizacions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TipoIdea id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoIdea() {
        return this.tipoIdea;
    }

    public TipoIdea tipoIdea(String tipoIdea) {
        this.setTipoIdea(tipoIdea);
        return this;
    }

    public void setTipoIdea(String tipoIdea) {
        this.tipoIdea = tipoIdea;
    }

    public Set<Idea> getIdeas() {
        return this.ideas;
    }

    public void setIdeas(Set<Idea> ideas) {
        if (this.ideas != null) {
            this.ideas.forEach(i -> i.setTipoIdea(null));
        }
        if (ideas != null) {
            ideas.forEach(i -> i.setTipoIdea(this));
        }
        this.ideas = ideas;
    }

    public TipoIdea ideas(Set<Idea> ideas) {
        this.setIdeas(ideas);
        return this;
    }

    public TipoIdea addIdea(Idea idea) {
        this.ideas.add(idea);
        idea.setTipoIdea(this);
        return this;
    }

    public TipoIdea removeIdea(Idea idea) {
        this.ideas.remove(idea);
        idea.setTipoIdea(null);
        return this;
    }

    public Set<InnovacionRacionalizacion> getInnovacionRacionalizacions() {
        return this.innovacionRacionalizacions;
    }

    public void setInnovacionRacionalizacions(Set<InnovacionRacionalizacion> innovacionRacionalizacions) {
        if (this.innovacionRacionalizacions != null) {
            this.innovacionRacionalizacions.forEach(i -> i.setTipoIdea(null));
        }
        if (innovacionRacionalizacions != null) {
            innovacionRacionalizacions.forEach(i -> i.setTipoIdea(this));
        }
        this.innovacionRacionalizacions = innovacionRacionalizacions;
    }

    public TipoIdea innovacionRacionalizacions(Set<InnovacionRacionalizacion> innovacionRacionalizacions) {
        this.setInnovacionRacionalizacions(innovacionRacionalizacions);
        return this;
    }

    public TipoIdea addInnovacionRacionalizacion(InnovacionRacionalizacion innovacionRacionalizacion) {
        this.innovacionRacionalizacions.add(innovacionRacionalizacion);
        innovacionRacionalizacion.setTipoIdea(this);
        return this;
    }

    public TipoIdea removeInnovacionRacionalizacion(InnovacionRacionalizacion innovacionRacionalizacion) {
        this.innovacionRacionalizacions.remove(innovacionRacionalizacion);
        innovacionRacionalizacion.setTipoIdea(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TipoIdea)) {
            return false;
        }
        return id != null && id.equals(((TipoIdea) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TipoIdea{" +
            "id=" + getId() +
            ", tipoIdea='" + getTipoIdea() + "'" +
            "}";
    }
}
