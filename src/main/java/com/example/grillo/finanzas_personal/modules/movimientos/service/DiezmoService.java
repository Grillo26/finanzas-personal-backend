package com.example.grillo.finanzas_personal.modules.movimientos.service;

import com.example.grillo.finanzas_personal.modules.movimientos.entity.Movimiento;
import com.example.grillo.finanzas_personal.modules.movimientos.repository.MovimientoRepository;
import com.example.grillo.finanzas_personal.modules.shared.enums.TipoMovimiento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;

@Service
@RequiredArgsConstructor
public class DiezmoService {

    private static final BigDecimal PORCENTAJE = new BigDecimal("0.10");
    private final MovimientoRepository movimientoRepository;

    public BigDecimal calcular(Movimiento movimiento){
        if(movimiento.getTipo() != TipoMovimiento.INGRESO || movimiento.isExcluidoDiezmo()){
            return BigDecimal.ZERO;
        }
        return movimiento.getMonto()
                .multiply(PORCENTAJE)
                .setScale(2, RoundingMode.HALF_UP);
    }

    @Transactional(readOnly = true)
    public BigDecimal calcularMensual(YearMonth periodo){
        return totalEntre(periodo.atDay(1), periodo.atEndOfMonth());
    }

    @Transactional(readOnly = true)
    public BigDecimal calcularAnual(int anio){
        LocalDate inicio = LocalDate.of(anio, 1,1);
        LocalDate fin = (anio == Year.now().getValue())
                ? YearMonth.now().atEndOfMonth() : LocalDate.of(anio, 12,31);
        return totalEntre(inicio, fin);
    }

    // Metodo Auxiliar
    private BigDecimal totalEntre(LocalDate inicio, LocalDate fin){
        return movimientoRepository.findParaDiezmo(TipoMovimiento.INGRESO, inicio, fin)
                .stream()
                .map(this::calcular)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


}
