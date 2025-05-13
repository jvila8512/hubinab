package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Servicios;
import com.mycompany.myapp.repository.ServiciosRepository;
import com.mycompany.myapp.service.ServiciosService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Servicios}.
 */
@Service
@Transactional
public class ServiciosServiceImpl implements ServiciosService {

    private final Logger log = LoggerFactory.getLogger(ServiciosServiceImpl.class);

    private final ServiciosRepository serviciosRepository;

    public ServiciosServiceImpl(ServiciosRepository serviciosRepository) {
        this.serviciosRepository = serviciosRepository;
    }

    @Override
    public Servicios save(Servicios servicios) {
        log.debug("Request to save Servicios : {}", servicios);
        return serviciosRepository.save(servicios);
    }

    @Override
    public Servicios update(Servicios servicios) {
        log.debug("Request to save Servicios : {}", servicios);
        return serviciosRepository.save(servicios);
    }

    @Override
    public Optional<Servicios> partialUpdate(Servicios servicios) {
        log.debug("Request to partially update Servicios : {}", servicios);

        return serviciosRepository
            .findById(servicios.getId())
            .map(existingServicios -> {
                if (servicios.getServicio() != null) {
                    existingServicios.setServicio(servicios.getServicio());
                }
                if (servicios.getDescripcion() != null) {
                    existingServicios.setDescripcion(servicios.getDescripcion());
                }
                if (servicios.getPublicado() != null) {
                    existingServicios.setPublicado(servicios.getPublicado());
                }
                if (servicios.getFoto() != null) {
                    existingServicios.setFoto(servicios.getFoto());
                }
                if (servicios.getFotoContentType() != null) {
                    existingServicios.setFotoContentType(servicios.getFotoContentType());
                }

                return existingServicios;
            })
            .map(serviciosRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Servicios> findAll() {
        log.debug("Request to get all Servicios");
        return serviciosRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Servicios> findAllByPublicado() {
        return serviciosRepository.findByPublicadoTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Servicios> findOne(Long id) {
        log.debug("Request to get Servicios : {}", id);
        return serviciosRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Servicios : {}", id);
        serviciosRepository.deleteById(id);
    }
}
