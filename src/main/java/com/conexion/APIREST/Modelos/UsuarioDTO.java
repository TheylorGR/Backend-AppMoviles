package com.conexion.APIREST.Modelos;

import lombok.Data;

@Data
public class UsuarioDTO {
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private Direccion direccion;
}