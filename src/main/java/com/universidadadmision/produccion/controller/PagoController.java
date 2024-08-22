package com.universidadadmision.produccion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.bind.annotation.RequestBody;
import com.universidadadmision.produccion.service.PagoService;
import com.universidadadmision.produccion.service.TransaccionService;
import com.universidadadmision.produccion.dto.SolicitudPagoDto;
import com.universidadadmision.produccion.entity.Transaccion;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    @Autowired
    private TransaccionService transaccionService;

    @PostMapping("/generar-token-sesion")
    public ResponseEntity<?> generarTokenSesion(@RequestBody SolicitudPagoDto solicitudPago) throws Exception {
        Map<String, Object> response = new HashMap<>();
        try {
            String tokenAcceso = pagoService.generarTokenAcceso();
            RespuestaTokenSesionDto respuestaTokenSesion = pagoService.generarTokenSesion(tokenAcceso, solicitudPago);
            System.out.println(respuestaTokenSesion);
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
    public RedirectView autorizarTransaccion(
            @RequestParam(value = "purchaseNumber") String purchaseNumber,
            @RequestParam(value = "amount") String amount,
            @RequestParam(value = "transactionToken") String transactionToken,
            @RequestParam(value = "channel") String channel) {

        SolicitudAutorizacionDto solicitudAutorizacion = new SolicitudAutorizacionDto();
        SolicitudAutorizacionDto.OrderDto order = new SolicitudAutorizacionDto.OrderDto();
        order.setPurchaseNumber(purchaseNumber);
        order.setAmount(Double.valueOf(amount));
        order.setCurrency("PEN");
        order.setTokenId(transactionToken);
        solicitudAutorizacion.setOrder(order);
        solicitudAutorizacion.setChannel(channel);
        solicitudAutorizacion.setCaptureType("manual");
        solicitudAutorizacion.setCountable(true);

        try {
            RespuestaAutorizacionDto respuesta = pagoService.autorizarTransaccion(solicitudAutorizacion);
            ObjectMapper objectMapper = new ObjectMapper();
            String respuestaJson = objectMapper.writeValueAsString(respuesta);
            System.out.println("Respuesta del servicio: " + respuestaJson);
            String redirectUrl = "https://inscripcion.politecnica.edu.pe/finalizacion-pago/" + purchaseNumber;

            return new RedirectView(redirectUrl);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return new RedirectView("https://inscripcion.politecnica.edu.pe/finalizacion-pago/error");
        }
    }

    @GetMapping("/consultar-transaccion")
    public ResponseEntity<?> consultarPorNumeroTransaccion(@RequestParam("purchaseNumber") String purchaseNumber) {
        Map<String, Object> response = new HashMap<>();
        try {
            Transaccion transaccion = transaccionService.findByPurchaseNumber(purchaseNumber);
            if (transaccion != null) {
                response.put("resultado", 1);
                response.put("mensaje", "Transacción encontrada");
                response.put("dato", transaccion);
            } else {
                response.put("resultado", 0);
                response.put("mensaje", "Transacción no encontrada");
                response.put("dato", "");
            }
        } catch (Exception e) {
            response.put("resultado", 0);
            response.put("mensaje", "Error al consultar la transacción: " + e.getMessage());
            response.put("dato", "");
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.ok(response);
    }
}