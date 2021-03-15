package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterWlsApp;
import com.mycompany.myapp.domain.Geregistreerde;
import com.mycompany.myapp.repository.GeregistreerdeRepository;
import com.mycompany.myapp.repository.search.GeregistreerdeSearchRepository;
import com.mycompany.myapp.service.GeregistreerdeService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link GeregistreerdeResource} REST controller.
 */
@SpringBootTest(classes = JhipsterWlsApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class GeregistreerdeResourceIT {

    private static final String DEFAULT_VOORNAMEN = "AAAAAAAAAA";
    private static final String UPDATED_VOORNAMEN = "BBBBBBBBBB";

    private static final String DEFAULT_ACHTERNAAM = "AAAAAAAAAA";
    private static final String UPDATED_ACHTERNAAM = "BBBBBBBBBB";

    private static final Instant DEFAULT_GEBOORTEDATUM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_GEBOORTEDATUM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_GEBOORTEPLAATS = "AAAAAAAAAA";
    private static final String UPDATED_GEBOORTEPLAATS = "BBBBBBBBBB";

    private static final Long DEFAULT_REGISTER_NUMMER = 1L;
    private static final Long UPDATED_REGISTER_NUMMER = 2L;

    private static final String DEFAULT_CLASSIFICATIE = "AAAAAAAAAA";
    private static final String UPDATED_CLASSIFICATIE = "BBBBBBBBBB";

    private static final String DEFAULT_PERSONEELNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_PERSONEELNUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_MAILADRES = "AAAAAAAAAA";
    private static final String UPDATED_MAILADRES = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFOON_NUMMER = "AAAAAAAAAA";
    private static final String UPDATED_TELEFOON_NUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_MOBIELE_NUMMER = "AAAAAAAAAA";
    private static final String UPDATED_MOBIELE_NUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_VERANTWOORDELIJKE_CREST = "AAAAAAAAAA";
    private static final String UPDATED_VERANTWOORDELIJKE_CREST = "BBBBBBBBBB";

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    @Autowired
    private GeregistreerdeRepository geregistreerdeRepository;

    @Autowired
    private GeregistreerdeService geregistreerdeService;

    /**
     * This repository is mocked in the com.mycompany.myapp.repository.search test package.
     *
     * @see com.mycompany.myapp.repository.search.GeregistreerdeSearchRepositoryMockConfiguration
     */
    @Autowired
    private GeregistreerdeSearchRepository mockGeregistreerdeSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGeregistreerdeMockMvc;

    private Geregistreerde geregistreerde;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Geregistreerde createEntity(EntityManager em) {
        Geregistreerde geregistreerde = new Geregistreerde()
            .voornamen(DEFAULT_VOORNAMEN)
            .achternaam(DEFAULT_ACHTERNAAM)
            .geboortedatum(DEFAULT_GEBOORTEDATUM)
            .geboorteplaats(DEFAULT_GEBOORTEPLAATS)
            .registerNummer(DEFAULT_REGISTER_NUMMER)
            .classificatie(DEFAULT_CLASSIFICATIE)
            .personeelnummer(DEFAULT_PERSONEELNUMMER)
            .mailadres(DEFAULT_MAILADRES)
            .telefoonNummer(DEFAULT_TELEFOON_NUMMER)
            .mobieleNummer(DEFAULT_MOBIELE_NUMMER)
            .verantwoordelijkeCrest(DEFAULT_VERANTWOORDELIJKE_CREST)
            .naam(DEFAULT_NAAM);
        return geregistreerde;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Geregistreerde createUpdatedEntity(EntityManager em) {
        Geregistreerde geregistreerde = new Geregistreerde()
            .voornamen(UPDATED_VOORNAMEN)
            .achternaam(UPDATED_ACHTERNAAM)
            .geboortedatum(UPDATED_GEBOORTEDATUM)
            .geboorteplaats(UPDATED_GEBOORTEPLAATS)
            .registerNummer(UPDATED_REGISTER_NUMMER)
            .classificatie(UPDATED_CLASSIFICATIE)
            .personeelnummer(UPDATED_PERSONEELNUMMER)
            .mailadres(UPDATED_MAILADRES)
            .telefoonNummer(UPDATED_TELEFOON_NUMMER)
            .mobieleNummer(UPDATED_MOBIELE_NUMMER)
            .verantwoordelijkeCrest(UPDATED_VERANTWOORDELIJKE_CREST)
            .naam(UPDATED_NAAM);
        return geregistreerde;
    }

    @BeforeEach
    public void initTest() {
        geregistreerde = createEntity(em);
    }

    @Test
    @Transactional
    public void createGeregistreerde() throws Exception {
        int databaseSizeBeforeCreate = geregistreerdeRepository.findAll().size();
        // Create the Geregistreerde
        restGeregistreerdeMockMvc.perform(post("/api/geregistreerdes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(geregistreerde)))
            .andExpect(status().isCreated());

        // Validate the Geregistreerde in the database
        List<Geregistreerde> geregistreerdeList = geregistreerdeRepository.findAll();
        assertThat(geregistreerdeList).hasSize(databaseSizeBeforeCreate + 1);
        Geregistreerde testGeregistreerde = geregistreerdeList.get(geregistreerdeList.size() - 1);
        assertThat(testGeregistreerde.getVoornamen()).isEqualTo(DEFAULT_VOORNAMEN);
        assertThat(testGeregistreerde.getAchternaam()).isEqualTo(DEFAULT_ACHTERNAAM);
        assertThat(testGeregistreerde.getGeboortedatum()).isEqualTo(DEFAULT_GEBOORTEDATUM);
        assertThat(testGeregistreerde.getGeboorteplaats()).isEqualTo(DEFAULT_GEBOORTEPLAATS);
        assertThat(testGeregistreerde.getRegisterNummer()).isEqualTo(DEFAULT_REGISTER_NUMMER);
        assertThat(testGeregistreerde.getClassificatie()).isEqualTo(DEFAULT_CLASSIFICATIE);
        assertThat(testGeregistreerde.getPersoneelnummer()).isEqualTo(DEFAULT_PERSONEELNUMMER);
        assertThat(testGeregistreerde.getMailadres()).isEqualTo(DEFAULT_MAILADRES);
        assertThat(testGeregistreerde.getTelefoonNummer()).isEqualTo(DEFAULT_TELEFOON_NUMMER);
        assertThat(testGeregistreerde.getMobieleNummer()).isEqualTo(DEFAULT_MOBIELE_NUMMER);
        assertThat(testGeregistreerde.getVerantwoordelijkeCrest()).isEqualTo(DEFAULT_VERANTWOORDELIJKE_CREST);
        assertThat(testGeregistreerde.getNaam()).isEqualTo(DEFAULT_NAAM);

        // Validate the Geregistreerde in Elasticsearch
        verify(mockGeregistreerdeSearchRepository, times(1)).save(testGeregistreerde);
    }

    @Test
    @Transactional
    public void createGeregistreerdeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = geregistreerdeRepository.findAll().size();

        // Create the Geregistreerde with an existing ID
        geregistreerde.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGeregistreerdeMockMvc.perform(post("/api/geregistreerdes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(geregistreerde)))
            .andExpect(status().isBadRequest());

        // Validate the Geregistreerde in the database
        List<Geregistreerde> geregistreerdeList = geregistreerdeRepository.findAll();
        assertThat(geregistreerdeList).hasSize(databaseSizeBeforeCreate);

        // Validate the Geregistreerde in Elasticsearch
        verify(mockGeregistreerdeSearchRepository, times(0)).save(geregistreerde);
    }


    @Test
    @Transactional
    public void checkVoornamenIsRequired() throws Exception {
        int databaseSizeBeforeTest = geregistreerdeRepository.findAll().size();
        // set the field null
        geregistreerde.setVoornamen(null);

        // Create the Geregistreerde, which fails.


        restGeregistreerdeMockMvc.perform(post("/api/geregistreerdes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(geregistreerde)))
            .andExpect(status().isBadRequest());

        List<Geregistreerde> geregistreerdeList = geregistreerdeRepository.findAll();
        assertThat(geregistreerdeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAchternaamIsRequired() throws Exception {
        int databaseSizeBeforeTest = geregistreerdeRepository.findAll().size();
        // set the field null
        geregistreerde.setAchternaam(null);

        // Create the Geregistreerde, which fails.


        restGeregistreerdeMockMvc.perform(post("/api/geregistreerdes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(geregistreerde)))
            .andExpect(status().isBadRequest());

        List<Geregistreerde> geregistreerdeList = geregistreerdeRepository.findAll();
        assertThat(geregistreerdeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGeboortedatumIsRequired() throws Exception {
        int databaseSizeBeforeTest = geregistreerdeRepository.findAll().size();
        // set the field null
        geregistreerde.setGeboortedatum(null);

        // Create the Geregistreerde, which fails.


        restGeregistreerdeMockMvc.perform(post("/api/geregistreerdes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(geregistreerde)))
            .andExpect(status().isBadRequest());

        List<Geregistreerde> geregistreerdeList = geregistreerdeRepository.findAll();
        assertThat(geregistreerdeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGeboorteplaatsIsRequired() throws Exception {
        int databaseSizeBeforeTest = geregistreerdeRepository.findAll().size();
        // set the field null
        geregistreerde.setGeboorteplaats(null);

        // Create the Geregistreerde, which fails.


        restGeregistreerdeMockMvc.perform(post("/api/geregistreerdes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(geregistreerde)))
            .andExpect(status().isBadRequest());

        List<Geregistreerde> geregistreerdeList = geregistreerdeRepository.findAll();
        assertThat(geregistreerdeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRegisterNummerIsRequired() throws Exception {
        int databaseSizeBeforeTest = geregistreerdeRepository.findAll().size();
        // set the field null
        geregistreerde.setRegisterNummer(null);

        // Create the Geregistreerde, which fails.


        restGeregistreerdeMockMvc.perform(post("/api/geregistreerdes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(geregistreerde)))
            .andExpect(status().isBadRequest());

        List<Geregistreerde> geregistreerdeList = geregistreerdeRepository.findAll();
        assertThat(geregistreerdeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkClassificatieIsRequired() throws Exception {
        int databaseSizeBeforeTest = geregistreerdeRepository.findAll().size();
        // set the field null
        geregistreerde.setClassificatie(null);

        // Create the Geregistreerde, which fails.


        restGeregistreerdeMockMvc.perform(post("/api/geregistreerdes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(geregistreerde)))
            .andExpect(status().isBadRequest());

        List<Geregistreerde> geregistreerdeList = geregistreerdeRepository.findAll();
        assertThat(geregistreerdeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVerantwoordelijkeCrestIsRequired() throws Exception {
        int databaseSizeBeforeTest = geregistreerdeRepository.findAll().size();
        // set the field null
        geregistreerde.setVerantwoordelijkeCrest(null);

        // Create the Geregistreerde, which fails.


        restGeregistreerdeMockMvc.perform(post("/api/geregistreerdes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(geregistreerde)))
            .andExpect(status().isBadRequest());

        List<Geregistreerde> geregistreerdeList = geregistreerdeRepository.findAll();
        assertThat(geregistreerdeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNaamIsRequired() throws Exception {
        int databaseSizeBeforeTest = geregistreerdeRepository.findAll().size();
        // set the field null
        geregistreerde.setNaam(null);

        // Create the Geregistreerde, which fails.


        restGeregistreerdeMockMvc.perform(post("/api/geregistreerdes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(geregistreerde)))
            .andExpect(status().isBadRequest());

        List<Geregistreerde> geregistreerdeList = geregistreerdeRepository.findAll();
        assertThat(geregistreerdeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGeregistreerdes() throws Exception {
        // Initialize the database
        geregistreerdeRepository.saveAndFlush(geregistreerde);

        // Get all the geregistreerdeList
        restGeregistreerdeMockMvc.perform(get("/api/geregistreerdes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(geregistreerde.getId().intValue())))
            .andExpect(jsonPath("$.[*].voornamen").value(hasItem(DEFAULT_VOORNAMEN)))
            .andExpect(jsonPath("$.[*].achternaam").value(hasItem(DEFAULT_ACHTERNAAM)))
            .andExpect(jsonPath("$.[*].geboortedatum").value(hasItem(DEFAULT_GEBOORTEDATUM.toString())))
            .andExpect(jsonPath("$.[*].geboorteplaats").value(hasItem(DEFAULT_GEBOORTEPLAATS)))
            .andExpect(jsonPath("$.[*].registerNummer").value(hasItem(DEFAULT_REGISTER_NUMMER.intValue())))
            .andExpect(jsonPath("$.[*].classificatie").value(hasItem(DEFAULT_CLASSIFICATIE)))
            .andExpect(jsonPath("$.[*].personeelnummer").value(hasItem(DEFAULT_PERSONEELNUMMER)))
            .andExpect(jsonPath("$.[*].mailadres").value(hasItem(DEFAULT_MAILADRES)))
            .andExpect(jsonPath("$.[*].telefoonNummer").value(hasItem(DEFAULT_TELEFOON_NUMMER)))
            .andExpect(jsonPath("$.[*].mobieleNummer").value(hasItem(DEFAULT_MOBIELE_NUMMER)))
            .andExpect(jsonPath("$.[*].verantwoordelijkeCrest").value(hasItem(DEFAULT_VERANTWOORDELIJKE_CREST)))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)));
    }
    
    @Test
    @Transactional
    public void getGeregistreerde() throws Exception {
        // Initialize the database
        geregistreerdeRepository.saveAndFlush(geregistreerde);

        // Get the geregistreerde
        restGeregistreerdeMockMvc.perform(get("/api/geregistreerdes/{id}", geregistreerde.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(geregistreerde.getId().intValue()))
            .andExpect(jsonPath("$.voornamen").value(DEFAULT_VOORNAMEN))
            .andExpect(jsonPath("$.achternaam").value(DEFAULT_ACHTERNAAM))
            .andExpect(jsonPath("$.geboortedatum").value(DEFAULT_GEBOORTEDATUM.toString()))
            .andExpect(jsonPath("$.geboorteplaats").value(DEFAULT_GEBOORTEPLAATS))
            .andExpect(jsonPath("$.registerNummer").value(DEFAULT_REGISTER_NUMMER.intValue()))
            .andExpect(jsonPath("$.classificatie").value(DEFAULT_CLASSIFICATIE))
            .andExpect(jsonPath("$.personeelnummer").value(DEFAULT_PERSONEELNUMMER))
            .andExpect(jsonPath("$.mailadres").value(DEFAULT_MAILADRES))
            .andExpect(jsonPath("$.telefoonNummer").value(DEFAULT_TELEFOON_NUMMER))
            .andExpect(jsonPath("$.mobieleNummer").value(DEFAULT_MOBIELE_NUMMER))
            .andExpect(jsonPath("$.verantwoordelijkeCrest").value(DEFAULT_VERANTWOORDELIJKE_CREST))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM));
    }
    @Test
    @Transactional
    public void getNonExistingGeregistreerde() throws Exception {
        // Get the geregistreerde
        restGeregistreerdeMockMvc.perform(get("/api/geregistreerdes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGeregistreerde() throws Exception {
        // Initialize the database
        geregistreerdeService.save(geregistreerde);

        int databaseSizeBeforeUpdate = geregistreerdeRepository.findAll().size();

        // Update the geregistreerde
        Geregistreerde updatedGeregistreerde = geregistreerdeRepository.findById(geregistreerde.getId()).get();
        // Disconnect from session so that the updates on updatedGeregistreerde are not directly saved in db
        em.detach(updatedGeregistreerde);
        updatedGeregistreerde
            .voornamen(UPDATED_VOORNAMEN)
            .achternaam(UPDATED_ACHTERNAAM)
            .geboortedatum(UPDATED_GEBOORTEDATUM)
            .geboorteplaats(UPDATED_GEBOORTEPLAATS)
            .registerNummer(UPDATED_REGISTER_NUMMER)
            .classificatie(UPDATED_CLASSIFICATIE)
            .personeelnummer(UPDATED_PERSONEELNUMMER)
            .mailadres(UPDATED_MAILADRES)
            .telefoonNummer(UPDATED_TELEFOON_NUMMER)
            .mobieleNummer(UPDATED_MOBIELE_NUMMER)
            .verantwoordelijkeCrest(UPDATED_VERANTWOORDELIJKE_CREST)
            .naam(UPDATED_NAAM);

        restGeregistreerdeMockMvc.perform(put("/api/geregistreerdes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedGeregistreerde)))
            .andExpect(status().isOk());

        // Validate the Geregistreerde in the database
        List<Geregistreerde> geregistreerdeList = geregistreerdeRepository.findAll();
        assertThat(geregistreerdeList).hasSize(databaseSizeBeforeUpdate);
        Geregistreerde testGeregistreerde = geregistreerdeList.get(geregistreerdeList.size() - 1);
        assertThat(testGeregistreerde.getVoornamen()).isEqualTo(UPDATED_VOORNAMEN);
        assertThat(testGeregistreerde.getAchternaam()).isEqualTo(UPDATED_ACHTERNAAM);
        assertThat(testGeregistreerde.getGeboortedatum()).isEqualTo(UPDATED_GEBOORTEDATUM);
        assertThat(testGeregistreerde.getGeboorteplaats()).isEqualTo(UPDATED_GEBOORTEPLAATS);
        assertThat(testGeregistreerde.getRegisterNummer()).isEqualTo(UPDATED_REGISTER_NUMMER);
        assertThat(testGeregistreerde.getClassificatie()).isEqualTo(UPDATED_CLASSIFICATIE);
        assertThat(testGeregistreerde.getPersoneelnummer()).isEqualTo(UPDATED_PERSONEELNUMMER);
        assertThat(testGeregistreerde.getMailadres()).isEqualTo(UPDATED_MAILADRES);
        assertThat(testGeregistreerde.getTelefoonNummer()).isEqualTo(UPDATED_TELEFOON_NUMMER);
        assertThat(testGeregistreerde.getMobieleNummer()).isEqualTo(UPDATED_MOBIELE_NUMMER);
        assertThat(testGeregistreerde.getVerantwoordelijkeCrest()).isEqualTo(UPDATED_VERANTWOORDELIJKE_CREST);
        assertThat(testGeregistreerde.getNaam()).isEqualTo(UPDATED_NAAM);

        // Validate the Geregistreerde in Elasticsearch
        verify(mockGeregistreerdeSearchRepository, times(2)).save(testGeregistreerde);
    }

    @Test
    @Transactional
    public void updateNonExistingGeregistreerde() throws Exception {
        int databaseSizeBeforeUpdate = geregistreerdeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGeregistreerdeMockMvc.perform(put("/api/geregistreerdes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(geregistreerde)))
            .andExpect(status().isBadRequest());

        // Validate the Geregistreerde in the database
        List<Geregistreerde> geregistreerdeList = geregistreerdeRepository.findAll();
        assertThat(geregistreerdeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Geregistreerde in Elasticsearch
        verify(mockGeregistreerdeSearchRepository, times(0)).save(geregistreerde);
    }

    @Test
    @Transactional
    public void deleteGeregistreerde() throws Exception {
        // Initialize the database
        geregistreerdeService.save(geregistreerde);

        int databaseSizeBeforeDelete = geregistreerdeRepository.findAll().size();

        // Delete the geregistreerde
        restGeregistreerdeMockMvc.perform(delete("/api/geregistreerdes/{id}", geregistreerde.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Geregistreerde> geregistreerdeList = geregistreerdeRepository.findAll();
        assertThat(geregistreerdeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Geregistreerde in Elasticsearch
        verify(mockGeregistreerdeSearchRepository, times(1)).deleteById(geregistreerde.getId());
    }

    @Test
    @Transactional
    public void searchGeregistreerde() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        geregistreerdeService.save(geregistreerde);
        when(mockGeregistreerdeSearchRepository.search(queryStringQuery("id:" + geregistreerde.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(geregistreerde), PageRequest.of(0, 1), 1));

        // Search the geregistreerde
        restGeregistreerdeMockMvc.perform(get("/api/_search/geregistreerdes?query=id:" + geregistreerde.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(geregistreerde.getId().intValue())))
            .andExpect(jsonPath("$.[*].voornamen").value(hasItem(DEFAULT_VOORNAMEN)))
            .andExpect(jsonPath("$.[*].achternaam").value(hasItem(DEFAULT_ACHTERNAAM)))
            .andExpect(jsonPath("$.[*].geboortedatum").value(hasItem(DEFAULT_GEBOORTEDATUM.toString())))
            .andExpect(jsonPath("$.[*].geboorteplaats").value(hasItem(DEFAULT_GEBOORTEPLAATS)))
            .andExpect(jsonPath("$.[*].registerNummer").value(hasItem(DEFAULT_REGISTER_NUMMER.intValue())))
            .andExpect(jsonPath("$.[*].classificatie").value(hasItem(DEFAULT_CLASSIFICATIE)))
            .andExpect(jsonPath("$.[*].personeelnummer").value(hasItem(DEFAULT_PERSONEELNUMMER)))
            .andExpect(jsonPath("$.[*].mailadres").value(hasItem(DEFAULT_MAILADRES)))
            .andExpect(jsonPath("$.[*].telefoonNummer").value(hasItem(DEFAULT_TELEFOON_NUMMER)))
            .andExpect(jsonPath("$.[*].mobieleNummer").value(hasItem(DEFAULT_MOBIELE_NUMMER)))
            .andExpect(jsonPath("$.[*].verantwoordelijkeCrest").value(hasItem(DEFAULT_VERANTWOORDELIJKE_CREST)))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)));
    }
}
