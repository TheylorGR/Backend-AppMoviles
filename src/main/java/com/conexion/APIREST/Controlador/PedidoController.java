package com.conexion.APIREST.Controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.conexion.APIREST.Contador.Pedidosoli;
import com.conexion.APIREST.Modelos.Pedido;
import com.conexion.APIREST.Servicio.PedidoService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/pedidos")
@CrossOrigin(origins = {"http://localhost:8030", "http://127.0.0.1:8083"}, allowCredentials = "true")
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;

    // Crear un nuevo pedido
    @PostMapping("/usuario/{usuarioId}/estado/{estadoId}")
    public Pedido crearPedido(@RequestBody Pedidosoli pedidoRequest, @PathVariable Integer usuarioId, @PathVariable Integer estadoId) {
        System.out.println("ComidaIds recibidos: " + pedidoRequest.getComidaIds());  // Agregar log

        // Verificar que comidaIds no esté vacío o nulo
        if (pedidoRequest.getComidaIds() == null || pedidoRequest.getComidaIds().isEmpty()) {
            throw new IllegalArgumentException("El campo comidaIds no puede estar vacío o nulo");
        }

        return pedidoService.crearPedido(pedidoRequest, usuarioId, estadoId);
    }

    // Obtener todos los pedidos
    @GetMapping
    public List<Pedido> obtenerPedidos() {
        return pedidoService.obtenerTodosLosPedidos();
    }

    // Obtener pedidos por usuario
    @GetMapping("/usuario/{usuarioId}")
    public List<Pedido> obtenerPedidosPorUsuario(@PathVariable Integer usuarioId) {
        return pedidoService.obtenerPedidosPorUsuario(usuarioId);
    }

    // Obtener pedido por su ID
    @GetMapping("/{id}")
    public Pedido obtenerPedidoById(@PathVariable Integer id) {
        return pedidoService.obtenerPedidoPorId(id);
    }

    // Actualizar estado del pedido
    @PutMapping("/{pedidoId}/estado/{estadoId}")
    public Pedido actualizarEstadoPedido(@PathVariable Integer pedidoId, @PathVariable Integer estadoId) {
        return pedidoService.actualizarEstadoPedido(pedidoId, estadoId);
    }

    // Eliminar un pedido por su ID
    @DeleteMapping("/{id}")
    public void eliminarPedido(@PathVariable Integer id) {
        pedidoService.eliminarPedido(id);
    }
}
