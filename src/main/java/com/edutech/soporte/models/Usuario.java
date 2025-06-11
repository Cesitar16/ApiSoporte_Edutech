package com.edutech.soporte.models;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuario")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Integer id;

    @Column(name = "username", nullable = false, length = 50)
    private String nombre;

    @Column(name = "email", nullable = false, length = 100)
    private String correo;

    @Column(name = "password",nullable = false, length = 100)
    private String contrasena;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDate fechaRegistro;

    @Column(name = "Activo")
    private Boolean EstadoUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROL_id_rol", nullable = false)
    @JsonBackReference
    private Rol rol;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMUNA_id_comuna", nullable = false)
    @JsonBackReference
    private Comuna comuna;
}