package com.universidadadmision.produccion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.universidadadmision.produccion.service.PagoService;
import com.universidadadmision.produccion.dto.SolicitudPagoDto;
import com.universidadadmision.produccion.dto.RespuestaAutorizacionDto;
import com.universidadadmision.produccion.dto.RespuestaTokenSesionDto;
import com.universidadadmision.produccion.dto.SolicitudAutorizacionDto;

import java.util.HashMap;
import java.util.Map;

@Controller
@CrossOrigin
@RequestMapping("/pago")
@Validated
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @PostMapping("/generar-token-sesion")
    public ResponseEntity<?> generarTokenSesion(@RequestBody SolicitudPagoDto solicitudPago) throws Exception {
        Map<String, Object> response = new HashMap<>();
        try {
            String tokenAcceso = pagoService.generarTokenAcceso();
            RespuestaTokenSesionDto respuestaTokenSesion = pagoService.generarTokenSesion(tokenAcceso, solicitudPago);

            response.put("resultado", 1);
            response.put("mensaje", "Token de sesión generado correctamente");
            response.put("dato", respuestaTokenSesion);
        } catch (Exception e) {
            response.put("resultado", 0);
            response.put("mensaje", "Error al generar el token de sesión: " + e.getMessage());
            response.put("dato", "");
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/autorizar-transaccion")
    public ResponseEntity<?> autorizarTransaccion(@RequestBody SolicitudAutorizacionDto solicitudAutorizacion) throws Exception {
        Map<String, Object> response = new HashMap<>();
        try {

            RespuestaAutorizacionDto respuestaAutorizacion = pagoService.autorizarTransaccion(solicitudAutorizacion);
            
            // Actualizar la tabla de control con el estado de la transacción

            response.put("resultado", 1);
            response.put("mensaje", "Autorización de la transacción realizada correctamente");
            response.put("dato", respuestaAutorizacion);
        } catch (Exception e) {
            response.put("resultado", 0);
            response.put("mensaje", "Error al autorizar la transacción: " + e.getMessage());
            response.put("dato", "");
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.ok(response);
    }
}