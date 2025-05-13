package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.TipoIdea;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TipoIdea entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoIdeaRepository extends JpaRepository<TipoIdea, Long> {}
