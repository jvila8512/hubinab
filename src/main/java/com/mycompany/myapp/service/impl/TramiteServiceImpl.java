package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Tramite;
import com.mycompany.myapp.repository.TramiteRepository;
import com.mycompany.myapp.service.TramiteService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Tramite}.
 */
@Service
@Transactional
public class TramiteServiceImpl implements TramiteService {

    private final Logger log = LoggerFactory.getLogger(TramiteServiceImpl.class);

    private final TramiteRepository tramiteRepository;

    public TramiteServiceImpl(TramiteRepository tramiteRepository) {
        this.tramiteRepository = tramiteRepository;
    }

    @Override
    public Tramite save(Tramite tramite) {
        log.debug("Request to save Tramite : {}", tramite);
        return tramiteRepository.save(tramite);
    }

    @Override
    public Tramite update(Tramite tramite) {
        log.debug("Request to save Tramite : {}", tramite);
        return tramiteRepository.save(tramite);
    }

    @Override
    public Optional<Tramite> partialUpdate(Tramite tramite) {
        log.debug("Request to partially update Tramite : {}", tramite);

        return tramiteRepository
            .findById(tramite.getId())
            .map(existingTramite -> {
                if (tramite.getInscripcion() != null) {
                    existingTramite.setInscripcion(tramite.getInscripcion());
                }
                if (tramite.getPruebaExperimental() != null) {
                    existingTramite.setPruebaExperimental(tramite.getPruebaExperimental());
                }
                if (tramite.getExmanenEvaluacion() != null) {
                    existingTramite.setExmanenEvaluacion(tramite.getExmanenEvaluacion());
                }
                if (tramite.getDictamen() != null) {
                    existingTramite.setDictamen(tramite.getDictamen());
                }
                if (tramite.getConcesion() != null) {
                    existingTramite.setConcesion(tramite.getConcesion());
                }
                if (tramite.getDenegado() != null) {
                    existingTramite.setDenegado(tramite.getDenegado());
                }
                if (tramite.getReclamacion() != null) {
                    existingTramite.setReclamacion(tramite.getReclamacion());
                }
                if (tramite.getAnulacion() != null) {
                    existingTramite.setAnulacion(tramite.getAnulacion());
                }
                if (tramite.getFechaNotificacion() != null) {
                    existingTramite.setFechaNotificacion(tramite.getFechaNotificacion());
                }
                if (tramite.getFecaCertificado() != null) {
                    existingTramite.setFecaCertificado(tramite.getFecaCertificado());
                }
                if (tramite.getObservacion() != null) {
                    existingTramite.setObservacion(tramite.getObservacion());
                }

                return existingTramite;
            })
            .map(tramiteRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Tramite> findAll() {
        log.debug("Request to get all Tramites");
        return tramiteRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Tramite> findOne(Long id) {
        log.debug("Request to get Tramite : {}", id);
        return tramiteRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Tramite : {}", id);
        tramiteRepository.deleteById(id);
    }
}
