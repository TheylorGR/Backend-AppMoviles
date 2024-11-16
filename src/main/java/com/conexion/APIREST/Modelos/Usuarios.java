package com.conexion.APIREST.Modelos;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Data
@Entity
@Table(name = "usuarios")
public class Usuarios {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Pedido> pedidos = new HashSet<>();
    
    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Size(min = 9, max = 9)
    @Column(nullable = false)
    private String telefono;

    @Column(nullable = false)
    private String contrasenia;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Direccion> direcciones;

    
    public void agregarDireccion(Direccion direccion) {
        this.direcciones.add(direccion);
        direccion.setUsuario(this);
    }

    public Usuarios() {
    this.direcciones = new ArrayList<>();
    }
    
}