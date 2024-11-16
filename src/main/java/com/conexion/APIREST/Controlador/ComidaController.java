package com.conexion.APIREST.Controlador;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.conexion.APIREST.Modelos.Comida;
import com.conexion.APIREST.Modelos.Filtro;
import com.conexion.APIREST.Servicio.ComidaService;
import com.conexion.APIREST.Servicio.FiltroService;

@RestController
@RequestMapping("/comida")
@CrossOrigin(origins = {"http://localhost:8030", "http://127.0.0.1:8083"}, allowCredentials = "true")
public class ComidaController {

    @Autowired
    private ComidaService comidaService;

    @Autowired
    private FiltroService filtroService;

    // Crear una nueva comida
    @PostMapping
    public Comida crearComida(@RequestBody Comida comida) {
        return comidaService.crearComida(comida);
    }

    // Obtener todas las comidas
    @GetMapping
    public ResponseEntity<List<Comida>> obtenerTodasLasComidas(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
            if (session != null) {
            List<Comida> comidas = comidaService.obtenerTodasLasComidas();
            return ResponseEntity.ok(comidas);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
    }

    // Obtener comida por su ID
    @GetMapping("/{id}")
    public Comida obtenerComidaPorId(@PathVariable Integer id) {
        return comidaService.obtenerComidaPorId(id);
    }

    @GetMapping("/filtro/{filtroId}")
    public ResponseEntity<List<Comida>> obtenerComidasPorFiltro(@PathVariable Integer filtroId) {
        Optional<Filtro> filtro = filtroService.obtenerFiltroPorId(filtroId);
    
        if (filtro.isPresent()) {
            List<Comida> comidas = comidaService.obtenerComidasPorFiltro(filtro.get());
            if (!comidas.isEmpty()) {
                return ResponseEntity.ok(comidas);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();  // Si no hay comidas para el filtro
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  // Si no existe el filtro
        }
    }
}
