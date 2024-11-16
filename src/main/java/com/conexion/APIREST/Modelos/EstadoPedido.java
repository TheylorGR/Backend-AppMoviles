package com.conexion.APIREST.Modelos;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import lombok.Data;

@Data
@Entity
@Table(name = "estado_pedido")
public class EstadoPedido {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String estado;

    @OneToMany(mappedBy = "estado", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Pedido> pedidos;
}
