package com.projeto.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.domain.exception.EntidadeNaoEncontradaException;
import com.projeto.domain.exception.RestauranteNaoEncontradoException;
import com.projeto.domain.model.Cozinha;
import com.projeto.domain.model.Restaurante;
import com.projeto.domain.repository.CozinhaRepository;
import com.projeto.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {
	
	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;

	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		Cozinha cozinha = cadastroCozinhaService.buscarOuFalhar(cozinhaId);

		restaurante.setCozinha(cozinha);

		return restauranteRepository.save(restaurante);

	}

	public Restaurante buscarOuFalhar(long restauranteId) {
		return restauranteRepository.findById(restauranteId).
				orElseThrow(() -> new RestauranteNaoEncontradoException(restauranteId));

	}
}
