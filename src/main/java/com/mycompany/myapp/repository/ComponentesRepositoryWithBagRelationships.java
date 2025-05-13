package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Componentes;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface ComponentesRepositoryWithBagRelationships {
    Optional<Componentes> fetchBagRelationships(Optional<Componentes> componentes);

    List<Componentes> fetchBagRelationships(List<Componentes> componentes);

    Page<Componentes> fetchBagRelationships(Page<Componentes> componentes);
}
