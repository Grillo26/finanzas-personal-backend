package com.example.grillo.finanzas_personal.modules.movimientos.dto;

import java.math.BigDecimal;
import java.time.YearMonth;

public record SaldoResponse(
        YearMonth periodo,
        BigDecimal totalIngresos,
        BigDecimal totalEgresos,
        BigDecimal totalDiezmo,
        BigDecimal saldoDisponible
) {
}
