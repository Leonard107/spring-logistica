package com.projeto.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.domain.exception.EntidadeNaoEncontradaException;
import com.projeto.domain.model.Cozinha;
import com.projeto.domain.model.Restaurante;
import com.projeto.domain.repository.RestauranteRepository;
import com.projeto.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CadastroRestauranteService cadastroRestauranteService;

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

	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante) {
		try {
			restaurante = cadastroRestauranteService.salvar(restaurante);

			return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	@PutMapping(value = "/{restauranteId}")
	public ResponseEntity<?> atualizar(@PathVariable Long restauranteId, 
			@RequestBody Restaurante restaurante) {
		try {

			Restaurante restauranteAtual = restauranteRepository.buscar(restauranteId);

			if (restauranteAtual != null) {
				BeanUtils.copyProperties(restaurante, restauranteAtual, "id");
				
				restauranteAtual = cadastroRestauranteService.salvar(restauranteAtual);

				return ResponseEntity.ok(restauranteAtual);
			}
			
			return ResponseEntity.notFound().build();

		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
