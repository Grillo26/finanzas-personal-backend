package com.example.grillo.finanzas_personal.modules.categorias.dto;

import com.example.grillo.finanzas_personal.modules.shared.enums.TipoCategoria;

public record CategoriaResponseDTO(
       Long id,
       String nombre,
       TipoCategoria tipo,
       String color,
       String icono,
       boolean activa,
       boolean protegida
) {
}
