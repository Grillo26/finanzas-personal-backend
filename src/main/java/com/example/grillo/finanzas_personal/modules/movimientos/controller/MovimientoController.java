package com.example.grillo.finanzas_personal.modules.movimientos.controller;

import com.example.grillo.finanzas_personal.modules.movimientos.dto.MovimientoRequestDTO;
import com.example.grillo.finanzas_personal.modules.movimientos.dto.MovimientoResponseDTO;
import com.example.grillo.finanzas_personal.modules.movimientos.service.MovimientoService;
import com.example.grillo.finanzas_personal.modules.shared.enums.TipoMovimiento;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/movimientos")
@RequiredArgsConstructor
public class MovimientoController {

    private final MovimientoService movimientoService;

    @PostMapping
    public ResponseEntity<MovimientoResponseDTO> registrarMovimiento(@Valid @RequestBody MovimientoRequestDTO requestDTO){
        MovimientoResponseDTO movimientoCreado = movimientoService.registrarMovimiento(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(movimientoCreado);
    }
    @GetMapping
    public ResponseEntity<Page<MovimientoResponseDTO>> listarMovimientos(
            @RequestParam(required = false) LocalDate fechaInicio,
            @RequestParam(required = false) LocalDate fechaFin,
            @RequestParam(required = false) List<Long> categoriaIds,
            @RequestParam(required = false)TipoMovimiento tipo,
            @PageableDefault(size = 10, sort = "fecha", direction = Sort.Direction.DESC) Pageable pageable
            ){
        Page<MovimientoResponseDTO> movimientos = movimientoService.listarMovimientos(fechaInicio, fechaFin, categoriaIds, tipo, pageable);
        return ResponseEntity.ok(movimientos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovimientoResponseDTO> modificarMovimiento(
            @PathVariable Long id,
            @Valid @RequestBody MovimientoRequestDTO requestDTO){
        MovimientoResponseDTO movimientoActualizado = movimientoService.modificarMovimiento(id, requestDTO);
        return ResponseEntity.ok(movimientoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMovimiento(@PathVariable Long id){
        movimientoService.eliminarMovimiento(id);
        return ResponseEntity.noContent().build();
    }
}
