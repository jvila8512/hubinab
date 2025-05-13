package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.FooterSection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FooterSectionRepository extends JpaRepository<FooterSection, Long> {
    List<FooterSection> findByActiveTrueOrderByOrderAsc();
    // Métodos CRUD estándar (ya incluidos en JpaRepository)
    // findAll() - Obtener todos
    // findById(Long id) - Buscar por ID
    // save(FooterSection) - Crear/Actualizar
    // deleteById(Long id) - Eliminar
    // Método adicional para validación
    boolean existsByTitleAndIdNot(String title, Long id);
}
