package com.conexion.APIREST.Servicio;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.conexion.APIREST.Modelos.Direccion;
import com.conexion.APIREST.Modelos.UsuarioDTO;
import com.conexion.APIREST.Modelos.Usuarios;
import com.conexion.APIREST.Repository.UsuariosRepository;


@Service
public class UsuarioService {

    @Autowired
    private UsuariosRepository usuariorepo;

    @Autowired
    private DireccionService direccionService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Guardar un nuevo usuario
    public Usuarios guardarUsuario(Usuarios usuario) {
        String hashedPassword = passwordEncoder.encode(usuario.getContrasenia());
        usuario.setContrasenia(hashedPassword);
        return usuariorepo.save(usuario);
    }

    // Obtener usuario por Email
    public Usuarios obtenerUsuarioPorEmail(String email) {
        return usuariorepo.findByEmail(email);
    }

    // Convertir entidad a DTO
    public UsuarioDTO convertirAUsuarioDTO(Usuarios usuario) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNombre(usuario.getNombre());
        usuarioDTO.setApellido(usuario.getApellido());
        usuarioDTO.setEmail(usuario.getEmail());
        usuarioDTO.setTelefono(usuario.getTelefono());
        return usuarioDTO;
    }

    public UsuarioDTO obtenerUsuarioDTOPorEmail(String email) {
        // Buscar al usuario por su email
        Usuarios usuario = usuariorepo.findByEmail(email);
        
        if (usuario != null) {
            // Convertir el usuario a UsuarioDTO
            UsuarioDTO usuarioDTO = convertirAUsuarioDTO(usuario);
            
            // Obtener la primera dirección de ese usuario
            Direccion primeraDireccion = direccionService.obtenerPrimeraDireccionPorUsuario(usuario.getEmail());
            
            // Incluir la dirección en el DTO del usuario
            usuarioDTO.setDireccion(primeraDireccion);
            
            return usuarioDTO;
        }
        return null;
    }

    // Actualizar solo email, teléfono y contraseña de un usuario
    public Usuarios actualizarUsuarioPorEmail(String email, Usuarios detallesActualizados) {
        Usuarios usuarioExistente = obtenerUsuarioPorEmail(email);

        if (usuarioExistente != null) {
            usuarioExistente.setNombre(detallesActualizados.getNombre());
            usuarioExistente.setApellido(detallesActualizados.getApellido());
            usuarioExistente.setTelefono(detallesActualizados.getTelefono());

            return usuariorepo.save(usuarioExistente);
        }
        return null;
    }

    // Verificar la contraseña
    public boolean verificarContrasenia(String contraseniaIngresada, String contraseniaAlmacenada) {
        return passwordEncoder.matches(contraseniaIngresada, contraseniaAlmacenada);
    }

    public List<UsuarioDTO> obtenerTodosLosUsuarios() {
        List<Usuarios> usuarios = usuariorepo.findAll();
        List<UsuarioDTO> usuarioDTOs = new ArrayList<>();

        for (Usuarios usuario : usuarios) {
            usuarioDTOs.add(convertirAUsuarioDTO(usuario));
        }

        return usuarioDTOs;
    }

    // Eliminar por ID
    public void eliminarUsuario(Integer id) {
        usuariorepo.deleteById(id);
    }
}