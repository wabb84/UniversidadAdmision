package com.universidadadmision.produccion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.universidadadmision.produccion.dto.GeneralDto;
import com.universidadadmision.produccion.entity.Pedido;
import com.universidadadmision.produccion.repository.PedidoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {
	@Autowired
	private PedidoRepository pedidorep;

	@Transactional
	@Override
	public Pedido save(Pedido pedido) {
		return pedidorep.save( pedido );
	}
	
	@Transactional
	@Override
	public GeneralDto generapedido() {
		return pedidorep.GeneraNumeroPedido();
	}
}
