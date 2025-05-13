package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.ContactoChangeMacker;
import com.mycompany.myapp.repository.ContactoChangeMackerRepository;
import com.mycompany.myapp.service.ContactoChangeMackerService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ContactoChangeMacker}.
 */
@Service
@Transactional
public class ContactoChangeMackerServiceImpl implements ContactoChangeMackerService {

    private final Logger log = LoggerFactory.getLogger(ContactoChangeMackerServiceImpl.class);

    private final ContactoChangeMackerRepository contactoChangeMackerRepository;

    public ContactoChangeMackerServiceImpl(ContactoChangeMackerRepository contactoChangeMackerRepository) {
        this.contactoChangeMackerRepository = contactoChangeMackerRepository;
    }

    @Override
    public ContactoChangeMacker save(ContactoChangeMacker contactoChangeMacker) {
        log.debug("Request to save ContactoChangeMacker : {}", contactoChangeMacker);
        return contactoChangeMackerRepository.save(contactoChangeMacker);
    }

    @Override
    public ContactoChangeMacker update(ContactoChangeMacker contactoChangeMacker) {
        log.debug("Request to save ContactoChangeMacker : {}", contactoChangeMacker);
        return contactoChangeMackerRepository.save(contactoChangeMacker);
    }

    @Override
    public Optional<ContactoChangeMacker> partialUpdate(ContactoChangeMacker contactoChangeMacker) {
        log.debug("Request to partially update ContactoChangeMacker : {}", contactoChangeMacker);

        return contactoChangeMackerRepository
            .findById(contactoChangeMacker.getId())
            .map(existingContactoChangeMacker -> {
                if (contactoChangeMacker.getNombre() != null) {
                    existingContactoChangeMacker.setNombre(contactoChangeMacker.getNombre());
                }
                if (contactoChangeMacker.getTelefono() != null) {
                    existingContactoChangeMacker.setTelefono(contactoChangeMacker.getTelefono());
                }
                if (contactoChangeMacker.getCorreo() != null) {
                    existingContactoChangeMacker.setCorreo(contactoChangeMacker.getCorreo());
                }
                if (contactoChangeMacker.getMensaje() != null) {
                    existingContactoChangeMacker.setMensaje(contactoChangeMacker.getMensaje());
                }
                if (contactoChangeMacker.getFechaContacto() != null) {
                    existingContactoChangeMacker.setFechaContacto(contactoChangeMacker.getFechaContacto());
                }

                return existingContactoChangeMacker;
            })
            .map(contactoChangeMackerRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContactoChangeMacker> findAll() {
        log.debug("Request to get all ContactoChangeMackers");
        return contactoChangeMackerRepository.findAllWithEagerRelationships();
    }

    public Page<ContactoChangeMacker> findAllWithEagerRelationships(Pageable pageable) {
        return contactoChangeMackerRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ContactoChangeMacker> findOne(Long id) {
        log.debug("Request to get ContactoChangeMacker : {}", id);
        return contactoChangeMackerRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ContactoChangeMacker : {}", id);
        contactoChangeMackerRepository.deleteById(id);
    }
}
