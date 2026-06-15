package com.bode.guazo.service;

import com.bode.guazo.dto.PromocionDTO;
import com.bode.guazo.entity.Promocion;
import com.bode.guazo.exception.ResourceNotFoundException;
import com.bode.guazo.repository.PromocionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PromocionService {

    @Autowired
    private PromocionRepository promocionRepository;

    // ─── Para el frontend: solo activas y vigentes ────────────────────────────

    public List<PromocionDTO> getPromocionesVigentes() {
        return promocionRepository.findActivasVigentes(LocalDateTime.now())
                .stream().map(this::toDto).toList();
    }

    // ─── Para el admin: todas ─────────────────────────────────────────────────

    public List<PromocionDTO> getTodasPromociones() {
        return promocionRepository.findAllByOrderByFechaCreacionDesc()
                .stream().map(this::toDto).toList();
    }

    public PromocionDTO getById(Integer id) {
        return toDto(findById(id));
    }

    @Transactional
    public PromocionDTO crear(PromocionDTO dto) {
        Promocion p = new Promocion();
        mapToEntity(dto, p);
        return toDto(promocionRepository.save(p));
    }

    @Transactional
    public PromocionDTO actualizar(Integer id, PromocionDTO dto) {
        Promocion p = findById(id);
        mapToEntity(dto, p);
        return toDto(promocionRepository.save(p));
    }

    @Transactional
    public void eliminar(Integer id) {
        if (!promocionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Promoción no encontrada con ID: " + id);
        }
        promocionRepository.deleteById(id);
    }

    @Transactional
    public PromocionDTO toggleActiva(Integer id) {
        Promocion p = findById(id);
        p.setActiva(!p.getActiva());
        return toDto(promocionRepository.save(p));
    }

    // ─── Helpers ─────────────────────────────────────────────────────────────

    private Promocion findById(Integer id) {
        return promocionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Promoción no encontrada con ID: " + id));
    }

    private void mapToEntity(PromocionDTO dto, Promocion p) {
        p.setBadge(dto.getBadge());
        p.setTitulo(dto.getTitulo());
        p.setDescripcion(dto.getDescripcion());
        p.setUrlImagen(dto.getUrlImagen());
        p.setTextoBoton(dto.getTextoBoton());
        p.setFechaVencimiento(dto.getFechaVencimiento());
        p.setActiva(dto.getActiva() != null ? dto.getActiva() : true);
    }

    private PromocionDTO toDto(Promocion p) {
        PromocionDTO dto = new PromocionDTO();
        dto.setId(p.getId());
        dto.setBadge(p.getBadge());
        dto.setTitulo(p.getTitulo());
        dto.setDescripcion(p.getDescripcion());
        dto.setUrlImagen(p.getUrlImagen());
        dto.setTextoBoton(p.getTextoBoton());
        dto.setFechaVencimiento(p.getFechaVencimiento());
        dto.setActiva(p.getActiva());
        dto.setFechaCreacion(p.getFechaCreacion());
        return dto;
    }
}
