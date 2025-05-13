package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.LikeIdea;
import com.mycompany.myapp.repository.LikeIdeaRepository;
import com.mycompany.myapp.service.LikeIdeaService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LikeIdea}.
 */
@Service
@Transactional
public class LikeIdeaServiceImpl implements LikeIdeaService {

    private final Logger log = LoggerFactory.getLogger(LikeIdeaServiceImpl.class);

    private final LikeIdeaRepository likeIdeaRepository;

    public LikeIdeaServiceImpl(LikeIdeaRepository likeIdeaRepository) {
        this.likeIdeaRepository = likeIdeaRepository;
    }

    @Override
    public LikeIdea save(LikeIdea likeIdea) {
        log.debug("Request to save LikeIdea : {}", likeIdea);
        return likeIdeaRepository.save(likeIdea);
    }

    @Override
    public LikeIdea update(LikeIdea likeIdea) {
        log.debug("Request to save LikeIdea : {}", likeIdea);
        return likeIdeaRepository.save(likeIdea);
    }

    @Override
    public Optional<LikeIdea> partialUpdate(LikeIdea likeIdea) {
        log.debug("Request to partially update LikeIdea : {}", likeIdea);

        return likeIdeaRepository
            .findById(likeIdea.getId())
            .map(existingLikeIdea -> {
                if (likeIdea.getLike() != null) {
                    existingLikeIdea.setLike(likeIdea.getLike());
                }
                if (likeIdea.getFechaInscripcion() != null) {
                    existingLikeIdea.setFechaInscripcion(likeIdea.getFechaInscripcion());
                }

                return existingLikeIdea;
            })
            .map(likeIdeaRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LikeIdea> findAll() {
        log.debug("Request to get all LikeIdeas");
        return likeIdeaRepository.findAllWithEagerRelationships();
    }

    @Override
    @Transactional(readOnly = true)
    public List<LikeIdea> findAllbyIdea(Long id) {
        log.debug("Request to get all LikeIdeas by Idea");
        return likeIdeaRepository.findAllByIdea(id);
    }

    public Page<LikeIdea> findAllWithEagerRelationships(Pageable pageable) {
        return likeIdeaRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LikeIdea> findOne(Long id) {
        log.debug("Request to get LikeIdea : {}", id);
        return likeIdeaRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete LikeIdea : {}", id);
        likeIdeaRepository.deleteById(id);
    }
}
