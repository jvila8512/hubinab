package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Entidad;
import com.mycompany.myapp.repository.EntidadRepository;
import com.mycompany.myapp.service.EntidadService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Entidad}.
 */
@Service
@Transactional
public class EntidadServiceImpl implements EntidadService {

    private final Logger log = LoggerFactory.getLogger(EntidadServiceImpl.class);

    private final EntidadRepository entidadRepository;

    public EntidadServiceImpl(EntidadRepository entidadRepository) {
        this.entidadRepository = entidadRepository;
    }

    @Override
    public Entidad save(Entidad entidad) {
        log.debug("Request to save Entidad : {}", entidad);
        return entidadRepository.save(entidad);
    }

    @Override
    public Entidad update(Entidad entidad) {
        log.debug("Request to save Entidad : {}", entidad);
        return entidadRepository.save(entidad);
    }

    @Override
    public Optional<Entidad> partialUpdate(Entidad entidad) {
        log.debug("Request to partially update Entidad : {}", entidad);

        return entidadRepository
            .findById(entidad.getId())
            .map(existingEntidad -> {
                if (entidad.getEntidad() != null) {
                    existingEntidad.setEntidad(entidad.getEntidad());
                }

                return existingEntidad;
            })
            .map(entidadRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Entidad> findAll() {
        log.debug("Request to get all Entidads");
        return entidadRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Entidad> findOne(Long id) {
        log.debug("Request to get Entidad : {}", id);
        return entidadRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Entidad : {}", id);
        entidadRepository.deleteById(id);
    }
}
