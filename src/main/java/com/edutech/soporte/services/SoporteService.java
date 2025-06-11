package com.edutech.soporte.services;

import com.edutech.soporte.dto.SoporteRequestDTO;
import com.edutech.soporte.dto.SoporteResponseDTO;
import com.edutech.soporte.dto.UsuarioDTO;
import com.edutech.soporte.models.Soporte;
import com.edutech.soporte.models.Usuario;
import com.edutech.soporte.repository.SoporteRepository;
import com.edutech.soporte.repository.UsuarioRepository; // Necesitas un repo para Usuario
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SoporteService {

    private final SoporteRepository soporteRepository;
    private final UsuarioRepository usuarioRepository;

    public SoporteService(SoporteRepository soporteRepository, UsuarioRepository usuarioRepository) {
        this.soporteRepository = soporteRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<SoporteResponseDTO> listarSoportes() {
        List<Soporte> soportes = soporteRepository.findAll();
        return soportes.stream()
            .map(this::convertirAResponseDTO)
            .collect(Collectors.toList());
    }

    public SoporteResponseDTO buscarSoportePorId(Integer id) {
        Soporte soporte = soporteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Soporte no encontrado"));
        return convertirAResponseDTO(soporte);
    }

    public SoporteResponseDTO crearSoporte(SoporteRequestDTO dto) {
        Soporte soporte = convertirAEntidad(dto);
        Soporte soporteGuardado = soporteRepository.save(soporte);
        return convertirAResponseDTO(soporteGuardado);
    }

    public SoporteResponseDTO actualizarSoporte(Integer id, SoporteRequestDTO dto) {
        Soporte soporte = soporteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Soporte no encontrado"));

        soporte.setAsunto(dto.getAsunto());
        soporte.setDescripcion(dto.getDescripcion());
        soporte.setEstado(dto.getEstado());

        if (dto.getFechaCreacion() != null) {
            soporte.setFechaCreacion(dto.getFechaCreacion());
        }

        if (dto.getUsuarioId() != null) {
            Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            soporte.setUsuario(usuario);
        }

        Soporte actualizado = soporteRepository.save(soporte);
        return convertirAResponseDTO(actualizado);
    }

    public void eliminarSoporte(Integer id) {
        soporteRepository.deleteById(id);
    }

    private SoporteResponseDTO convertirAResponseDTO(Soporte soporte) {
        UsuarioDTO usuarioDTO = null;
        if (soporte.getUsuario() != null) {
            usuarioDTO = new UsuarioDTO(
                soporte.getUsuario().getNombre(),
                soporte.getUsuario().getCorreo(),
                soporte.getUsuario().getRol().getNombreRol()
            );
        }

        return new SoporteResponseDTO(
            soporte.getId(),
            soporte.getAsunto(),
            soporte.getDescripcion(),
            soporte.getEstado(),
            soporte.getFechaCreacion(),
            usuarioDTO
        );
    }

    private Soporte convertirAEntidad(SoporteRequestDTO dto) {
        Soporte soporte = new Soporte();
        soporte.setAsunto(dto.getAsunto());
        soporte.setDescripcion(dto.getDescripcion());
        soporte.setEstado(dto.getEstado());

        // Aquí ya es LocalDate, no hace falta parsear
        soporte.setFechaCreacion(dto.getFechaCreacion() != null ? dto.getFechaCreacion() : LocalDate.now());

        if (dto.getUsuarioId() != null) {
            Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            soporte.setUsuario(usuario);
        } else {
            throw new RuntimeException("El usuarioId es obligatorio");
        }

        return soporte;
    }
}
