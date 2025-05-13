package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.EcosistemaRol;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the EcosistemaRol entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EcosistemaRolRepository extends JpaRepository<EcosistemaRol, Long> {}
