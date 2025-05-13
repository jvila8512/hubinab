package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Ods;
import com.mycompany.myapp.repository.OdsRepository;
import com.mycompany.myapp.service.OdsService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Ods}.
 */
@Service
@Transactional
public class OdsServiceImpl implements OdsService {

    private final Logger log = LoggerFactory.getLogger(OdsServiceImpl.class);

    private final OdsRepository odsRepository;

    public OdsServiceImpl(OdsRepository odsRepository) {
        this.odsRepository = odsRepository;
    }

    @Override
    public Ods save(Ods ods) {
        log.debug("Request to save Ods : {}", ods);
        return odsRepository.save(ods);
    }

    @Override
    public Ods update(Ods ods) {
        log.debug("Request to save Ods : {}", ods);
        return odsRepository.save(ods);
    }

    @Override
    public Optional<Ods> partialUpdate(Ods ods) {
        log.debug("Request to partially update Ods : {}", ods);

        return odsRepository
            .findById(ods.getId())
            .map(existingOds -> {
                if (ods.getOds() != null) {
                    existingOds.setOds(ods.getOds());
                }

                return existingOds;
            })
            .map(odsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Ods> findAll() {
        log.debug("Request to get all Ods");
        return odsRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Ods> findOne(Long id) {
        log.debug("Request to get Ods : {}", id);
        return odsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Ods : {}", id);
        odsRepository.deleteById(id);
    }
}
