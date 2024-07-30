package com.universidadadmision.produccion.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class TransaccionDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String ecoreTransactionUUID;
    private Long ecoreTransactionDate;
    private double millis;
    private String channel;
    private String merchantId;
    private String terminalId;
    private String captureType;
    private boolean countable;
    private boolean fastPayment;
    private String purchaseNumber;
    private Integer installment;
    private double authorizedAmount;
    private String TERMINAL;
    private String BRAND_ACTION_CODE;
    private String BRAND_HOST_DATE_TIME;
    private String TRACE_NUMBER;
    private String CARD_TYPE;
    private String ECI_DESCRIPTION;
    private String CARD;
    private String MERCHANT;
    private String STATUS;
    private String ACTION_DESCRIPTION;
    private String ID_UNICO;
    private String AMOUNT;
    private String BRAND_HOST_ID;
    private String AUTHORIZATION_CODE;
    private String YAPE_ID;
    private String CURRENCY;
    private String TRANSACTION_DATE;
    private String ACTION_CODE;
    private String ECI;
    private String ID_RESOLUTOR;
    private String BRAND;
    private String ADQUIRENTE;
    private String BRAND_NAME;
    private String PROCESS_CODE;
    private String TRANSACTION_ID;

}
