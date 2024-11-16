package com.conexion.APIREST.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.conexion.APIREST.Modelos.Direccion;

public interface DireccionRepository extends JpaRepository<Direccion, Integer>{
    List<Direccion> findByUsuarioId(Integer usuarioId);
}
