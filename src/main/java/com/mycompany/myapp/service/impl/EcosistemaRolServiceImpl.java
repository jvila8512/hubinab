package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.EcosistemaRol;
import com.mycompany.myapp.repository.EcosistemaRolRepository;
import com.mycompany.myapp.service.EcosistemaRolService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EcosistemaRol}.
 */
@Service
@Transactional
public class EcosistemaRolServiceImpl implements EcosistemaRolService {

    private final Logger log = LoggerFactory.getLogger(EcosistemaRolServiceImpl.class);

    private final EcosistemaRolRepository ecosistemaRolRepository;

    public EcosistemaRolServiceImpl(EcosistemaRolRepository ecosistemaRolRepository) {
        this.ecosistemaRolRepository = ecosistemaRolRepository;
    }

    @Override
    public EcosistemaRol save(EcosistemaRol ecosistemaRol) {
        log.debug("Request to save EcosistemaRol : {}", ecosistemaRol);
        return ecosistemaRolRepository.save(ecosistemaRol);
    }

    @Override
    public EcosistemaRol update(EcosistemaRol ecosistemaRol) {
        log.debug("Request to save EcosistemaRol : {}", ecosistemaRol);
        return ecosistemaRolRepository.save(ecosistemaRol);
    }

    @Override
    public Optional<EcosistemaRol> partialUpdate(EcosistemaRol ecosistemaRol) {
        log.debug("Request to partially update EcosistemaRol : {}", ecosistemaRol);

        return ecosistemaRolRepository
            .findById(ecosistemaRol.getId())
            .map(existingEcosistemaRol -> {
                if (ecosistemaRol.getEcosistemaRol() != null) {
                    existingEcosistemaRol.setEcosistemaRol(ecosistemaRol.getEcosistemaRol());
                }
                if (ecosistemaRol.getDescripcion() != null) {
                    existingEcosistemaRol.setDescripcion(ecosistemaRol.getDescripcion());
                }

                return existingEcosistemaRol;
            })
            .map(ecosistemaRolRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EcosistemaRol> findAll() {
        log.debug("Request to get all EcosistemaRols");
        return ecosistemaRolRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EcosistemaRol> findOne(Long id) {
        log.debug("Request to get EcosistemaRol : {}", id);
        return ecosistemaRolRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EcosistemaRol : {}", id);
        ecosistemaRolRepository.deleteById(id);
    }
}
