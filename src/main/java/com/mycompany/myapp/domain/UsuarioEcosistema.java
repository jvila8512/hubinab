package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.transaction.annotation.Transactional;

/**
 * A UsuarioEcosistema.
 */
@Entity
@Table(name = "usuario_ecosistema")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UsuarioEcosistema implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "fecha_ingreso", nullable = false)
    private LocalDate fechaIngreso;

    @Column(name = "bloqueado")
    private Boolean bloqueado;

    @ManyToOne
    private User user;

    @ManyToOne
    @JsonIgnoreProperties(value = { "usurioecosistemas" }, allowSetters = true)
    private CategoriaUser categoriaUser;

    @ManyToMany
    @JoinTable(
        name = "rel_usuario_ecosistema__ecosistema",
        joinColumns = @JoinColumn(name = "usuario_ecosistema_id"),
        inverseJoinColumns = @JoinColumn(name = "ecosistema_id")
    )
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

    public UsuarioEcosistema id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaIngreso() {
        return this.fechaIngreso;
    }

    public UsuarioEcosistema fechaIngreso(LocalDate fechaIngreso) {
        this.setFechaIngreso(fechaIngreso);
        return this;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Boolean getBloqueado() {
        return this.bloqueado;
    }

    public UsuarioEcosistema bloqueado(Boolean bloqueado) {
        this.setBloqueado(bloqueado);
        return this;
    }

    public void setBloqueado(Boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UsuarioEcosistema user(User user) {
        this.setUser(user);
        return this;
    }

    public CategoriaUser getCategoriaUser() {
        return this.categoriaUser;
    }

    public void setCategoriaUser(CategoriaUser categoriaUser) {
        this.categoriaUser = categoriaUser;
    }

    public UsuarioEcosistema categoriaUser(CategoriaUser categoriaUser) {
        this.setCategoriaUser(categoriaUser);
        return this;
    }

    @Transactional
    public Set<Ecosistema> getEcosistemas() {
        return this.ecosistemas;
    }

    public void setEcosistemas(Set<Ecosistema> ecosistemas) {
        this.ecosistemas = ecosistemas;
    }

    public UsuarioEcosistema ecosistemas(Set<Ecosistema> ecosistemas) {
        this.setEcosistemas(ecosistemas);
        return this;
    }

    public UsuarioEcosistema addEcosistema(Ecosistema ecosistema) {
        this.ecosistemas.add(ecosistema);
        ecosistema.getUsurioecosistemas().add(this);
        return this;
    }

    public UsuarioEcosistema removeEcosistema(Ecosistema ecosistema) {
        this.ecosistemas.remove(ecosistema);
        ecosistema.getUsurioecosistemas().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UsuarioEcosistema)) {
            return false;
        }
        return id != null && id.equals(((UsuarioEcosistema) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UsuarioEcosistema{" +
            "id=" + getId() +
            ", fechaIngreso='" + getFechaIngreso() + "'" +
            ", bloqueado='" + getBloqueado() + "'" +
            "}";
    }
}
