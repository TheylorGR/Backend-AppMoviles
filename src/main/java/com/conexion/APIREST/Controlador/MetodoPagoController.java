package com.conexion.APIREST.Controlador;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.conexion.APIREST.Modelos.MetodoPago;
import com.conexion.APIREST.Servicio.MetodoPagoService;

@RestController
@RequestMapping("/metodo_pago")
@CrossOrigin(origins = {"http://localhost:8030", "http://127.0.0.1:8083"}, allowCredentials = "true")
public class MetodoPagoController {
    
    @Autowired
    private MetodoPagoService metodoPagoService;

        @GetMapping
        public ResponseEntity<List<MetodoPago>> ObtenerMetodos(HttpServletRequest request) {
            HttpSession session = request.getSession(false);
                if (session != null) {
                List<MetodoPago> metodoPagos = metodoPagoService.ObtenerMetodos();
                return ResponseEntity.ok(metodoPagos);
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }
        }

}
