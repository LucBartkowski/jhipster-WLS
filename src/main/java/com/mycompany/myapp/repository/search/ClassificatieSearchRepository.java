package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.Classificatie;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Classificatie} entity.
 */
public interface ClassificatieSearchRepository extends ElasticsearchRepository<Classificatie, Long> {
}
