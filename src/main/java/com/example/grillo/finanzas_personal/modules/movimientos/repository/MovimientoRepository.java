package com.example.grillo.finanzas_personal.modules.movimientos.repository;

import com.example.grillo.finanzas_personal.modules.movimientos.entity.Movimiento;
import com.example.grillo.finanzas_personal.modules.shared.enums.TipoMovimiento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long>, JpaSpecificationExecutor<Movimiento> {
    Page<Movimiento> findByActivoTrue(Pageable pageable);

    @Query("""
        SELECT COALESCE(SUM(m.monto), 0) FROM Movimiento m
        WHERE m.tipo = :tipo AND m.activo = true
        AND m.fecha BETWEEN :inicio AND :fin
        """)
    BigDecimal sumarPorTipoYPeriodo(@Param("tipo") TipoMovimiento tipo,
                                    @Param("inicio") LocalDate inicio,
                                    @Param("fin") LocalDate fin);

    @Query("""
        SELECT COALESCE(SUM(m.monto), 0) FROM Movimiento m
        WHERE m.tipo = 'INGRESO' AND m.activo = true AND m.excluidoDiezmo = false
        AND m.fecha BETWEEN :inicio AND :fin
        """)
    BigDecimal sumarIngresosParaDiezmo(@Param("inicio") LocalDate inicio,
                                       @Param("fin") LocalDate fin);
}
