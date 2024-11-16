package com.conexion.APIREST.Controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.conexion.APIREST.Modelos.Direccion;
import com.conexion.APIREST.Servicio.DireccionService;

@RestController
@RequestMapping("/direccion")
@CrossOrigin(origins = {"http://localhost:8030", "http://127.0.0.1:8083"}, allowCredentials = "true")
public class DireccionController {
    
    @Autowired
    private DireccionService direccionService;

    // Guardar una nueva dirección
    @PostMapping("/usuarios/{usuariosId}/direccion")
    public ResponseEntity<Direccion> agregarDireccion(@PathVariable Integer usuariosId, @RequestBody Direccion direccion) {
        if (direccion == null || direccion.getNombre_ubi() == null || direccion.getNombre_ubi().isEmpty()) {
            return ResponseEntity.badRequest().body(null); // Devuelve un error si la dirección no es válida
        }
    
        Direccion nuevaDireccion = direccionService.guardarDireccionParaUsuario(usuariosId, direccion);
        return ResponseEntity.ok(nuevaDireccion);
    }


    // Obtener todas las direcciones de un usuario
    @GetMapping("/usuarios/{usuariosId}")
    public ResponseEntity<List<Direccion>> obtenerDireccionesPorUsuarioId(@PathVariable Integer usuariosId) {
        List<Direccion> direcciones = direccionService.obtenerDireccionesPorUsuarioId(usuariosId);
        return ResponseEntity.ok(direcciones);
    }

    // Actualizar una dirección existente
    @PutMapping("/{id}")
    public ResponseEntity<Direccion> actualizarDireccion(@PathVariable Integer id, @RequestBody Direccion detallesDireccion) {
        Direccion direccionActualizada = direccionService.actualizarDireccion(id, detallesDireccion);
        if (direccionActualizada != null) {
            return ResponseEntity.ok(direccionActualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar una dirección
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDireccion(@PathVariable Integer id) {
        direccionService.eliminarDireccion(id);
        return ResponseEntity.noContent().build();
    }
    
}