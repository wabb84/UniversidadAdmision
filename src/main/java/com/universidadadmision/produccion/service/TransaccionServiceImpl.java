package com.universidadadmision.produccion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.universidadadmision.produccion.entity.Transaccion;
import com.universidadadmision.produccion.repository.TransaccionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransaccionServiceImpl implements TransaccionService {

    @Autowired
    private TransaccionRepository transaccionRepository;

    @Override
    public void guardarTransaccion(Transaccion transaccion) {
        transaccionRepository.save(transaccion);

    }

    @Override
    public Transaccion findByPurchaseNumber(String purchaseNumber) {
        return transaccionRepository.findByPurchaseNumber(purchaseNumber);
    }
}
