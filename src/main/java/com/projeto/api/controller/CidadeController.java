package com.projeto.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
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

import com.projeto.api.assembler.CidadeDTOAssembler;
import com.projeto.api.assembler.CidadeInputDisassembler;
import com.projeto.domain.exception.EstadoNaoEncontradoException;
import com.projeto.domain.exception.NegocioException;
import com.projeto.domain.model.Cidade;
import com.projeto.domain.model.DTO.CidadeDTO;
import com.projeto.domain.model.input.CidadeInput;
import com.projeto.domain.repository.CidadeRepository;
import com.projeto.domain.service.CadastroCidadeService;

@RestController
@RequestMapping("cidades")
public class CidadeController {

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private CadastroCidadeService cadastroCidadeService;

	@Autowired
	CidadeDTOAssembler cidadeDTOAssembler;

	@Autowired
	CidadeInputDisassembler cidadeInputDisassembler;

	@GetMapping
	public List<CidadeDTO> listar() {

		List<Cidade> cidades = cidadeRepository.findAll();

		return cidadeDTOAssembler.toCollectionModel(cidades);
	}

	@GetMapping(value = "/{cidadeId}")
	public CidadeDTO buscar(@PathVariable Long cidadeId) {

		Cidade cidade = cadastroCidadeService.buscarOuFalhar(cidadeId);

		return cidadeDTOAssembler.toModel(cidade);

	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeDTO adicionar(@RequestBody @Valid CidadeInput cidadeInput) {
		try {
			Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);

			cidade = cadastroCidadeService.salvar(cidade);

			return cidadeDTOAssembler.toModel(cidade);
		} catch (EstadoNaoEncontradoException  e) {
			throw new NegocioException(e.getMessage(), e);
		}

	}

	@PutMapping(value = "/{cidadeId}")
	public CidadeDTO atualizar(@PathVariable Long cidadeId, @RequestBody @Valid CidadeInput cidadeInput) {
		try {
			
			
			Cidade cidadeAtual = cadastroCidadeService.buscarOuFalhar(cidadeId);
			
			cidadeInputDisassembler.copyToDomainObject(cidadeInput, cidadeAtual);

			//BeanUtils.copyProperties(cidade, cidadeAtual, "id");
			
	        cidadeAtual = cadastroCidadeService.salvar(cidadeAtual);

			
			return cidadeDTOAssembler.toModel(cidadeAtual);
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@DeleteMapping("/{cidadeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cidadeId) {

		cadastroCidadeService.excluir(cidadeId);

	}

}
