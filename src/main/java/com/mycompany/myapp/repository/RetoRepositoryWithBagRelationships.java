package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Reto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface RetoRepositoryWithBagRelationships {
    Optional<Reto> fetchBagRelationships(Optional<Reto> Reto);

    List<Reto> fetchBagRelationships(List<Reto> Retos);

    Page<Reto> fetchBagRelationships(Page<Reto> Retos);
}
