package com.example.grillo.finanzas_personal.modules.reportes.dto;

import java.math.BigDecimal;

public record GastoPorCategoriaResponse(
        Long categoriaId,
        String categoriaNombre,
        BigDecimal total
) {
}
