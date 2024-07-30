package com.universidadadmision.produccion.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RespuestaTokenSesionDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private String sessionKey;
    private long expirationTime;
    private String merchantId;
}
