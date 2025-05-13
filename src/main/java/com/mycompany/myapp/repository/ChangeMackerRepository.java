package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ChangeMacker;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ChangeMacker entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChangeMackerRepository extends JpaRepository<ChangeMacker, Long> {}
