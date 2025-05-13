package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Idea;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface IdeaRepositoryWithBagRelationships {
    Optional<Idea> fetchBagRelationships(Optional<Idea> ideas);

    List<Idea> fetchBagRelationships(List<Idea> ideas);

    Page<Idea> fetchBagRelationships(Page<Idea> ideas);
}
