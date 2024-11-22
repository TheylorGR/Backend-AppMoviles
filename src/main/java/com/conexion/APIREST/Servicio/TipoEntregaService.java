package com.conexion.APIREST.Servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conexion.APIREST.Modelos.TipoEntrega;
import com.conexion.APIREST.Repository.TipoEntregaRepository;


@Service
public class TipoEntregaService {
    
    @Autowired
    TipoEntregaRepository tipoEntregaRepository;

    public List<TipoEntrega> ObtenerTipoEntrega() {
        return tipoEntregaRepository.findAll();
    }

    public Optional<TipoEntrega> obtenerTipoEntregaId(Integer id) {
        return tipoEntregaRepository.findById(id);
    }
}