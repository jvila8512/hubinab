package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.TipoContacto;
import com.mycompany.myapp.repository.TipoContactoRepository;
import com.mycompany.myapp.service.TipoContactoService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TipoContacto}.
 */
@Service
@Transactional
public class TipoContactoServiceImpl implements TipoContactoService {

    private final Logger log = LoggerFactory.getLogger(TipoContactoServiceImpl.class);

    private final TipoContactoRepository tipoContactoRepository;

    public TipoContactoServiceImpl(TipoContactoRepository tipoContactoRepository) {
        this.tipoContactoRepository = tipoContactoRepository;
    }

    @Override
    public TipoContacto save(TipoContacto tipoContacto) {
        log.debug("Request to save TipoContacto : {}", tipoContacto);
        return tipoContactoRepository.save(tipoContacto);
    }

    @Override
    public TipoContacto update(TipoContacto tipoContacto) {
        log.debug("Request to save TipoContacto : {}", tipoContacto);
        return tipoContactoRepository.save(tipoContacto);
    }

    @Override
    public Optional<TipoContacto> partialUpdate(TipoContacto tipoContacto) {
        log.debug("Request to partially update TipoContacto : {}", tipoContacto);

        return tipoContactoRepository
            .findById(tipoContacto.getId())
            .map(existingTipoContacto -> {
                if (tipoContacto.getTipoContacto() != null) {
                    existingTipoContacto.setTipoContacto(tipoContacto.getTipoContacto());
                }

                return existingTipoContacto;
            })
            .map(tipoContactoRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoContacto> findAll() {
        log.debug("Request to get all TipoContactos");
        return tipoContactoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TipoContacto> findOne(Long id) {
        log.debug("Request to get TipoContacto : {}", id);
        return tipoContactoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TipoContacto : {}", id);
        tipoContactoRepository.deleteById(id);
    }
}
