package com.example.grillo.finanzas_personal.modules.movimientos.specification;

import com.example.grillo.finanzas_personal.modules.movimientos.entity.Movimiento;
import com.example.grillo.finanzas_personal.modules.shared.enums.TipoMovimiento;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.List;

public class MovimientoSpec {

    public static Specification<Movimiento> estaActivo(){
        return (root, query, cb) -> cb.isTrue(root.get("activo"));
    }

    public static Specification<Movimiento> tieneTipo(TipoMovimiento tipo){
        return(root, query, cb)-> cb.equal(root.get("tipo"), tipo);
    }

    public static Specification<Movimiento> tieneCategoria(Long categoriaId){
        return(root, query, cb)-> cb.equal(root.get("categoria").get("id"), categoriaId);
    }

    public static Specification<Movimiento> entreFechas(LocalDate inicio, LocalDate fin){
        return((root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("fecha"), inicio, fin));
    }

    public static Specification<Movimiento> tieneCategorias(List<Long> categoriaIds){
        return( (root, query, criteriaBuilder) -> root.get("categoria").get("id").in(categoriaIds));
    }
}
