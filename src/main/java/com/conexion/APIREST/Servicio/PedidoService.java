package com.conexion.APIREST.Servicio;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conexion.APIREST.Contador.Pedidosoli;
import com.conexion.APIREST.Modelos.Comida;
import com.conexion.APIREST.Modelos.EstadoPedido;
import com.conexion.APIREST.Modelos.Pedido;
import com.conexion.APIREST.Modelos.Usuarios;
import com.conexion.APIREST.Repository.ComidaRepository;
import com.conexion.APIREST.Repository.EstadoPedidoRepository;
import com.conexion.APIREST.Repository.PedidoRepository;
import com.conexion.APIREST.Repository.UsuariosRepository;

@Service
public class PedidoService {
    
    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ComidaRepository comidaRepository;

    @Autowired
    private EstadoPedidoRepository estadoPedidoRepository;

    @Autowired
    private UsuariosRepository usuarioRepository;

    // Crear un nuevo pedido y asociarlo a un usuario con un estado inicial
    public Pedido crearPedido(Pedidosoli pedido_, Integer usuarioId, Integer estadoId) {
        Optional<Usuarios> usuarioOpt = usuarioRepository.findById(usuarioId);
        Optional<EstadoPedido> estadoOpt = estadoPedidoRepository.findById(estadoId);

        if (usuarioOpt.isPresent() && estadoOpt.isPresent()) {
            Usuarios usuario = usuarioOpt.get();
            EstadoPedido estado = estadoOpt.get();

            // Validar que comidaIds no esté vacío o nulo antes de procesar
            if (pedido_.getComidaIds() == null || pedido_.getComidaIds().isEmpty()) {
                throw new IllegalArgumentException("El campo comidaIds no puede estar vacío o nulo");
            }

            Set<Comida> comidas = comidaRepository.findAllById(pedido_.getComidaIds()).stream().collect(Collectors.toSet());

            Integer totalCompra = comidas.stream()
                    .mapToInt(Comida::getPrecio)
                    .sum();

            // Crear un nuevo pedido con los valores calculados
            Pedido nuevoPedido = new Pedido();
            nuevoPedido.setUsuario(usuario);
            nuevoPedido.setEstado(estado);
            nuevoPedido.setComidas(comidas);
            nuevoPedido.setTotalcompra(totalCompra);

            return pedidoRepository.save(nuevoPedido);
        }

        return null;
    }

    // Obtener todos los pedidos
    public List<Pedido> obtenerTodosLosPedidos() {
        return pedidoRepository.findAll();
    }

    // Obtener pedido por ID
    public Pedido obtenerPedidoPorId(Integer id) {
        return pedidoRepository.findById(id).orElse(null);
    }

    // Obtener todos los pedidos de un usuario específico
    public List<Pedido> obtenerPedidosPorUsuario(Integer usuarioId) {
        return pedidoRepository.findByUsuarioId(usuarioId);
    }

    // Actualizar estado del pedido
    public Pedido actualizarEstadoPedido(Integer pedidoId, Integer estadoId) {
        Optional<Pedido> pedidoOpt = pedidoRepository.findById(pedidoId);
        Optional<EstadoPedido> estadoOpt = estadoPedidoRepository.findById(estadoId);

        if (pedidoOpt.isPresent() && estadoOpt.isPresent()) {
            Pedido pedido = pedidoOpt.get();
            EstadoPedido nuevoEstado = estadoOpt.get();
            pedido.setEstado(nuevoEstado);
            return pedidoRepository.save(pedido);
        }
        return null;
    }

    // Eliminar pedido
    public void eliminarPedido(Integer id) {
        pedidoRepository.deleteById(id);
    }
}
