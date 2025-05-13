package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.TipoNoticia;
import com.mycompany.myapp.repository.TipoNoticiaRepository;
import com.mycompany.myapp.service.TipoNoticiaService;
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
public class TipoNoticiaServiceImpl implements TipoNoticiaService {

    private final Logger log = LoggerFactory.getLogger(TipoNoticiaServiceImpl.class);

    private final TipoNoticiaRepository tipoNoticiaRepository;

    public TipoNoticiaServiceImpl(TipoNoticiaRepository tipoNoticiaRepository) {
        this.tipoNoticiaRepository = tipoNoticiaRepository;
    }

    @Override
    public TipoNoticia save(TipoNoticia tipoNoticia) {
        log.debug("Request to save TipoNoticia : {}", tipoNoticia);
        return tipoNoticiaRepository.save(tipoNoticia);
    }

    @Override
    public TipoNoticia update(TipoNoticia tipoNoticia) {
        log.debug("Request to save TipoNoticia : {}", tipoNoticia);
        return tipoNoticiaRepository.save(tipoNoticia);
    }

    @Override
    public Optional<TipoNoticia> partialUpdate(TipoNoticia tipoNoticia) {
        log.debug("Request to partially update TipoNoticia : {}", tipoNoticia);

        return tipoNoticiaRepository
            .findById(tipoNoticia.getId())
            .map(existingTipoNoticia -> {
                if (tipoNoticia.getTipoNoticia() != null) {
                    existingTipoNoticia.setTipoNoticia(tipoNoticia.getTipoNoticia());
                }

                return existingTipoNoticia;
            })
            .map(tipoNoticiaRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoNoticia> findAll() {
        log.debug("Request to get all TipoNoticias");
        return tipoNoticiaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TipoNoticia> findOne(Long id) {
        log.debug("Request to get TipoNoticia : {}", id);
        return tipoNoticiaRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TipoNoticia : {}", id);
        tipoNoticiaRepository.deleteById(id);
    }
}
