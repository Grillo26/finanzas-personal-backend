package com.example.grillo.finanzas_personal.modules.categorias.mapper;

import com.example.grillo.finanzas_personal.modules.categorias.dto.CategoriaResponseDTO;
import com.example.grillo.finanzas_personal.modules.categorias.entity.Categoria;
import org.springframework.stereotype.Component;

@Component
public class CategoriaMapper {
    public CategoriaResponseDTO toResponse(Categoria entity){
        return new CategoriaResponseDTO(
                entity.getId(),
                entity.getNombre(),
                entity.getTipo(),
                entity.getColor(),
                entity.getColor(),
                entity.isActiva(),
                entity.isProtegida()
        );
    }
}
