package com.universidadadmision.produccion.service;

import org.springframework.stereotype.Service;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import com.universidadadmision.produccion.dto.RespuestaAutorizacionDto;
import com.universidadadmision.produccion.dto.RespuestaTokenSesionDto;
import com.universidadadmision.produccion.dto.SolicitudAutorizacionDto;
import com.universidadadmision.produccion.dto.SolicitudPagoDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PagoServiceImpl implements PagoService {

    @Value("${pago.api.seguridad.url}")
    private String urlSeguridad;

    @Value("${pago.api.autorizacion.url}")
    private String urlAutorizacion;

    @Value("${pago.api.ecommerce.url}")
    private String urlEcommerce;

    @Value("${pago.api.credenciales}")
    private String credencialesApi;

    @Value("${pago.api.merchantId}")
    private String merchantId;

    @Override
    public String generarTokenAcceso() throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        String encodedAuth = Base64.getEncoder().encodeToString(credencialesApi.getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + encodedAuth);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(urlSeguridad, HttpMethod.GET, entity, String.class);
        if (response.getStatusCode() != HttpStatus.CREATED) {
            throw new Exception("Error al generar token de acceso");
        } 
        return response.getBody();
    }

    @Override
    public RespuestaTokenSesionDto generarTokenSesion(String tokenAcceso, SolicitudPagoDto solicitudPago)
            throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", tokenAcceso);

        HttpEntity<SolicitudPagoDto> entity = new HttpEntity<>(solicitudPago, headers);

        ResponseEntity<RespuestaTokenSesionDto> response = restTemplate.exchange(
                urlEcommerce + "/token/session/" + merchantId, HttpMethod.POST, entity, RespuestaTokenSesionDto.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new Exception("Error al generar token de sesión");
        } 
        return response.getBody();
    }

    @Override
    public RespuestaAutorizacionDto autorizarTransaccion(SolicitudAutorizacionDto solicitudAutorizacion) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + solicitudAutorizacion.getTokenSesion());
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<RespuestaAutorizacionDto> response = restTemplate.exchange(
                urlAutorizacion + "/" + merchantId,
                HttpMethod.POST,
                entity,
                RespuestaAutorizacionDto.class);

        return response.getBody();
    }
}
