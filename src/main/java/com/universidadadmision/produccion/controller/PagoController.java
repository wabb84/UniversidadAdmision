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
import com.universidadadmision.produccion.service.PostulantesService;
import com.universidadadmision.produccion.service.TransaccionService;
import com.universidadadmision.produccion.dto.SolicitudPagoDto;
import com.universidadadmision.produccion.dto.TransaccionDto;
import com.universidadadmision.produccion.entity.Postulantes;
import com.universidadadmision.produccion.entity.Transaccion;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.universidadadmision.produccion.dto.MigraAcadDto;
import com.universidadadmision.produccion.dto.ModalidadDtoR;
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
    private PostulantesService postulantesService;

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
                String respuestaJsons = objectMapper.writeValueAsString(respuesta);
                System.out.println(respuestaJsons);
            if (respuesta.getTipoRespuesta() == "exito") {
                MigraAcadDto migraacad  = postulantesService.executeActivarPago(purchaseNumber);
                System.out.println("respuesta sp actualizar estado pagado");
            } 
            // validar estado pago
            String redirectUrl = "http://localhost:3000/finalizacion-pago/" + purchaseNumber;
            //String redirectUrl = "https://inscripciones.politecnica.edu.pe/finalizacion-pago/" + purchaseNumber;
            return new RedirectView(redirectUrl);

        } catch (Exception e) {
            System.out.println("error en pago");
            System.out.println(e.getLocalizedMessage());
            e.printStackTrace();
            return new RedirectView("https://inscripciones.politecnica.edu.pe/finalizacion-pago/error");
            //return new RedirectView("http://localhost:3000/finalizacion-pago/error");
        }
    }

    @PostMapping("/consultar-transaccion")
    public ResponseEntity<?> consultarPorNumeroTransaccion(@RequestBody TransaccionDto transaccionDto) {
        Map<String, Object> response = new HashMap<>();
        try {
            Transaccion transaccion = transaccionService.findByPurchaseNumber(transaccionDto.getPurchaseNumber());
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