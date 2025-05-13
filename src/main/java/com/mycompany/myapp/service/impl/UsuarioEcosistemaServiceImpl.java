package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.UsuarioEcosistema;
import com.mycompany.myapp.repository.UsuarioEcosistemaRepository;
import com.mycompany.myapp.service.UsuarioEcosistemaService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link UsuarioEcosistema}.
 */
@Service
@Transactional
public class UsuarioEcosistemaServiceImpl implements UsuarioEcosistemaService {

    private final Logger log = LoggerFactory.getLogger(UsuarioEcosistemaServiceImpl.class);

    private final UsuarioEcosistemaRepository usuarioEcosistemaRepository;

    public UsuarioEcosistemaServiceImpl(UsuarioEcosistemaRepository usuarioEcosistemaRepository) {
        this.usuarioEcosistemaRepository = usuarioEcosistemaRepository;
    }

    @Override
    public UsuarioEcosistema save(UsuarioEcosistema usuarioEcosistema) {
        log.debug("Request to save UsuarioEcosistema : {}", usuarioEcosistema);
        return usuarioEcosistemaRepository.save(usuarioEcosistema);
    }

    @Override
    public UsuarioEcosistema update(UsuarioEcosistema usuarioEcosistema) {
        log.debug("Request to save UsuarioEcosistema : {}", usuarioEcosistema);
        return usuarioEcosistemaRepository.save(usuarioEcosistema);
    }

    @Override
    public Optional<UsuarioEcosistema> partialUpdate(UsuarioEcosistema usuarioEcosistema) {
        log.debug("Request to partially update UsuarioEcosistema : {}", usuarioEcosistema);

        return usuarioEcosistemaRepository
            .findById(usuarioEcosistema.getId())
            .map(existingUsuarioEcosistema -> {
                if (usuarioEcosistema.getFechaIngreso() != null) {
                    existingUsuarioEcosistema.setFechaIngreso(usuarioEcosistema.getFechaIngreso());
                }
                if (usuarioEcosistema.getBloqueado() != null) {
                    existingUsuarioEcosistema.setBloqueado(usuarioEcosistema.getBloqueado());
                }

                return existingUsuarioEcosistema;
            })
            .map(usuarioEcosistemaRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioEcosistema> findAll() {
        log.debug("Request to get all UsuarioEcosistemas");
        return usuarioEcosistemaRepository.findAllWithEagerRelationships();
    }

    public Page<UsuarioEcosistema> findAllWithEagerRelationships(Pageable pageable) {
        return usuarioEcosistemaRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UsuarioEcosistema> findOne(Long id) {
        log.debug("Request to get UsuarioEcosistema : {}", id);
        return usuarioEcosistemaRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UsuarioEcosistema : {}", id);
        usuarioEcosistemaRepository.deleteById(id);
    }

    @Override
    public Optional<UsuarioEcosistema> buscarOneByUserId(Long id) {
        log.debug("Request to get UsuarioEcosistema by User Id : {}", id);
        return usuarioEcosistemaRepository.findByUserIdWithEagerRelationships(id);
    }
}
