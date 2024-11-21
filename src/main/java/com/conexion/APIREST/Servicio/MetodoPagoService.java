package com.conexion.APIREST.Servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conexion.APIREST.Modelos.MetodoPago;
import com.conexion.APIREST.Repository.MetodoPagoRepository;

@Service
public class MetodoPagoService {
    
    @Autowired
    MetodoPagoRepository metodoPagoRepository;

    public List<MetodoPago> ObtenerMetodos() {
        return metodoPagoRepository.findAll();
    }

    public Optional<MetodoPago> obtenerMetodosId(Integer id) {
        return metodoPagoRepository.findById(id);
    }

}
