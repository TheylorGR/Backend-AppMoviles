package com.conexion.APIREST.Controlador;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.conexion.APIREST.Modelos.Usuarios;
import com.conexion.APIREST.Servicio.UsuarioService;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = {"http://localhost:8030", "http://127.0.0.1:8083"}, allowCredentials = "true")
public class UsuariosController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuarios>> obtenerTodosLosUsuarios(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
            if (session != null) {
            List<Usuarios> usuarios = usuarioService.obtenerTodosLosUsuarios();
            return ResponseEntity.ok(usuarios);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
    }


    // Crear un nuevo usuario
    @PostMapping
    public Usuarios crearUsuario(@RequestBody Usuarios usuario) {
        return usuarioService.guardarUsuario(usuario);
    }

    // Actualizar usuario
    @PutMapping("/email/{email}")
    public ResponseEntity<Usuarios> actualizarUsuario(@PathVariable String email, @RequestBody Usuarios detallesActualizados) {
        Usuarios usuarioActualizado = usuarioService.actualizarUsuarioPorEmail(email, detallesActualizados);

        if (usuarioActualizado != null) {
            return ResponseEntity.ok(usuarioActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar un usuario por su ID
    @DeleteMapping("/{id}")
    public void eliminarUsuario(@PathVariable Integer id) {
        usuarioService.eliminarUsuario(id);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Usuarios> obtenerUsuarioPorEmail(@PathVariable String email) {
    Usuarios usuario = usuarioService.obtenerUsuarioPorEmail(email);
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    // Login de usuario
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Usuarios usuario, HttpServletRequest request) {
        // Crear un mapa para almacenar la respuesta en formato JSON
        Map<String, Object> response = new HashMap<>();

        // Obtener el usuario por el email
        Usuarios usuarioExistente = usuarioService.obtenerUsuarioPorEmail(usuario.getEmail());

        // Verificar si existe y si la contraseña es correcta
        if (usuarioExistente != null && usuarioService.verificarContrasenia(usuario.getContrasenia(), usuarioExistente.getContrasenia())) {
            response.put("message", "Inicio de sesión exitoso");
            response.put("usuarioId", usuarioExistente.getId()); // Agregar el usuarioId a la respuesta

            // Crear sesión si es necesario
            HttpSession session = request.getSession();
            session.setAttribute("usuarioId", usuarioExistente.getId()); // Guardar el usuarioId en la sesión

            return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, "JSESSIONID=" + session.getId()).body(response);
        } else {
            response.put("message", "Credenciales incorrectas");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

}