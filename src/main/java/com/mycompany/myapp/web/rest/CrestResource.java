package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Crest;
import com.mycompany.myapp.repository.CrestRepository;
import com.mycompany.myapp.repository.search.CrestSearchRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Crest}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CrestResource {

    private final Logger log = LoggerFactory.getLogger(CrestResource.class);

    private static final String ENTITY_NAME = "crest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CrestRepository crestRepository;

    private final CrestSearchRepository crestSearchRepository;

    public CrestResource(CrestRepository crestRepository, CrestSearchRepository crestSearchRepository) {
        this.crestRepository = crestRepository;
        this.crestSearchRepository = crestSearchRepository;
    }

    /**
     * {@code POST  /crests} : Create a new crest.
     *
     * @param crest the crest to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new crest, or with status {@code 400 (Bad Request)} if the crest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/crests")
    public ResponseEntity<Crest> createCrest(@Valid @RequestBody Crest crest) throws URISyntaxException {
        log.debug("REST request to save Crest : {}", crest);
        if (crest.getId() != null) {
            throw new BadRequestAlertException("A new crest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Crest result = crestRepository.save(crest);
        crestSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/crests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /crests} : Updates an existing crest.
     *
     * @param crest the crest to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated crest,
     * or with status {@code 400 (Bad Request)} if the crest is not valid,
     * or with status {@code 500 (Internal Server Error)} if the crest couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/crests")
    public ResponseEntity<Crest> updateCrest(@Valid @RequestBody Crest crest) throws URISyntaxException {
        log.debug("REST request to update Crest : {}", crest);
        if (crest.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Crest result = crestRepository.save(crest);
        crestSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, crest.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /crests} : get all the crests.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of crests in body.
     */
    @GetMapping("/crests")
    public List<Crest> getAllCrests() {
        log.debug("REST request to get all Crests");
        return crestRepository.findAll();
    }

    /**
     * {@code GET  /crests/:id} : get the "id" crest.
     *
     * @param id the id of the crest to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the crest, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/crests/{id}")
    public ResponseEntity<Crest> getCrest(@PathVariable Long id) {
        log.debug("REST request to get Crest : {}", id);
        Optional<Crest> crest = crestRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(crest);
    }

    /**
     * {@code DELETE  /crests/:id} : delete the "id" crest.
     *
     * @param id the id of the crest to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/crests/{id}")
    public ResponseEntity<Void> deleteCrest(@PathVariable Long id) {
        log.debug("REST request to delete Crest : {}", id);
        crestRepository.deleteById(id);
        crestSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/crests?query=:query} : search for the crest corresponding
     * to the query.
     *
     * @param query the query of the crest search.
     * @return the result of the search.
     */
    @GetMapping("/_search/crests")
    public List<Crest> searchCrests(@RequestParam String query) {
        log.debug("REST request to search Crests for query {}", query);
        return StreamSupport
            .stream(crestSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
