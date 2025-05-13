package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Anirista;
import com.mycompany.myapp.repository.AniristaRepository;
import com.mycompany.myapp.service.AniristaService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Anirista}.
 */
@Service
@Transactional
public class AniristaServiceImpl implements AniristaService {

    private final Logger log = LoggerFactory.getLogger(AniristaServiceImpl.class);

    private final AniristaRepository aniristaRepository;

    public AniristaServiceImpl(AniristaRepository aniristaRepository) {
        this.aniristaRepository = aniristaRepository;
    }

    @Override
    public Anirista save(Anirista anirista) {
        log.debug("Request to save Anirista : {}", anirista);
        return aniristaRepository.save(anirista);
    }

    @Override
    public Anirista update(Anirista anirista) {
        log.debug("Request to save Anirista : {}", anirista);
        return aniristaRepository.save(anirista);
    }

    @Override
    public Optional<Anirista> partialUpdate(Anirista anirista) {
        log.debug("Request to partially update Anirista : {}", anirista);

        return aniristaRepository
            .findById(anirista.getId())
            .map(existingAnirista -> {
                if (anirista.getNombre() != null) {
                    existingAnirista.setNombre(anirista.getNombre());
                }
                if (anirista.getFechaEntrada() != null) {
                    existingAnirista.setFechaEntrada(anirista.getFechaEntrada());
                }
                if (anirista.getDescripcion() != null) {
                    existingAnirista.setDescripcion(anirista.getDescripcion());
                }

                return existingAnirista;
            })
            .map(aniristaRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Anirista> findAll() {
        log.debug("Request to get all Aniristas");
        return aniristaRepository.findAllWithEagerRelationships();
    }

    public Page<Anirista> findAllWithEagerRelationships(Pageable pageable) {
        return aniristaRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Anirista> findOne(Long id) {
        log.debug("Request to get Anirista : {}", id);
        return aniristaRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Anirista : {}", id);
        aniristaRepository.deleteById(id);
    }
}
