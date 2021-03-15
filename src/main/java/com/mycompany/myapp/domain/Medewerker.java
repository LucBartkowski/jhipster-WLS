package com.mycompany.myapp.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Medewerker.
 */
@Entity
@Table(name = "medewerker")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "medewerker")
public class Medewerker implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "naam", nullable = false)
    private String naam;

    @NotNull
    @Column(name = "functie", nullable = false)
    private String functie;

    /**
     * A relationship
     */
    @ApiModelProperty(value = "A relationship")
    @OneToMany(mappedBy = "naam")
    private Set<Geregistreerde> geregistreerdes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaam() {
        return naam;
    }

    public Medewerker naam(String naam) {
        this.naam = naam;
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getFunctie() {
        return functie;
    }

    public Medewerker functie(String functie) {
        this.functie = functie;
        return this;
    }

    public void setFunctie(String functie) {
        this.functie = functie;
    }

    public Set<Geregistreerde> getGeregistreerdes() {
        return geregistreerdes;
    }

    public Medewerker geregistreerdes(Set<Geregistreerde> geregistreerdes) {
        this.geregistreerdes = geregistreerdes;
        return this;
    }

    public Medewerker addGeregistreerde(Geregistreerde geregistreerde) {
        this.geregistreerdes.add(geregistreerde);
        geregistreerde.setNaam(this);
        return this;
    }

    public Medewerker removeGeregistreerde(Geregistreerde geregistreerde) {
        this.geregistreerdes.remove(geregistreerde);
        geregistreerde.setNaam(null);
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
        if (!(o instanceof Medewerker)) {
            return false;
        }
        return id != null && id.equals(((Medewerker) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Medewerker{" +
            "id=" + getId() +
            ", naam='" + getNaam() + "'" +
            ", functie='" + getFunctie() + "'" +
            "}";
    }
}
