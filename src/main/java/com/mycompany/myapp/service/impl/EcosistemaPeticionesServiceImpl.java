package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.EcosistemaPeticiones;
import com.mycompany.myapp.repository.EcosistemaPeticionesRepository;
import com.mycompany.myapp.service.EcosistemaPeticionesService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EcosistemaPeticiones}.
 */
@Service
@Transactional
public class EcosistemaPeticionesServiceImpl implements EcosistemaPeticionesService {

    private final Logger log = LoggerFactory.getLogger(EcosistemaPeticionesServiceImpl.class);

    private final EcosistemaPeticionesRepository ecosistemaPeticionesRepository;

    public EcosistemaPeticionesServiceImpl(EcosistemaPeticionesRepository ecosistemaPeticionesRepository) {
        this.ecosistemaPeticionesRepository = ecosistemaPeticionesRepository;
    }

    @Override
    public EcosistemaPeticiones save(EcosistemaPeticiones ecosistemaPeticiones) {
        log.debug("Request to save EcosistemaPeticiones : {}", ecosistemaPeticiones);
        return ecosistemaPeticionesRepository.save(ecosistemaPeticiones);
    }

    @Override
    public EcosistemaPeticiones update(EcosistemaPeticiones ecosistemaPeticiones) {
        log.debug("Request to save EcosistemaPeticiones : {}", ecosistemaPeticiones);
        return ecosistemaPeticionesRepository.save(ecosistemaPeticiones);
    }

    @Override
    public Optional<EcosistemaPeticiones> partialUpdate(EcosistemaPeticiones ecosistemaPeticiones) {
        log.debug("Request to partially update EcosistemaPeticiones : {}", ecosistemaPeticiones);

        return ecosistemaPeticionesRepository
            .findById(ecosistemaPeticiones.getId())
            .map(existingEcosistemaPeticiones -> {
                if (ecosistemaPeticiones.getMotivo() != null) {
                    existingEcosistemaPeticiones.setMotivo(ecosistemaPeticiones.getMotivo());
                }
                if (ecosistemaPeticiones.getFechaPeticion() != null) {
                    existingEcosistemaPeticiones.setFechaPeticion(ecosistemaPeticiones.getFechaPeticion());
                }
                if (ecosistemaPeticiones.getAprobada() != null) {
                    existingEcosistemaPeticiones.setAprobada(ecosistemaPeticiones.getAprobada());
                }

                return existingEcosistemaPeticiones;
            })
            .map(ecosistemaPeticionesRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EcosistemaPeticiones> findAll() {
        log.debug("Request to get all EcosistemaPeticiones");
        return ecosistemaPeticionesRepository.findAllWithEagerRelationships();
    }

    public Page<EcosistemaPeticiones> findAllWithEagerRelationships(Pageable pageable) {
        return ecosistemaPeticionesRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EcosistemaPeticiones> findOne(Long id) {
        log.debug("Request to get EcosistemaPeticiones : {}", id);
        return ecosistemaPeticionesRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EcosistemaPeticiones : {}", id);
        ecosistemaPeticionesRepository.deleteById(id);
    }
}
