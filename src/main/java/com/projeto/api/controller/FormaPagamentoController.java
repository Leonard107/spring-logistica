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

import com.projeto.domain.exception.EntidadeEmUsoException;
import com.projeto.domain.exception.EntidadeNaoEncontradaException;
import com.projeto.domain.model.FormaPagamento;
import com.projeto.domain.repository.FormaPagamentoRepository;
import com.projeto.domain.service.CadastroFormaPagamentoService;

@RestController
@RequestMapping("/formadepagamentos")
public class FormaPagamentoController {

	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;
	
	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamentoService;


	@GetMapping
	public List<FormaPagamento> listar() {
		 return formaPagamentoRepository.findAll();
		
	}

	@GetMapping(value = "/{pagamentoId}")
	public ResponseEntity<FormaPagamento> buscar(@PathVariable Long pagamentoId) {
		Optional<FormaPagamento> formaPagamento = formaPagamentoRepository.findById(pagamentoId);

		if (formaPagamento.isPresent()) {
			return ResponseEntity.ok(formaPagamento.get());

		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public FormaPagamento adicionar(@RequestBody FormaPagamento formaPagamento) {
		return cadastroFormaPagamentoService.salvar(formaPagamento);
	}

	@PutMapping(value = "/{formaPagamentoId}")
	public ResponseEntity<FormaPagamento> atualizar(@PathVariable Long formaPagamentoId, @RequestBody FormaPagamento formaPagamento) {

		Optional<FormaPagamento> formpagamentoAtual = formaPagamentoRepository.findById(formaPagamentoId);

		if (formpagamentoAtual.isPresent()) {

			BeanUtils.copyProperties(formaPagamento, formpagamentoAtual.get(), "id");

			FormaPagamento formaPagamentoSalvo = cadastroFormaPagamentoService.salvar(formpagamentoAtual.get());

			return ResponseEntity.ok(formaPagamentoSalvo);

		}

		return ResponseEntity.notFound().build();

	}

	@DeleteMapping(value = "/{formaPagamentoId}")
	public ResponseEntity<FormaPagamento> remover(@PathVariable Long formaPagamentoId) {
		
		try {
			
			cadastroFormaPagamentoService.excluir(formaPagamentoId);
			
			return ResponseEntity.noContent().build();
			
		} catch (EntidadeNaoEncontradaException e) {
			
			return ResponseEntity.notFound().build();
			
		} catch (EntidadeEmUsoException e) {
			
			return ResponseEntity.status(HttpStatus.CONFLICT).build();

		}
	}
}
