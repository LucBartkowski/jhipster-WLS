package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterWlsApp;
import com.mycompany.myapp.domain.Crest;
import com.mycompany.myapp.repository.CrestRepository;
import com.mycompany.myapp.repository.search.CrestSearchRepository;

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
 * Integration tests for the {@link CrestResource} REST controller.
 */
@SpringBootTest(classes = JhipsterWlsApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class CrestResourceIT {

    private static final String DEFAULT_VERANTWOORDELIJKE_CREST = "AAAAAAAAAA";
    private static final String UPDATED_VERANTWOORDELIJKE_CREST = "BBBBBBBBBB";

    private static final String DEFAULT_NAAM_ENTITEIT = "AAAAAAAAAA";
    private static final String UPDATED_NAAM_ENTITEIT = "BBBBBBBBBB";

    @Autowired
    private CrestRepository crestRepository;

    /**
     * This repository is mocked in the com.mycompany.myapp.repository.search test package.
     *
     * @see com.mycompany.myapp.repository.search.CrestSearchRepositoryMockConfiguration
     */
    @Autowired
    private CrestSearchRepository mockCrestSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCrestMockMvc;

    private Crest crest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Crest createEntity(EntityManager em) {
        Crest crest = new Crest()
            .verantwoordelijkeCrest(DEFAULT_VERANTWOORDELIJKE_CREST)
            .naamEntiteit(DEFAULT_NAAM_ENTITEIT);
        return crest;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Crest createUpdatedEntity(EntityManager em) {
        Crest crest = new Crest()
            .verantwoordelijkeCrest(UPDATED_VERANTWOORDELIJKE_CREST)
            .naamEntiteit(UPDATED_NAAM_ENTITEIT);
        return crest;
    }

    @BeforeEach
    public void initTest() {
        crest = createEntity(em);
    }

    @Test
    @Transactional
    public void createCrest() throws Exception {
        int databaseSizeBeforeCreate = crestRepository.findAll().size();
        // Create the Crest
        restCrestMockMvc.perform(post("/api/crests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(crest)))
            .andExpect(status().isCreated());

        // Validate the Crest in the database
        List<Crest> crestList = crestRepository.findAll();
        assertThat(crestList).hasSize(databaseSizeBeforeCreate + 1);
        Crest testCrest = crestList.get(crestList.size() - 1);
        assertThat(testCrest.getVerantwoordelijkeCrest()).isEqualTo(DEFAULT_VERANTWOORDELIJKE_CREST);
        assertThat(testCrest.getNaamEntiteit()).isEqualTo(DEFAULT_NAAM_ENTITEIT);

        // Validate the Crest in Elasticsearch
        verify(mockCrestSearchRepository, times(1)).save(testCrest);
    }

    @Test
    @Transactional
    public void createCrestWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = crestRepository.findAll().size();

        // Create the Crest with an existing ID
        crest.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCrestMockMvc.perform(post("/api/crests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(crest)))
            .andExpect(status().isBadRequest());

        // Validate the Crest in the database
        List<Crest> crestList = crestRepository.findAll();
        assertThat(crestList).hasSize(databaseSizeBeforeCreate);

        // Validate the Crest in Elasticsearch
        verify(mockCrestSearchRepository, times(0)).save(crest);
    }


    @Test
    @Transactional
    public void checkVerantwoordelijkeCrestIsRequired() throws Exception {
        int databaseSizeBeforeTest = crestRepository.findAll().size();
        // set the field null
        crest.setVerantwoordelijkeCrest(null);

        // Create the Crest, which fails.


        restCrestMockMvc.perform(post("/api/crests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(crest)))
            .andExpect(status().isBadRequest());

        List<Crest> crestList = crestRepository.findAll();
        assertThat(crestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNaamEntiteitIsRequired() throws Exception {
        int databaseSizeBeforeTest = crestRepository.findAll().size();
        // set the field null
        crest.setNaamEntiteit(null);

        // Create the Crest, which fails.


        restCrestMockMvc.perform(post("/api/crests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(crest)))
            .andExpect(status().isBadRequest());

        List<Crest> crestList = crestRepository.findAll();
        assertThat(crestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCrests() throws Exception {
        // Initialize the database
        crestRepository.saveAndFlush(crest);

        // Get all the crestList
        restCrestMockMvc.perform(get("/api/crests?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(crest.getId().intValue())))
            .andExpect(jsonPath("$.[*].verantwoordelijkeCrest").value(hasItem(DEFAULT_VERANTWOORDELIJKE_CREST)))
            .andExpect(jsonPath("$.[*].naamEntiteit").value(hasItem(DEFAULT_NAAM_ENTITEIT)));
    }
    
    @Test
    @Transactional
    public void getCrest() throws Exception {
        // Initialize the database
        crestRepository.saveAndFlush(crest);

        // Get the crest
        restCrestMockMvc.perform(get("/api/crests/{id}", crest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(crest.getId().intValue()))
            .andExpect(jsonPath("$.verantwoordelijkeCrest").value(DEFAULT_VERANTWOORDELIJKE_CREST))
            .andExpect(jsonPath("$.naamEntiteit").value(DEFAULT_NAAM_ENTITEIT));
    }
    @Test
    @Transactional
    public void getNonExistingCrest() throws Exception {
        // Get the crest
        restCrestMockMvc.perform(get("/api/crests/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCrest() throws Exception {
        // Initialize the database
        crestRepository.saveAndFlush(crest);

        int databaseSizeBeforeUpdate = crestRepository.findAll().size();

        // Update the crest
        Crest updatedCrest = crestRepository.findById(crest.getId()).get();
        // Disconnect from session so that the updates on updatedCrest are not directly saved in db
        em.detach(updatedCrest);
        updatedCrest
            .verantwoordelijkeCrest(UPDATED_VERANTWOORDELIJKE_CREST)
            .naamEntiteit(UPDATED_NAAM_ENTITEIT);

        restCrestMockMvc.perform(put("/api/crests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCrest)))
            .andExpect(status().isOk());

        // Validate the Crest in the database
        List<Crest> crestList = crestRepository.findAll();
        assertThat(crestList).hasSize(databaseSizeBeforeUpdate);
        Crest testCrest = crestList.get(crestList.size() - 1);
        assertThat(testCrest.getVerantwoordelijkeCrest()).isEqualTo(UPDATED_VERANTWOORDELIJKE_CREST);
        assertThat(testCrest.getNaamEntiteit()).isEqualTo(UPDATED_NAAM_ENTITEIT);

        // Validate the Crest in Elasticsearch
        verify(mockCrestSearchRepository, times(1)).save(testCrest);
    }

    @Test
    @Transactional
    public void updateNonExistingCrest() throws Exception {
        int databaseSizeBeforeUpdate = crestRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCrestMockMvc.perform(put("/api/crests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(crest)))
            .andExpect(status().isBadRequest());

        // Validate the Crest in the database
        List<Crest> crestList = crestRepository.findAll();
        assertThat(crestList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Crest in Elasticsearch
        verify(mockCrestSearchRepository, times(0)).save(crest);
    }

    @Test
    @Transactional
    public void deleteCrest() throws Exception {
        // Initialize the database
        crestRepository.saveAndFlush(crest);

        int databaseSizeBeforeDelete = crestRepository.findAll().size();

        // Delete the crest
        restCrestMockMvc.perform(delete("/api/crests/{id}", crest.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Crest> crestList = crestRepository.findAll();
        assertThat(crestList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Crest in Elasticsearch
        verify(mockCrestSearchRepository, times(1)).deleteById(crest.getId());
    }

    @Test
    @Transactional
    public void searchCrest() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        crestRepository.saveAndFlush(crest);
        when(mockCrestSearchRepository.search(queryStringQuery("id:" + crest.getId())))
            .thenReturn(Collections.singletonList(crest));

        // Search the crest
        restCrestMockMvc.perform(get("/api/_search/crests?query=id:" + crest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(crest.getId().intValue())))
            .andExpect(jsonPath("$.[*].verantwoordelijkeCrest").value(hasItem(DEFAULT_VERANTWOORDELIJKE_CREST)))
            .andExpect(jsonPath("$.[*].naamEntiteit").value(hasItem(DEFAULT_NAAM_ENTITEIT)));
    }
}
