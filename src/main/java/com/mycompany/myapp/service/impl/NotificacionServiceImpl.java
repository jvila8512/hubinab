package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Noti;
import com.mycompany.myapp.domain.Notificacion;
import com.mycompany.myapp.repository.NotiRepository;
import com.mycompany.myapp.repository.NotificacionRepository;
import com.mycompany.myapp.service.NotiService;
import com.mycompany.myapp.service.NotificacionService;
import java.util.List;
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
public class NotificacionServiceImpl implements NotificacionService {

    private final Logger log = LoggerFactory.getLogger(NotificacionServiceImpl.class);

    private final NotificacionRepository notificacionRepository;

    public NotificacionServiceImpl(NotificacionRepository notificacionRepository) {
        this.notificacionRepository = notificacionRepository;
    }

    @Override
    public Notificacion save(Notificacion noti) {
        log.debug("Request to save Noti : {}", noti);
        return notificacionRepository.save(noti);
    }

    @Override
    public void save1(Notificacion noti) {
        log.debug("Request to save Noti : {}", noti);
        notificacionRepository.save(noti);
    }

    @Override
    public Notificacion update(Notificacion noti) {
        log.debug("Request to save Noti : {}", noti);
        return notificacionRepository.save(noti);
    }

    @Override
    public Optional<Notificacion> partialUpdate(Notificacion noti) {
        log.debug("Request to partially update Noti : {}", noti);

        return notificacionRepository
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
            .map(notificacionRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Notificacion> findAll(Pageable pageable) {
        log.debug("Request to get all Notis");
        return notificacionRepository.findAll(pageable);
    }

    public Page<Notificacion> findAllWithEagerRelationships(Pageable pageable) {
        return notificacionRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Notificacion> findOne(Long id) {
        log.debug("Request to get Noti : {}", id);
        return notificacionRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Noti : {}", id);
        notificacionRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Notificacion> findTodasNotificaciones() {
        log.debug("Request to get Noti todas");
        return notificacionRepository.findAllNotificaciones();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Notificacion> findTodasNotificacionesbyUser(Long iduser) {
        log.debug("Request to et Noti : {}", iduser);
        return notificacionRepository.findAllNotificacionesbyUser(iduser);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Notificacion> findTodasNotificacionesbyUserVisto(Long iduser) {
        log.debug("Request to et Noti : {}", iduser);
        return notificacionRepository.findAllNotificacionesbyUserByVista(iduser);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Notificacion> findTodasNotificacionesbyUserNoVisto(Long iduser) {
        log.debug("Request to et Noti : {}", iduser);
        return notificacionRepository.findAllNotificacionesbyUserByNoVista(iduser);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Notificacion> findTodasNotificacionesbyUserCreada(Long idusercreada) {
        log.debug("Request to et Noti : {}", idusercreada);
        return notificacionRepository.findAllNotificacionesbyUsercreada(idusercreada);
    }

    @Override
    public Optional<Notificacion> findByUserIdByNotiId(Long iduser, Long idNoti) {
        return notificacionRepository.findOneByUserIdAndId(iduser, idNoti);
    }
}
