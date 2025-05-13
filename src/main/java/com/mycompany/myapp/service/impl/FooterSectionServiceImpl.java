package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.FooterSection;
import com.mycompany.myapp.repository.FooterSectionRepository;
import com.mycompany.myapp.service.FooterSectionService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FooterSectionServiceImpl implements FooterSectionService {

    private FooterSectionRepository repository;

    // Inyección por constructor (recomendado)
    public FooterSectionServiceImpl(FooterSectionRepository repository) {
        this.repository = repository;
    }

    @Override
    public FooterSection create(FooterSection footerSection) {
        // Las validaciones principales están en el Controller
        return repository.save(footerSection);
    }

    @Override
    public FooterSection update(FooterSection footerSection) {
        return repository.save(footerSection);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FooterSection> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FooterSection> findOne(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FooterSection> findActiveSections() {
        return repository.findByActiveTrueOrderByOrderAsc();
    }

    @Override
    public boolean titleExists(FooterSection footerSection) {
        if (footerSection.getTitle() == null || footerSection.getId() == null) {
            return false;
        }

        // Verifica si existe otro registro con el mismo título pero diferente ID
        return repository.existsByTitleAndIdNot(footerSection.getTitle().trim(), footerSection.getId());
    }
}
