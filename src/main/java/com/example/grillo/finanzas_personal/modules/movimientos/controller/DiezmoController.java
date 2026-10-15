package com.example.grillo.finanzas_personal.modules.movimientos.controller;

import com.example.grillo.finanzas_personal.modules.movimientos.dto.DiezmoResponse;
import com.example.grillo.finanzas_personal.modules.movimientos.service.DiezmoService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Year;
import java.time.YearMonth;

@RestController
@RequestMapping("/api/diezmo")
@RequiredArgsConstructor
public class DiezmoController {

    private final DiezmoService diezmoService;

    @GetMapping("/mensual")
    public DiezmoResponse mensual( @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM")YearMonth periodo){
        YearMonth mes = (periodo != null) ? periodo : YearMonth.now();
        return new DiezmoResponse(mes.toString(), diezmoService.calcularMensual(mes));
    }

    @GetMapping("/anual")
    public DiezmoResponse anual(@RequestParam(required = false) Integer anio){
        int year = (anio != null) ? anio : Year.now().getValue();
        return new DiezmoResponse(String.valueOf(year), diezmoService.calcularAnual(year));
    }
}
