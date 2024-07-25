package com.universidadadmision.produccion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.universidadadmision.produccion.dto.GeneralDto;
import com.universidadadmision.produccion.entity.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>   {
	@Transactional(readOnly=true)
	@Query(value = "select right('0000000000'+cast((cast(substring(coalesce(max(numeropedido),''),1,10) as int) + 1) as varchar(10)),10) numeropedido \r\n"
			+ " from admision.pedido", nativeQuery = true )
	
	public GeneralDto GeneraNumeroPedido();
}
