package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Comunidad;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Comunidad entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ComunidadRepository extends JpaRepository<Comunidad, Long> {
    List<Comunidad> findByActivoTrue();
}
