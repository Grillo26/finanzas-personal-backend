package com.example.grillo.finanzas_personal.modules.movimientos.mapper;

import com.example.grillo.finanzas_personal.modules.categorias.entity.Categoria;
import com.example.grillo.finanzas_personal.modules.categorias.mapper.CategoriaMapper;
import com.example.grillo.finanzas_personal.modules.movimientos.dto.MovimientoRequestDTO;
import com.example.grillo.finanzas_personal.modules.movimientos.dto.MovimientoResponseDTO;
import com.example.grillo.finanzas_personal.modules.movimientos.entity.Movimiento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MovimientoMapper {

    private final CategoriaMapper categoriaMapper;

    public MovimientoResponseDTO toResponse(Movimiento entity){
        return new MovimientoResponseDTO(
                entity.getId(),
                entity.getTipo(),
                entity.getMonto(),
                entity.getFecha(),
                entity.getDescripcion(),
                categoriaMapper.toResponse(entity.getCategoria()),
                entity.getFechaCreacion(),
                entity.isPlanificado()
        );
    }

    public Movimiento toEntity(MovimientoRequestDTO requestDTO, Categoria categoria){
        return Movimiento.builder()
                .tipo(requestDTO.tipoMovimiento())
                .monto(requestDTO.monto())
                .fecha(requestDTO.fecha())
                .categoria(categoria)
                .descripcion(requestDTO.descripcion())
                .planificado(Boolean.TRUE.equals(requestDTO.planificado()))
                .build();
    }
}
