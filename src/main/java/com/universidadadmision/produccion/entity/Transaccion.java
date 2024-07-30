package com.universidadadmision.produccion.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Transacciones", schema="Admision")
public class Transaccion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", unique=true, nullable=false)
    private Long id;

    @Column(name = "ecore_transaction_uuid")
    private String ecoreTransactionUUID;

    @Column(name = "ecore_transaction_date")
    private Long ecoreTransactionDate;

    @Column(name = "millis")
    private double millis;

    @Column(name = "channel")
    private String channel;

    @Column(name = "merchant_id")
    private String merchantId;

    @Column(name = "terminal_id")
    private String terminalId;

    @Column(name = "capture_type")
    private String captureType;

    @Column(name = "countable")
    private boolean countable;

    @Column(name = "fast_payment")
    private boolean fastPayment;

    @Column(name = "purchase_number")
    private String purchaseNumber;

    @Column(name = "installment")
    private Integer installment;

    @Column(name = "authorized_amount")
    private double authorizedAmount;

    @Column(name = "terminal")
    private String TERMINAL;

    @Column(name = "brand_action_code")
    private String BRAND_ACTION_CODE;

    @Column(name = "brand_host_date_time")
    private String BRAND_HOST_DATE_TIME;

    @Column(name = "trace_number")
    private String TRACE_NUMBER;

    @Column(name = "card_type")
    private String CARD_TYPE;

    @Column(name = "eci_description")
    private String ECI_DESCRIPTION;

    @Column(name = "card")
    private String CARD;

    @Column(name = "merchant")
    private String MERCHANT;

    @Column(name = "status")
    private String STATUS;

    @Column(name = "action_description")
    private String ACTION_DESCRIPTION;

    @Column(name = "id_unico")
    private String ID_UNICO;

    @Column(name = "amount")
    private String AMOUNT;

    @Column(name = "brand_host_id")
    private String BRAND_HOST_ID;

    @Column(name = "authorization_code")
    private String AUTHORIZATION_CODE;

    @Column(name = "yape_id")
    private String YAPE_ID;

    @Column(name = "currency")
    private String CURRENCY;

    @Column(name = "transaction_date")
    private String TRANSACTION_DATE;

    @Column(name = "action_code")
    private String ACTION_CODE;

    @Column(name = "eci")
    private String ECI;

    @Column(name = "id_resolutor")
    private String ID_RESOLUTOR;

    @Column(name = "brand")
    private String BRAND;

    @Column(name = "adquirente")
    private String ADQUIRENTE;

    @Column(name = "brand_name")
    private String BRAND_NAME;

    @Column(name = "process_code")
    private String PROCESS_CODE;

    @Column(name = "transaction_id")
    private String TRANSACTION_ID;
}