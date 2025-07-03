package com.edutech.soporte.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.edutech.soporte.dto.SoporteRequestDTO;
import com.edutech.soporte.dto.SoporteResponseDTO;
import com.edutech.soporte.services.SoporteService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/soportes")
@RequiredArgsConstructor
public class SoporteController {

    private final SoporteService soporteService;

    @GetMapping
    public ResponseEntity<List<SoporteResponseDTO>> listarTodos() {
        List<SoporteResponseDTO> soportes = soporteService.listarSoportes();
        return ResponseEntity.ok(soportes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer id) {
        try {
            SoporteResponseDTO soporte = soporteService.buscarSoportePorId(id);
            return ResponseEntity.ok(soporte);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", ex.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> crearSoporte(@RequestBody SoporteRequestDTO request) {
        try {
            SoporteResponseDTO soporteCreado = soporteService.crearSoporte(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(soporteCreado);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", ex.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarSoporte(
            @PathVariable Integer id,
            @RequestBody SoporteRequestDTO soporteDTO) {
        try {
            SoporteResponseDTO soporteActualizado = soporteService.actualizarSoporte(id, soporteDTO);
            return ResponseEntity.ok(soporteActualizado);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", ex.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSoporte(@PathVariable Integer id) {
        try {
            soporteService.eliminarSoporte(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/hateoas/{id}")
    public SoporteResponseDTO obtenerHATEOAS(@PathVariable Integer id) {
        SoporteResponseDTO dto = soporteService.buscarSoportePorId(id);

        String gatewayUrl = "http://localhost:8888/api/proxy/soportes";

        // Link a sí mismo
        dto.add(Link.of(gatewayUrl + "/hateoas/" + id).withSelfRel());

        // Link a la lista de todos los soportes
        dto.add(Link.of(gatewayUrl + "/hateoas").withRel("todos-los-soportes"));

        // Link para eliminar
        dto.add(Link.of(gatewayUrl + "/" + id).withRel("eliminar").withType("DELETE"));

        // Link para actualizar
        dto.add(Link.of(gatewayUrl + "/" + id).withRel("actualizar").withType("PUT"));

        return dto;
    }

    /**
     * Obtiene todos los tickets de soporte y añade enlaces HATEOAS a cada uno.
     */
    @GetMapping("/hateoas")
    public List<SoporteResponseDTO> listarHATEOAS() {
        List<SoporteResponseDTO> soportes = soporteService.listarSoportes();
        String gatewayUrl = "http://localhost:8888/api/proxy/soportes";

        for (SoporteResponseDTO dto : soportes) {

            dto.add(Link.of(gatewayUrl + "/hateoas/" + dto.getId()).withSelfRel());

            dto.add(Link.of(gatewayUrl).withRel("editar-soporte").withType("PUT"));

            dto.add(Link.of(gatewayUrl).withRel("eliminar-soporte").withType("DELETE"));
        }

        return soportes;
    }
}
