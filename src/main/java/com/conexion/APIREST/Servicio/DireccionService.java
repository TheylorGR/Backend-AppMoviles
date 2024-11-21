package com.conexion.APIREST.Servicio;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conexion.APIREST.Modelos.Direccion;
import com.conexion.APIREST.Modelos.DireccionDTO;
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

    public Direccion obtenerPrimeraDireccionPorUsuarioId(Integer usuarioId) {
        return direccionRepo.findFirstByUsuarioIdOrderByIdAsc(usuarioId)
                             .orElseThrow(() -> new RuntimeException("Primera dirección no encontrada para el usuario"));
    }
    
    // Obtener todas las direcciones de un usuario por su ID, usando DireccionDTO
    public List<DireccionDTO> obtenerDireccionesDTOPorUsuarioId(Integer usuarioId) {
        List<Direccion> direcciones = direccionRepo.findByUsuarioId(usuarioId);
        // Convertir las direcciones a DTOs
        return direcciones.stream()
                .map(direccion -> new DireccionDTO(direccion.getId(), direccion.getLatitud(), direccion.getLongitud(), direccion.getNombre_ubi()))
                .collect(Collectors.toList());
    }

    private DireccionDTO convertirADireccionDTO(Direccion direccion) {
        DireccionDTO direccionDTO = new DireccionDTO();
        direccionDTO.setId(direccion.getId());
        direccionDTO.setNombre_ubi(direccion.getNombre_ubi());
        direccionDTO.setLatitud(direccion.getLatitud());
        direccionDTO.setLongitud(direccion.getLongitud());
        // Otros campos de la dirección
        return direccionDTO;
    }

    // Obtener la primera dirección por usuario ID y convertirla a DireccionDTO
    public DireccionDTO obtenerPrimeraDireccionDTOPorUsuarioId(Integer usuarioId) {
        List<Direccion> direcciones = direccionRepo.findByUsuarioId(usuarioId);
        
        if (direcciones != null && !direcciones.isEmpty()) {
            // Obtener la primera dirección
            Direccion primeraDireccion = direcciones.get(0);
            return convertirADireccionDTO(primeraDireccion);
        }
        return null;
    }

    public Direccion obtenerPrimeraDireccionPorUsuario(String email) {
        return direccionRepo.findTop1ByUsuarioEmailOrderByIdAsc(email);
    }

    // Eliminar una dirección por su ID
    public void eliminarDireccion(Integer id) {
        direccionRepo.deleteById(id);
    }

}