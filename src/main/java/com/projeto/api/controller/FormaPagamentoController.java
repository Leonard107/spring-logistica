package com.projeto.api.controller;

import java.util.List;
import java.util.Optional;

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

import com.projeto.api.assembler.FormaPagamentoDTOAssembler;
import com.projeto.api.assembler.FormaPagamentoInputDisassembler;
import com.projeto.domain.exception.EntidadeEmUsoException;
import com.projeto.domain.exception.EntidadeNaoEncontradaException;
import com.projeto.domain.model.FormaPagamento;
import com.projeto.domain.model.DTO.FormaPagamentoDTO;
import com.projeto.domain.model.input.FormaPagamentoInput;
import com.projeto.domain.repository.FormaPagamentoRepository;
import com.projeto.domain.service.CadastroFormaPagamentoService;

@RestController
@RequestMapping("/formadepagamentos")
public class FormaPagamentoController {

	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;

	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamentoService;

	@Autowired
	private FormaPagamentoDTOAssembler formaPagamentoDTOAssembler;

	@Autowired
	private FormaPagamentoInputDisassembler formaPagamentoInputDisassembler;

	@GetMapping
	public List<FormaPagamentoDTO> listar() {

		List<FormaPagamento> todasformasPagamentos = formaPagamentoRepository.findAll();

		return formaPagamentoDTOAssembler.toCollectionDTO(todasformasPagamentos);

	}

	@GetMapping(value = "/{pagamentoId}")
	public FormaPagamentoDTO buscar(@PathVariable Long pagamentoId) {

		FormaPagamento formaPagamento = cadastroFormaPagamentoService.buscarOuFalhar(pagamentoId);
		formaPagamentoDTOAssembler.toDTO(formaPagamento);

		return formaPagamentoDTOAssembler.toDTO(formaPagamento);

	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public FormaPagamentoDTO adicionar(@RequestBody FormaPagamentoInput formaPagamentoInput) {

		FormaPagamento formaDePagamento = formaPagamentoInputDisassembler.toDomainObject(formaPagamentoInput);

		formaDePagamento = cadastroFormaPagamentoService.salvar(formaDePagamento);

		return formaPagamentoDTOAssembler.toDTO(formaDePagamento);
	}

	@PutMapping(value = "/{formaPagamentoId}")
	public FormaPagamentoDTO atualizar(@PathVariable Long formaPagamentoId,
			@RequestBody FormaPagamentoInput formaPagamentoInput) {
		
		FormaPagamento formpagamentoAtual = cadastroFormaPagamentoService.buscarOuFalhar(formaPagamentoId);

		formaPagamentoInputDisassembler.copyToDomainObject(formaPagamentoInput, formpagamentoAtual);
		
		formpagamentoAtual = cadastroFormaPagamentoService.salvar(formpagamentoAtual);

		return formaPagamentoDTOAssembler.toDTO(formpagamentoAtual);

	}

	@DeleteMapping(value = "/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long formaPagamentoId) {
			cadastroFormaPagamentoService.excluir(formaPagamentoId);
	}
}
