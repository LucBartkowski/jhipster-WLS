package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.GeregistreerdeService;
import com.mycompany.myapp.domain.Geregistreerde;
import com.mycompany.myapp.repository.GeregistreerdeRepository;
import com.mycompany.myapp.repository.search.GeregistreerdeSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Geregistreerde}.
 */
@Service
@Transactional
public class GeregistreerdeServiceImpl implements GeregistreerdeService {

    private final Logger log = LoggerFactory.getLogger(GeregistreerdeServiceImpl.class);

    private final GeregistreerdeRepository geregistreerdeRepository;

    private final GeregistreerdeSearchRepository geregistreerdeSearchRepository;

    public GeregistreerdeServiceImpl(GeregistreerdeRepository geregistreerdeRepository, GeregistreerdeSearchRepository geregistreerdeSearchRepository) {
        this.geregistreerdeRepository = geregistreerdeRepository;
        this.geregistreerdeSearchRepository = geregistreerdeSearchRepository;
    }

    @Override
    public Geregistreerde save(Geregistreerde geregistreerde) {
        log.debug("Request to save Geregistreerde : {}", geregistreerde);
        Geregistreerde result = geregistreerdeRepository.save(geregistreerde);
        geregistreerdeSearchRepository.save(result);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Geregistreerde> findAll(Pageable pageable) {
        log.debug("Request to get all Geregistreerdes");
        return geregistreerdeRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Geregistreerde> findOne(Long id) {
        log.debug("Request to get Geregistreerde : {}", id);
        return geregistreerdeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Geregistreerde : {}", id);
        geregistreerdeRepository.deleteById(id);
        geregistreerdeSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Geregistreerde> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Geregistreerdes for query {}", query);
        return geregistreerdeSearchRepository.search(queryStringQuery(query), pageable);    }
}
