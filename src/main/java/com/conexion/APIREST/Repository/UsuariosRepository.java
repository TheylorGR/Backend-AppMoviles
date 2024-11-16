package com.conexion.APIREST.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.conexion.APIREST.Modelos.Usuarios;

public interface UsuariosRepository extends JpaRepository<Usuarios, Integer>{
    Usuarios findByEmail(String email);
}
