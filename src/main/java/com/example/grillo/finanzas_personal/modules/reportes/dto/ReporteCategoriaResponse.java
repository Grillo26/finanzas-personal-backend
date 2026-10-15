package com.example.grillo.finanzas_personal.modules.reportes.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record ReporteCategoriaResponse(
        LocalDate desde,
        LocalDate hasta,
        BigDecimal totalGeneral,
        List<GastoPorCategoriaResponse> categorias
) {
}
