package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.FooterSection;
import java.util.List;
import java.util.Optional;

public interface FooterSectionService {
    // CRUD Básico
    FooterSection create(FooterSection footerSection);
    FooterSection update(FooterSection footerSection);
    void delete(Long id);
    List<FooterSection> findAll();
    Optional<FooterSection> findOne(Long id);

    // Métodos específicos de negocio
    List<FooterSection> findActiveSections();
    boolean titleExists(FooterSection footerSection);
}
