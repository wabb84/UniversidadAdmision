package com.universidadadmision.produccion.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RespuestaError500Dto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String errorMessage;

    public RespuestaError500Dto(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}