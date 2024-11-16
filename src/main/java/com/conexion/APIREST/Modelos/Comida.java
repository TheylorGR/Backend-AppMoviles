package com.conexion.APIREST.Modelos;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

//Se usa para no ingresar getters y setters
import lombok.Data;

@Data
@Entity
@Table(name = "comida")
public class Comida {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;
    private Integer precio;
    private String descripcion;
    private String imagen;
    private Integer stock;
    
    @ManyToOne
    @JoinColumn(name = "filtro_id")
    private Filtro filtro;

    @ManyToMany(mappedBy = "comidas")
    private Set<Pedido> pedidos = new HashSet<>();
}