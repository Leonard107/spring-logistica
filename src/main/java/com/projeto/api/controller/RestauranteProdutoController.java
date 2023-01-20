package com.projeto.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.api.assembler.ProdutoDTOAssembler;
import com.projeto.api.assembler.ProdutoInputDisassembler;
import com.projeto.domain.model.Produto;
import com.projeto.domain.model.Restaurante;
import com.projeto.domain.model.DTO.ProdutoDTO;
import com.projeto.domain.model.input.ProdutoInput;
import com.projeto.domain.repository.ProdutoRepository;
import com.projeto.domain.service.CadastroProdutoService;
import com.projeto.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CadastroProdutoService cadastroProdutoService;
	
	@Autowired
	private CadastroRestauranteService cadastroRestauranteService;
	
	@Autowired
	private ProdutoDTOAssembler produtoDTOAssembler;
	
	@Autowired
	private ProdutoInputDisassembler produtoInputDisassembler;
	
	@GetMapping
	public List<ProdutoDTO> listar(@PathVariable Long restauranteId){
		Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);
		
		 List<Produto> todosProtudos = produtoRepository.findByRestaurante(restaurante);
		 
		return produtoDTOAssembler.toCollectionDTO(todosProtudos);
	}
	
	@GetMapping("/{produtoId}")
	public ProdutoDTO buscar(@PathVariable Long restauranteId, @PathVariable long produtoId) {
		
		Produto produto = cadastroProdutoService.buscarOuFalhar(restauranteId, restauranteId);
		
		return produtoDTOAssembler.toDTO(produto);
		
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProdutoDTO adicionar(@PathVariable Long restauranteId, @RequestBody @Valid ProdutoInput produtoInput) {
		
		Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);
		Produto produto = produtoInputDisassembler.toDomainObject(produtoInput);
		produto.setRestaurante(restaurante);
		
		produto =  cadastroProdutoService.salvar(produto);
		return produtoDTOAssembler.toDTO(produto);
	}
	
	@PutMapping("/{produtoId}")
	public ProdutoDTO atualizar(@PathVariable Long restauranteId, @PathVariable Long produtoId, @RequestBody ProdutoInput produtoInput) {
		
		Produto produtoAtual = cadastroProdutoService.buscarOuFalhar(restauranteId, produtoId);
		
		produtoInputDisassembler.copyToDomainObject(produtoInput, produtoAtual);
		
		produtoAtual = cadastroProdutoService.salvar(produtoAtual);
			
		return produtoDTOAssembler.toDTO(produtoAtual);
	}

}
