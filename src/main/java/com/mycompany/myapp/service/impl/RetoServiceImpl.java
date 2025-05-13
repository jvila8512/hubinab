package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Ecosistema;
import com.mycompany.myapp.domain.Reto;
import com.mycompany.myapp.repository.RetoRepository;
import com.mycompany.myapp.service.RetoService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Reto}.
 */
@Service
@Transactional
public class RetoServiceImpl implements RetoService {

    private final Logger log = LoggerFactory.getLogger(RetoServiceImpl.class);

    private final RetoRepository retoRepository;

    public RetoServiceImpl(RetoRepository retoRepository) {
        this.retoRepository = retoRepository;
    }

    @Override
    public Reto save(Reto reto) {
        log.debug("Request to save Reto : {}", reto);
        return retoRepository.save(reto);
    }

    @Override
    public Reto update(Reto reto) {
        log.debug("Request to save Reto : {}", reto);
        return retoRepository.save(reto);
    }

    @Override
    public Optional<Reto> partialUpdate(Reto reto) {
        log.debug("Request to partially update Reto : {}", reto);

        return retoRepository
            .findById(reto.getId())
            .map(existingReto -> {
                if (reto.getReto() != null) {
                    existingReto.setReto(reto.getReto());
                }
                if (reto.getDescripcion() != null) {
                    existingReto.setDescripcion(reto.getDescripcion());
                }
                if (reto.getMotivacion() != null) {
                    existingReto.setMotivacion(reto.getMotivacion());
                }
                if (reto.getFechaInicio() != null) {
                    existingReto.setFechaInicio(reto.getFechaInicio());
                }
                if (reto.getFechaFin() != null) {
                    existingReto.setFechaFin(reto.getFechaFin());
                }
                if (reto.getActivo() != null) {
                    existingReto.setActivo(reto.getActivo());
                }
                if (reto.getValidado() != null) {
                    existingReto.setValidado(reto.getValidado());
                }
                if (reto.getUrlFoto() != null) {
                    existingReto.setUrlFoto(reto.getUrlFoto());
                }
                if (reto.getUrlFotoContentType() != null) {
                    existingReto.setUrlFotoContentType(reto.getUrlFotoContentType());
                }
                if (reto.getVisto() != null) {
                    existingReto.setVisto(reto.getVisto());
                }

                return existingReto;
            })
            .map(retoRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Reto> findAll(Pageable pageable) {
        log.debug("Request to get all Retos");
        return retoRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Reto> findAllWithEagerRelationshipsByIdUsuario(Long id) {
        return retoRepository.findAllWithRelationshipsByIdUsuario(id);
    }

    public Page<Reto> findAllWithEagerRelationships(Pageable pageable) {
        return retoRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Reto> findOne(Long id) {
        log.debug("Request to get Reto : {}", id);
        return retoRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Reto : {}", id);
        retoRepository.deleteById(id);
    }

    @Override
    public Long contarEcosistemas(Long id) {
        return retoRepository.contar(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Reto> findAllWithEagerRelationshipsByIdEcosistema(Long id) {
        return retoRepository.findAllWithEagerRelationshipsByIdEcosistemaIdeas(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Reto> findAllWithEagerRelationshipsByIdEcosistemaTodas(Long id) {
        return retoRepository.findAllByEcosistemaIdOrderByFechaInicioDescActivoDesc(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Reto> findAllWithEagerRelationshipsByIdEcosistema1(Pageable pageable, Long id) {
        return retoRepository.findAllWithEagerRelationshipsByIdEcosistemaIdeas1(pageable, id);
    }

    @Override
    public List<Reto> findAllWithEagerRelationshipsByIdUsuariobyIdUser(Long id, Long iduser) {
        return retoRepository.findAllWithEagerRelationshipsByIdEcosistemaByIdUser(id, iduser);
    }

    @Override
    public List<Reto> findAllTodosPublicosByEcosistemaId(Long idecosistema) {
        return retoRepository.findAllByEcosistemaIdAndPublicoTrueOrderByFechaInicioDesc(idecosistema);
    }

    @Override
    public List<Reto> busquedaGeneralPorEcosistemasId(List<Long> ecosistemas) {
        return retoRepository.findAllByActivoTrueAndEcosistemaIdInOrderByEcosistemaIdDesc(ecosistemas);
    }
}
