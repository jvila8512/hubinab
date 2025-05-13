package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.RedesSociales;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the RedesSociales entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RedesSocialesRepository extends JpaRepository<RedesSociales, Long> {}
