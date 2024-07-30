package com.universidadadmision.produccion.dto;

import java.io.Serializable;
import java.util.Map;
import lombok.Getter;

@Getter
public class SolicitudPagoDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private String channel;
    private double amount;
    private Antifraude antifraud;
    private DatosTarjeta dataMap;

    @Getter
    public static class Antifraude {
        private String clientIp;
        private Map<String, String> merchantDefineData;
    }

    @Getter
    public static class DatosTarjeta {
        private String cardholderCity;
        private String cardholderCountry;
        private String cardholderAddress;
        private String cardholderPostalCode;
        private String cardholderState;
        private String cardholderPhoneNumber;
    }
}