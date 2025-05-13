package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Ecosistema;
import com.mycompany.myapp.repository.EcosistemaRepository;
import com.mycompany.myapp.service.EcosistemaService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Ecosistema}.
 */
@Service
@Transactional
public class EcosistemaServiceImpl implements EcosistemaService {

    private final Logger log = LoggerFactory.getLogger(EcosistemaServiceImpl.class);

    private final EcosistemaRepository ecosistemaRepository;

    public EcosistemaServiceImpl(EcosistemaRepository ecosistemaRepository) {
        this.ecosistemaRepository = ecosistemaRepository;
    }

    @Override
    public Ecosistema save(Ecosistema ecosistema) {
        log.debug("Request to save Ecosistema : {}", ecosistema);
        return ecosistemaRepository.save(ecosistema);
    }

    @Override
    public Ecosistema update(Ecosistema ecosistema) {
        log.debug("Request to save Ecosistema : {}", ecosistema);
        return ecosistemaRepository.save(ecosistema);
    }

    @Override
    public Optional<Ecosistema> partialUpdate(Ecosistema ecosistema) {
        log.debug("Request to partially update Ecosistema : {}", ecosistema);

        return ecosistemaRepository
            .findById(ecosistema.getId())
            .map(existingEcosistema -> {
                if (ecosistema.getNombre() != null) {
                    existingEcosistema.setNombre(ecosistema.getNombre());
                }
                if (ecosistema.getTematica() != null) {
                    existingEcosistema.setTematica(ecosistema.getTematica());
                }
                if (ecosistema.getActivo() != null) {
                    existingEcosistema.setActivo(ecosistema.getActivo());
                }
                if (ecosistema.getLogoUrl() != null) {
                    existingEcosistema.setLogoUrl(ecosistema.getLogoUrl());
                }
                if (ecosistema.getLogoUrlContentType() != null) {
                    existingEcosistema.setLogoUrlContentType(ecosistema.getLogoUrlContentType());
                }
                if (ecosistema.getRanking() != null) {
                    existingEcosistema.setRanking(ecosistema.getRanking());
                }
                if (ecosistema.getUsuariosCant() != null) {
                    existingEcosistema.setUsuariosCant(ecosistema.getUsuariosCant());
                }
                if (ecosistema.getRetosCant() != null) {
                    existingEcosistema.setRetosCant(ecosistema.getRetosCant());
                }
                if (ecosistema.getIdeasCant() != null) {
                    existingEcosistema.setIdeasCant(ecosistema.getIdeasCant());
                }

                return existingEcosistema;
            })
            .map(ecosistemaRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Ecosistema> findAll() {
        log.debug("Request to get all Ecosistemas");
        return ecosistemaRepository.findAllWithEagerRelationships();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Ecosistema> findAllbyActivo() {
        log.debug("Request to get all Ecosistemas by Activo");
        return ecosistemaRepository.findAllWithEagerRelationshipsByActivo();
    }

    public Page<Ecosistema> findAllWithEagerRelationships(Pageable pageable) {
        return ecosistemaRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Ecosistema> findOne(Long id) {
        log.debug("Request to get Ecosistema : {}", id);
        return ecosistemaRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Ecosistema : {}", id);
        ecosistemaRepository.deleteById(id);
    }

    @Override
    public boolean usuarioEnEcosistemas(Long userId) {
        return ecosistemaRepository.existsByUserOrInUsers(userId);
    }
}
