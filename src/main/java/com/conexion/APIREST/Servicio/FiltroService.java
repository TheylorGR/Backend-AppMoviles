package com.conexion.APIREST.Servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conexion.APIREST.Modelos.Filtro;
import com.conexion.APIREST.Repository.FiltroRepository;

@Service
public class FiltroService {
    
    @Autowired
    FiltroRepository filtrorepo;

    public List<Filtro> ObtenerFiltros() {
        return filtrorepo.findAll();
    }

    public Optional<Filtro> obtenerFiltroPorId(Integer id) {
        return filtrorepo.findById(id);
    }
}