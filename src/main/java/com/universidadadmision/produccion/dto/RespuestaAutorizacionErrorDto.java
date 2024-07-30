package com.universidadadmision.produccion.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RespuestaAutorizacionErrorDto  implements Serializable  {
    private static final long serialVersionUID = 1L;
    
    private Integer errorCode;
    private String errorMessage;
    private HeaderDto header;
    private DataDto data;

    @Getter
    @Setter
    public static class HeaderDto {
        private String ecoreTransactionUUID;
        private Long ecoreTransactionDate;
        private Double millis;
    }

    @Getter
    @Setter
    public static class DataDto {
        private String CURRENCY;
        private String TERMINAL;
        private String TRANSACTION_DATE;
        private String BRAND_ACTION_CODE;
        private String BRAND_HOST_DATE_TIME;
        private String ACTION_CODE;
        private String TRACE_NUMBER;
        private String CVV2_VALIDATION_RESULT;
        private String CARD_TYPE;
        private String ECI_DESCRIPTION;
        private String ECI;
        private String SIGNATURE;
        private String CARD;
        private String MERCHANT;
        private String BRAND;
        private String STATUS;
        private String ACTION_DESCRIPTION;
        private String ADQUIRENTE;
        private String ID_UNICO;
        private String AMOUNT;
        private String BRAND_NAME;
        private String PROCESS_CODE;
        private String BRAND_HOST_ID;
        private String TRANSACTION_ID;
    }
}
