package com.universidadadmision.produccion.service;

import com.universidadadmision.produccion.dto.GeneralDto;
import com.universidadadmision.produccion.entity.Pedido;

public interface PedidoService {
	public Pedido save( Pedido postulantes);
	public GeneralDto generapedido();
}
