package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Classificatie;
import com.mycompany.myapp.repository.ClassificatieRepository;
import com.mycompany.myapp.repository.search.ClassificatieSearchRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Classificatie}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ClassificatieResource {

    private final Logger log = LoggerFactory.getLogger(ClassificatieResource.class);

    private static final String ENTITY_NAME = "classificatie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClassificatieRepository classificatieRepository;

    private final ClassificatieSearchRepository classificatieSearchRepository;

    public ClassificatieResource(ClassificatieRepository classificatieRepository, ClassificatieSearchRepository classificatieSearchRepository) {
        this.classificatieRepository = classificatieRepository;
        this.classificatieSearchRepository = classificatieSearchRepository;
    }

    /**
     * {@code POST  /classificaties} : Create a new classificatie.
     *
     * @param classificatie the classificatie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new classificatie, or with status {@code 400 (Bad Request)} if the classificatie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/classificaties")
    public ResponseEntity<Classificatie> createClassificatie(@Valid @RequestBody Classificatie classificatie) throws URISyntaxException {
        log.debug("REST request to save Classificatie : {}", classificatie);
        if (classificatie.getId() != null) {
            throw new BadRequestAlertException("A new classificatie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Classificatie result = classificatieRepository.save(classificatie);
        classificatieSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/classificaties/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /classificaties} : Updates an existing classificatie.
     *
     * @param classificatie the classificatie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated classificatie,
     * or with status {@code 400 (Bad Request)} if the classificatie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the classificatie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/classificaties")
    public ResponseEntity<Classificatie> updateClassificatie(@Valid @RequestBody Classificatie classificatie) throws URISyntaxException {
        log.debug("REST request to update Classificatie : {}", classificatie);
        if (classificatie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Classificatie result = classificatieRepository.save(classificatie);
        classificatieSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, classificatie.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /classificaties} : get all the classificaties.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of classificaties in body.
     */
    @GetMapping("/classificaties")
    public List<Classificatie> getAllClassificaties() {
        log.debug("REST request to get all Classificaties");
        return classificatieRepository.findAll();
    }

    /**
     * {@code GET  /classificaties/:id} : get the "id" classificatie.
     *
     * @param id the id of the classificatie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the classificatie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/classificaties/{id}")
    public ResponseEntity<Classificatie> getClassificatie(@PathVariable Long id) {
        log.debug("REST request to get Classificatie : {}", id);
        Optional<Classificatie> classificatie = classificatieRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(classificatie);
    }

    /**
     * {@code DELETE  /classificaties/:id} : delete the "id" classificatie.
     *
     * @param id the id of the classificatie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/classificaties/{id}")
    public ResponseEntity<Void> deleteClassificatie(@PathVariable Long id) {
        log.debug("REST request to delete Classificatie : {}", id);
        classificatieRepository.deleteById(id);
        classificatieSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/classificaties?query=:query} : search for the classificatie corresponding
     * to the query.
     *
     * @param query the query of the classificatie search.
     * @return the result of the search.
     */
    @GetMapping("/_search/classificaties")
    public List<Classificatie> searchClassificaties(@RequestParam String query) {
        log.debug("REST request to search Classificaties for query {}", query);
        return StreamSupport
            .stream(classificatieSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
