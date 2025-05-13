package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Componentes;
import com.mycompany.myapp.repository.ComponentesRepository;
import com.mycompany.myapp.service.ComponentesService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Componentes}.
 */
@Service
@Transactional
public class ComponentesServiceImpl implements ComponentesService {

    private final Logger log = LoggerFactory.getLogger(ComponentesServiceImpl.class);

    private final ComponentesRepository componentesRepository;

    public ComponentesServiceImpl(ComponentesRepository componentesRepository) {
        this.componentesRepository = componentesRepository;
    }

    @Override
    public Componentes save(Componentes componentes) {
        log.debug("Request to save Componentes : {}", componentes);
        return componentesRepository.save(componentes);
    }

    @Override
    public Componentes update(Componentes componentes) {
        log.debug("Request to save Componentes : {}", componentes);
        return componentesRepository.save(componentes);
    }

    @Override
    public Optional<Componentes> partialUpdate(Componentes componentes) {
        log.debug("Request to partially update Componentes : {}", componentes);

        return componentesRepository
            .findById(componentes.getId())
            .map(existingComponentes -> {
                if (componentes.getComponente() != null) {
                    existingComponentes.setComponente(componentes.getComponente());
                }
                if (componentes.getDescripcion() != null) {
                    existingComponentes.setDescripcion(componentes.getDescripcion());
                }
                if (componentes.getActivo() != null) {
                    existingComponentes.setActivo(componentes.getActivo());
                }

                return existingComponentes;
            })
            .map(componentesRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Componentes> findAll() {
        log.debug("Request to get all Componentes");
        return componentesRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Componentes> findOne(Long id) {
        log.debug("Request to get Componentes : {}", id);
        return componentesRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Componentes : {}", id);
        componentesRepository.deleteById(id);
    }
}
