package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.TipoNoticia;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TipoNoticia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoNoticiaRepository extends JpaRepository<TipoNoticia, Long> {}
