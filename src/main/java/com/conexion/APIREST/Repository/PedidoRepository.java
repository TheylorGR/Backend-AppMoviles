package com.conexion.APIREST.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.conexion.APIREST.Modelos.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer>{
    List<Pedido> findByUsuarioId(Integer usuarioId);
    List<Pedido> findByEstadoId(Integer estadoId);
}
