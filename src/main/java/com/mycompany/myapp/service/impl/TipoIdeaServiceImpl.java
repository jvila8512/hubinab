package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.TipoIdea;
import com.mycompany.myapp.repository.TipoIdeaRepository;
import com.mycompany.myapp.service.TipoIdeaService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TipoIdea}.
 */
@Service
@Transactional
public class TipoIdeaServiceImpl implements TipoIdeaService {

    private final Logger log = LoggerFactory.getLogger(TipoIdeaServiceImpl.class);

    private final TipoIdeaRepository tipoIdeaRepository;

    public TipoIdeaServiceImpl(TipoIdeaRepository tipoIdeaRepository) {
        this.tipoIdeaRepository = tipoIdeaRepository;
    }

    @Override
    public TipoIdea save(TipoIdea tipoIdea) {
        log.debug("Request to save TipoIdea : {}", tipoIdea);
        return tipoIdeaRepository.save(tipoIdea);
    }

    @Override
    public TipoIdea update(TipoIdea tipoIdea) {
        log.debug("Request to save TipoIdea : {}", tipoIdea);
        return tipoIdeaRepository.save(tipoIdea);
    }

    @Override
    public Optional<TipoIdea> partialUpdate(TipoIdea tipoIdea) {
        log.debug("Request to partially update TipoIdea : {}", tipoIdea);

        return tipoIdeaRepository
            .findById(tipoIdea.getId())
            .map(existingTipoIdea -> {
                if (tipoIdea.getTipoIdea() != null) {
                    existingTipoIdea.setTipoIdea(tipoIdea.getTipoIdea());
                }

                return existingTipoIdea;
            })
            .map(tipoIdeaRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoIdea> findAll() {
        log.debug("Request to get all TipoIdeas");
        return tipoIdeaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TipoIdea> findOne(Long id) {
        log.debug("Request to get TipoIdea : {}", id);
        return tipoIdeaRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TipoIdea : {}", id);
        tipoIdeaRepository.deleteById(id);
    }
}
