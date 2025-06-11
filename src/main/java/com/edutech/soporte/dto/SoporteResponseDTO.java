package com.edutech.soporte.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SoporteResponseDTO {
    private Integer id;
    private String asunto;
    private String descripcion;
    private String estado;
    private LocalDate fechaCreacion;
    private UsuarioDTO usuario;
}
