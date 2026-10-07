package com.example.grillo.finanzas_personal.modules.movimientos.dto;

import com.example.grillo.finanzas_personal.modules.categorias.entity.Categoria;
import com.example.grillo.finanzas_personal.modules.shared.enums.TipoMovimiento;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record MovimientoRequestDTO(

        @NotNull(message = "El tipo es obligatorio")
        TipoMovimiento tipoMovimiento,

        @NotNull(message = "El monto es obligatorio")
        @DecimalMin(value = "0.01", message = "el monto debe ser mayor a cero")
        BigDecimal monto,

        @NotNull(message = "La fecha es obligatoria")
        LocalDate fecha,

        @NotNull(message = "La categoría es obligatoria")
        Long categoriaId,

        @Size(max = 200, message = "La descripción no puede superar 200 caracteres")
        String descripcion,

        Boolean planificado

) {
}
