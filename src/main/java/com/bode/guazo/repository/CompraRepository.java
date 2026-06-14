package com.bode.guazo.repository;

import com.bode.guazo.entity.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Integer> {

    List<Compra> findByClienteId(Integer clienteId);

    List<Compra> findByClienteNumero(String numero);

    @Query("SELECT c FROM Compra c WHERE c.fecha BETWEEN :inicio AND :fin ORDER BY c.fecha DESC")
    List<Compra> findByFechaBetween(
            @Param("inicio") LocalDateTime inicio,
            @Param("fin") LocalDateTime fin
    );
}
