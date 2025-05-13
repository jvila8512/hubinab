package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.ChangeMacker;
import com.mycompany.myapp.repository.ChangeMackerRepository;
import com.mycompany.myapp.service.ChangeMackerService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ChangeMacker}.
 */
@Service
@Transactional
public class ChangeMackerServiceImpl implements ChangeMackerService {

    private final Logger log = LoggerFactory.getLogger(ChangeMackerServiceImpl.class);

    private final ChangeMackerRepository changeMackerRepository;

    public ChangeMackerServiceImpl(ChangeMackerRepository changeMackerRepository) {
        this.changeMackerRepository = changeMackerRepository;
    }

    @Override
    public ChangeMacker save(ChangeMacker changeMacker) {
        log.debug("Request to save ChangeMacker : {}", changeMacker);
        return changeMackerRepository.save(changeMacker);
    }

    @Override
    public ChangeMacker update(ChangeMacker changeMacker) {
        log.debug("Request to save ChangeMacker : {}", changeMacker);
        return changeMackerRepository.save(changeMacker);
    }

    @Override
    public Optional<ChangeMacker> partialUpdate(ChangeMacker changeMacker) {
        log.debug("Request to partially update ChangeMacker : {}", changeMacker);

        return changeMackerRepository
            .findById(changeMacker.getId())
            .map(existingChangeMacker -> {
                if (changeMacker.getFoto() != null) {
                    existingChangeMacker.setFoto(changeMacker.getFoto());
                }
                if (changeMacker.getFotoContentType() != null) {
                    existingChangeMacker.setFotoContentType(changeMacker.getFotoContentType());
                }
                if (changeMacker.getNombre() != null) {
                    existingChangeMacker.setNombre(changeMacker.getNombre());
                }
                if (changeMacker.getTema() != null) {
                    existingChangeMacker.setTema(changeMacker.getTema());
                }
                if (changeMacker.getDescripcion() != null) {
                    existingChangeMacker.setDescripcion(changeMacker.getDescripcion());
                }

                return existingChangeMacker;
            })
            .map(changeMackerRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChangeMacker> findAll() {
        log.debug("Request to get all ChangeMackers");
        return changeMackerRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ChangeMacker> findOne(Long id) {
        log.debug("Request to get ChangeMacker : {}", id);
        return changeMackerRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ChangeMacker : {}", id);
        changeMackerRepository.deleteById(id);
    }
}
