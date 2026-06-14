package com.bode.guazo.repository;

import com.bode.guazo.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    public Optional<Cliente> findByNumero(String numero);
}
