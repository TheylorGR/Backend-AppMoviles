package com.conexion.APIREST.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.conexion.APIREST.Modelos.EstadoPedido;

public interface EstadoPedidoRepository extends JpaRepository<EstadoPedido, Integer>{
}
