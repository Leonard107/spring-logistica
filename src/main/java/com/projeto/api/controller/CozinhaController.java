package com.projeto.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.domain.exception.EntidadeEmUsoException;
import com.projeto.domain.exception.EntidadeNaoEncontradaException;
import com.projeto.domain.model.Cozinha;
import com.projeto.domain.repository.CozinhaRepository;
import com.projeto.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;

	@GetMapping
	public List<Cozinha> listar() {
		return cozinhaRepository.listar();
	}

	@GetMapping(value = "/{cozinhaId}")
	public ResponseEntity<Cozinha> buscar(@PathVariable Long cozinhaId) {
		Cozinha cozinha = cozinhaRepository.buscar(cozinhaId);

		if (cozinha != null) {
			return ResponseEntity.ok(cozinha);
		}

		// return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		return ResponseEntity.notFound().build();

	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cozinha adicionar(@RequestBody Cozinha cozinha) {
		return cadastroCozinhaService.salvar(cozinha);
	}

	@PutMapping(value = "/{cozinhaId}")
	public ResponseEntity<Cozinha> atualizar(@PathVariable Long cozinhaId, @RequestBody Cozinha cozinha) {

		Cozinha cozinhaAtual = cozinhaRepository.buscar(cozinhaId);

		if (cozinhaAtual != null) {

			// cozinhaAtual.setNome(cozinha.getNome());
			BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");

			cozinhaAtual = cadastroCozinhaService.salvar(cozinhaAtual);

			return ResponseEntity.ok(cozinhaAtual);

		}

		return ResponseEntity.notFound().build();

	}

	@DeleteMapping(value = "/{cozinhaId}")
	public ResponseEntity<Cozinha> remover(@PathVariable Long cozinhaId) {

		try {

			cadastroCozinhaService.excluir(cozinhaId);

			return ResponseEntity.noContent().build();

		} catch (EntidadeNaoEncontradaException e) {

			return ResponseEntity.notFound().build();

		} catch (EntidadeEmUsoException e) {

			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
}
