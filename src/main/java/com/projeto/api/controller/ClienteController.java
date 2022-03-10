package com.projeto.api.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.domain.model.Cliente;

@RestController
public class ClienteController {
	
	@GetMapping("/clientes")
	public List<Cliente> Listar() {
		Cliente cliente1 = new Cliente();
		
		cliente1.setId(1L);
		cliente1.setNome("Leonard");
		cliente1.setTelefone("99247-1050");
		cliente1.setEmail("leonardrichard107@gmail.com");
		
		Cliente cliente2 = new Cliente();
		
		cliente2.setId(2L);
		cliente2.setNome("Carlinhos");
		cliente2.setTelefone("98247-2011");
		cliente2.setEmail("carlinhos@gmail.com");
		
		return Arrays.asList(cliente1, cliente2);
	}

}
