package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.UsuarioEcosistema;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface UsuarioEcosistemaRepositoryWithBagRelationships {
    Optional<UsuarioEcosistema> fetchBagRelationships(Optional<UsuarioEcosistema> usuarioEcosistema);

    List<UsuarioEcosistema> fetchBagRelationships(List<UsuarioEcosistema> usuarioEcosistemas);

    Page<UsuarioEcosistema> fetchBagRelationships(Page<UsuarioEcosistema> usuarioEcosistemas);
}
