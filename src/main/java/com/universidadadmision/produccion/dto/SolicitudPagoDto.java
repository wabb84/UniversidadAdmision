package com.universidadadmision.produccion.dto;

import java.util.Map;
import lombok.Getter;

@Getter
public class SolicitudPagoDto {
    private String canal;
    private double monto;
    private Antifraude antifraude;
    private DatosTarjeta datosTarjeta;

    @Getter
    public static class Antifraude {
        private String ipCliente;
        private Map<String, String> datosDefinidosComerciante;

    }

    @Getter
    public static class DatosTarjeta {
        private String ciudadTitular;
        private String paisTitular;
        private String direccionTitular;
        private String codigoPostalTitular;
        private String estadoTitular;
        private String telefonoTitular;
    }
}