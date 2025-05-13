package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Entidad;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Entidad entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EntidadRepository extends JpaRepository<Entidad, Long> {}
