package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.Crest;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Crest} entity.
 */
public interface CrestSearchRepository extends ElasticsearchRepository<Crest, Long> {
}
