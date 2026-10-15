package com.example.grillo.finanzas_personal.modules.reportes.controller;

import com.example.grillo.finanzas_personal.modules.reportes.dto.ReporteCategoriaResponse;
import com.example.grillo.finanzas_personal.modules.reportes.service.ReporteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.YearMonth;

@RestController
@RequestMapping("/api/reportes")
@RequiredArgsConstructor
public class ReporteController {

    private final ReporteService reporteService;

    @GetMapping("/gastos-por-categoria")
    public ReporteCategoriaResponse gastorPorCategoria(
            @RequestParam(required = false) LocalDate inicio,
            @RequestParam(required = false) LocalDate fin){

        LocalDate desde = (inicio != null) ? inicio : YearMonth.now().atDay(1);
        LocalDate hasta = (fin != null) ? fin : YearMonth.now().atEndOfMonth();

        return reporteService.gastosPorCategoria(desde, hasta);
    }
}
