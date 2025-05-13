package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Noticias.
 */
@Entity
@Table(name = "noticias")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Noticias implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Lob
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Lob
    @Column(name = "url_foto")
    private byte[] urlFoto;

    @Column(name = "url_foto_content_type")
    private String urlFotoContentType;

    @Column(name = "publica")
    private Boolean publica;

    @Column(name = "publicar")
    private Boolean publicar;

    @Column(name = "fecha_creada")
    private LocalDate fechaCreada;

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

    @ManyToOne
    @JsonIgnoreProperties(value = { "noticias" }, allowSetters = true)
    private TipoNoticia tipoNoticia;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Noticias id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public Noticias titulo(String titulo) {
        this.setTitulo(titulo);
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public Noticias descripcion(String descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public byte[] getUrlFoto() {
        return this.urlFoto;
    }

    public Noticias urlFoto(byte[] urlFoto) {
        this.setUrlFoto(urlFoto);
        return this;
    }

    public void setUrlFoto(byte[] urlFoto) {
        this.urlFoto = urlFoto;
    }

    public String getUrlFotoContentType() {
        return this.urlFotoContentType;
    }

    public Noticias urlFotoContentType(String urlFotoContentType) {
        this.urlFotoContentType = urlFotoContentType;
        return this;
    }

    public void setUrlFotoContentType(String urlFotoContentType) {
        this.urlFotoContentType = urlFotoContentType;
    }

    public Boolean getPublica() {
        return this.publica;
    }

    public Noticias publica(Boolean publica) {
        this.setPublica(publica);
        return this;
    }

    public void setPublica(Boolean publica) {
        this.publica = publica;
    }

    public Boolean getPublicar() {
        return this.publicar;
    }

    public Noticias publicar(Boolean publicar) {
        this.setPublicar(publicar);
        return this;
    }

    public void setPublicar(Boolean publicar) {
        this.publicar = publicar;
    }

    public LocalDate getFechaCreada() {
        return this.fechaCreada;
    }

    public Noticias fechaCreada(LocalDate fechaCreada) {
        this.setFechaCreada(fechaCreada);
        return this;
    }

    public void setFechaCreada(LocalDate fechaCreada) {
        this.fechaCreada = fechaCreada;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Noticias user(User user) {
        this.setUser(user);
        return this;
    }

    public Ecosistema getEcosistema() {
        return this.ecosistema;
    }

    public void setEcosistema(Ecosistema ecosistema) {
        this.ecosistema = ecosistema;
    }

    public Noticias ecosistema(Ecosistema ecosistema) {
        this.setEcosistema(ecosistema);
        return this;
    }

    public TipoNoticia getTipoNoticia() {
        return this.tipoNoticia;
    }

    public void setTipoNoticia(TipoNoticia tipoNoticia) {
        this.tipoNoticia = tipoNoticia;
    }

    public Noticias tipoNoticia(TipoNoticia tipoNoticia) {
        this.setTipoNoticia(tipoNoticia);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Noticias)) {
            return false;
        }
        return id != null && id.equals(((Noticias) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Noticias{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", urlFoto='" + getUrlFoto() + "'" +
            ", urlFotoContentType='" + getUrlFotoContentType() + "'" +
            ", publica='" + getPublica() + "'" +
            ", publicar='" + getPublicar() + "'" +
            ", fechaCreada='" + getFechaCreada() + "'" +
            "}";
    }
}
