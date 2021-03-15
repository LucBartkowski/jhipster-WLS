package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Instant;

/**
 * A Geregistreerde.
 */
@Entity
@Table(name = "geregistreerde")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "geregistreerde")
public class Geregistreerde implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "voornamen", nullable = false)
    private String voornamen;

    @NotNull
    @Column(name = "achternaam", nullable = false)
    private String achternaam;

    @NotNull
    @Column(name = "geboortedatum", nullable = false)
    private Instant geboortedatum;

    @NotNull
    @Column(name = "geboorteplaats", nullable = false)
    private String geboorteplaats;

    @NotNull
    @Column(name = "register_nummer", nullable = false)
    private Long registerNummer;

    @NotNull
    @Column(name = "classificatie", nullable = false)
    private String classificatie;

    @Column(name = "personeelnummer")
    private String personeelnummer;

    @Column(name = "mailadres")
    private String mailadres;

    @Column(name = "telefoon_nummer")
    private String telefoonNummer;

    @Column(name = "mobiele_nummer")
    private String mobieleNummer;

    @NotNull
    @Column(name = "verantwoordelijke_crest", nullable = false)
    private String verantwoordelijkeCrest;

    @NotNull
    @Column(name = "naam", nullable = false)
    private String naam;

    @ManyToOne
    @JsonIgnoreProperties(value = "geregistreerdes", allowSetters = true)
    private Crest verantwoordelijkeCrest;

    @ManyToOne
    @JsonIgnoreProperties(value = "geregistreerdes", allowSetters = true)
    private Medewerker naam;

    @ManyToOne
    @JsonIgnoreProperties(value = "geregistreerdes", allowSetters = true)
    private Classificatie classificatie;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVoornamen() {
        return voornamen;
    }

    public Geregistreerde voornamen(String voornamen) {
        this.voornamen = voornamen;
        return this;
    }

    public void setVoornamen(String voornamen) {
        this.voornamen = voornamen;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public Geregistreerde achternaam(String achternaam) {
        this.achternaam = achternaam;
        return this;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public Instant getGeboortedatum() {
        return geboortedatum;
    }

    public Geregistreerde geboortedatum(Instant geboortedatum) {
        this.geboortedatum = geboortedatum;
        return this;
    }

    public void setGeboortedatum(Instant geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    public String getGeboorteplaats() {
        return geboorteplaats;
    }

    public Geregistreerde geboorteplaats(String geboorteplaats) {
        this.geboorteplaats = geboorteplaats;
        return this;
    }

    public void setGeboorteplaats(String geboorteplaats) {
        this.geboorteplaats = geboorteplaats;
    }

    public Long getRegisterNummer() {
        return registerNummer;
    }

    public Geregistreerde registerNummer(Long registerNummer) {
        this.registerNummer = registerNummer;
        return this;
    }

    public void setRegisterNummer(Long registerNummer) {
        this.registerNummer = registerNummer;
    }

    public String getClassificatie() {
        return classificatie;
    }

    public Geregistreerde classificatie(String classificatie) {
        this.classificatie = classificatie;
        return this;
    }

    public void setClassificatie(String classificatie) {
        this.classificatie = classificatie;
    }

    public String getPersoneelnummer() {
        return personeelnummer;
    }

    public Geregistreerde personeelnummer(String personeelnummer) {
        this.personeelnummer = personeelnummer;
        return this;
    }

    public void setPersoneelnummer(String personeelnummer) {
        this.personeelnummer = personeelnummer;
    }

    public String getMailadres() {
        return mailadres;
    }

    public Geregistreerde mailadres(String mailadres) {
        this.mailadres = mailadres;
        return this;
    }

    public void setMailadres(String mailadres) {
        this.mailadres = mailadres;
    }

    public String getTelefoonNummer() {
        return telefoonNummer;
    }

    public Geregistreerde telefoonNummer(String telefoonNummer) {
        this.telefoonNummer = telefoonNummer;
        return this;
    }

    public void setTelefoonNummer(String telefoonNummer) {
        this.telefoonNummer = telefoonNummer;
    }

    public String getMobieleNummer() {
        return mobieleNummer;
    }

    public Geregistreerde mobieleNummer(String mobieleNummer) {
        this.mobieleNummer = mobieleNummer;
        return this;
    }

    public void setMobieleNummer(String mobieleNummer) {
        this.mobieleNummer = mobieleNummer;
    }

    public String getVerantwoordelijkeCrest() {
        return verantwoordelijkeCrest;
    }

    public Geregistreerde verantwoordelijkeCrest(String verantwoordelijkeCrest) {
        this.verantwoordelijkeCrest = verantwoordelijkeCrest;
        return this;
    }

    public void setVerantwoordelijkeCrest(String verantwoordelijkeCrest) {
        this.verantwoordelijkeCrest = verantwoordelijkeCrest;
    }

    public String getNaam() {
        return naam;
    }

    public Geregistreerde naam(String naam) {
        this.naam = naam;
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public Crest getVerantwoordelijkeCrest() {
        return verantwoordelijkeCrest;
    }

    public Geregistreerde verantwoordelijkeCrest(Crest crest) {
        this.verantwoordelijkeCrest = crest;
        return this;
    }

    public void setVerantwoordelijkeCrest(Crest crest) {
        this.verantwoordelijkeCrest = crest;
    }

    public Medewerker getNaam() {
        return naam;
    }

    public Geregistreerde naam(Medewerker medewerker) {
        this.naam = medewerker;
        return this;
    }

    public void setNaam(Medewerker medewerker) {
        this.naam = medewerker;
    }

    public Classificatie getClassificatie() {
        return classificatie;
    }

    public Geregistreerde classificatie(Classificatie classificatie) {
        this.classificatie = classificatie;
        return this;
    }

    public void setClassificatie(Classificatie classificatie) {
        this.classificatie = classificatie;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Geregistreerde)) {
            return false;
        }
        return id != null && id.equals(((Geregistreerde) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Geregistreerde{" +
            "id=" + getId() +
            ", voornamen='" + getVoornamen() + "'" +
            ", achternaam='" + getAchternaam() + "'" +
            ", geboortedatum='" + getGeboortedatum() + "'" +
            ", geboorteplaats='" + getGeboorteplaats() + "'" +
            ", registerNummer=" + getRegisterNummer() +
            ", classificatie='" + getClassificatie() + "'" +
            ", personeelnummer='" + getPersoneelnummer() + "'" +
            ", mailadres='" + getMailadres() + "'" +
            ", telefoonNummer='" + getTelefoonNummer() + "'" +
            ", mobieleNummer='" + getMobieleNummer() + "'" +
            ", verantwoordelijkeCrest='" + getVerantwoordelijkeCrest() + "'" +
            ", naam='" + getNaam() + "'" +
            "}";
    }
}
