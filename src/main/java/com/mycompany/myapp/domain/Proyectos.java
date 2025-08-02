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
 * A Proyectos.
 */
@Entity
@Table(name = "proyectos")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Proyectos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Lob
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @NotNull
    @Column(name = "autor", nullable = false)
    private String autor;

    @Column(name = "entidad_gestora", nullable = false)
    private String entidadGestora;

    @Column(name = "aprobada")
    private Boolean aprobada;

    public Boolean getAprobada() {
        return aprobada;
    }

    public void setAprobada(Boolean aprobada) {
        this.aprobada = aprobada;
    }

    public String getEntidadGestora() {
        return entidadGestora;
    }

    public void setEntidadGestora(String entidadGestora) {
        this.entidadGestora = entidadGestora;
    }

    public String getEntidad_Ejecutora() {
        return entidad_Ejecutora;
    }

    public void setEntidad_Ejecutora(String entidad_Ejecutora) {
        this.entidad_Ejecutora = entidad_Ejecutora;
    }

    public String getEntidadParticipantes() {
        return entidadParticipantes;
    }

    public void setEntidadParticipantes(String entidadParticipantes) {
        this.entidadParticipantes = entidadParticipantes;
    }

    @Column(name = "entidad_ejecutora", nullable = false)
    private String entidad_Ejecutora;

    @Column(name = "entidad_participantes", nullable = false)
    private String entidadParticipantes;

    @NotNull
    @Column(name = "necesidad", nullable = false)
    private String necesidad;

    @NotNull
    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @NotNull
    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    @Lob
    @Column(name = "logo_url")
    private byte[] logoUrl;

    @Column(name = "logo_url_content_type")
    private String logoUrlContentType;

    @OneToMany(mappedBy = "proyectos")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "proyectos" }, allowSetters = true)
    private Set<Participantes> partipantes = new HashSet<>();

    @ManyToOne
    private User user;

    @ManyToMany
    @JoinTable(
        name = "rel_proyectos__sector",
        joinColumns = @JoinColumn(name = "proyectos_id"),
        inverseJoinColumns = @JoinColumn(name = "sector_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "proyectos" }, allowSetters = true)
    private Set<Sector> sectors = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_proyectos__linea_investigacion",
        joinColumns = @JoinColumn(name = "proyectos_id"),
        inverseJoinColumns = @JoinColumn(name = "linea_investigacion_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "proyectos" }, allowSetters = true)
    private Set<LineaInvestigacion> lineaInvestigacions = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_proyectos__ods",
        joinColumns = @JoinColumn(name = "proyectos_id"),
        inverseJoinColumns = @JoinColumn(name = "ods_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "proyectos" }, allowSetters = true)
    private Set<Ods> ods = new HashSet<>();

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
    @JsonIgnoreProperties(value = { "proyectos" }, allowSetters = true)
    private TipoProyecto tipoProyecto;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Proyectos id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Proyectos nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public Proyectos descripcion(String descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getAutor() {
        return this.autor;
    }

    public Proyectos autor(String autor) {
        this.setAutor(autor);
        return this;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getNecesidad() {
        return this.necesidad;
    }

    public Proyectos necesidad(String necesidad) {
        this.setNecesidad(necesidad);
        return this;
    }

    public void setNecesidad(String necesidad) {
        this.necesidad = necesidad;
    }

    public LocalDate getFechaInicio() {
        return this.fechaInicio;
    }

    public Proyectos fechaInicio(LocalDate fechaInicio) {
        this.setFechaInicio(fechaInicio);
        return this;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return this.fechaFin;
    }

    public Proyectos fechaFin(LocalDate fechaFin) {
        this.setFechaFin(fechaFin);
        return this;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public byte[] getLogoUrl() {
        return this.logoUrl;
    }

    public Proyectos logoUrl(byte[] logoUrl) {
        this.setLogoUrl(logoUrl);
        return this;
    }

    public void setLogoUrl(byte[] logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getLogoUrlContentType() {
        return this.logoUrlContentType;
    }

    public Proyectos logoUrlContentType(String logoUrlContentType) {
        this.logoUrlContentType = logoUrlContentType;
        return this;
    }

    public void setLogoUrlContentType(String logoUrlContentType) {
        this.logoUrlContentType = logoUrlContentType;
    }

    public Set<Participantes> getPartipantes() {
        return this.partipantes;
    }

    public void setPartipantes(Set<Participantes> participantes) {
        if (this.partipantes != null) {
            this.partipantes.forEach(i -> i.setProyectos(null));
        }
        if (participantes != null) {
            participantes.forEach(i -> i.setProyectos(this));
        }
        this.partipantes = participantes;
    }

    public Proyectos partipantes(Set<Participantes> participantes) {
        this.setPartipantes(participantes);
        return this;
    }

    public Proyectos addPartipantes(Participantes participantes) {
        this.partipantes.add(participantes);
        participantes.setProyectos(this);
        return this;
    }

    public Proyectos removePartipantes(Participantes participantes) {
        this.partipantes.remove(participantes);
        participantes.setProyectos(null);
        return this;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Proyectos user(User user) {
        this.setUser(user);
        return this;
    }

    public Set<Sector> getSectors() {
        return this.sectors;
    }

    public void setSectors(Set<Sector> sectors) {
        this.sectors = sectors;
    }

    public Proyectos sectors(Set<Sector> sectors) {
        this.setSectors(sectors);
        return this;
    }

    public Proyectos addSector(Sector sector) {
        this.sectors.add(sector);
        sector.getProyectos().add(this);
        return this;
    }

    public Proyectos removeSector(Sector sector) {
        this.sectors.remove(sector);
        sector.getProyectos().remove(this);
        return this;
    }

    public Set<LineaInvestigacion> getLineaInvestigacions() {
        return this.lineaInvestigacions;
    }

    public void setLineaInvestigacions(Set<LineaInvestigacion> lineaInvestigacions) {
        this.lineaInvestigacions = lineaInvestigacions;
    }

    public Proyectos lineaInvestigacions(Set<LineaInvestigacion> lineaInvestigacions) {
        this.setLineaInvestigacions(lineaInvestigacions);
        return this;
    }

    public Proyectos addLineaInvestigacion(LineaInvestigacion lineaInvestigacion) {
        this.lineaInvestigacions.add(lineaInvestigacion);
        lineaInvestigacion.getProyectos().add(this);
        return this;
    }

    public Proyectos removeLineaInvestigacion(LineaInvestigacion lineaInvestigacion) {
        this.lineaInvestigacions.remove(lineaInvestigacion);
        lineaInvestigacion.getProyectos().remove(this);
        return this;
    }

    public Set<Ods> getOds() {
        return this.ods;
    }

    public void setOds(Set<Ods> ods) {
        this.ods = ods;
    }

    public Proyectos ods(Set<Ods> ods) {
        this.setOds(ods);
        return this;
    }

    public Proyectos addOds(Ods ods) {
        this.ods.add(ods);
        ods.getProyectos().add(this);
        return this;
    }

    public Proyectos removeOds(Ods ods) {
        this.ods.remove(ods);
        ods.getProyectos().remove(this);
        return this;
    }

    public Ecosistema getEcosistema() {
        return this.ecosistema;
    }

    public void setEcosistema(Ecosistema ecosistema) {
        this.ecosistema = ecosistema;
    }

    public Proyectos ecosistema(Ecosistema ecosistema) {
        this.setEcosistema(ecosistema);
        return this;
    }

    public TipoProyecto getTipoProyecto() {
        return this.tipoProyecto;
    }

    public void setTipoProyecto(TipoProyecto tipoProyecto) {
        this.tipoProyecto = tipoProyecto;
    }

    public Proyectos tipoProyecto(TipoProyecto tipoProyecto) {
        this.setTipoProyecto(tipoProyecto);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Proyectos)) {
            return false;
        }
        return id != null && id.equals(((Proyectos) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Proyectos{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", autor='" + getAutor() + "'" +
            ", necesidad='" + getNecesidad() + "'" +
            ", fechaInicio='" + getFechaInicio() + "'" +
            ", fechaFin='" + getFechaFin() + "'" +
            ", logoUrl='" + getLogoUrl() + "'" +
            ", logoUrlContentType='" + getLogoUrlContentType() + "'" +
            "}";
    }
}
