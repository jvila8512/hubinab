package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CategoriaUser;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CategoriaUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategoriaUserRepository extends JpaRepository<CategoriaUser, Long> {}
