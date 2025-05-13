package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Participantes;
import com.mycompany.myapp.repository.ParticipantesRepository;
import com.mycompany.myapp.service.ParticipantesService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Participantes}.
 */
@Service
@Transactional
public class ParticipantesServiceImpl implements ParticipantesService {

    private final Logger log = LoggerFactory.getLogger(ParticipantesServiceImpl.class);

    private final ParticipantesRepository participantesRepository;

    public ParticipantesServiceImpl(ParticipantesRepository participantesRepository) {
        this.participantesRepository = participantesRepository;
    }

    @Override
    public Participantes save(Participantes participantes) {
        log.debug("Request to save Participantes : {}", participantes);
        return participantesRepository.save(participantes);
    }

    @Override
    public Participantes update(Participantes participantes) {
        log.debug("Request to save Participantes : {}", participantes);
        return participantesRepository.save(participantes);
    }

    @Override
    public Optional<Participantes> partialUpdate(Participantes participantes) {
        log.debug("Request to partially update Participantes : {}", participantes);

        return participantesRepository
            .findById(participantes.getId())
            .map(existingParticipantes -> {
                if (participantes.getNombre() != null) {
                    existingParticipantes.setNombre(participantes.getNombre());
                }
                if (participantes.getTelefono() != null) {
                    existingParticipantes.setTelefono(participantes.getTelefono());
                }
                if (participantes.getCorreo() != null) {
                    existingParticipantes.setCorreo(participantes.getCorreo());
                }

                return existingParticipantes;
            })
            .map(participantesRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Participantes> findAll() {
        log.debug("Request to get all Participantes");
        return participantesRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Participantes> findOne(Long id) {
        log.debug("Request to get Participantes : {}", id);
        return participantesRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Participantes : {}", id);
        participantesRepository.deleteById(id);
    }
}
