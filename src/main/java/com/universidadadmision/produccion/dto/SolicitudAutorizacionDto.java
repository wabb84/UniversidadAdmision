package com.universidadadmision.produccion.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SolicitudAutorizacionDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String channel;
    private String captureType;
    private Boolean countable;
    private OrderDto order;

    @Getter
    @Setter
    public static class OrderDto {
        private String tokenId;
        private String purchaseNumber;
        private Double amount;
        private String currency;
    }
}