package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Classificatie;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Classificatie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClassificatieRepository extends JpaRepository<Classificatie, Long> {
}
