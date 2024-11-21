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

import com.conexion.APIREST.Modelos.Direccion;
import com.conexion.APIREST.Modelos.UsuarioDTO;
import com.conexion.APIREST.Modelos.Usuarios;
import com.conexion.APIREST.Servicio.DireccionService;
import com.conexion.APIREST.Servicio.UsuarioService;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = {"http://localhost:8030", "http://127.0.0.1:8083"}, allowCredentials = "true")
public class UsuariosController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private DireccionService direccionService;

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> obtenerTodosLosUsuarios(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            List<UsuarioDTO> usuariosDTO = usuarioService.obtenerTodosLosUsuarios();
            return ResponseEntity.ok(usuariosDTO);
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
    public ResponseEntity<UsuarioDTO> actualizarUsuario(@PathVariable String email, @RequestBody Usuarios detallesActualizados) {
        Usuarios usuarioActualizado = usuarioService.actualizarUsuarioPorEmail(email, detallesActualizados);

        if (usuarioActualizado != null) {
            UsuarioDTO usuarioDTO = usuarioService.convertirAUsuarioDTO(usuarioActualizado);
            return ResponseEntity.ok(usuarioDTO);
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
    public ResponseEntity<UsuarioDTO> obtenerUsuarioPorEmail(@PathVariable String email) {
        Usuarios usuario = usuarioService.obtenerUsuarioPorEmail(email);
        if (usuario != null) {
            UsuarioDTO usuarioDTO = usuarioService.convertirAUsuarioDTO(usuario);
            return ResponseEntity.ok(usuarioDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //UsuarioDTO para limitar los datos y relaciones
    @GetMapping("/perfil/{email}")
    public ResponseEntity<UsuarioDTO> obtenerPerfilUsuario(@PathVariable String email) {
        // Obtener el usuario DTO por el email
        UsuarioDTO usuarioDTO = usuarioService.obtenerUsuarioDTOPorEmail(email);
        
        if (usuarioDTO != null) {
            // Obtener la primera dirección asociada al usuario
            Direccion primeraDireccion = direccionService.obtenerPrimeraDireccionPorUsuario(email);
            
            if (primeraDireccion != null) {
                // Asignar la dirección al DTO
                usuarioDTO.setDireccion(primeraDireccion);
            }

            return ResponseEntity.ok(usuarioDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Login de usuario
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Usuarios usuario, HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();

        Usuarios usuarioExistente = usuarioService.obtenerUsuarioPorEmail(usuario.getEmail());

        if (usuarioExistente != null && usuarioService.verificarContrasenia(usuario.getContrasenia(), usuarioExistente.getContrasenia())) {
            response.put("message", "Inicio de sesión exitoso");
            response.put("usuarioId", usuarioExistente.getId());

            HttpSession session = request.getSession();
            session.setAttribute("usuarioId", usuarioExistente.getId());

            return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, "JSESSIONID=" + session.getId()).body(response);
        } else {
            response.put("message", "Credenciales incorrectas");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
}