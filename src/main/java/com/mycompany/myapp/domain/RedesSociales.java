package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A RedesSociales.
 */
@Entity
@Table(name = "redes_sociales")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RedesSociales implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "redes_url", nullable = false)
    private String redesUrl;

    @Lob
    @Column(name = "logo_url", nullable = false)
    private byte[] logoUrl;

    @NotNull
    @Column(name = "logo_url_content_type", nullable = false)
    private String logoUrlContentType;

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

    public RedesSociales id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRedesUrl() {
        return this.redesUrl;
    }

    public RedesSociales redesUrl(String redesUrl) {
        this.setRedesUrl(redesUrl);
        return this;
    }

    public void setRedesUrl(String redesUrl) {
        this.redesUrl = redesUrl;
    }

    public byte[] getLogoUrl() {
        return this.logoUrl;
    }

    public RedesSociales logoUrl(byte[] logoUrl) {
        this.setLogoUrl(logoUrl);
        return this;
    }

    public void setLogoUrl(byte[] logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getLogoUrlContentType() {
        return this.logoUrlContentType;
    }

    public RedesSociales logoUrlContentType(String logoUrlContentType) {
        this.logoUrlContentType = logoUrlContentType;
        return this;
    }

    public void setLogoUrlContentType(String logoUrlContentType) {
        this.logoUrlContentType = logoUrlContentType;
    }

    public Ecosistema getEcosistema() {
        return this.ecosistema;
    }

    public void setEcosistema(Ecosistema ecosistema) {
        this.ecosistema = ecosistema;
    }

    public RedesSociales ecosistema(Ecosistema ecosistema) {
        this.setEcosistema(ecosistema);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RedesSociales)) {
            return false;
        }
        return id != null && id.equals(((RedesSociales) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RedesSociales{" +
            "id=" + getId() +
            ", redesUrl='" + getRedesUrl() + "'" +
            ", logoUrl='" + getLogoUrl() + "'" +
            ", logoUrlContentType='" + getLogoUrlContentType() + "'" +
            "}";
    }
}
