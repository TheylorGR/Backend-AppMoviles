package com.conexion.APIREST.Controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.conexion.APIREST.Modelos.EstadoPedido;
import com.conexion.APIREST.Servicio.EstadoPedidoSerive;

@RestController
@RequestMapping("/estadopedido")
@CrossOrigin(origins = {"http://localhost:8030", "http://127.0.0.1:8083"}, allowCredentials = "true")
public class EstadoPedidoController {
    @Autowired
    private EstadoPedidoSerive estadoPedidoService;

    // Obtener todos los estados
    @GetMapping
    public List<EstadoPedido> obtenerEstados() {
        return estadoPedidoService.obtenerTodosLosEstados();
    }

    // Crear un nuevo estado
    @PostMapping
    public EstadoPedido crearEstado(@RequestBody EstadoPedido estado) {
        return estadoPedidoService.crearEstado(estado);
    }

    // Eliminar un estado por su ID
    @DeleteMapping("/{id}")
    public void eliminarEstado(@PathVariable Integer id) {
        estadoPedidoService.eliminarEstado(id);
    }
}
