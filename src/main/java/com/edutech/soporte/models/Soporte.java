package com.edutech.soporte.models;

import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "soporte")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Soporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ticket")
    private Integer id;

    @Column(name = "asunto", nullable = false, length = 50)
    private String asunto;

    @Column(name = "descripcion", nullable = false, length = 500)
    private String descripcion;

    @Column(name = "estado", nullable = false, length = 25)
    private String estado;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDate fechaCreacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USUARIO_id_user", nullable = false)
    private Usuario usuario;
}
