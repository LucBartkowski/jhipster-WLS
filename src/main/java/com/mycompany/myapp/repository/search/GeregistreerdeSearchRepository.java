package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.Geregistreerde;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Geregistreerde} entity.
 */
public interface GeregistreerdeSearchRepository extends ElasticsearchRepository<Geregistreerde, Long> {
}
