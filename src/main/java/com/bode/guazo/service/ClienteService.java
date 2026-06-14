package com.bode.guazo.service;

import com.bode.guazo.dto.ClienteDTO;
import com.bode.guazo.entity.Cliente;
import com.bode.guazo.exception.BusinessException;
import com.bode.guazo.exception.ResourceNotFoundException;
import com.bode.guazo.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    // ─── CRUD ─────────────────────────────────────────────────────────────────

    @Transactional
    public ClienteDTO crearCliente(ClienteDTO dto) {
        if (clienteRepository.findByNumero(dto.getNumero()).isPresent()) {
            throw new BusinessException("Ya existe un cliente registrado con el número: " + dto.getNumero());
        }
        Cliente cliente = new Cliente(dto.getNombre().toLowerCase(), dto.getNumero());
        return toDto(clienteRepository.save(cliente));
    }

    public List<ClienteDTO> getAllClientes() {
        return clienteRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    public ClienteDTO getClienteById(Integer id) {
        return toDto(findById(id));
    }

    @Transactional
    public ClienteDTO actualizarCliente(Integer id, ClienteDTO dto) {
        Cliente cliente = findById(id);
        // Si cambia el número, verificar que no esté en uso
        if (!cliente.getNumero().equals(dto.getNumero())) {
            clienteRepository.findByNumero(dto.getNumero()).ifPresent(c -> {
                throw new BusinessException("El número " + dto.getNumero() + " ya está en uso por otro cliente");
            });
        }
        cliente.setNombre(dto.getNombre().toLowerCase());
        cliente.setNumero(dto.getNumero());
        return toDto(clienteRepository.save(cliente));
    }

    @Transactional
    public void eliminarCliente(Integer id) {
        if (!clienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cliente no encontrado con ID: " + id);
        }
        clienteRepository.deleteById(id);
    }

    // ─── Uso interno ──────────────────────────────────────────────────────────

    public Cliente obtenerCliente(String nombre, String numero) {
        return clienteRepository.findByNumero(numero).orElseGet(() -> {
            Cliente nuevo = new Cliente(nombre.toLowerCase(), numero);
            return clienteRepository.save(nuevo);
        });
    }

    public Cliente findById(Integer id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + id));
    }

    // ─── Helper ───────────────────────────────────────────────────────────────

    public ClienteDTO toDto(Cliente c) {
        return new ClienteDTO(c.getId(), c.getNombre(), c.getNumero());
    }
}
