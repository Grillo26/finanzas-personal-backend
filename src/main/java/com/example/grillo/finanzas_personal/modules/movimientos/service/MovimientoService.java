package com.example.grillo.finanzas_personal.modules.movimientos.service;

import com.example.grillo.finanzas_personal.common.exception.ResourceNotFoundException;
import com.example.grillo.finanzas_personal.modules.categorias.entity.Categoria;
import com.example.grillo.finanzas_personal.modules.categorias.repository.CategoriaRepository;
import com.example.grillo.finanzas_personal.modules.movimientos.dto.MovimientoRequestDTO;
import com.example.grillo.finanzas_personal.modules.movimientos.dto.MovimientoResponseDTO;
import com.example.grillo.finanzas_personal.modules.movimientos.entity.Movimiento;
import com.example.grillo.finanzas_personal.modules.movimientos.mapper.MovimientoMapper;
import com.example.grillo.finanzas_personal.modules.movimientos.repository.MovimientoRepository;
import com.example.grillo.finanzas_personal.modules.movimientos.specification.MovimientoSpec;
import com.example.grillo.finanzas_personal.modules.shared.enums.TipoMovimiento;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovimientoService {

    private final MovimientoRepository movimientoRepository;
    private final MovimientoMapper movimientoMapper;
    private final CategoriaRepository categoriaRepository;

    @Transactional(readOnly = true)
    public Page<MovimientoResponseDTO> listarMovimientos(LocalDate fechaIncio, LocalDate fechaFin, List<Long> categoriaIds, TipoMovimiento tipo, Pageable pageable){
        Specification<Movimiento> spec = Specification.where(MovimientoSpec.estaActivo());
        if(tipo != null){
            spec = spec.and(MovimientoSpec.tieneTipo(tipo));
        }
        if(categoriaIds != null && !categoriaIds.isEmpty()){
            spec = spec.and(MovimientoSpec.tieneCategorias(categoriaIds));
        }
        if(fechaIncio != null && fechaFin != null){
            spec = spec.and(MovimientoSpec.entreFechas(fechaIncio, fechaFin));
        }

        Page<Movimiento> movimientos = movimientoRepository.findAll(spec, pageable);
        return movimientos.map(movimientoMapper::toResponse);
    }

    @Transactional
    public MovimientoResponseDTO registrarMovimiento(MovimientoRequestDTO requestDTO){

        boolean esPlanificado = Boolean.TRUE.equals(requestDTO.planificado());

        // Verificar si es EGRESO y si es planificada, fecha futura RN-008
        if(requestDTO.tipoMovimiento() == TipoMovimiento.EGRESO && requestDTO.fecha().isAfter(LocalDate.now()) && !esPlanificado){
            throw new IllegalArgumentException("No se permiten egresos con fecha futura, salvo planificados");
        }

        // RN-009 Planificado solo válido para INGRESO
        if(requestDTO.tipoMovimiento() == TipoMovimiento.EGRESO && esPlanificado){
            throw new IllegalArgumentException("Solo ingresos pueden marcarse como planificado");
        }

        Categoria categoria = categoriaRepository.findById(requestDTO.categoriaId())
                .orElseThrow( ()-> new ResourceNotFoundException("Categoria", requestDTO.categoriaId()));

        Movimiento movimiento = movimientoMapper.toEntity(requestDTO, categoria);
        Movimiento movimientoGuardado = movimientoRepository.save(movimiento);

        return movimientoMapper.toResponse(movimientoGuardado);
    }

    @Transactional
    public MovimientoResponseDTO modificarMovimiento(Long id, MovimientoRequestDTO requestDTO){

        Movimiento movimiento = movimientoRepository.findById(id)
                .orElseThrow( () ->new ResourceNotFoundException("Movimiento: ", id));

        validarMesActual(movimiento.getFecha(), "editar");

        // Buscar por si el usuario cambio la categoria
        Categoria categoria = categoriaRepository.findById(requestDTO.categoriaId())
                .orElseThrow(()-> new ResourceNotFoundException("Categoria", requestDTO.categoriaId()));

        // Actualizar los campos
        movimiento.setTipo(requestDTO.tipoMovimiento());
        movimiento.setMonto(requestDTO.monto());
        movimiento.setFecha(requestDTO.fecha());
        movimiento.setCategoria(categoria);
        movimiento.setDescripcion(requestDTO.descripcion());
        movimiento.setPlanificado(Boolean.TRUE.equals(requestDTO.planificado()));

        Movimiento movimientoActualizado = movimientoRepository.save(movimiento);

        return movimientoMapper.toResponse(movimientoActualizado);
    }

    @Transactional
    public void eliminarMovimiento(long id){

        Movimiento movimiento = movimientoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movimiento", id));

        validarMesActual(movimiento.getFecha(), "eliminar");

        movimiento.setActivo(false);
        movimientoRepository.save(movimiento);
    }

    private void validarMesActual(LocalDate fecha, String accion){
        YearMonth mesDelMovimiento = YearMonth.from(fecha);
        YearMonth mesActual = YearMonth.now();

        if(!mesDelMovimiento.equals(mesActual)){
            throw new IllegalArgumentException("No se puede "+ accion +" un movimiento de un mes cerrado");
        }

    }
}
