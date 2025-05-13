package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Participantes;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface ParticipantesRepositoryWithBagRelationships {
    Optional<Participantes> fetchBagRelationships(Optional<Participantes> participantes);

    List<Participantes> fetchBagRelationships(List<Participantes> participantes);

    Page<Participantes> fetchBagRelationships(Page<Participantes> participantes);
}
