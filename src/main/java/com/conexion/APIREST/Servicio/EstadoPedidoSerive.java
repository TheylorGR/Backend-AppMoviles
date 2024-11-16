package com.conexion.APIREST.Servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conexion.APIREST.Modelos.EstadoPedido;
import com.conexion.APIREST.Repository.EstadoPedidoRepository;

@Service
public class EstadoPedidoSerive { 
    
    @Autowired
    private EstadoPedidoRepository estadoPedidoRepository;

    // Obtener todos los estados de pedido
    public List<EstadoPedido> obtenerTodosLosEstados() {
        return estadoPedidoRepository.findAll();
    }

    // Crear un nuevo estado
    public EstadoPedido crearEstado(EstadoPedido estado) {
        return estadoPedidoRepository.save(estado);
    }

    // Eliminar un estado por ID
    public void eliminarEstado(Integer id) {
        estadoPedidoRepository.deleteById(id);
    }
}