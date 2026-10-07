package com.example.grillo.finanzas_personal.modules.movimientos.entity;

import com.example.grillo.finanzas_personal.modules.categorias.entity.Categoria;
import com.example.grillo.finanzas_personal.modules.shared.enums.TipoMovimiento;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "movimientos")

public class Movimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "tipo", nullable = false)
    private TipoMovimiento tipo;

    @Column(name = "monto", nullable = false, precision = 12, scale = 2)
    private BigDecimal monto;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "descripcion", length = 200)
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @Builder.Default
    @Column(name = "excluido_diezmo", nullable = false)
    private boolean excluidoDiezmo = false;

    @Builder.Default
    @Column(name = "planificado", nullable = false)
    private boolean planificado = false;

    @Builder.Default
    @Column(name = "activo", nullable = false)
    private boolean activo = true;

    @CreationTimestamp
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @UpdateTimestamp
    @Column(name = "fecha_modificacion", nullable = false)
    private LocalDateTime fechaModificacion;

}
