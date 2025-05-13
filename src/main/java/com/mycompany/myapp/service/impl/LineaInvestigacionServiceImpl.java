package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.LineaInvestigacion;
import com.mycompany.myapp.repository.LineaInvestigacionRepository;
import com.mycompany.myapp.service.LineaInvestigacionService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LineaInvestigacion}.
 */
@Service
@Transactional
public class LineaInvestigacionServiceImpl implements LineaInvestigacionService {

    private final Logger log = LoggerFactory.getLogger(LineaInvestigacionServiceImpl.class);

    private final LineaInvestigacionRepository lineaInvestigacionRepository;

    public LineaInvestigacionServiceImpl(LineaInvestigacionRepository lineaInvestigacionRepository) {
        this.lineaInvestigacionRepository = lineaInvestigacionRepository;
    }

    @Override
    public LineaInvestigacion save(LineaInvestigacion lineaInvestigacion) {
        log.debug("Request to save LineaInvestigacion : {}", lineaInvestigacion);
        return lineaInvestigacionRepository.save(lineaInvestigacion);
    }

    @Override
    public LineaInvestigacion update(LineaInvestigacion lineaInvestigacion) {
        log.debug("Request to save LineaInvestigacion : {}", lineaInvestigacion);
        return lineaInvestigacionRepository.save(lineaInvestigacion);
    }

    @Override
    public Optional<LineaInvestigacion> partialUpdate(LineaInvestigacion lineaInvestigacion) {
        log.debug("Request to partially update LineaInvestigacion : {}", lineaInvestigacion);

        return lineaInvestigacionRepository
            .findById(lineaInvestigacion.getId())
            .map(existingLineaInvestigacion -> {
                if (lineaInvestigacion.getLinea() != null) {
                    existingLineaInvestigacion.setLinea(lineaInvestigacion.getLinea());
                }

                return existingLineaInvestigacion;
            })
            .map(lineaInvestigacionRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LineaInvestigacion> findAll() {
        log.debug("Request to get all LineaInvestigacions");
        return lineaInvestigacionRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LineaInvestigacion> findOne(Long id) {
        log.debug("Request to get LineaInvestigacion : {}", id);
        return lineaInvestigacionRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete LineaInvestigacion : {}", id);
        lineaInvestigacionRepository.deleteById(id);
    }
}
