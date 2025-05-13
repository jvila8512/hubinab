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
 * A Ecosistema.
 */
@Entity
@Table(name = "ecosistema")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Ecosistema implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "tematica", nullable = false)
    private String tematica;

    @Column(name = "activo")
    private Boolean activo;

    @Lob
    @Column(name = "logo_url")
    private byte[] logoUrl;

    @Column(name = "logo_url_content_type")
    private String logoUrlContentType;

    @Column(name = "ranking")
    private Integer ranking;

    @Column(name = "usuarios_cant")
    private Integer usuariosCant;

    @Column(name = "retos_cant")
    private Integer retosCant;

    @Column(name = "ideas_cant")
    private Integer ideasCant;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "ecosistema")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "ideas", "user", "ecosistema" }, allowSetters = true)
    private Set<Reto> retos = new HashSet<>();

    @OneToMany(mappedBy = "ecosistema")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "partipantes", "user", "sectors", "lineaInvestigacions", "ods", "ecosistema", "tipoProyecto" },
        allowSetters = true
    )
    private Set<Proyectos> proyectos = new HashSet<>();

    @OneToMany(mappedBy = "ecosistema")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "user", "ecosistema" }, allowSetters = true)
    private Set<EcosistemaPeticiones> ecosistemaPeticiones = new HashSet<>();

    @OneToMany(mappedBy = "ecosistema")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "user", "ecosistema", "tipoNoticia" }, allowSetters = true)
    private Set<Noticias> noticias = new HashSet<>();

    @OneToMany(mappedBy = "ecosistema")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "ecosistema" }, allowSetters = true)
    private Set<RedesSociales> redesUrls = new HashSet<>();

    @OneToMany(mappedBy = "ecosistema")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "ecosistema" }, allowSetters = true)
    private Set<Anirista> aniristas = new HashSet<>();

    @OneToMany(mappedBy = "ecosistema")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "componente", "ecosistema" }, allowSetters = true)
    private Set<EcosistemaComponente> ecosistemaComponentes = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_ecosistema__user",
        joinColumns = @JoinColumn(name = "ecosistema_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<User> users = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "ecosistemas" }, allowSetters = true)
    private EcosistemaRol ecosistemaRol;

    @ManyToMany(mappedBy = "ecosistemas")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "user", "categoriaUser", "ecosistemas" }, allowSetters = true)
    private Set<UsuarioEcosistema> usurioecosistemas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return this.id;
    }

    public Ecosistema id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Ecosistema nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTematica() {
        return this.tematica;
    }

    public Ecosistema tematica(String tematica) {
        this.setTematica(tematica);
        return this;
    }

    public void setTematica(String tematica) {
        this.tematica = tematica;
    }

    public Boolean getActivo() {
        return this.activo;
    }

    public Ecosistema activo(Boolean activo) {
        this.setActivo(activo);
        return this;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public byte[] getLogoUrl() {
        return this.logoUrl;
    }

    public Ecosistema logoUrl(byte[] logoUrl) {
        this.setLogoUrl(logoUrl);
        return this;
    }

    public void setLogoUrl(byte[] logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getLogoUrlContentType() {
        return this.logoUrlContentType;
    }

    public Ecosistema logoUrlContentType(String logoUrlContentType) {
        this.logoUrlContentType = logoUrlContentType;
        return this;
    }

    public void setLogoUrlContentType(String logoUrlContentType) {
        this.logoUrlContentType = logoUrlContentType;
    }

    public Integer getRanking() {
        return this.ranking;
    }

    public Ecosistema ranking(Integer ranking) {
        this.setRanking(ranking);
        return this;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public Integer getUsuariosCant() {
        return this.usuariosCant;
    }

    public Ecosistema usuariosCant(Integer usuariosCant) {
        this.setUsuariosCant(usuariosCant);
        return this;
    }

    public void setUsuariosCant(Integer usuariosCant) {
        this.usuariosCant = usuariosCant;
    }

    public Integer getRetosCant() {
        return this.retosCant;
    }

    public Ecosistema retosCant(Integer retosCant) {
        this.setRetosCant(retosCant);
        return this;
    }

    public void setRetosCant(Integer retosCant) {
        this.retosCant = retosCant;
    }

    public Integer getIdeasCant() {
        return this.ideasCant;
    }

    public Ecosistema ideasCant(Integer ideasCant) {
        this.setIdeasCant(ideasCant);
        return this;
    }

    public void setIdeasCant(Integer ideasCant) {
        this.ideasCant = ideasCant;
    }

    public Set<Reto> getRetos() {
        return this.retos;
    }

    public void setRetos(Set<Reto> retos) {
        if (this.retos != null) {
            this.retos.forEach(i -> i.setEcosistema(null));
        }
        if (retos != null) {
            retos.forEach(i -> i.setEcosistema(this));
        }
        this.retos = retos;
    }

    public Ecosistema retos(Set<Reto> retos) {
        this.setRetos(retos);
        return this;
    }

    public Ecosistema addReto(Reto reto) {
        this.retos.add(reto);
        reto.setEcosistema(this);
        return this;
    }

    public Ecosistema removeReto(Reto reto) {
        this.retos.remove(reto);
        reto.setEcosistema(null);
        return this;
    }

    public Set<Proyectos> getProyectos() {
        return this.proyectos;
    }

    public void setProyectos(Set<Proyectos> proyectos) {
        if (this.proyectos != null) {
            this.proyectos.forEach(i -> i.setEcosistema(null));
        }
        if (proyectos != null) {
            proyectos.forEach(i -> i.setEcosistema(this));
        }
        this.proyectos = proyectos;
    }

    public Ecosistema proyectos(Set<Proyectos> proyectos) {
        this.setProyectos(proyectos);
        return this;
    }

    public Ecosistema addProyectos(Proyectos proyectos) {
        this.proyectos.add(proyectos);
        proyectos.setEcosistema(this);
        return this;
    }

    public Ecosistema removeProyectos(Proyectos proyectos) {
        this.proyectos.remove(proyectos);
        proyectos.setEcosistema(null);
        return this;
    }

    public Set<EcosistemaPeticiones> getEcosistemaPeticiones() {
        return this.ecosistemaPeticiones;
    }

    public void setEcosistemaPeticiones(Set<EcosistemaPeticiones> ecosistemaPeticiones) {
        if (this.ecosistemaPeticiones != null) {
            this.ecosistemaPeticiones.forEach(i -> i.setEcosistema(null));
        }
        if (ecosistemaPeticiones != null) {
            ecosistemaPeticiones.forEach(i -> i.setEcosistema(this));
        }
        this.ecosistemaPeticiones = ecosistemaPeticiones;
    }

    public Ecosistema ecosistemaPeticiones(Set<EcosistemaPeticiones> ecosistemaPeticiones) {
        this.setEcosistemaPeticiones(ecosistemaPeticiones);
        return this;
    }

    public Ecosistema addEcosistemaPeticiones(EcosistemaPeticiones ecosistemaPeticiones) {
        this.ecosistemaPeticiones.add(ecosistemaPeticiones);
        ecosistemaPeticiones.setEcosistema(this);
        return this;
    }

    public Ecosistema removeEcosistemaPeticiones(EcosistemaPeticiones ecosistemaPeticiones) {
        this.ecosistemaPeticiones.remove(ecosistemaPeticiones);
        ecosistemaPeticiones.setEcosistema(null);
        return this;
    }

    public Set<Noticias> getNoticias() {
        return this.noticias;
    }

    public void setNoticias(Set<Noticias> noticias) {
        if (this.noticias != null) {
            this.noticias.forEach(i -> i.setEcosistema(null));
        }
        if (noticias != null) {
            noticias.forEach(i -> i.setEcosistema(this));
        }
        this.noticias = noticias;
    }

    public Ecosistema noticias(Set<Noticias> noticias) {
        this.setNoticias(noticias);
        return this;
    }

    public Ecosistema addNoticias(Noticias noticias) {
        this.noticias.add(noticias);
        noticias.setEcosistema(this);
        return this;
    }

    public Ecosistema removeNoticias(Noticias noticias) {
        this.noticias.remove(noticias);
        noticias.setEcosistema(null);
        return this;
    }

    public Set<RedesSociales> getRedesUrls() {
        return this.redesUrls;
    }

    public void setRedesUrls(Set<RedesSociales> redesSociales) {
        if (this.redesUrls != null) {
            this.redesUrls.forEach(i -> i.setEcosistema(null));
        }
        if (redesSociales != null) {
            redesSociales.forEach(i -> i.setEcosistema(this));
        }
        this.redesUrls = redesSociales;
    }

    public Ecosistema redesUrls(Set<RedesSociales> redesSociales) {
        this.setRedesUrls(redesSociales);
        return this;
    }

    public Ecosistema addRedesUrl(RedesSociales redesSociales) {
        this.redesUrls.add(redesSociales);
        redesSociales.setEcosistema(this);
        return this;
    }

    public Ecosistema removeRedesUrl(RedesSociales redesSociales) {
        this.redesUrls.remove(redesSociales);
        redesSociales.setEcosistema(null);
        return this;
    }

    public Set<Anirista> getAniristas() {
        return this.aniristas;
    }

    public void setAniristas(Set<Anirista> aniristas) {
        if (this.aniristas != null) {
            this.aniristas.forEach(i -> i.setEcosistema(null));
        }
        if (aniristas != null) {
            aniristas.forEach(i -> i.setEcosistema(this));
        }
        this.aniristas = aniristas;
    }

    public Ecosistema aniristas(Set<Anirista> aniristas) {
        this.setAniristas(aniristas);
        return this;
    }

    public Ecosistema addAnirista(Anirista anirista) {
        this.aniristas.add(anirista);
        anirista.setEcosistema(this);
        return this;
    }

    public Ecosistema removeAnirista(Anirista anirista) {
        this.aniristas.remove(anirista);
        anirista.setEcosistema(null);
        return this;
    }

    public Set<EcosistemaComponente> getEcosistemaComponentes() {
        return this.ecosistemaComponentes;
    }

    public void setEcosistemaComponentes(Set<EcosistemaComponente> ecosistemaComponentes) {
        if (this.ecosistemaComponentes != null) {
            this.ecosistemaComponentes.forEach(i -> i.setEcosistema(null));
        }
        if (ecosistemaComponentes != null) {
            ecosistemaComponentes.forEach(i -> i.setEcosistema(this));
        }
        this.ecosistemaComponentes = ecosistemaComponentes;
    }

    public Ecosistema ecosistemaComponentes(Set<EcosistemaComponente> ecosistemaComponentes) {
        this.setEcosistemaComponentes(ecosistemaComponentes);
        return this;
    }

    public Ecosistema addEcosistemaComponente(EcosistemaComponente ecosistemaComponente) {
        this.ecosistemaComponentes.add(ecosistemaComponente);
        ecosistemaComponente.setEcosistema(this);
        return this;
    }

    public Ecosistema removeEcosistemaComponente(EcosistemaComponente ecosistemaComponente) {
        this.ecosistemaComponentes.remove(ecosistemaComponente);
        ecosistemaComponente.setEcosistema(null);
        return this;
    }

    public Set<User> getUsers() {
        return this.users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Ecosistema users(Set<User> users) {
        this.setUsers(users);
        return this;
    }

    public Ecosistema addUser(User user) {
        this.users.add(user);
        return this;
    }

    public Ecosistema removeUser(User user) {
        this.users.remove(user);
        return this;
    }

    public EcosistemaRol getEcosistemaRol() {
        return this.ecosistemaRol;
    }

    public void setEcosistemaRol(EcosistemaRol ecosistemaRol) {
        this.ecosistemaRol = ecosistemaRol;
    }

    public Ecosistema ecosistemaRol(EcosistemaRol ecosistemaRol) {
        this.setEcosistemaRol(ecosistemaRol);
        return this;
    }

    public Set<UsuarioEcosistema> getUsurioecosistemas() {
        return this.usurioecosistemas;
    }

    public void setUsurioecosistemas(Set<UsuarioEcosistema> usuarioEcosistemas) {
        if (this.usurioecosistemas != null) {
            this.usurioecosistemas.forEach(i -> i.removeEcosistema(this));
        }
        if (usuarioEcosistemas != null) {
            usuarioEcosistemas.forEach(i -> i.addEcosistema(this));
        }
        this.usurioecosistemas = usuarioEcosistemas;
    }

    public Ecosistema usurioecosistemas(Set<UsuarioEcosistema> usuarioEcosistemas) {
        this.setUsurioecosistemas(usuarioEcosistemas);
        return this;
    }

    public Ecosistema addUsurioecosistema(UsuarioEcosistema usuarioEcosistema) {
        this.usurioecosistemas.add(usuarioEcosistema);
        usuarioEcosistema.getEcosistemas().add(this);
        return this;
    }

    public Ecosistema removeUsurioecosistema(UsuarioEcosistema usuarioEcosistema) {
        this.usurioecosistemas.remove(usuarioEcosistema);
        usuarioEcosistema.getEcosistemas().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ecosistema)) {
            return false;
        }
        return id != null && id.equals(((Ecosistema) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ecosistema{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", tematica='" + getTematica() + "'" +
            ", activo='" + getActivo() + "'" +
            ", logoUrl='" + getLogoUrl() + "'" +
            ", logoUrlContentType='" + getLogoUrlContentType() + "'" +
            ", ranking=" + getRanking() +
            ", usuariosCant=" + getUsuariosCant() +
            ", retosCant=" + getRetosCant() +
            ", ideasCant=" + getIdeasCant() +
            "}";
    }
}
