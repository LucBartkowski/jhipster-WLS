package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Medewerker;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Medewerker entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MedewerkerRepository extends JpaRepository<Medewerker, Long> {
}
