package com.universidadadmision.produccion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.universidadadmision.produccion.entity.Transaccion;

@Repository
public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {
    public Transaccion findByPurchaseNumber(String purchaseNumber);
}