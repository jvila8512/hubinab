package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Noticias;
import com.mycompany.myapp.domain.Reto;
import com.mycompany.myapp.repository.NoticiasRepository;
import com.mycompany.myapp.service.NoticiasService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Noticias}.
 */
@Service
@Transactional
public class NoticiasServiceImpl implements NoticiasService {

    private final Logger log = LoggerFactory.getLogger(NoticiasServiceImpl.class);

    private final NoticiasRepository noticiasRepository;

    public NoticiasServiceImpl(NoticiasRepository noticiasRepository) {
        this.noticiasRepository = noticiasRepository;
    }

    @Override
    public Noticias save(Noticias noticias) {
        log.debug("Request to save Noticias : {}", noticias);
        return noticiasRepository.save(noticias);
    }

    @Override
    public Noticias update(Noticias noticias) {
        log.debug("Request to save Noticias : {}", noticias);
        return noticiasRepository.save(noticias);
    }

    @Override
    public Optional<Noticias> partialUpdate(Noticias noticias) {
        log.debug("Request to partially update Noticias : {}", noticias);

        return noticiasRepository
            .findById(noticias.getId())
            .map(existingNoticias -> {
                if (noticias.getTitulo() != null) {
                    existingNoticias.setTitulo(noticias.getTitulo());
                }
                if (noticias.getDescripcion() != null) {
                    existingNoticias.setDescripcion(noticias.getDescripcion());
                }
                if (noticias.getUrlFoto() != null) {
                    existingNoticias.setUrlFoto(noticias.getUrlFoto());
                }
                if (noticias.getUrlFotoContentType() != null) {
                    existingNoticias.setUrlFotoContentType(noticias.getUrlFotoContentType());
                }
                if (noticias.getPublica() != null) {
                    existingNoticias.setPublica(noticias.getPublica());
                }
                if (noticias.getPublicar() != null) {
                    existingNoticias.setPublicar(noticias.getPublicar());
                }
                if (noticias.getFechaCreada() != null) {
                    existingNoticias.setFechaCreada(noticias.getFechaCreada());
                }

                return existingNoticias;
            })
            .map(noticiasRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Noticias> findAll(Pageable pageable) {
        log.debug("Request to get all Noticias");
        return noticiasRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Noticias> findAllByPublicar() {
        return noticiasRepository.findOneWithToOneRelationshipsByPublicar();
    }

    public Page<Noticias> findAllWithEagerRelationships(Pageable pageable) {
        return noticiasRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Noticias> findOne(Long id) {
        log.debug("Request to get Noticias : {}", id);
        return noticiasRepository.findOneWithEagerRelationships(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Noticias> findAllByPublicaByEcosistemaId(Long id) {
        return noticiasRepository.findAllWithToOneRelationshipsByPublicaByEcositema(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Noticias : {}", id);
        noticiasRepository.deleteById(id);
    }

    @Override
    public Long contarEcosistemas(Long id) {
        return noticiasRepository.contar(id);
    }

    @Override
    public List<Noticias> findAllByPublicaByEcosistemaIdByUserId(Long id, Long iduser) {
        return noticiasRepository.findTodosByIdEcosistemabyIdUser(id, iduser);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Noticias> findAllByPublicaByEcosistemaIdByUserIdPaginado(Pageable pageable, Long id) {
        return noticiasRepository.findAllByNoticiaByIdEcosistemabyIdUserPaginado(pageable, id);
    }

    @Override
    public List<Noticias> findAllByPublicaByEcosistemaIdByUserIdSolo(Long id, Long iduser) {
        return noticiasRepository.findTodosByIdEcosistemabyIdUserSolo(id, iduser);
    }

    @Override
    public List<Noticias> busquedaGeneralPorEcosistemasId(List<Long> ecosistemas) {
        return noticiasRepository.findAllByPublicaTrueAndEcosistemaIdInOrderByEcosistemaIdDesc(ecosistemas);
    }
}
