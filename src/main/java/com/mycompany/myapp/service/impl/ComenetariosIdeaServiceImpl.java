package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.ComenetariosIdea;
import com.mycompany.myapp.repository.ComenetariosIdeaRepository;
import com.mycompany.myapp.service.ComenetariosIdeaService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ComenetariosIdea}.
 */
@Service
@Transactional
public class ComenetariosIdeaServiceImpl implements ComenetariosIdeaService {

    private final Logger log = LoggerFactory.getLogger(ComenetariosIdeaServiceImpl.class);

    private final ComenetariosIdeaRepository comenetariosIdeaRepository;

    public ComenetariosIdeaServiceImpl(ComenetariosIdeaRepository comenetariosIdeaRepository) {
        this.comenetariosIdeaRepository = comenetariosIdeaRepository;
    }

    @Override
    public ComenetariosIdea save(ComenetariosIdea comenetariosIdea) {
        log.debug("Request to save ComenetariosIdea : {}", comenetariosIdea);
        return comenetariosIdeaRepository.save(comenetariosIdea);
    }

    @Override
    public ComenetariosIdea update(ComenetariosIdea comenetariosIdea) {
        log.debug("Request to save ComenetariosIdea : {}", comenetariosIdea);
        return comenetariosIdeaRepository.save(comenetariosIdea);
    }

    @Override
    public Optional<ComenetariosIdea> partialUpdate(ComenetariosIdea comenetariosIdea) {
        log.debug("Request to partially update ComenetariosIdea : {}", comenetariosIdea);

        return comenetariosIdeaRepository
            .findById(comenetariosIdea.getId())
            .map(existingComenetariosIdea -> {
                if (comenetariosIdea.getComentario() != null) {
                    existingComenetariosIdea.setComentario(comenetariosIdea.getComentario());
                }
                if (comenetariosIdea.getFechaInscripcion() != null) {
                    existingComenetariosIdea.setFechaInscripcion(comenetariosIdea.getFechaInscripcion());
                }

                return existingComenetariosIdea;
            })
            .map(comenetariosIdeaRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ComenetariosIdea> findAll() {
        log.debug("Request to get all ComenetariosIdeas");
        return comenetariosIdeaRepository.findAllWithEagerRelationships();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ComenetariosIdea> findAllByIdIdea(Long id) {
        log.debug("Request to get all ComenetariosIdeas by Id the Ideas");
        return comenetariosIdeaRepository.findAllWithEagerRelationshipsByIdIdea(id);
    }

    public Page<ComenetariosIdea> findAllWithEagerRelationships(Pageable pageable) {
        return comenetariosIdeaRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ComenetariosIdea> findOne(Long id) {
        log.debug("Request to get ComenetariosIdea : {}", id);
        return comenetariosIdeaRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ComenetariosIdea : {}", id);
        comenetariosIdeaRepository.deleteById(id);
    }
}
