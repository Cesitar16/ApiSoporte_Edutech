package com.edutech.soporte.services;


import com.edutech.soporte.models.Soporte;
import com.edutech.soporte.models.Usuario;
import com.edutech.soporte.dto.SoporteRequestDTO;
import com.edutech.soporte.dto.SoporteResponseDTO;
import com.edutech.soporte.dto.UsuarioDTO;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

// Esta clase prueba que los Modelos y DTOs se comportan como se espera.
class SoporteModelsTest {

    @Test
    @DisplayName("Prueba para verificar la creación de un Ticket de Soporte y sus relaciones")
    void testCrearSoporteConRelacion() {
        // 1. Crear objetos de dependencia
        Usuario usuario = new Usuario();
        usuario.setId(1); // Usa setId() como en tu modelo
        usuario.setNombre("testuser"); // Usa setNombre()

        // 2. Crear el objeto principal (Soporte)
        Soporte ticket = new Soporte();
        ticket.setId(101); // Usa setId() como en tu modelo
        ticket.setAsunto("Problema de prueba");
        ticket.setEstado("Abierto");
        ticket.setFechaCreacion(LocalDate.now()); // Usa setFechaCreacion()
        ticket.setUsuario(usuario);

        // 3. Validar el objeto y su relación
        assertNotNull(ticket);
        assertEquals(101, ticket.getId());
        assertEquals("Abierto", ticket.getEstado());
        assertEquals("testuser", ticket.getUsuario().getNombre());
    }

    @Test
    @DisplayName("Prueba para verificar la creación de los DTOs de Soporte")
    void testCrearSoporteDTOs() {
        // --- Prueba para SoporteRequestDTO ---
        SoporteRequestDTO requestDTO = new SoporteRequestDTO();
        requestDTO.setAsunto("Asunto desde Request");
        requestDTO.setDescripcion("Descripción de la petición");
        requestDTO.setUsuarioId(5);

        assertNotNull(requestDTO);
        assertEquals("Asunto desde Request", requestDTO.getAsunto());
        assertEquals(5, requestDTO.getUsuarioId());

        // --- Prueba para SoporteResponseDTO ---
        UsuarioDTO usuarioInfo = new UsuarioDTO();
        usuarioInfo.setUsername("respuesta_user"); // Usa setUsername() como en tu DTO
        usuarioInfo.setEmail("respuesta@test.com");
        usuarioInfo.setRol("Admin");

        SoporteResponseDTO responseDTO = new SoporteResponseDTO();
        responseDTO.setId(202); // Usa setId() como en tu DTO
        responseDTO.setEstado("Resuelto");
        responseDTO.setUsuario(usuarioInfo);

        assertNotNull(responseDTO);
        assertEquals(202, responseDTO.getId());
        assertEquals("Resuelto", responseDTO.getEstado());
        assertEquals("respuesta_user", responseDTO.getUsuario().getUsername());
    }
}
