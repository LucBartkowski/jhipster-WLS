package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterWlsApp;
import com.mycompany.myapp.domain.Medewerker;
import com.mycompany.myapp.repository.MedewerkerRepository;
import com.mycompany.myapp.repository.search.MedewerkerSearchRepository;

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
 * Integration tests for the {@link MedewerkerResource} REST controller.
 */
@SpringBootTest(classes = JhipsterWlsApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class MedewerkerResourceIT {

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_FUNCTIE = "AAAAAAAAAA";
    private static final String UPDATED_FUNCTIE = "BBBBBBBBBB";

    @Autowired
    private MedewerkerRepository medewerkerRepository;

    /**
     * This repository is mocked in the com.mycompany.myapp.repository.search test package.
     *
     * @see com.mycompany.myapp.repository.search.MedewerkerSearchRepositoryMockConfiguration
     */
    @Autowired
    private MedewerkerSearchRepository mockMedewerkerSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMedewerkerMockMvc;

    private Medewerker medewerker;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Medewerker createEntity(EntityManager em) {
        Medewerker medewerker = new Medewerker()
            .naam(DEFAULT_NAAM)
            .functie(DEFAULT_FUNCTIE);
        return medewerker;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Medewerker createUpdatedEntity(EntityManager em) {
        Medewerker medewerker = new Medewerker()
            .naam(UPDATED_NAAM)
            .functie(UPDATED_FUNCTIE);
        return medewerker;
    }

    @BeforeEach
    public void initTest() {
        medewerker = createEntity(em);
    }

    @Test
    @Transactional
    public void createMedewerker() throws Exception {
        int databaseSizeBeforeCreate = medewerkerRepository.findAll().size();
        // Create the Medewerker
        restMedewerkerMockMvc.perform(post("/api/medewerkers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(medewerker)))
            .andExpect(status().isCreated());

        // Validate the Medewerker in the database
        List<Medewerker> medewerkerList = medewerkerRepository.findAll();
        assertThat(medewerkerList).hasSize(databaseSizeBeforeCreate + 1);
        Medewerker testMedewerker = medewerkerList.get(medewerkerList.size() - 1);
        assertThat(testMedewerker.getNaam()).isEqualTo(DEFAULT_NAAM);
        assertThat(testMedewerker.getFunctie()).isEqualTo(DEFAULT_FUNCTIE);

        // Validate the Medewerker in Elasticsearch
        verify(mockMedewerkerSearchRepository, times(1)).save(testMedewerker);
    }

    @Test
    @Transactional
    public void createMedewerkerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = medewerkerRepository.findAll().size();

        // Create the Medewerker with an existing ID
        medewerker.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedewerkerMockMvc.perform(post("/api/medewerkers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(medewerker)))
            .andExpect(status().isBadRequest());

        // Validate the Medewerker in the database
        List<Medewerker> medewerkerList = medewerkerRepository.findAll();
        assertThat(medewerkerList).hasSize(databaseSizeBeforeCreate);

        // Validate the Medewerker in Elasticsearch
        verify(mockMedewerkerSearchRepository, times(0)).save(medewerker);
    }


    @Test
    @Transactional
    public void checkNaamIsRequired() throws Exception {
        int databaseSizeBeforeTest = medewerkerRepository.findAll().size();
        // set the field null
        medewerker.setNaam(null);

        // Create the Medewerker, which fails.


        restMedewerkerMockMvc.perform(post("/api/medewerkers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(medewerker)))
            .andExpect(status().isBadRequest());

        List<Medewerker> medewerkerList = medewerkerRepository.findAll();
        assertThat(medewerkerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFunctieIsRequired() throws Exception {
        int databaseSizeBeforeTest = medewerkerRepository.findAll().size();
        // set the field null
        medewerker.setFunctie(null);

        // Create the Medewerker, which fails.


        restMedewerkerMockMvc.perform(post("/api/medewerkers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(medewerker)))
            .andExpect(status().isBadRequest());

        List<Medewerker> medewerkerList = medewerkerRepository.findAll();
        assertThat(medewerkerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMedewerkers() throws Exception {
        // Initialize the database
        medewerkerRepository.saveAndFlush(medewerker);

        // Get all the medewerkerList
        restMedewerkerMockMvc.perform(get("/api/medewerkers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medewerker.getId().intValue())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].functie").value(hasItem(DEFAULT_FUNCTIE)));
    }
    
    @Test
    @Transactional
    public void getMedewerker() throws Exception {
        // Initialize the database
        medewerkerRepository.saveAndFlush(medewerker);

        // Get the medewerker
        restMedewerkerMockMvc.perform(get("/api/medewerkers/{id}", medewerker.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(medewerker.getId().intValue()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.functie").value(DEFAULT_FUNCTIE));
    }
    @Test
    @Transactional
    public void getNonExistingMedewerker() throws Exception {
        // Get the medewerker
        restMedewerkerMockMvc.perform(get("/api/medewerkers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMedewerker() throws Exception {
        // Initialize the database
        medewerkerRepository.saveAndFlush(medewerker);

        int databaseSizeBeforeUpdate = medewerkerRepository.findAll().size();

        // Update the medewerker
        Medewerker updatedMedewerker = medewerkerRepository.findById(medewerker.getId()).get();
        // Disconnect from session so that the updates on updatedMedewerker are not directly saved in db
        em.detach(updatedMedewerker);
        updatedMedewerker
            .naam(UPDATED_NAAM)
            .functie(UPDATED_FUNCTIE);

        restMedewerkerMockMvc.perform(put("/api/medewerkers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedMedewerker)))
            .andExpect(status().isOk());

        // Validate the Medewerker in the database
        List<Medewerker> medewerkerList = medewerkerRepository.findAll();
        assertThat(medewerkerList).hasSize(databaseSizeBeforeUpdate);
        Medewerker testMedewerker = medewerkerList.get(medewerkerList.size() - 1);
        assertThat(testMedewerker.getNaam()).isEqualTo(UPDATED_NAAM);
        assertThat(testMedewerker.getFunctie()).isEqualTo(UPDATED_FUNCTIE);

        // Validate the Medewerker in Elasticsearch
        verify(mockMedewerkerSearchRepository, times(1)).save(testMedewerker);
    }

    @Test
    @Transactional
    public void updateNonExistingMedewerker() throws Exception {
        int databaseSizeBeforeUpdate = medewerkerRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMedewerkerMockMvc.perform(put("/api/medewerkers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(medewerker)))
            .andExpect(status().isBadRequest());

        // Validate the Medewerker in the database
        List<Medewerker> medewerkerList = medewerkerRepository.findAll();
        assertThat(medewerkerList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Medewerker in Elasticsearch
        verify(mockMedewerkerSearchRepository, times(0)).save(medewerker);
    }

    @Test
    @Transactional
    public void deleteMedewerker() throws Exception {
        // Initialize the database
        medewerkerRepository.saveAndFlush(medewerker);

        int databaseSizeBeforeDelete = medewerkerRepository.findAll().size();

        // Delete the medewerker
        restMedewerkerMockMvc.perform(delete("/api/medewerkers/{id}", medewerker.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Medewerker> medewerkerList = medewerkerRepository.findAll();
        assertThat(medewerkerList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Medewerker in Elasticsearch
        verify(mockMedewerkerSearchRepository, times(1)).deleteById(medewerker.getId());
    }

    @Test
    @Transactional
    public void searchMedewerker() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        medewerkerRepository.saveAndFlush(medewerker);
        when(mockMedewerkerSearchRepository.search(queryStringQuery("id:" + medewerker.getId())))
            .thenReturn(Collections.singletonList(medewerker));

        // Search the medewerker
        restMedewerkerMockMvc.perform(get("/api/_search/medewerkers?query=id:" + medewerker.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medewerker.getId().intValue())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].functie").value(hasItem(DEFAULT_FUNCTIE)));
    }
}
