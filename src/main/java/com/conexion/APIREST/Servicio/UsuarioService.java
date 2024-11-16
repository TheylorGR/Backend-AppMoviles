package com.conexion.APIREST.Servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.conexion.APIREST.Modelos.Usuarios;
import com.conexion.APIREST.Repository.UsuariosRepository;


@Service
public class UsuarioService {

    @Autowired
    private UsuariosRepository usuariorepo;

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

    public List<Usuarios> obtenerTodosLosUsuarios() {
        return usuariorepo.findAll();
    }

    // Eliminar por ID
    public void eliminarUsuario(Integer id) {
        usuariorepo.deleteById(id);
    }
}

