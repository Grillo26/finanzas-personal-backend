package com.example.grillo.finanzas_personal.modules.reportes.service;

import com.example.grillo.finanzas_personal.modules.movimientos.repository.MovimientoRepository;
import com.example.grillo.finanzas_personal.modules.reportes.dto.GastoPorCategoriaResponse;
import com.example.grillo.finanzas_personal.modules.reportes.dto.ReporteCategoriaResponse;
import com.example.grillo.finanzas_personal.modules.shared.enums.TipoMovimiento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReporteService {

    private final MovimientoRepository movimientoRepository;

    @Transactional(readOnly = true)
    public ReporteCategoriaResponse gastosPorCategoria(LocalDate inicio, LocalDate fin){
        if(inicio.isAfter(fin)){
            throw new IllegalArgumentException("La fecha inicial no puede ser posterior a la final");
        }

        List<GastoPorCategoriaResponse> categorias = movimientoRepository.totalPorCategoria(TipoMovimiento.EGRESO, inicio, fin);

        BigDecimal totalGeneral = categorias.stream()
                .map(GastoPorCategoriaResponse::total)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new ReporteCategoriaResponse(inicio, fin, totalGeneral, categorias);
    }
}
