package com.mycompany.myapp.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Tramite.
 */
@Entity
@Table(name = "tramite")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Tramite implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "inscripcion")
    private String inscripcion;

    @Column(name = "prueba_experimental")
    private String pruebaExperimental;

    @Column(name = "exmanen_evaluacion")
    private String exmanenEvaluacion;

    @Column(name = "dictamen")
    private String dictamen;

    @Column(name = "concesion")
    private Boolean concesion;

    @Column(name = "denegado")
    private Boolean denegado;

    @Column(name = "reclamacion")
    private Boolean reclamacion;

    @Column(name = "anulacion")
    private Boolean anulacion;

    @Column(name = "fecha_notificacion")
    private LocalDate fechaNotificacion;

    @Column(name = "feca_certificado")
    private LocalDate fecaCertificado;

    @Column(name = "observacion")
    private String observacion;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Tramite id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInscripcion() {
        return this.inscripcion;
    }

    public Tramite inscripcion(String inscripcion) {
        this.setInscripcion(inscripcion);
        return this;
    }

    public void setInscripcion(String inscripcion) {
        this.inscripcion = inscripcion;
    }

    public String getPruebaExperimental() {
        return this.pruebaExperimental;
    }

    public Tramite pruebaExperimental(String pruebaExperimental) {
        this.setPruebaExperimental(pruebaExperimental);
        return this;
    }

    public void setPruebaExperimental(String pruebaExperimental) {
        this.pruebaExperimental = pruebaExperimental;
    }

    public String getExmanenEvaluacion() {
        return this.exmanenEvaluacion;
    }

    public Tramite exmanenEvaluacion(String exmanenEvaluacion) {
        this.setExmanenEvaluacion(exmanenEvaluacion);
        return this;
    }

    public void setExmanenEvaluacion(String exmanenEvaluacion) {
        this.exmanenEvaluacion = exmanenEvaluacion;
    }

    public String getDictamen() {
        return this.dictamen;
    }

    public Tramite dictamen(String dictamen) {
        this.setDictamen(dictamen);
        return this;
    }

    public void setDictamen(String dictamen) {
        this.dictamen = dictamen;
    }

    public Boolean getConcesion() {
        return this.concesion;
    }

    public Tramite concesion(Boolean concesion) {
        this.setConcesion(concesion);
        return this;
    }

    public void setConcesion(Boolean concesion) {
        this.concesion = concesion;
    }

    public Boolean getDenegado() {
        return this.denegado;
    }

    public Tramite denegado(Boolean denegado) {
        this.setDenegado(denegado);
        return this;
    }

    public void setDenegado(Boolean denegado) {
        this.denegado = denegado;
    }

    public Boolean getReclamacion() {
        return this.reclamacion;
    }

    public Tramite reclamacion(Boolean reclamacion) {
        this.setReclamacion(reclamacion);
        return this;
    }

    public void setReclamacion(Boolean reclamacion) {
        this.reclamacion = reclamacion;
    }

    public Boolean getAnulacion() {
        return this.anulacion;
    }

    public Tramite anulacion(Boolean anulacion) {
        this.setAnulacion(anulacion);
        return this;
    }

    public void setAnulacion(Boolean anulacion) {
        this.anulacion = anulacion;
    }

    public LocalDate getFechaNotificacion() {
        return this.fechaNotificacion;
    }

    public Tramite fechaNotificacion(LocalDate fechaNotificacion) {
        this.setFechaNotificacion(fechaNotificacion);
        return this;
    }

    public void setFechaNotificacion(LocalDate fechaNotificacion) {
        this.fechaNotificacion = fechaNotificacion;
    }

    public LocalDate getFecaCertificado() {
        return this.fecaCertificado;
    }

    public Tramite fecaCertificado(LocalDate fecaCertificado) {
        this.setFecaCertificado(fecaCertificado);
        return this;
    }

    public void setFecaCertificado(LocalDate fecaCertificado) {
        this.fecaCertificado = fecaCertificado;
    }

    public String getObservacion() {
        return this.observacion;
    }

    public Tramite observacion(String observacion) {
        this.setObservacion(observacion);
        return this;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tramite)) {
            return false;
        }
        return id != null && id.equals(((Tramite) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Tramite{" +
            "id=" + getId() +
            ", inscripcion='" + getInscripcion() + "'" +
            ", pruebaExperimental='" + getPruebaExperimental() + "'" +
            ", exmanenEvaluacion='" + getExmanenEvaluacion() + "'" +
            ", dictamen='" + getDictamen() + "'" +
            ", concesion='" + getConcesion() + "'" +
            ", denegado='" + getDenegado() + "'" +
            ", reclamacion='" + getReclamacion() + "'" +
            ", anulacion='" + getAnulacion() + "'" +
            ", fechaNotificacion='" + getFechaNotificacion() + "'" +
            ", fecaCertificado='" + getFecaCertificado() + "'" +
            ", observacion='" + getObservacion() + "'" +
            "}";
    }
}
