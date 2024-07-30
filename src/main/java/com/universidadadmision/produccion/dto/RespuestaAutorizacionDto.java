package com.universidadadmision.produccion.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RespuestaAutorizacionDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String tipoRespuesta;
    private RespuestaAutorizacionExitoDto exito;
    private RespuestaAutorizacionErrorDto error;
}