package com.universidadadmision.produccion.service;

import com.universidadadmision.produccion.entity.Transaccion;

public interface TransaccionService {
    public void guardarTransaccion(Transaccion transaccion);
    public Transaccion findByPurchaseNumber(String purchaseNumber);

}
