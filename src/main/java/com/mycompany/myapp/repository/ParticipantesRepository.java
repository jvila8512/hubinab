package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Participantes;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Participantes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParticipantesRepository extends JpaRepository<Participantes, Long> {}
