package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Ecosistema;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface EcosistemaRepositoryWithBagRelationships {
    Optional<Ecosistema> fetchBagRelationships(Optional<Ecosistema> ecosistema);

    List<Ecosistema> fetchBagRelationships(List<Ecosistema> ecosistemas);

    Page<Ecosistema> fetchBagRelationships(Page<Ecosistema> ecosistemas);
}
