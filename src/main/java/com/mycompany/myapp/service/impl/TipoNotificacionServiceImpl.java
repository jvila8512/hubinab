package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.TipoNoticia;
import com.mycompany.myapp.domain.TipoNotificacion;
import com.mycompany.myapp.repository.TipoNotificacionRepository;
import com.mycompany.myapp.service.TipoNotificacionService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TipoNoticia}.
 */
@Service
@Transactional
public class TipoNotificacionServiceImpl implements TipoNotificacionService {

    private final Logger log = LoggerFactory.getLogger(TipoNotificacionServiceImpl.class);

    private final TipoNotificacionRepository tipoNotificacionRepository;

    public TipoNotificacionServiceImpl(TipoNotificacionRepository tipoNotificacionRepository) {
        this.tipoNotificacionRepository = tipoNotificacionRepository;
    }

    @Override
    public TipoNotificacion save(TipoNotificacion tipoNotificacion) {
        log.debug("Request to save TipoNoticia : {}", tipoNotificacion);
        return tipoNotificacionRepository.save(tipoNotificacion);
    }

    @Override
    public TipoNotificacion update(TipoNotificacion tipoNotificacion) {
        log.debug("Request to save TipoNoticia : {}", tipoNotificacion);
        return tipoNotificacionRepository.save(tipoNotificacion);
    }

    @Override
    public Optional<TipoNotificacion> partialUpdate(TipoNotificacion tipoNotificacion) {
        log.debug("Request to partially update TipoNoticia : {}", tipoNotificacion);

        return tipoNotificacionRepository
            .findById(tipoNotificacion.getId())
            .map(existingTipoNoticia -> {
                if (tipoNotificacion.getTipoNotificacion() != null) {
                    existingTipoNoticia.setTipoNotificacion(tipoNotificacion.getTipoNotificacion());
                }

                return existingTipoNoticia;
            })
            .map(tipoNotificacionRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoNotificacion> findAll() {
        log.debug("Request to get all TipoNoticias");
        return tipoNotificacionRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TipoNotificacion> findOne(Long id) {
        log.debug("Request to get TipoNoticia : {}", id);
        return tipoNotificacionRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TipoNoticia : {}", id);
        tipoNotificacionRepository.deleteById(id);
    }
}
