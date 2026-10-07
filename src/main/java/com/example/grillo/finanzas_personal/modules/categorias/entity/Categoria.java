package com.example.grillo.finanzas_personal.modules.categorias.entity;

import com.example.grillo.finanzas_personal.modules.shared.enums.TipoCategoria;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categorias")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 40)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "tipo", nullable = false)
    private TipoCategoria tipo;

    @Column(name="color", length = 20)
    private String color;

    @Column(name = "icono", length = 50)
    private String icono;

    @Builder.Default
    @Column(name = "activa", nullable = false)
    private boolean activa = true;

    @Builder.Default
    @Column(name = "protegida", nullable = false)
    private boolean protegida = false;

    @CreationTimestamp
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @UpdateTimestamp
    @Column(name = "fecha_modificacion", nullable = false)
    private LocalDateTime fechaModificacion;



}
