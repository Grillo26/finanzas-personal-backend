package com.example.grillo.finanzas_personal.modules.movimientos.controller;

import com.example.grillo.finanzas_personal.modules.movimientos.dto.SaldoResponse;
import com.example.grillo.finanzas_personal.modules.movimientos.service.SaldoService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.YearMonth;

@RestController
@RequestMapping("/api/saldo")
@RequiredArgsConstructor
public class SaldoController {

    private final SaldoService saldoService;

    @GetMapping
    public SaldoResponse obtenerSaldo(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM")YearMonth periodo){
        YearMonth mesConsultado = (periodo != null)  ? periodo : YearMonth.now();
        return saldoService.calcularSaldo(mesConsultado);
    }
}


