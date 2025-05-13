package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Proyectos;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface ProyectosRepositoryWithBagRelationships {
    Optional<Proyectos> fetchBagRelationships(Optional<Proyectos> proyectos);

    List<Proyectos> fetchBagRelationships(List<Proyectos> proyectos);

    Page<Proyectos> fetchBagRelationships(Page<Proyectos> proyectos);
}
