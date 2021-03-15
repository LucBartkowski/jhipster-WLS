package com.mycompany.myapp.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Classificatie.
 */
@Entity
@Table(name = "classificatie")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "classificatie")
public class Classificatie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "classificatie", nullable = false)
    private String classificatie;

    @Column(name = "kleur")
    private String kleur;

    @NotNull
    @Column(name = "omschrijving", nullable = false)
    private String omschrijving;

    @OneToMany(mappedBy = "classificatie")
    private Set<Geregistreerde> geregistreerdes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClassificatie() {
        return classificatie;
    }

    public Classificatie classificatie(String classificatie) {
        this.classificatie = classificatie;
        return this;
    }

    public void setClassificatie(String classificatie) {
        this.classificatie = classificatie;
    }

    public String getKleur() {
        return kleur;
    }

    public Classificatie kleur(String kleur) {
        this.kleur = kleur;
        return this;
    }

    public void setKleur(String kleur) {
        this.kleur = kleur;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public Classificatie omschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Set<Geregistreerde> getGeregistreerdes() {
        return geregistreerdes;
    }

    public Classificatie geregistreerdes(Set<Geregistreerde> geregistreerdes) {
        this.geregistreerdes = geregistreerdes;
        return this;
    }

    public Classificatie addGeregistreerde(Geregistreerde geregistreerde) {
        this.geregistreerdes.add(geregistreerde);
        geregistreerde.setClassificatie(this);
        return this;
    }

    public Classificatie removeGeregistreerde(Geregistreerde geregistreerde) {
        this.geregistreerdes.remove(geregistreerde);
        geregistreerde.setClassificatie(null);
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
        if (!(o instanceof Classificatie)) {
            return false;
        }
        return id != null && id.equals(((Classificatie) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Classificatie{" +
            "id=" + getId() +
            ", classificatie='" + getClassificatie() + "'" +
            ", kleur='" + getKleur() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            "}";
    }
}
