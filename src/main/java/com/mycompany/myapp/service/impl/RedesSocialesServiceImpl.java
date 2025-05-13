package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.RedesSociales;
import com.mycompany.myapp.repository.RedesSocialesRepository;
import com.mycompany.myapp.service.RedesSocialesService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RedesSociales}.
 */
@Service
@Transactional
public class RedesSocialesServiceImpl implements RedesSocialesService {

    private final Logger log = LoggerFactory.getLogger(RedesSocialesServiceImpl.class);

    private final RedesSocialesRepository redesSocialesRepository;

    public RedesSocialesServiceImpl(RedesSocialesRepository redesSocialesRepository) {
        this.redesSocialesRepository = redesSocialesRepository;
    }

    @Override
    public RedesSociales save(RedesSociales redesSociales) {
        log.debug("Request to save RedesSociales : {}", redesSociales);
        return redesSocialesRepository.save(redesSociales);
    }

    @Override
    public RedesSociales update(RedesSociales redesSociales) {
        log.debug("Request to save RedesSociales : {}", redesSociales);
        return redesSocialesRepository.save(redesSociales);
    }

    @Override
    public Optional<RedesSociales> partialUpdate(RedesSociales redesSociales) {
        log.debug("Request to partially update RedesSociales : {}", redesSociales);

        return redesSocialesRepository
            .findById(redesSociales.getId())
            .map(existingRedesSociales -> {
                if (redesSociales.getRedesUrl() != null) {
                    existingRedesSociales.setRedesUrl(redesSociales.getRedesUrl());
                }
                if (redesSociales.getLogoUrl() != null) {
                    existingRedesSociales.setLogoUrl(redesSociales.getLogoUrl());
                }
                if (redesSociales.getLogoUrlContentType() != null) {
                    existingRedesSociales.setLogoUrlContentType(redesSociales.getLogoUrlContentType());
                }

                return existingRedesSociales;
            })
            .map(redesSocialesRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RedesSociales> findAll() {
        log.debug("Request to get all RedesSociales");
        return redesSocialesRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RedesSociales> findOne(Long id) {
        log.debug("Request to get RedesSociales : {}", id);
        return redesSocialesRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RedesSociales : {}", id);
        redesSocialesRepository.deleteById(id);
    }
}
