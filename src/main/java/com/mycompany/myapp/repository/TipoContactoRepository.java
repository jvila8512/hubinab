package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.TipoContacto;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TipoContacto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoContactoRepository extends JpaRepository<TipoContacto, Long> {}
