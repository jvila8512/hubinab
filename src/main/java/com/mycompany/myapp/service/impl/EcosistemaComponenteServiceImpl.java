package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.EcosistemaComponente;
import com.mycompany.myapp.repository.EcosistemaComponenteRepository;
import com.mycompany.myapp.service.EcosistemaComponenteService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EcosistemaComponente}.
 */
@Service
@Transactional
public class EcosistemaComponenteServiceImpl implements EcosistemaComponenteService {

    private final Logger log = LoggerFactory.getLogger(EcosistemaComponenteServiceImpl.class);

    private final EcosistemaComponenteRepository ecosistemaComponenteRepository;

    public EcosistemaComponenteServiceImpl(EcosistemaComponenteRepository ecosistemaComponenteRepository) {
        this.ecosistemaComponenteRepository = ecosistemaComponenteRepository;
    }

    @Override
    public EcosistemaComponente save(EcosistemaComponente ecosistemaComponente) {
        log.debug("Request to save EcosistemaComponente : {}", ecosistemaComponente);
        return ecosistemaComponenteRepository.save(ecosistemaComponente);
    }

    @Override
    public EcosistemaComponente update(EcosistemaComponente ecosistemaComponente) {
        log.debug("Request to save EcosistemaComponente : {}", ecosistemaComponente);
        return ecosistemaComponenteRepository.save(ecosistemaComponente);
    }

    @Override
    public Optional<EcosistemaComponente> partialUpdate(EcosistemaComponente ecosistemaComponente) {
        log.debug("Request to partially update EcosistemaComponente : {}", ecosistemaComponente);

        return ecosistemaComponenteRepository
            .findById(ecosistemaComponente.getId())
            .map(existingEcosistemaComponente -> {
                if (ecosistemaComponente.getLink() != null) {
                    existingEcosistemaComponente.setLink(ecosistemaComponente.getLink());
                }
                if (ecosistemaComponente.getDocumentoUrl() != null) {
                    existingEcosistemaComponente.setDocumentoUrl(ecosistemaComponente.getDocumentoUrl());
                }
                if (ecosistemaComponente.getDocumentoUrlContentType() != null) {
                    existingEcosistemaComponente.setDocumentoUrlContentType(ecosistemaComponente.getDocumentoUrlContentType());
                }

                return existingEcosistemaComponente;
            })
            .map(ecosistemaComponenteRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EcosistemaComponente> findAll() {
        log.debug("Request to get all EcosistemaComponentes");
        return ecosistemaComponenteRepository.findAllWithEagerRelationships();
    }

    public Page<EcosistemaComponente> findAllWithEagerRelationships(Pageable pageable) {
        return ecosistemaComponenteRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EcosistemaComponente> findOne(Long id) {
        log.debug("Request to get EcosistemaComponente : {}", id);
        return ecosistemaComponenteRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EcosistemaComponente : {}", id);
        ecosistemaComponenteRepository.deleteById(id);
    }

    @Override
    public List<EcosistemaComponente> findAllComponentesbyEcosistema(Long id) {
        return ecosistemaComponenteRepository.findAllWithComponentesByEcosistema(id);
    }
}
