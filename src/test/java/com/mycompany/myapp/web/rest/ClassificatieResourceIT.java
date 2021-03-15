package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterWlsApp;
import com.mycompany.myapp.domain.Classificatie;
import com.mycompany.myapp.repository.ClassificatieRepository;
import com.mycompany.myapp.repository.search.ClassificatieSearchRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ClassificatieResource} REST controller.
 */
@SpringBootTest(classes = JhipsterWlsApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ClassificatieResourceIT {

    private static final String DEFAULT_CLASSIFICATIE = "AAAAAAAAAA";
    private static final String UPDATED_CLASSIFICATIE = "BBBBBBBBBB";

    private static final String DEFAULT_KLEUR = "AAAAAAAAAA";
    private static final String UPDATED_KLEUR = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    @Autowired
    private ClassificatieRepository classificatieRepository;

    /**
     * This repository is mocked in the com.mycompany.myapp.repository.search test package.
     *
     * @see com.mycompany.myapp.repository.search.ClassificatieSearchRepositoryMockConfiguration
     */
    @Autowired
    private ClassificatieSearchRepository mockClassificatieSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClassificatieMockMvc;

    private Classificatie classificatie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Classificatie createEntity(EntityManager em) {
        Classificatie classificatie = new Classificatie()
            .classificatie(DEFAULT_CLASSIFICATIE)
            .kleur(DEFAULT_KLEUR)
            .omschrijving(DEFAULT_OMSCHRIJVING);
        return classificatie;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Classificatie createUpdatedEntity(EntityManager em) {
        Classificatie classificatie = new Classificatie()
            .classificatie(UPDATED_CLASSIFICATIE)
            .kleur(UPDATED_KLEUR)
            .omschrijving(UPDATED_OMSCHRIJVING);
        return classificatie;
    }

    @BeforeEach
    public void initTest() {
        classificatie = createEntity(em);
    }

    @Test
    @Transactional
    public void createClassificatie() throws Exception {
        int databaseSizeBeforeCreate = classificatieRepository.findAll().size();
        // Create the Classificatie
        restClassificatieMockMvc.perform(post("/api/classificaties")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classificatie)))
            .andExpect(status().isCreated());

        // Validate the Classificatie in the database
        List<Classificatie> classificatieList = classificatieRepository.findAll();
        assertThat(classificatieList).hasSize(databaseSizeBeforeCreate + 1);
        Classificatie testClassificatie = classificatieList.get(classificatieList.size() - 1);
        assertThat(testClassificatie.getClassificatie()).isEqualTo(DEFAULT_CLASSIFICATIE);
        assertThat(testClassificatie.getKleur()).isEqualTo(DEFAULT_KLEUR);
        assertThat(testClassificatie.getOmschrijving()).isEqualTo(DEFAULT_OMSCHRIJVING);

        // Validate the Classificatie in Elasticsearch
        verify(mockClassificatieSearchRepository, times(1)).save(testClassificatie);
    }

    @Test
    @Transactional
    public void createClassificatieWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = classificatieRepository.findAll().size();

        // Create the Classificatie with an existing ID
        classificatie.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClassificatieMockMvc.perform(post("/api/classificaties")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classificatie)))
            .andExpect(status().isBadRequest());

        // Validate the Classificatie in the database
        List<Classificatie> classificatieList = classificatieRepository.findAll();
        assertThat(classificatieList).hasSize(databaseSizeBeforeCreate);

        // Validate the Classificatie in Elasticsearch
        verify(mockClassificatieSearchRepository, times(0)).save(classificatie);
    }


    @Test
    @Transactional
    public void checkClassificatieIsRequired() throws Exception {
        int databaseSizeBeforeTest = classificatieRepository.findAll().size();
        // set the field null
        classificatie.setClassificatie(null);

        // Create the Classificatie, which fails.


        restClassificatieMockMvc.perform(post("/api/classificaties")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classificatie)))
            .andExpect(status().isBadRequest());

        List<Classificatie> classificatieList = classificatieRepository.findAll();
        assertThat(classificatieList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOmschrijvingIsRequired() throws Exception {
        int databaseSizeBeforeTest = classificatieRepository.findAll().size();
        // set the field null
        classificatie.setOmschrijving(null);

        // Create the Classificatie, which fails.


        restClassificatieMockMvc.perform(post("/api/classificaties")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classificatie)))
            .andExpect(status().isBadRequest());

        List<Classificatie> classificatieList = classificatieRepository.findAll();
        assertThat(classificatieList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllClassificaties() throws Exception {
        // Initialize the database
        classificatieRepository.saveAndFlush(classificatie);

        // Get all the classificatieList
        restClassificatieMockMvc.perform(get("/api/classificaties?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classificatie.getId().intValue())))
            .andExpect(jsonPath("$.[*].classificatie").value(hasItem(DEFAULT_CLASSIFICATIE)))
            .andExpect(jsonPath("$.[*].kleur").value(hasItem(DEFAULT_KLEUR)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }
    
    @Test
    @Transactional
    public void getClassificatie() throws Exception {
        // Initialize the database
        classificatieRepository.saveAndFlush(classificatie);

        // Get the classificatie
        restClassificatieMockMvc.perform(get("/api/classificaties/{id}", classificatie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(classificatie.getId().intValue()))
            .andExpect(jsonPath("$.classificatie").value(DEFAULT_CLASSIFICATIE))
            .andExpect(jsonPath("$.kleur").value(DEFAULT_KLEUR))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }
    @Test
    @Transactional
    public void getNonExistingClassificatie() throws Exception {
        // Get the classificatie
        restClassificatieMockMvc.perform(get("/api/classificaties/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClassificatie() throws Exception {
        // Initialize the database
        classificatieRepository.saveAndFlush(classificatie);

        int databaseSizeBeforeUpdate = classificatieRepository.findAll().size();

        // Update the classificatie
        Classificatie updatedClassificatie = classificatieRepository.findById(classificatie.getId()).get();
        // Disconnect from session so that the updates on updatedClassificatie are not directly saved in db
        em.detach(updatedClassificatie);
        updatedClassificatie
            .classificatie(UPDATED_CLASSIFICATIE)
            .kleur(UPDATED_KLEUR)
            .omschrijving(UPDATED_OMSCHRIJVING);

        restClassificatieMockMvc.perform(put("/api/classificaties")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedClassificatie)))
            .andExpect(status().isOk());

        // Validate the Classificatie in the database
        List<Classificatie> classificatieList = classificatieRepository.findAll();
        assertThat(classificatieList).hasSize(databaseSizeBeforeUpdate);
        Classificatie testClassificatie = classificatieList.get(classificatieList.size() - 1);
        assertThat(testClassificatie.getClassificatie()).isEqualTo(UPDATED_CLASSIFICATIE);
        assertThat(testClassificatie.getKleur()).isEqualTo(UPDATED_KLEUR);
        assertThat(testClassificatie.getOmschrijving()).isEqualTo(UPDATED_OMSCHRIJVING);

        // Validate the Classificatie in Elasticsearch
        verify(mockClassificatieSearchRepository, times(1)).save(testClassificatie);
    }

    @Test
    @Transactional
    public void updateNonExistingClassificatie() throws Exception {
        int databaseSizeBeforeUpdate = classificatieRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClassificatieMockMvc.perform(put("/api/classificaties")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classificatie)))
            .andExpect(status().isBadRequest());

        // Validate the Classificatie in the database
        List<Classificatie> classificatieList = classificatieRepository.findAll();
        assertThat(classificatieList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Classificatie in Elasticsearch
        verify(mockClassificatieSearchRepository, times(0)).save(classificatie);
    }

    @Test
    @Transactional
    public void deleteClassificatie() throws Exception {
        // Initialize the database
        classificatieRepository.saveAndFlush(classificatie);

        int databaseSizeBeforeDelete = classificatieRepository.findAll().size();

        // Delete the classificatie
        restClassificatieMockMvc.perform(delete("/api/classificaties/{id}", classificatie.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Classificatie> classificatieList = classificatieRepository.findAll();
        assertThat(classificatieList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Classificatie in Elasticsearch
        verify(mockClassificatieSearchRepository, times(1)).deleteById(classificatie.getId());
    }

    @Test
    @Transactional
    public void searchClassificatie() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        classificatieRepository.saveAndFlush(classificatie);
        when(mockClassificatieSearchRepository.search(queryStringQuery("id:" + classificatie.getId())))
            .thenReturn(Collections.singletonList(classificatie));

        // Search the classificatie
        restClassificatieMockMvc.perform(get("/api/_search/classificaties?query=id:" + classificatie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classificatie.getId().intValue())))
            .andExpect(jsonPath("$.[*].classificatie").value(hasItem(DEFAULT_CLASSIFICATIE)))
            .andExpect(jsonPath("$.[*].kleur").value(hasItem(DEFAULT_KLEUR)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }
}
