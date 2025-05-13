package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.CategoriaUser;
import com.mycompany.myapp.repository.CategoriaUserRepository;
import com.mycompany.myapp.service.CategoriaUserService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CategoriaUser}.
 */
@Service
@Transactional
public class CategoriaUserServiceImpl implements CategoriaUserService {

    private final Logger log = LoggerFactory.getLogger(CategoriaUserServiceImpl.class);

    private final CategoriaUserRepository categoriaUserRepository;

    public CategoriaUserServiceImpl(CategoriaUserRepository categoriaUserRepository) {
        this.categoriaUserRepository = categoriaUserRepository;
    }

    @Override
    public CategoriaUser save(CategoriaUser categoriaUser) {
        log.debug("Request to save CategoriaUser : {}", categoriaUser);
        return categoriaUserRepository.save(categoriaUser);
    }

    @Override
    public CategoriaUser update(CategoriaUser categoriaUser) {
        log.debug("Request to save CategoriaUser : {}", categoriaUser);
        return categoriaUserRepository.save(categoriaUser);
    }

    @Override
    public Optional<CategoriaUser> partialUpdate(CategoriaUser categoriaUser) {
        log.debug("Request to partially update CategoriaUser : {}", categoriaUser);

        return categoriaUserRepository
            .findById(categoriaUser.getId())
            .map(existingCategoriaUser -> {
                if (categoriaUser.getCategoriaUser() != null) {
                    existingCategoriaUser.setCategoriaUser(categoriaUser.getCategoriaUser());
                }

                return existingCategoriaUser;
            })
            .map(categoriaUserRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaUser> findAll() {
        log.debug("Request to get all CategoriaUsers");
        return categoriaUserRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CategoriaUser> findOne(Long id) {
        log.debug("Request to get CategoriaUser : {}", id);
        return categoriaUserRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CategoriaUser : {}", id);
        categoriaUserRepository.deleteById(id);
    }
}
