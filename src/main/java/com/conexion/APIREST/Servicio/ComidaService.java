package com.conexion.APIREST.Servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conexion.APIREST.Modelos.Comida;
import com.conexion.APIREST.Modelos.Filtro;
import com.conexion.APIREST.Repository.ComidaRepository;

@Service
public class ComidaService {
    
    @Autowired
    private ComidaRepository comidaRepository;

    // Crear una nueva comida
    public Comida crearComida(Comida comida) {
        return comidaRepository.save(comida);
    }

    // Obtener todas las comidas
    public List<Comida> obtenerTodasLasComidas() {
        return comidaRepository.findAll();
    }

    // Obtener comida por ID
    public Comida obtenerComidaPorId(Integer id) {
        return comidaRepository.findById(id).orElse(null);
    }

    public List<Comida> obtenerComidasPorFiltro(Filtro filtroId) {
        return comidaRepository.findByFiltro(filtroId);
    }

    // Eliminar comida por ID
    public void eliminarComida(Integer id) {
        comidaRepository.deleteById(id);
    }
}
