package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.TipoProyecto;
import com.mycompany.myapp.repository.TipoProyectoRepository;
import com.mycompany.myapp.service.TipoProyectoService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TipoProyecto}.
 */
@Service
@Transactional
public class TipoProyectoServiceImpl implements TipoProyectoService {

    private final Logger log = LoggerFactory.getLogger(TipoProyectoServiceImpl.class);

    private final TipoProyectoRepository tipoProyectoRepository;

    public TipoProyectoServiceImpl(TipoProyectoRepository tipoProyectoRepository) {
        this.tipoProyectoRepository = tipoProyectoRepository;
    }

    @Override
    public TipoProyecto save(TipoProyecto tipoProyecto) {
        log.debug("Request to save TipoProyecto : {}", tipoProyecto);
        return tipoProyectoRepository.save(tipoProyecto);
    }

    @Override
    public TipoProyecto update(TipoProyecto tipoProyecto) {
        log.debug("Request to save TipoProyecto : {}", tipoProyecto);
        return tipoProyectoRepository.save(tipoProyecto);
    }

    @Override
    public Optional<TipoProyecto> partialUpdate(TipoProyecto tipoProyecto) {
        log.debug("Request to partially update TipoProyecto : {}", tipoProyecto);

        return tipoProyectoRepository
            .findById(tipoProyecto.getId())
            .map(existingTipoProyecto -> {
                if (tipoProyecto.getTipoProyecto() != null) {
                    existingTipoProyecto.setTipoProyecto(tipoProyecto.getTipoProyecto());
                }

                return existingTipoProyecto;
            })
            .map(tipoProyectoRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoProyecto> findAll() {
        log.debug("Request to get all TipoProyectos");
        return tipoProyectoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TipoProyecto> findOne(Long id) {
        log.debug("Request to get TipoProyecto : {}", id);
        return tipoProyectoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TipoProyecto : {}", id);
        tipoProyectoRepository.deleteById(id);
    }
}
