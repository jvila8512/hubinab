package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.InnovacionRacionalizacion;
import com.mycompany.myapp.repository.InnovacionRacionalizacionRepository;
import com.mycompany.myapp.service.InnovacionRacionalizacionService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link InnovacionRacionalizacion}.
 */
@Service
@Transactional
public class InnovacionRacionalizacionServiceImpl implements InnovacionRacionalizacionService {

    private final Logger log = LoggerFactory.getLogger(InnovacionRacionalizacionServiceImpl.class);

    private final InnovacionRacionalizacionRepository innovacionRacionalizacionRepository;

    public InnovacionRacionalizacionServiceImpl(InnovacionRacionalizacionRepository innovacionRacionalizacionRepository) {
        this.innovacionRacionalizacionRepository = innovacionRacionalizacionRepository;
    }

    @Override
    public InnovacionRacionalizacion save(InnovacionRacionalizacion innovacionRacionalizacion) {
        log.debug("Request to save InnovacionRacionalizacion : {}", innovacionRacionalizacion);
        return innovacionRacionalizacionRepository.save(innovacionRacionalizacion);
    }

    @Override
    public InnovacionRacionalizacion update(InnovacionRacionalizacion innovacionRacionalizacion) {
        log.debug("Request to save InnovacionRacionalizacion : {}", innovacionRacionalizacion);
        return innovacionRacionalizacionRepository.save(innovacionRacionalizacion);
    }

    @Override
    public Optional<InnovacionRacionalizacion> partialUpdate(InnovacionRacionalizacion innovacionRacionalizacion) {
        log.debug("Request to partially update InnovacionRacionalizacion : {}", innovacionRacionalizacion);

        return innovacionRacionalizacionRepository
            .findById(innovacionRacionalizacion.getId())
            .map(existingInnovacionRacionalizacion -> {
                if (innovacionRacionalizacion.getTematica() != null) {
                    existingInnovacionRacionalizacion.setTematica(innovacionRacionalizacion.getTematica());
                }
                if (innovacionRacionalizacion.getFecha() != null) {
                    existingInnovacionRacionalizacion.setFecha(innovacionRacionalizacion.getFecha());
                }
                if (innovacionRacionalizacion.getVp() != null) {
                    existingInnovacionRacionalizacion.setVp(innovacionRacionalizacion.getVp());
                }
                if (innovacionRacionalizacion.getAutores() != null) {
                    existingInnovacionRacionalizacion.setAutores(innovacionRacionalizacion.getAutores());
                }
                if (innovacionRacionalizacion.getNumeroIdentidad() != null) {
                    existingInnovacionRacionalizacion.setNumeroIdentidad(innovacionRacionalizacion.getNumeroIdentidad());
                }
                if (innovacionRacionalizacion.getObservacion() != null) {
                    existingInnovacionRacionalizacion.setObservacion(innovacionRacionalizacion.getObservacion());
                }
                if (innovacionRacionalizacion.getAprobada() != null) {
                    existingInnovacionRacionalizacion.setAprobada(innovacionRacionalizacion.getAprobada());
                }
                if (innovacionRacionalizacion.getPublico() != null) {
                    existingInnovacionRacionalizacion.setPublico(innovacionRacionalizacion.getPublico());
                }

                return existingInnovacionRacionalizacion;
            })
            .map(innovacionRacionalizacionRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InnovacionRacionalizacion> findAll(Pageable pageable) {
        log.debug("Request to get all InnovacionRacionalizacions");
        return innovacionRacionalizacionRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InnovacionRacionalizacion> findAllbyPublico() {
        return innovacionRacionalizacionRepository.findAllbyPublica();
    }

    public Page<InnovacionRacionalizacion> findAllWithEagerRelationships(Pageable pageable) {
        return innovacionRacionalizacionRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<InnovacionRacionalizacion> findOne(Long id) {
        log.debug("Request to get InnovacionRacionalizacion : {}", id);
        return innovacionRacionalizacionRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete InnovacionRacionalizacion : {}", id);
        innovacionRacionalizacionRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InnovacionRacionalizacion> findAllbyPublicoByUser_id(Long iduser) {
        return innovacionRacionalizacionRepository.findAllbyPublicaByUserid(iduser);
    }
}
