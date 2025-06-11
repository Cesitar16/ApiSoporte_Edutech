package com.edutech.soporte.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// SoporteRequestDTO.java
@Data
@AllArgsConstructor
@NoArgsConstructor

public class SoporteRequestDTO {
    private String asunto;
    private String descripcion;
    private String estado;
    private LocalDate fechaCreacion; 
    private Integer usuarioId;  
}

