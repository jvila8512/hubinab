package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Contacto;
import com.mycompany.myapp.repository.ContactoRepository;
import com.mycompany.myapp.service.ContactoService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Contacto}.
 */
@Service
@Transactional
public class ContactoServiceImpl implements ContactoService {

    private final Logger log = LoggerFactory.getLogger(ContactoServiceImpl.class);

    private final ContactoRepository contactoRepository;

    public ContactoServiceImpl(ContactoRepository contactoRepository) {
        this.contactoRepository = contactoRepository;
    }

    @Override
    public Contacto save(Contacto contacto) {
        log.debug("Request to save Contacto : {}", contacto);
        return contactoRepository.save(contacto);
    }

    @Override
    public Contacto update(Contacto contacto) {
        log.debug("Request to save Contacto : {}", contacto);
        return contactoRepository.save(contacto);
    }

    @Override
    public Optional<Contacto> partialUpdate(Contacto contacto) {
        log.debug("Request to partially update Contacto : {}", contacto);

        return contactoRepository
            .findById(contacto.getId())
            .map(existingContacto -> {
                if (contacto.getNombre() != null) {
                    existingContacto.setNombre(contacto.getNombre());
                }
                if (contacto.getTelefono() != null) {
                    existingContacto.setTelefono(contacto.getTelefono());
                }
                if (contacto.getCorreo() != null) {
                    existingContacto.setCorreo(contacto.getCorreo());
                }
                if (contacto.getMensaje() != null) {
                    existingContacto.setMensaje(contacto.getMensaje());
                }
                if (contacto.getFechaContacto() != null) {
                    existingContacto.setFechaContacto(contacto.getFechaContacto());
                }

                return existingContacto;
            })
            .map(contactoRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Contacto> findAll() {
        log.debug("Request to get all Contactos");
        return contactoRepository.findAllWithEagerRelationships();
    }

    public Page<Contacto> findAllWithEagerRelationships(Pageable pageable) {
        return contactoRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Contacto> findOne(Long id) {
        log.debug("Request to get Contacto : {}", id);
        return contactoRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Contacto : {}", id);
        contactoRepository.deleteById(id);
    }
}
