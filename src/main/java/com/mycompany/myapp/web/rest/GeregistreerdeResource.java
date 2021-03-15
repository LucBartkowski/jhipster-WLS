package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Geregistreerde;
import com.mycompany.myapp.service.GeregistreerdeService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Geregistreerde}.
 */
@RestController
@RequestMapping("/api")
public class GeregistreerdeResource {

    private final Logger log = LoggerFactory.getLogger(GeregistreerdeResource.class);

    private static final String ENTITY_NAME = "geregistreerde";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GeregistreerdeService geregistreerdeService;

    public GeregistreerdeResource(GeregistreerdeService geregistreerdeService) {
        this.geregistreerdeService = geregistreerdeService;
    }

    /**
     * {@code POST  /geregistreerdes} : Create a new geregistreerde.
     *
     * @param geregistreerde the geregistreerde to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new geregistreerde, or with status {@code 400 (Bad Request)} if the geregistreerde has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/geregistreerdes")
    public ResponseEntity<Geregistreerde> createGeregistreerde(@Valid @RequestBody Geregistreerde geregistreerde) throws URISyntaxException {
        log.debug("REST request to save Geregistreerde : {}", geregistreerde);
        if (geregistreerde.getId() != null) {
            throw new BadRequestAlertException("A new geregistreerde cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Geregistreerde result = geregistreerdeService.save(geregistreerde);
        return ResponseEntity.created(new URI("/api/geregistreerdes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /geregistreerdes} : Updates an existing geregistreerde.
     *
     * @param geregistreerde the geregistreerde to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated geregistreerde,
     * or with status {@code 400 (Bad Request)} if the geregistreerde is not valid,
     * or with status {@code 500 (Internal Server Error)} if the geregistreerde couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/geregistreerdes")
    public ResponseEntity<Geregistreerde> updateGeregistreerde(@Valid @RequestBody Geregistreerde geregistreerde) throws URISyntaxException {
        log.debug("REST request to update Geregistreerde : {}", geregistreerde);
        if (geregistreerde.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Geregistreerde result = geregistreerdeService.save(geregistreerde);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, geregistreerde.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /geregistreerdes} : get all the geregistreerdes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of geregistreerdes in body.
     */
    @GetMapping("/geregistreerdes")
    public ResponseEntity<List<Geregistreerde>> getAllGeregistreerdes(Pageable pageable) {
        log.debug("REST request to get a page of Geregistreerdes");
        Page<Geregistreerde> page = geregistreerdeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /geregistreerdes/:id} : get the "id" geregistreerde.
     *
     * @param id the id of the geregistreerde to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the geregistreerde, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/geregistreerdes/{id}")
    public ResponseEntity<Geregistreerde> getGeregistreerde(@PathVariable Long id) {
        log.debug("REST request to get Geregistreerde : {}", id);
        Optional<Geregistreerde> geregistreerde = geregistreerdeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(geregistreerde);
    }

    /**
     * {@code DELETE  /geregistreerdes/:id} : delete the "id" geregistreerde.
     *
     * @param id the id of the geregistreerde to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/geregistreerdes/{id}")
    public ResponseEntity<Void> deleteGeregistreerde(@PathVariable Long id) {
        log.debug("REST request to delete Geregistreerde : {}", id);
        geregistreerdeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/geregistreerdes?query=:query} : search for the geregistreerde corresponding
     * to the query.
     *
     * @param query the query of the geregistreerde search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/geregistreerdes")
    public ResponseEntity<List<Geregistreerde>> searchGeregistreerdes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Geregistreerdes for query {}", query);
        Page<Geregistreerde> page = geregistreerdeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
