package com.universidadadmision.produccion.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.universidadadmision.produccion.dto.GeneralDto;
import com.universidadadmision.produccion.dto.PedidoDtoR;
import com.universidadadmision.produccion.entity.Pedido;
import com.universidadadmision.produccion.service.PedidoService;

@RestController
@CrossOrigin
@RequestMapping("/pedido")
@Validated
public class PedidoController {
	@Autowired
	private PedidoService pedidoservice;

	@PostMapping("/nuevo")
	public ResponseEntity<?> NuevoPedido(@RequestBody PedidoDtoR pedidodto) throws Exception {
		Map<String, Object> response = new HashMap<>();
		GeneralDto numeropedido = pedidoservice.generapedido();

		Pedido pedido = new Pedido();
		pedido.setNumero(numeropedido.getNumeropedido());
		pedido.setPostulanteid(pedidodto.getIdpostulante());
		pedidoservice.save(pedido);

		response.put("numeropedido", pedido.getNumero());
		response.put("monto", 200);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/prueba")
	public ResponseEntity<?> Prueba() throws Exception {
		System.out.println("Entrando...");
		return ResponseEntity.ok("response");
	}
}
