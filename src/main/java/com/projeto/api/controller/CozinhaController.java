package com.projeto.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.api.assembler.CozinhaDTOAssembler;
import com.projeto.api.assembler.CozinhaInputDisassembler;
import com.projeto.domain.model.Cozinha;
import com.projeto.domain.model.DTO.CozinhaDTO;
import com.projeto.domain.model.input.CozinhaInput;
import com.projeto.domain.repository.CozinhaRepository;
import com.projeto.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;

	@Autowired
	private CozinhaDTOAssembler cozinhaDTOAssembler;

	@Autowired
	private CozinhaInputDisassembler cozinhaInputDisassembler;

	@GetMapping
	public List<CozinhaDTO> listar() {

		List<Cozinha> cozinhas = cozinhaRepository.findAll();

		return cozinhaDTOAssembler.toCollectionModel(cozinhas);

	}

	@GetMapping(value = "/{cozinhaId}")
	public CozinhaDTO buscar(@PathVariable Long cozinhaId) {

		Cozinha cozinha = cadastroCozinhaService.buscarOuFalhar(cozinhaId);

		return cozinhaDTOAssembler.toModel(cozinha);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaDTO adicionar(@RequestBody @Valid CozinhaInput cozinhaInput) {

		Cozinha cozinha = cozinhaInputDisassembler.toDomainObject(cozinhaInput);

		cozinha = cadastroCozinhaService.salvar(cozinha);

		return cozinhaDTOAssembler.toModel(cozinha);

	}

	@PutMapping(value = "/{cozinhaId}")
	public CozinhaDTO atualizar(@PathVariable Long cozinhaId, @RequestBody @Valid CozinhaInput cozinhaInput) {
		
		Cozinha cozinhaAtual = cadastroCozinhaService.buscarOuFalhar(cozinhaId);
		
		 cozinhaInputDisassembler.copyToDomainObject(cozinhaInput, cozinhaAtual);
		 
		 cadastroCozinhaService.salvar(cozinhaAtual);

		//BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");

		return cozinhaDTOAssembler.toModel(cozinhaAtual);

	}

	@DeleteMapping(value = "/{cozinhaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cozinhaId) {
		cadastroCozinhaService.excluir(cozinhaId);

	}
}
