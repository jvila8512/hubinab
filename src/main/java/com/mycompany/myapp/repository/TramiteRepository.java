package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Tramite;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Tramite entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TramiteRepository extends JpaRepository<Tramite, Long> {}
