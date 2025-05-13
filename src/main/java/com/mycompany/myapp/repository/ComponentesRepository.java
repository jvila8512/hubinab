package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Componentes;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Componentes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ComponentesRepository extends JpaRepository<Componentes, Long> {}
