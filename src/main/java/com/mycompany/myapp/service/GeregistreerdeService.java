package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Geregistreerde;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Geregistreerde}.
 */
public interface GeregistreerdeService {

    /**
     * Save a geregistreerde.
     *
     * @param geregistreerde the entity to save.
     * @return the persisted entity.
     */
    Geregistreerde save(Geregistreerde geregistreerde);

    /**
     * Get all the geregistreerdes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Geregistreerde> findAll(Pageable pageable);


    /**
     * Get the "id" geregistreerde.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Geregistreerde> findOne(Long id);

    /**
     * Delete the "id" geregistreerde.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the geregistreerde corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Geregistreerde> search(String query, Pageable pageable);
}
