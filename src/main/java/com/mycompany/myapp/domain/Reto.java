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

/**
 * A Reto.
 */
@Entity
@Table(name = "reto")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Reto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "reto", nullable = false)
    private String reto;

    @Lob
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @NotNull
    @Column(name = "motivacion", nullable = false)
    private String motivacion;

    @NotNull
    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @NotNull
    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    @Column(name = "activo")
    private Boolean activo;

    @Column(name = "validado")
    private Boolean validado;

    @Lob
    @Column(name = "url_foto")
    private byte[] urlFoto;

    @Column(name = "url_foto_content_type")
    private String urlFotoContentType;

    @Column(name = "visto")
    private Integer visto;

    @Column(name = "publico")
    private Boolean publico;

    @OneToMany(mappedBy = "reto")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "comentarios", "likes", "user", "reto", "tipoIdea", "entidad" }, allowSetters = true)
    private Set<Idea> ideas = new HashSet<>();

    @ManyToOne
    private User user;

    @ManyToOne
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
    private Ecosistema ecosistema;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Reto id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReto() {
        return this.reto;
    }

    public Boolean getPublico() {
        return publico;
    }

    public void setPublico(Boolean publico) {
        this.publico = publico;
    }

    public Reto reto(String reto) {
        this.setReto(reto);
        return this;
    }

    public void setReto(String reto) {
        this.reto = reto;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public Reto descripcion(String descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMotivacion() {
        return this.motivacion;
    }

    public Reto motivacion(String motivacion) {
        this.setMotivacion(motivacion);
        return this;
    }

    public void setMotivacion(String motivacion) {
        this.motivacion = motivacion;
    }

    public LocalDate getFechaInicio() {
        return this.fechaInicio;
    }

    public Reto fechaInicio(LocalDate fechaInicio) {
        this.setFechaInicio(fechaInicio);
        return this;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return this.fechaFin;
    }

    public Reto fechaFin(LocalDate fechaFin) {
        this.setFechaFin(fechaFin);
        return this;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Boolean getActivo() {
        return this.activo;
    }

    public Reto activo(Boolean activo) {
        this.setActivo(activo);
        return this;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Boolean getValidado() {
        return this.validado;
    }

    public Reto validado(Boolean validado) {
        this.setValidado(validado);
        return this;
    }

    public void setValidado(Boolean validado) {
        this.validado = validado;
    }

    public byte[] getUrlFoto() {
        return this.urlFoto;
    }

    public Reto urlFoto(byte[] urlFoto) {
        this.setUrlFoto(urlFoto);
        return this;
    }

    public void setUrlFoto(byte[] urlFoto) {
        this.urlFoto = urlFoto;
    }

    public String getUrlFotoContentType() {
        return this.urlFotoContentType;
    }

    public Reto urlFotoContentType(String urlFotoContentType) {
        this.urlFotoContentType = urlFotoContentType;
        return this;
    }

    public void setUrlFotoContentType(String urlFotoContentType) {
        this.urlFotoContentType = urlFotoContentType;
    }

    public Integer getVisto() {
        return this.visto;
    }

    public Reto visto(Integer visto) {
        this.setVisto(visto);
        return this;
    }

    public void setVisto(Integer visto) {
        this.visto = visto;
    }

    public Set<Idea> getIdeas() {
        return this.ideas;
    }

    public void setIdeas(Set<Idea> ideas) {
        if (this.ideas != null) {
            this.ideas.forEach(i -> i.setReto(null));
        }
        if (ideas != null) {
            ideas.forEach(i -> i.setReto(this));
        }
        this.ideas = ideas;
    }

    public Reto ideas(Set<Idea> ideas) {
        this.setIdeas(ideas);
        return this;
    }

    public Reto addIdea(Idea idea) {
        this.ideas.add(idea);
        idea.setReto(this);
        return this;
    }

    public Reto removeIdea(Idea idea) {
        this.ideas.remove(idea);
        idea.setReto(null);
        return this;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Reto user(User user) {
        this.setUser(user);
        return this;
    }

    public Ecosistema getEcosistema() {
        return this.ecosistema;
    }

    public void setEcosistema(Ecosistema ecosistema) {
        this.ecosistema = ecosistema;
    }

    public Reto ecosistema(Ecosistema ecosistema) {
        this.setEcosistema(ecosistema);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Reto)) {
            return false;
        }
        return id != null && id.equals(((Reto) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Reto{" +
            "id=" + getId() +
            ", reto='" + getReto() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", motivacion='" + getMotivacion() + "'" +
            ", fechaInicio='" + getFechaInicio() + "'" +
            ", fechaFin='" + getFechaFin() + "'" +
            ", activo='" + getActivo() + "'" +
            ", validado='" + getValidado() + "'" +
            ", urlFoto='" + getUrlFoto() + "'" +
            ", urlFotoContentType='" + getUrlFotoContentType() + "'" +
            ", visto=" + getVisto() +
            "}";
    }
}
