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
 * A CategoriaUser.
 */
@Entity
@Table(name = "categoria_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CategoriaUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "categoria_user", nullable = false)
    private String categoriaUser;

    @OneToMany(mappedBy = "categoriaUser")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "user", "categoriaUser", "ecosistemas" }, allowSetters = true)
    private Set<UsuarioEcosistema> usurioecosistemas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CategoriaUser id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoriaUser() {
        return this.categoriaUser;
    }

    public CategoriaUser categoriaUser(String categoriaUser) {
        this.setCategoriaUser(categoriaUser);
        return this;
    }

    public void setCategoriaUser(String categoriaUser) {
        this.categoriaUser = categoriaUser;
    }

    public Set<UsuarioEcosistema> getUsurioecosistemas() {
        return this.usurioecosistemas;
    }

    public void setUsurioecosistemas(Set<UsuarioEcosistema> usuarioEcosistemas) {
        if (this.usurioecosistemas != null) {
            this.usurioecosistemas.forEach(i -> i.setCategoriaUser(null));
        }
        if (usuarioEcosistemas != null) {
            usuarioEcosistemas.forEach(i -> i.setCategoriaUser(this));
        }
        this.usurioecosistemas = usuarioEcosistemas;
    }

    public CategoriaUser usurioecosistemas(Set<UsuarioEcosistema> usuarioEcosistemas) {
        this.setUsurioecosistemas(usuarioEcosistemas);
        return this;
    }

    public CategoriaUser addUsurioecosistema(UsuarioEcosistema usuarioEcosistema) {
        this.usurioecosistemas.add(usuarioEcosistema);
        usuarioEcosistema.setCategoriaUser(this);
        return this;
    }

    public CategoriaUser removeUsurioecosistema(UsuarioEcosistema usuarioEcosistema) {
        this.usurioecosistemas.remove(usuarioEcosistema);
        usuarioEcosistema.setCategoriaUser(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CategoriaUser)) {
            return false;
        }
        return id != null && id.equals(((CategoriaUser) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategoriaUser{" +
            "id=" + getId() +
            ", categoriaUser='" + getCategoriaUser() + "'" +
            "}";
    }
}
