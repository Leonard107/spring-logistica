package com.projeto.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.domain.model.Restaurante;
import com.projeto.domain.repository.RestauranteRepository;

@RestController
@RequestMapping("restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@GetMapping
	public List<Restaurante> Listar() {
		return restauranteRepository.listar();
	}

	@GetMapping(value = "/{restauranteId}")
	public ResponseEntity<Restaurante> buscar(@PathVariable Long restauranteId) {

		Restaurante restaurante = restauranteRepository.buscar(restauranteId);

		if (restaurante != null) {
			return ResponseEntity.ok(restaurante);

		}

		return ResponseEntity.notFound().build();
	}

}
