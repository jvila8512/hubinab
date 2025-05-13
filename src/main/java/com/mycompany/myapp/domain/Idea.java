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
 * A Idea.
 */
@Entity
@Table(name = "idea")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Idea implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "numero_registro", nullable = false)
    private Integer numeroRegistro;

    @NotNull
    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Lob
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @NotNull
    @Column(name = "autor", nullable = false)
    private String autor;

    @NotNull
    @Column(name = "fecha_inscripcion", nullable = false)
    private LocalDate fechaInscripcion;

    @Column(name = "visto")
    private Integer visto;

    @Lob
    @Column(name = "foto")
    private byte[] foto;

    @Column(name = "foto_content_type")
    private String fotoContentType;

    @Column(name = "aceptada")
    private Boolean aceptada;

    @Column(name = "publica")
    private Boolean publica;

    @OneToMany(mappedBy = "idea")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "user", "idea" }, allowSetters = true)
    private Set<ComenetariosIdea> comentarios = new HashSet<>();

    @OneToMany(mappedBy = "idea")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "idea" }, allowSetters = true)
    private Set<LikeIdea> likes = new HashSet<>();

    @ManyToOne
    private User user;

    @ManyToOne
    @JsonIgnoreProperties(value = { "ideas", "user", "ecosistema" }, allowSetters = true)
    private Reto reto;

    @ManyToOne
    @JsonIgnoreProperties(value = { "ideas", "innovacionRacionalizacions" }, allowSetters = true)
    private TipoIdea tipoIdea;

    @ManyToOne
    @JsonIgnoreProperties(value = { "ideas" }, allowSetters = true)
    private Entidad entidad;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Idea id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumeroRegistro() {
        return this.numeroRegistro;
    }

    public Idea numeroRegistro(Integer numeroRegistro) {
        this.setNumeroRegistro(numeroRegistro);
        return this;
    }

    public void setNumeroRegistro(Integer numeroRegistro) {
        this.numeroRegistro = numeroRegistro;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public Idea titulo(String titulo) {
        this.setTitulo(titulo);
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public Idea descripcion(String descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getAutor() {
        return this.autor;
    }

    public Idea autor(String autor) {
        this.setAutor(autor);
        return this;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public LocalDate getFechaInscripcion() {
        return this.fechaInscripcion;
    }

    public Idea fechaInscripcion(LocalDate fechaInscripcion) {
        this.setFechaInscripcion(fechaInscripcion);
        return this;
    }

    public void setFechaInscripcion(LocalDate fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public Integer getVisto() {
        return this.visto;
    }

    public Idea visto(Integer visto) {
        this.setVisto(visto);
        return this;
    }

    public void setVisto(Integer visto) {
        this.visto = visto;
    }

    public byte[] getFoto() {
        return this.foto;
    }

    public Idea foto(byte[] foto) {
        this.setFoto(foto);
        return this;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getFotoContentType() {
        return this.fotoContentType;
    }

    public Idea fotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
        return this;
    }

    public void setFotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
    }

    public Boolean getAceptada() {
        return this.aceptada;
    }

    public Idea aceptada(Boolean aceptada) {
        this.setAceptada(aceptada);
        return this;
    }

    public void setAceptada(Boolean aceptada) {
        this.aceptada = aceptada;
    }

    public Boolean getPublica() {
        return this.publica;
    }

    public Idea publica(Boolean publica) {
        this.setPublica(publica);
        return this;
    }

    public void setPublica(Boolean publica) {
        this.publica = publica;
    }

    public Set<ComenetariosIdea> getComentarios() {
        return this.comentarios;
    }

    public void setComentarios(Set<ComenetariosIdea> comenetariosIdeas) {
        if (this.comentarios != null) {
            this.comentarios.forEach(i -> i.setIdea(null));
        }
        if (comenetariosIdeas != null) {
            comenetariosIdeas.forEach(i -> i.setIdea(this));
        }
        this.comentarios = comenetariosIdeas;
    }

    public Idea comentarios(Set<ComenetariosIdea> comenetariosIdeas) {
        this.setComentarios(comenetariosIdeas);
        return this;
    }

    public Idea addComentario(ComenetariosIdea comenetariosIdea) {
        this.comentarios.add(comenetariosIdea);
        comenetariosIdea.setIdea(this);
        return this;
    }

    public Idea removeComentario(ComenetariosIdea comenetariosIdea) {
        this.comentarios.remove(comenetariosIdea);
        comenetariosIdea.setIdea(null);
        return this;
    }

    public Set<LikeIdea> getLikes() {
        return this.likes;
    }

    public void setLikes(Set<LikeIdea> likeIdeas) {
        if (this.likes != null) {
            this.likes.forEach(i -> i.setIdea(null));
        }
        if (likeIdeas != null) {
            likeIdeas.forEach(i -> i.setIdea(this));
        }
        this.likes = likeIdeas;
    }

    public Idea likes(Set<LikeIdea> likeIdeas) {
        this.setLikes(likeIdeas);
        return this;
    }

    public Idea addLike(LikeIdea likeIdea) {
        this.likes.add(likeIdea);
        likeIdea.setIdea(this);
        return this;
    }

    public Idea removeLike(LikeIdea likeIdea) {
        this.likes.remove(likeIdea);
        likeIdea.setIdea(null);
        return this;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Idea user(User user) {
        this.setUser(user);
        return this;
    }

    public Reto getReto() {
        return this.reto;
    }

    public void setReto(Reto reto) {
        this.reto = reto;
    }

    public Idea reto(Reto reto) {
        this.setReto(reto);
        return this;
    }

    public TipoIdea getTipoIdea() {
        return this.tipoIdea;
    }

    public void setTipoIdea(TipoIdea tipoIdea) {
        this.tipoIdea = tipoIdea;
    }

    public Idea tipoIdea(TipoIdea tipoIdea) {
        this.setTipoIdea(tipoIdea);
        return this;
    }

    public Entidad getEntidad() {
        return this.entidad;
    }

    public void setEntidad(Entidad entidad) {
        this.entidad = entidad;
    }

    public Idea entidad(Entidad entidad) {
        this.setEntidad(entidad);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Idea)) {
            return false;
        }
        return id != null && id.equals(((Idea) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Idea{" +
            "id=" + getId() +
            ", numeroRegistro=" + getNumeroRegistro() +
            ", titulo='" + getTitulo() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", autor='" + getAutor() + "'" +
            ", fechaInscripcion='" + getFechaInscripcion() + "'" +
            ", visto=" + getVisto() +
            ", foto='" + getFoto() + "'" +
            ", fotoContentType='" + getFotoContentType() + "'" +
            ", aceptada='" + getAceptada() + "'" +
            ", publica='" + getPublica() + "'" +
            "}";
    }
}
