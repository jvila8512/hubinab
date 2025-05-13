package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.ContactoServicio;
import com.mycompany.myapp.repository.ContactoServicioRepository;
import com.mycompany.myapp.service.ContactoServicioService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ContactoServicio}.
 */
@Service
@Transactional
public class ContactoServicioServiceImpl implements ContactoServicioService {

    private final Logger log = LoggerFactory.getLogger(ContactoServicioServiceImpl.class);

    private final ContactoServicioRepository contactoServicioRepository;

    public ContactoServicioServiceImpl(ContactoServicioRepository contactoServicioRepository) {
        this.contactoServicioRepository = contactoServicioRepository;
    }

    @Override
    public ContactoServicio save(ContactoServicio contactoServicio) {
        log.debug("Request to save ContactoServicio : {}", contactoServicio);
        return contactoServicioRepository.save(contactoServicio);
    }

    @Override
    public ContactoServicio update(ContactoServicio contactoServicio) {
        log.debug("Request to save ContactoServicio : {}", contactoServicio);
        return contactoServicioRepository.save(contactoServicio);
    }

    @Override
    public Optional<ContactoServicio> partialUpdate(ContactoServicio contactoServicio) {
        log.debug("Request to partially update ContactoServicio : {}", contactoServicio);

        return contactoServicioRepository
            .findById(contactoServicio.getId())
            .map(existingContactoServicio -> {
                if (contactoServicio.getNombre() != null) {
                    existingContactoServicio.setNombre(contactoServicio.getNombre());
                }
                if (contactoServicio.getTelefono() != null) {
                    existingContactoServicio.setTelefono(contactoServicio.getTelefono());
                }
                if (contactoServicio.getCorreo() != null) {
                    existingContactoServicio.setCorreo(contactoServicio.getCorreo());
                }
                if (contactoServicio.getMensaje() != null) {
                    existingContactoServicio.setMensaje(contactoServicio.getMensaje());
                }
                if (contactoServicio.getFechaContacto() != null) {
                    existingContactoServicio.setFechaContacto(contactoServicio.getFechaContacto());
                }

                return existingContactoServicio;
            })
            .map(contactoServicioRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContactoServicio> findAll() {
        log.debug("Request to get all ContactoServicios");
        return contactoServicioRepository.findAllWithEagerRelationships();
    }

    public Page<ContactoServicio> findAllWithEagerRelationships(Pageable pageable) {
        return contactoServicioRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ContactoServicio> findOne(Long id) {
        log.debug("Request to get ContactoServicio : {}", id);
        return contactoServicioRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ContactoServicio : {}", id);
        contactoServicioRepository.deleteById(id);
    }
}
