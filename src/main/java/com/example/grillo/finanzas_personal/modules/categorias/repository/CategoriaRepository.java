package com.example.grillo.finanzas_personal.modules.categorias.repository;

import com.example.grillo.finanzas_personal.modules.categorias.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
