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
 * A TipoNoticia.
 */
@Entity
@Table(name = "tipo_noticia")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TipoNoticia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "tipo_noticia", nullable = false)
    private String tipoNoticia;

    @OneToMany(mappedBy = "tipoNoticia")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "user", "ecosistema", "tipoNoticia" }, allowSetters = true)
    private Set<Noticias> noticias = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TipoNoticia id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoNoticia() {
        return this.tipoNoticia;
    }

    public TipoNoticia tipoNoticia(String tipoNoticia) {
        this.setTipoNoticia(tipoNoticia);
        return this;
    }

    public void setTipoNoticia(String tipoNoticia) {
        this.tipoNoticia = tipoNoticia;
    }

    public Set<Noticias> getNoticias() {
        return this.noticias;
    }

    public void setNoticias(Set<Noticias> noticias) {
        if (this.noticias != null) {
            this.noticias.forEach(i -> i.setTipoNoticia(null));
        }
        if (noticias != null) {
            noticias.forEach(i -> i.setTipoNoticia(this));
        }
        this.noticias = noticias;
    }

    public TipoNoticia noticias(Set<Noticias> noticias) {
        this.setNoticias(noticias);
        return this;
    }

    public TipoNoticia addNoticias(Noticias noticias) {
        this.noticias.add(noticias);
        noticias.setTipoNoticia(this);
        return this;
    }

    public TipoNoticia removeNoticias(Noticias noticias) {
        this.noticias.remove(noticias);
        noticias.setTipoNoticia(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TipoNoticia)) {
            return false;
        }
        return id != null && id.equals(((TipoNoticia) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TipoNoticia{" +
            "id=" + getId() +
            ", tipoNoticia='" + getTipoNoticia() + "'" +
            "}";
    }
}
