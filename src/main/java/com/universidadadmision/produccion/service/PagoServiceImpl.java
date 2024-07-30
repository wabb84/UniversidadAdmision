package com.universidadadmision.produccion.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.universidadadmision.produccion.dto.RespuestaAutorizacionDto;
import com.universidadadmision.produccion.dto.RespuestaAutorizacionErrorDto;
import com.universidadadmision.produccion.dto.RespuestaAutorizacionExitoDto;
import com.universidadadmision.produccion.dto.RespuestaError500Dto;
import com.universidadadmision.produccion.dto.RespuestaTokenSesionDto;
import com.universidadadmision.produccion.dto.SolicitudAutorizacionDto;
import com.universidadadmision.produccion.dto.SolicitudPagoDto;
import com.universidadadmision.produccion.dto.TransaccionDto;
import com.universidadadmision.produccion.entity.Transaccion;
import com.universidadadmision.produccion.repository.TransaccionRepository;

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

    @Autowired
    private TransaccionRepository transaccionRepository;

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

        RespuestaTokenSesionDto respuesta = response.getBody();
        if (respuesta != null) {
            respuesta.setMerchantId(merchantId);
        }
        return respuesta;
    }

    @Override
    public RespuestaAutorizacionDto autorizarTransaccion(SolicitudAutorizacionDto solicitudAutorizacion)
            throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        String tokenAcceso = generarTokenAcceso();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", tokenAcceso);

        HttpEntity<SolicitudAutorizacionDto> entity = new HttpEntity<>(solicitudAutorizacion, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                urlAutorizacion + "/" + merchantId,
                HttpMethod.POST,
                entity,
                String.class);

        HttpStatus statusCode = (HttpStatus) response.getStatusCode();
        String responseBody = response.getBody();

        ObjectMapper objectMapper = new ObjectMapper();
        RespuestaAutorizacionDto resultado = new RespuestaAutorizacionDto();

        try {
            if (statusCode == HttpStatus.OK) {
                RespuestaAutorizacionExitoDto exito = objectMapper.readValue(responseBody,
                        RespuestaAutorizacionExitoDto.class);
                resultado.setTipoRespuesta("exito");
                resultado.setExito(exito);

                Transaccion transaccion = new Transaccion();
                transaccion.setEcoreTransactionUUID(exito.getHeader().getEcoreTransactionUUID());
                transaccion.setEcoreTransactionDate(exito.getHeader().getEcoreTransactionDate());
                transaccion.setMillis((float) exito.getHeader().getMillis());
                transaccion.setChannel(exito.getFulfillment().getChannel());
                transaccion.setMerchantId(exito.getFulfillment().getMerchantId());
                transaccion.setTerminalId(exito.getFulfillment().getTerminalId());
                transaccion.setCaptureType(exito.getFulfillment().getCaptureType());
                transaccion.setCountable(exito.getFulfillment().isCountable());
                transaccion.setFastPayment(exito.getFulfillment().isFastPayment());
                transaccion.setPurchaseNumber(exito.getOrder().getPurchaseNumber());
                transaccion.setInstallment(exito.getOrder().getInstallment());
                transaccion.setAuthorizedAmount((float) exito.getOrder().getAuthorizedAmount());
                transaccion.setTERMINAL(exito.getDataMap().getTerminal());
                transaccion.setBRAND_ACTION_CODE(exito.getDataMap().getBrandActionCode());
                transaccion.setBRAND_HOST_DATE_TIME(exito.getDataMap().getBrandHostDateTime());
                transaccion.setTRACE_NUMBER(exito.getDataMap().getTraceNumber());
                transaccion.setCARD_TYPE(exito.getDataMap().getCardType());
                transaccion.setECI_DESCRIPTION(exito.getDataMap().getEciDescription());
                transaccion.setCARD(exito.getDataMap().getCard());
                transaccion.setMERCHANT(exito.getDataMap().getMerchant());
                transaccion.setSTATUS(exito.getDataMap().getStatus());
                transaccion.setACTION_DESCRIPTION(exito.getDataMap().getActionDescription());
                transaccion.setID_UNICO(exito.getDataMap().getIdUnico());
                transaccion.setAMOUNT(exito.getDataMap().getAmount());
                transaccion.setBRAND_HOST_ID(exito.getDataMap().getBrandHostId());
                transaccion.setAUTHORIZATION_CODE(exito.getDataMap().getAuthorizationCode());
                transaccion.setYAPE_ID(exito.getDataMap().getYapeId());
                transaccion.setCURRENCY(exito.getDataMap().getCurrency());
                transaccion.setTRANSACTION_DATE(exito.getDataMap().getTransactionDate());
                transaccion.setACTION_CODE(exito.getDataMap().getActionCode());
                transaccion.setECI(exito.getDataMap().getEci());
                transaccion.setID_RESOLUTOR(exito.getDataMap().getIdResolutor());
                transaccion.setBRAND(exito.getDataMap().getBrand());
                transaccion.setADQUIRENTE(exito.getDataMap().getAdquirente());
                transaccion.setBRAND_NAME(exito.getDataMap().getBrandName());
                transaccion.setPROCESS_CODE(exito.getDataMap().getProcessCode());
                transaccion.setTRANSACTION_ID(exito.getDataMap().getTransactionId());

                transaccionRepository.save(transaccion);

            } else if (statusCode == HttpStatus.BAD_REQUEST) {
                RespuestaAutorizacionErrorDto error = objectMapper.readValue(responseBody,
                        RespuestaAutorizacionErrorDto.class);
                resultado.setTipoRespuesta("error");
                resultado.setError(error);
            } else {
                throw new RuntimeException("Error inesperado: " + statusCode);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al deserializar la respuesta: " + e.getMessage(), e);
        }
        return resultado;
    }
}
