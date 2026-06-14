package com.bode.guazo.repository;

import com.bode.guazo.entity.Producto;
import com.bode.guazo.entity.enums.Categoria;
import com.bode.guazo.entity.enums.ProductoEstado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Producto, Integer> {
    List<Producto> findByCategoria(Categoria categoria);
    List<Producto> findByEstado(ProductoEstado estado);
}
