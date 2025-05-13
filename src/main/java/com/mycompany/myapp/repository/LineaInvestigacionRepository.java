package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.LineaInvestigacion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the LineaInvestigacion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LineaInvestigacionRepository extends JpaRepository<LineaInvestigacion, Long> {}
