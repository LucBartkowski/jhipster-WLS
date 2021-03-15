package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Crest;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Crest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CrestRepository extends JpaRepository<Crest, Long> {
}
