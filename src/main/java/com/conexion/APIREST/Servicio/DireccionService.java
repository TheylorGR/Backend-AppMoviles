package com.conexion.APIREST.Servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conexion.APIREST.Modelos.Direccion;
import com.conexion.APIREST.Modelos.Usuarios;
import com.conexion.APIREST.Repository.DireccionRepository;
import com.conexion.APIREST.Repository.UsuariosRepository;

@Service
public class DireccionService {
    
    @Autowired
    private DireccionRepository direccionRepo;
    @Autowired
    private UsuariosRepository usuariosRepository;

    // Guardar una nueva dirección para un usuario
    public Direccion guardarDireccionParaUsuario(Integer usuarioId, Direccion direccion) {
    Usuarios usuario = usuariosRepository.findById(usuarioId).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    direccion.setUsuario(usuario);
    return direccionRepo.save(direccion);
    }

    // Obtener todas las direcciones de un usuario por su ID
    public List<Direccion> obtenerDireccionesPorUsuarioId(Integer usuarioId) {
        return direccionRepo.findByUsuarioId(usuarioId);
    }

    // Actualizar una dirección existente
    public Direccion actualizarDireccion(Integer id, Direccion detallesDireccion) {
        return direccionRepo.findById(id).map(direccionExistente -> {
            direccionExistente.setLatitud(detallesDireccion.getLatitud());
            direccionExistente.setLongitud(detallesDireccion.getLongitud());
            direccionExistente.setNombre_ubi(detallesDireccion.getNombre_ubi());
            return direccionRepo.save(direccionExistente);
        }).orElse(null);
    }

    // Eliminar una dirección por su ID
    public void eliminarDireccion(Integer id) {
        direccionRepo.deleteById(id);
    }

}
