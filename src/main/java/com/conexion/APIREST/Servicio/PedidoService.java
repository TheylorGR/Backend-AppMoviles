package com.conexion.APIREST.Servicio;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conexion.APIREST.Modelos.Comida;
import com.conexion.APIREST.Modelos.Direccion;
import com.conexion.APIREST.Modelos.Pedido;
import com.conexion.APIREST.Modelos.TipoEntrega;
import com.conexion.APIREST.Repository.ComidaRepository;
import com.conexion.APIREST.Repository.DireccionRepository;
import com.conexion.APIREST.Repository.PedidoRepository;
import com.conexion.APIREST.Repository.TipoEntregaRepository;

@Service
public class PedidoService {
    
    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ComidaRepository comidaRepository;

    @Autowired
    private DireccionRepository direccionRepository;

    @Autowired
    private TipoEntregaRepository tipoEntregaRepository;

    @Transactional
    public Pedido guardarPedido(Pedido pedido) {
        if (pedido.getComidas() == null || pedido.getComidas().isEmpty()) {
            throw new RuntimeException("El pedido debe contener al menos una comida.");
        }

        // Calcular el total de la compra
        double totalCompra = 0.0;

        if (pedido.getUsuario() == null || pedido.getUsuario().getId() == null) {
        throw new RuntimeException("El pedido debe contener un usuario válido.");
    }

        // Validar dirección
        if (pedido.getDireccion() == null || pedido.getDireccion().getId() == null) {
            throw new RuntimeException("El pedido debe contener una dirección válida.");
        }

        // Buscar la dirección en la base de datos
        Direccion direccion = direccionRepository.findById(pedido.getDireccion().getId())
                .orElseThrow(() -> new RuntimeException("La dirección no existe con el ID: " + pedido.getDireccion().getId()));

        // Validar que la dirección pertenece al usuario
        if (!direccion.getUsuario().getId().equals(pedido.getUsuario().getId())) {
            throw new RuntimeException("La dirección no pertenece al usuario.");
        }

        // Asignar la dirección al pedido
        pedido.setDireccion(direccion);

        // Validar tipo de entrega
        if (pedido.getTipoEntrega() == null || pedido.getTipoEntrega().getId() == null) {
            throw new RuntimeException("El pedido debe contener un tipo de entrega válido.");
        }

        TipoEntrega tipoEntrega = tipoEntregaRepository.findById(pedido.getTipoEntrega().getId())
                .orElseThrow(() -> new RuntimeException("El tipo de entrega no existe con el ID: " + pedido.getTipoEntrega().getId()));

        pedido.setTipoEntrega(tipoEntrega);

        for (Comida comida : pedido.getComidas()) {
            // Validar cantidad solicitada
            Integer cantidadSolicitada = comida.getCantidadSolicitada();
            if (cantidadSolicitada == null || cantidadSolicitada <= 0) {
                throw new RuntimeException("Cantidad solicitada inválida para la comida con ID: " + comida.getId());
            }

            // Buscar la comida en la base de datos
            Comida comidaBase = comidaRepository.findById(comida.getId())
                    .orElseThrow(() -> new RuntimeException("Comida no encontrada con ID: " + comida.getId()));

            // Validar stock
            if (comidaBase.getStock() < cantidadSolicitada) {
                throw new RuntimeException("Stock insuficiente para la comida: " + comidaBase.getNombre());
            }

            // Actualizar el stock
            comidaBase.setStock(comidaBase.getStock() - cantidadSolicitada);
            comidaRepository.save(comidaBase);

            // Calcular el subtotal
            totalCompra += comidaBase.getPrecio() * cantidadSolicitada;
        }

        // Configurar los totales del pedido
        pedido.setTotalCompra(totalCompra);
        pedido.setTotal(totalCompra + tipoEntrega.getCosto());

        // Guardar el pedido y la relación con comidas
        return pedidoRepository.save(pedido);
    }
}