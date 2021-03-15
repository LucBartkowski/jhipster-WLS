package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.Medewerker;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Medewerker} entity.
 */
public interface MedewerkerSearchRepository extends ElasticsearchRepository<Medewerker, Long> {
}
