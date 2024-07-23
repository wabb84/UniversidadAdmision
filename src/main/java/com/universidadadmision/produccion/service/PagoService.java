package com.universidadadmision.produccion.service;

import com.universidadadmision.produccion.dto.RespuestaAutorizacionDto;
import com.universidadadmision.produccion.dto.RespuestaTokenSesionDto;
import com.universidadadmision.produccion.dto.SolicitudAutorizacionDto;
import com.universidadadmision.produccion.dto.SolicitudPagoDto;

public interface PagoService {
    String generarTokenAcceso() throws Exception;
    RespuestaTokenSesionDto generarTokenSesion(String tokenAcceso, SolicitudPagoDto solicitudPago) throws Exception;
    RespuestaAutorizacionDto autorizarTransaccion(SolicitudAutorizacionDto solicitudAutorizacion) throws Exception;
}
