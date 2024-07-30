package com.universidadadmision.produccion.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RespuestaAutorizacionExitoDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private HeaderDto header;
    private FulfillmentDto fulfillment;
    private OrderDto order;
    private DataMapDto dataMap;

    @Getter
    @Setter
    public static class HeaderDto {
        private String ecoreTransactionUUID;
        private long ecoreTransactionDate;
        private double millis;

    }

    @Getter
    @Setter
    public static class FulfillmentDto {
        private String channel;
        private String merchantId;
        private String terminalId;
        private String captureType;
        private boolean countable;
        private boolean fastPayment;
        private String signature;
    }

    @Getter
    @Setter
    public static class OrderDto {
        private String tokenId;
        private String purchaseNumber;
        private double amount;
        private int installment;
        private String currency;
        private double authorizedAmount;
        private String authorizationCode;
        private String actionCode;
        private String traceNumber;
        private String transactionDate;
        private String transactionId;
    }

    @Getter
    @Setter
    public static class DataMapDto {
        @JsonProperty("TERMINAL")
        private String terminal;

        @JsonProperty("BRAND_ACTION_CODE")
        private String brandActionCode;

        @JsonProperty("BRAND_HOST_DATE_TIME")
        private String brandHostDateTime;

        @JsonProperty("TRACE_NUMBER")
        private String traceNumber;

        @JsonProperty("CARD_TYPE")
        private String cardType;

        @JsonProperty("ECI_DESCRIPTION")
        private String eciDescription;

        @JsonProperty("SIGNATURE")
        private String signature;

        @JsonProperty("CARD")
        private String card;

        @JsonProperty("MERCHANT")
        private String merchant;

        @JsonProperty("STATUS")
        private String status;

        @JsonProperty("ACTION_DESCRIPTION")
        private String actionDescription;

        @JsonProperty("ID_UNICO")
        private String idUnico;

        @JsonProperty("AMOUNT")
        private String amount;

        @JsonProperty("BRAND_HOST_ID")
        private String brandHostId;

        @JsonProperty("AUTHORIZATION_CODE")
        private String authorizationCode;

        @JsonProperty("YAPE_ID")
        private String yapeId;

        @JsonProperty("CURRENCY")
        private String currency;

        @JsonProperty("TRANSACTION_DATE")
        private String transactionDate;

        @JsonProperty("ACTION_CODE")
        private String actionCode;

        @JsonProperty("ECI")
        private String eci;

        @JsonProperty("ID_RESOLUTOR")
        private String idResolutor;

        @JsonProperty("BRAND")
        private String brand;

        @JsonProperty("ADQUIRENTE")
        private String adquirente;

        @JsonProperty("BRAND_NAME")
        private String brandName;

        @JsonProperty("PROCESS_CODE")
        private String processCode;

        @JsonProperty("TRANSACTION_ID")
        private String transactionId;
    }
}