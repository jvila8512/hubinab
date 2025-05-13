package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A EcosistemaComponente.
 */
@Entity
@Table(name = "ecosistema_componente")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EcosistemaComponente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "link")
    private String link;

    @Lob
    @Column(name = "componentehijo", nullable = false)
    private String componentehijo;

    @Lob
    @Column(name = "documento_url")
    private byte[] documentoUrl;

    @Lob
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Column(name = "documento_url_content_type")
    private String documentoUrlContentType;

    @ManyToOne
    private Componentes componente;

    public String getComponentehijo() {
        return componentehijo;
    }

    public void setComponentehijo(String componentehijo) {
        this.componentehijo = componentehijo;
    }

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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public EcosistemaComponente id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLink() {
        return this.link;
    }

    public EcosistemaComponente link(String link) {
        this.setLink(link);
        return this;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public byte[] getDocumentoUrl() {
        return this.documentoUrl;
    }

    public EcosistemaComponente documentoUrl(byte[] documentoUrl) {
        this.setDocumentoUrl(documentoUrl);
        return this;
    }

    public void setDocumentoUrl(byte[] documentoUrl) {
        this.documentoUrl = documentoUrl;
    }

    public String getDocumentoUrlContentType() {
        return this.documentoUrlContentType;
    }

    public EcosistemaComponente documentoUrlContentType(String documentoUrlContentType) {
        this.documentoUrlContentType = documentoUrlContentType;
        return this;
    }

    public void setDocumentoUrlContentType(String documentoUrlContentType) {
        this.documentoUrlContentType = documentoUrlContentType;
    }

    public Componentes getComponente() {
        return this.componente;
    }

    public void setComponente(Componentes componentes) {
        this.componente = componentes;
    }

    public EcosistemaComponente componente(Componentes componentes) {
        this.setComponente(componentes);
        return this;
    }

    public Ecosistema getEcosistema() {
        return this.ecosistema;
    }

    public void setEcosistema(Ecosistema ecosistema) {
        this.ecosistema = ecosistema;
    }

    public EcosistemaComponente ecosistema(Ecosistema ecosistema) {
        this.setEcosistema(ecosistema);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EcosistemaComponente)) {
            return false;
        }
        return id != null && id.equals(((EcosistemaComponente) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EcosistemaComponente{" +
            "id=" + getId() +
            ", link='" + getLink() + "'" +
            ", documentoUrl='" + getDocumentoUrl() + "'" +
            ", documentoUrlContentType='" + getDocumentoUrlContentType() + "'" +
            "}";
    }
}
