package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Medewerker;
import com.mycompany.myapp.repository.MedewerkerRepository;
import com.mycompany.myapp.repository.search.MedewerkerSearchRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Medewerker}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class MedewerkerResource {

    private final Logger log = LoggerFactory.getLogger(MedewerkerResource.class);

    private static final String ENTITY_NAME = "medewerker";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MedewerkerRepository medewerkerRepository;

    private final MedewerkerSearchRepository medewerkerSearchRepository;

    public MedewerkerResource(MedewerkerRepository medewerkerRepository, MedewerkerSearchRepository medewerkerSearchRepository) {
        this.medewerkerRepository = medewerkerRepository;
        this.medewerkerSearchRepository = medewerkerSearchRepository;
    }

    /**
     * {@code POST  /medewerkers} : Create a new medewerker.
     *
     * @param medewerker the medewerker to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new medewerker, or with status {@code 400 (Bad Request)} if the medewerker has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/medewerkers")
    public ResponseEntity<Medewerker> createMedewerker(@Valid @RequestBody Medewerker medewerker) throws URISyntaxException {
        log.debug("REST request to save Medewerker : {}", medewerker);
        if (medewerker.getId() != null) {
            throw new BadRequestAlertException("A new medewerker cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Medewerker result = medewerkerRepository.save(medewerker);
        medewerkerSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/medewerkers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /medewerkers} : Updates an existing medewerker.
     *
     * @param medewerker the medewerker to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated medewerker,
     * or with status {@code 400 (Bad Request)} if the medewerker is not valid,
     * or with status {@code 500 (Internal Server Error)} if the medewerker couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/medewerkers")
    public ResponseEntity<Medewerker> updateMedewerker(@Valid @RequestBody Medewerker medewerker) throws URISyntaxException {
        log.debug("REST request to update Medewerker : {}", medewerker);
        if (medewerker.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Medewerker result = medewerkerRepository.save(medewerker);
        medewerkerSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, medewerker.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /medewerkers} : get all the medewerkers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of medewerkers in body.
     */
    @GetMapping("/medewerkers")
    public List<Medewerker> getAllMedewerkers() {
        log.debug("REST request to get all Medewerkers");
        return medewerkerRepository.findAll();
    }

    /**
     * {@code GET  /medewerkers/:id} : get the "id" medewerker.
     *
     * @param id the id of the medewerker to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the medewerker, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/medewerkers/{id}")
    public ResponseEntity<Medewerker> getMedewerker(@PathVariable Long id) {
        log.debug("REST request to get Medewerker : {}", id);
        Optional<Medewerker> medewerker = medewerkerRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(medewerker);
    }

    /**
     * {@code DELETE  /medewerkers/:id} : delete the "id" medewerker.
     *
     * @param id the id of the medewerker to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/medewerkers/{id}")
    public ResponseEntity<Void> deleteMedewerker(@PathVariable Long id) {
        log.debug("REST request to delete Medewerker : {}", id);
        medewerkerRepository.deleteById(id);
        medewerkerSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/medewerkers?query=:query} : search for the medewerker corresponding
     * to the query.
     *
     * @param query the query of the medewerker search.
     * @return the result of the search.
     */
    @GetMapping("/_search/medewerkers")
    public List<Medewerker> searchMedewerkers(@RequestParam String query) {
        log.debug("REST request to search Medewerkers for query {}", query);
        return StreamSupport
            .stream(medewerkerSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
