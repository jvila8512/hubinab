package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Componentes;
import com.mycompany.myapp.domain.Comunidad;
import com.mycompany.myapp.repository.ComunidadRepository;
import com.mycompany.myapp.service.ComunidadService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Comunidad}.
 */
@Service
@Transactional
public class ComunidadServiceImpl implements ComunidadService {

    private final Logger log = LoggerFactory.getLogger(ComunidadServiceImpl.class);

    private final ComunidadRepository comunidadRepository;

    public ComunidadServiceImpl(ComunidadRepository comunidadRepository) {
        this.comunidadRepository = comunidadRepository;
    }

    @Override
    public Comunidad save(Comunidad comunidad) {
        log.debug("Request to save Comunidad : {}", comunidad);
        return comunidadRepository.save(comunidad);
    }

    @Override
    public Comunidad update(Comunidad comunidad) {
        log.debug("Request to save Componentes : {}", comunidad);
        return comunidadRepository.save(comunidad);
    }

    @Override
    public Optional<Comunidad> partialUpdate(Comunidad comunidad) {
        log.debug("Request to partially update Comunidad : {}", comunidad);

        return comunidadRepository
            .findById(comunidad.getId())
            .map(existingComunidad -> {
                if (comunidad.getComunidad() != null) {
                    existingComunidad.setComunidad(comunidad.getComunidad());
                }
                if (comunidad.getDescripcion() != null) {
                    existingComunidad.setDescripcion(comunidad.getDescripcion());
                }
                if (comunidad.getActivo() != null) {
                    existingComunidad.setActivo(comunidad.getActivo());
                }

                return existingComunidad;
            })
            .map(comunidadRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comunidad> findAll() {
        log.debug("Request to get all Comunidad");
        return comunidadRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comunidad> findAllByActivo() {
        log.debug("Request to get all Comunidad");
        return comunidadRepository.findByActivoTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Comunidad> findOne(Long id) {
        log.debug("Request to get Comunidad : {}", id);
        return comunidadRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Comunidad : {}", id);
        comunidadRepository.deleteById(id);
    }
}
