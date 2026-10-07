package com.example.grillo.finanzas_personal.modules.movimientos.service;

import com.example.grillo.finanzas_personal.modules.movimientos.dto.SaldoResponse;
import com.example.grillo.finanzas_personal.modules.movimientos.repository.MovimientoRepository;
import com.example.grillo.finanzas_personal.modules.shared.enums.TipoMovimiento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;

@Service
@RequiredArgsConstructor
public class SaldoService {

    private static final BigDecimal PORCENTAJE_DIEZMO = new BigDecimal("0.10");
    private final MovimientoRepository movimientoRepository;

    @Transactional(readOnly = true)
    public SaldoResponse calcularSaldo(YearMonth periodo) {
        LocalDate inicio = periodo.atDay(1);
        LocalDate fin = periodo.atEndOfMonth();

        BigDecimal totalIngresos = movimientoRepository.sumarPorTipoYPeriodo(TipoMovimiento.INGRESO, inicio, fin);

        BigDecimal totalEgresos = movimientoRepository.sumarPorTipoYPeriodo(TipoMovimiento.EGRESO, inicio, fin);

        BigDecimal baseDiezmo = movimientoRepository.sumarIngresosParaDiezmo(inicio, fin);

        BigDecimal totalDiezmo = baseDiezmo.multiply(PORCENTAJE_DIEZMO);

        BigDecimal saldoDisponible = totalIngresos.subtract(totalEgresos).subtract(totalDiezmo);

        return new SaldoResponse(periodo, totalIngresos, totalEgresos, totalDiezmo, saldoDisponible);

    }
}
