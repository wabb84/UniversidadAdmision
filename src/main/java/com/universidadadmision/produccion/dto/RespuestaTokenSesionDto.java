package com.universidadadmision.produccion.dto;

import lombok.Getter;

@Getter
public class RespuestaTokenSesionDto {
    private String claveSesion;
    private long tiempoExpiracion;

}
