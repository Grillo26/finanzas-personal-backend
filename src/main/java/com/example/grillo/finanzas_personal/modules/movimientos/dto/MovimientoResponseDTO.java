package com.example.grillo.finanzas_personal.modules.movimientos.dto;

import com.example.grillo.finanzas_personal.modules.categorias.dto.CategoriaResponseDTO;
import com.example.grillo.finanzas_personal.modules.shared.enums.TipoMovimiento;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record MovimientoResponseDTO(
        Long id,
        TipoMovimiento tipo,
        BigDecimal monto,
        LocalDate fecha,
        String descripcion,
        CategoriaResponseDTO categoria,
        LocalDateTime fechaCreacion,
        boolean planificado
) {
}
