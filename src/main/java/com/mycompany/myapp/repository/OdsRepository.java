package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Ods;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Ods entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OdsRepository extends JpaRepository<Ods, Long> {}
