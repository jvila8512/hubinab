package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Proyectos;
import com.mycompany.myapp.domain.Reto;
import com.mycompany.myapp.repository.ProyectosRepository;
import com.mycompany.myapp.service.ProyectosService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Proyectos}.
 */
@Service
@Transactional
public class ProyectosServiceImpl implements ProyectosService {

    private final Logger log = LoggerFactory.getLogger(ProyectosServiceImpl.class);

    private final ProyectosRepository proyectosRepository;

    public ProyectosServiceImpl(ProyectosRepository proyectosRepository) {
        this.proyectosRepository = proyectosRepository;
    }

    @Override
    public Proyectos save(Proyectos proyectos) {
        log.debug("Request to save Proyectos : {}", proyectos);
        return proyectosRepository.save(proyectos);
    }

    @Override
    public Proyectos update(Proyectos proyectos) {
        log.debug("Request to save Proyectos : {}", proyectos);
        return proyectosRepository.save(proyectos);
    }

    @Override
    public Optional<Proyectos> partialUpdate(Proyectos proyectos) {
        log.debug("Request to partially update Proyectos : {}", proyectos);

        return proyectosRepository
            .findById(proyectos.getId())
            .map(existingProyectos -> {
                if (proyectos.getNombre() != null) {
                    existingProyectos.setNombre(proyectos.getNombre());
                }
                if (proyectos.getDescripcion() != null) {
                    existingProyectos.setDescripcion(proyectos.getDescripcion());
                }
                if (proyectos.getAutor() != null) {
                    existingProyectos.setAutor(proyectos.getAutor());
                }
                if (proyectos.getNecesidad() != null) {
                    existingProyectos.setNecesidad(proyectos.getNecesidad());
                }
                if (proyectos.getFechaInicio() != null) {
                    existingProyectos.setFechaInicio(proyectos.getFechaInicio());
                }
                if (proyectos.getFechaFin() != null) {
                    existingProyectos.setFechaFin(proyectos.getFechaFin());
                }
                if (proyectos.getLogoUrl() != null) {
                    existingProyectos.setLogoUrl(proyectos.getLogoUrl());
                }
                if (proyectos.getLogoUrlContentType() != null) {
                    existingProyectos.setLogoUrlContentType(proyectos.getLogoUrlContentType());
                }

                return existingProyectos;
            })
            .map(proyectosRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Proyectos> findAll(Pageable pageable) {
        log.debug("Request to get all Proyectos");
        return proyectosRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Proyectos> findAll() {
        log.debug("Request to get all Proyectos");
        return proyectosRepository.findAllTodos();
    }

    public Page<Proyectos> findAllWithEagerRelationships(Pageable pageable) {
        return proyectosRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    public List<Proyectos> findAllWithEagerRelationshipsByEcosistema(Long id) {
        return proyectosRepository.findAllWithEagerRelationshipsByEcosistema(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Proyectos> findOne(Long id) {
        log.debug("Request to get Proyectos : {}", id);
        return proyectosRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Proyectos : {}", id);
        proyectosRepository.deleteById(id);
    }

    @Override
    public Long contarEcosistemas(Long id) {
        return proyectosRepository.contar(id);
    }

    @Override
    public List<Proyectos> busquedaGeneralPorEcosistemasId(List<Long> ecosistemas) {
        return proyectosRepository.findAllByEcosistemaIdInOrderByEcosistemaIdDesc(ecosistemas);
    }
}
