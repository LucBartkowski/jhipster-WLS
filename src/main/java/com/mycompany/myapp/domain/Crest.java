package com.mycompany.myapp.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Crest.
 */
@Entity
@Table(name = "crest")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "crest")
public class Crest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "verantwoordelijke_crest", nullable = false)
    private String verantwoordelijkeCrest;

    @NotNull
    @Column(name = "naam_entiteit", nullable = false)
    private String naamEntiteit;

    @OneToMany(mappedBy = "verantwoordelijkeCrest")
    private Set<Geregistreerde> geregistreerdes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVerantwoordelijkeCrest() {
        return verantwoordelijkeCrest;
    }

    public Crest verantwoordelijkeCrest(String verantwoordelijkeCrest) {
        this.verantwoordelijkeCrest = verantwoordelijkeCrest;
        return this;
    }

    public void setVerantwoordelijkeCrest(String verantwoordelijkeCrest) {
        this.verantwoordelijkeCrest = verantwoordelijkeCrest;
    }

    public String getNaamEntiteit() {
        return naamEntiteit;
    }

    public Crest naamEntiteit(String naamEntiteit) {
        this.naamEntiteit = naamEntiteit;
        return this;
    }

    public void setNaamEntiteit(String naamEntiteit) {
        this.naamEntiteit = naamEntiteit;
    }

    public Set<Geregistreerde> getGeregistreerdes() {
        return geregistreerdes;
    }

    public Crest geregistreerdes(Set<Geregistreerde> geregistreerdes) {
        this.geregistreerdes = geregistreerdes;
        return this;
    }

    public Crest addGeregistreerde(Geregistreerde geregistreerde) {
        this.geregistreerdes.add(geregistreerde);
        geregistreerde.setVerantwoordelijkeCrest(this);
        return this;
    }

    public Crest removeGeregistreerde(Geregistreerde geregistreerde) {
        this.geregistreerdes.remove(geregistreerde);
        geregistreerde.setVerantwoordelijkeCrest(null);
        return this;
    }

    public void setGeregistreerdes(Set<Geregistreerde> geregistreerdes) {
        this.geregistreerdes = geregistreerdes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Crest)) {
            return false;
        }
        return id != null && id.equals(((Crest) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Crest{" +
            "id=" + getId() +
            ", verantwoordelijkeCrest='" + getVerantwoordelijkeCrest() + "'" +
            ", naamEntiteit='" + getNaamEntiteit() + "'" +
            "}";
    }
}
