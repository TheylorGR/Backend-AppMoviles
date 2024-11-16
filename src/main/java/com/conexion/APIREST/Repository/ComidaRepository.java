package com.conexion.APIREST.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.conexion.APIREST.Modelos.Comida;
import com.conexion.APIREST.Modelos.Filtro;


public interface ComidaRepository extends JpaRepository<Comida, Integer>{
    List<Comida> findByFiltro(Filtro filtro);
}
