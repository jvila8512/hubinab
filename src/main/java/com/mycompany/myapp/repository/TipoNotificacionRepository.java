package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.TipoNoticia;
import com.mycompany.myapp.domain.TipoNotificacion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TipoNotificacion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoNotificacionRepository extends JpaRepository<TipoNotificacion, Long> {}
