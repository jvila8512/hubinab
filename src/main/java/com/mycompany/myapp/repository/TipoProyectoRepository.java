package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.TipoProyecto;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TipoProyecto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoProyectoRepository extends JpaRepository<TipoProyecto, Long> {}
