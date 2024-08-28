package com.universidadadmision.produccion.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidaPostulanteDto {
    private Long id;
    private String codigo;
    private String pedido;
    private String estado;
}
