package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.InnovacionRacionalizacion;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface InnovacionRepositoryWithBagRelationships {
    Optional<InnovacionRacionalizacion> fetchBagRelationships(Optional<InnovacionRacionalizacion> proyectos);

    List<InnovacionRacionalizacion> fetchBagRelationships(List<InnovacionRacionalizacion> proyectos);

    Page<InnovacionRacionalizacion> fetchBagRelationships(Page<InnovacionRacionalizacion> proyectos);
}
