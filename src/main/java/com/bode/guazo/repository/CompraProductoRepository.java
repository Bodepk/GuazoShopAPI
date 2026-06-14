package com.bode.guazo.repository;

import com.bode.guazo.entity.CompraProductos;
import com.bode.guazo.entity.CompraProductosPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CompraProductoRepository extends JpaRepository<CompraProductos, CompraProductosPK> {

    @Query("""
        SELECT cp.producto.id, cp.producto.nombre, SUM(cp.cantidad), SUM(cp.subTotal)
        FROM CompraProductos cp
        WHERE cp.compra.fecha BETWEEN :inicio AND :fin
        GROUP BY cp.producto.id, cp.producto.nombre
        ORDER BY SUM(cp.cantidad) DESC
    """)
    List<Object[]> findProductosMasVendidos(
            @Param("inicio") LocalDateTime inicio,
            @Param("fin") LocalDateTime fin
    );
}
