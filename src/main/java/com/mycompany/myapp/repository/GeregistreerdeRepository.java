package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Geregistreerde;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Geregistreerde entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GeregistreerdeRepository extends JpaRepository<Geregistreerde, Long> {
}
