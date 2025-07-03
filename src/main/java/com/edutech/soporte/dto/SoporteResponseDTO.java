package com.edutech.soporte.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SoporteResponseDTO extends RepresentationModel<SoporteResponseDTO> {
    private Integer id;
    private String asunto;
    private String descripcion;
    private String estado;
    private LocalDate fechaCreacion;
    private UsuarioDTO usuario;
}
