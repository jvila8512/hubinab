package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Noti;
import com.mycompany.myapp.repository.NotiRepository;
import com.mycompany.myapp.service.NotiService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Noti}.
 */
@Service
@Transactional
public class NotiServiceImpl implements NotiService {

    private final Logger log = LoggerFactory.getLogger(NotiServiceImpl.class);

    private final NotiRepository notiRepository;

    public NotiServiceImpl(NotiRepository notiRepository) {
        this.notiRepository = notiRepository;
    }

    @Override
    public Noti save(Noti noti) {
        log.debug("Request to save Noti : {}", noti);
        return notiRepository.save(noti);
    }

    @Override
    public Noti update(Noti noti) {
        log.debug("Request to save Noti : {}", noti);
        return notiRepository.save(noti);
    }

    @Override
    public Optional<Noti> partialUpdate(Noti noti) {
        log.debug("Request to partially update Noti : {}", noti);

        return notiRepository
            .findById(noti.getId())
            .map(existingNoti -> {
                if (noti.getDescripcion() != null) {
                    existingNoti.setDescripcion(noti.getDescripcion());
                }
                if (noti.getVisto() != null) {
                    existingNoti.setVisto(noti.getVisto());
                }
                if (noti.getFecha() != null) {
                    existingNoti.setFecha(noti.getFecha());
                }

                return existingNoti;
            })
            .map(notiRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Noti> findAll(Pageable pageable) {
        log.debug("Request to get all Notis");
        return notiRepository.findAll(pageable);
    }

    public Page<Noti> findAllWithEagerRelationships(Pageable pageable) {
        return notiRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Noti> findOne(Long id) {
        log.debug("Request to get Noti : {}", id);
        return notiRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Noti : {}", id);
        notiRepository.deleteById(id);
    }
}
