package com.conexion.APIREST.Modelos;

import javax.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name = "filtro")
public class Filtro {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;
    private String imagen;
}
