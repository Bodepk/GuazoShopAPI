package com.bode.guazo.repository;

import com.bode.guazo.entity.Promocion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PromocionRepository extends JpaRepository<Promocion, Integer> {

    // Solo las activas y no vencidas (para el frontend)
    @Query("SELECT p FROM Promocion p WHERE p.activa = true AND p.fechaVencimiento > :ahora ORDER BY p.fechaCreacion DESC")
    List<Promocion> findActivasVigentes(LocalDateTime ahora);

    // Todas (para el admin)
    List<Promocion> findAllByOrderByFechaCreacionDesc();
}
