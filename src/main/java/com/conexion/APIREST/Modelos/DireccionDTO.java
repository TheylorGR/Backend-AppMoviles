package com.conexion.APIREST.Modelos;

import lombok.Data;

@Data
public class DireccionDTO {
    
    private Integer id;
    private Double latitud;
    private Double longitud;
    private String nombre_ubi;

    
    public DireccionDTO(Integer id,Double latitud, Double longitud, String nombre_ubi) {
        this.id = id;
        this.latitud = latitud;
        this.longitud = longitud;
        this.nombre_ubi = nombre_ubi;
    }


    public DireccionDTO() {}

}
