package com.conexion.APIREST.Modelos;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Data
@Entity
@Table(name = "direccion")
public class Direccion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Double latitud;
    private Double longitud;
    private String nombre_ubi;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = true)
    @JsonBackReference
    private Usuarios usuario;

}
