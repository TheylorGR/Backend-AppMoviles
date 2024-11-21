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

import com.conexion.APIREST.Modelos.TipoEntrega;
import com.conexion.APIREST.Servicio.TipoEntregaService;

@RestController
@RequestMapping("/tipo_entrega")
@CrossOrigin(origins = {"http://localhost:8030", "http://127.0.0.1:8083"}, allowCredentials = "true")
public class TipoEntregaController {
    
    @Autowired
    private TipoEntregaService tipoEntregaService;

        @GetMapping
        public ResponseEntity<List<TipoEntrega>> ObtenerTipoEntrega(HttpServletRequest request) {
            HttpSession session = request.getSession(false);
                if (session != null) {
                List<TipoEntrega> tipoentrega = tipoEntregaService.ObtenerTipoEntrega();
                return ResponseEntity.ok(tipoentrega);
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }
        }

}
