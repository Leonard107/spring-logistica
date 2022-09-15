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

import com.projeto.api.assembler.EstadoDTOAssembler;
import com.projeto.api.assembler.EstadoInputDisassembler;
import com.projeto.domain.model.Estado;
import com.projeto.domain.model.DTO.EstadoDTO;
import com.projeto.domain.model.input.EstadoInput;
import com.projeto.domain.repository.EstadoRepository;
import com.projeto.domain.service.CadastroEstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController {

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private CadastroEstadoService cadastroEstadoService;
	
	@Autowired
	private EstadoDTOAssembler estadoDTOAssembler;
	
	@Autowired
	private EstadoInputDisassembler estadoInputDisassembler;

	@GetMapping
	public List<EstadoDTO> listar() {
		List<Estado> todosEstados = estadoRepository.findAll();
		
		return estadoDTOAssembler.toCollectionDTO(todosEstados);
	}
	

	@GetMapping(value = "/{estadoId}")
	public EstadoDTO buscar(@PathVariable Long estadoId) {
		 Estado estado = cadastroEstadoService.buscarOufalhar(estadoId);
		 
		 return estadoDTOAssembler.toDTO(estado);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EstadoDTO adicionar(@RequestBody @Valid EstadoInput estadoInput) {
		   Estado estado = estadoInputDisassembler.toDomainObject(estadoInput);
		   
		    estado = cadastroEstadoService.salvar(estado);
		    
		    return estadoDTOAssembler.toDTO(estado);
		 
	}

	@PutMapping(value = "/{estadoId}")
	public EstadoDTO atualizar(@PathVariable Long estadoId, @RequestBody @Valid EstadoInput estadoInput) {

		Estado estadoAtual = cadastroEstadoService.buscarOufalhar(estadoId);
		
		estadoInputDisassembler.copyToDomainObject(estadoInput, estadoAtual);
		//BeanUtils.copyProperties(estado, estadoAtual, "id");

		 estadoAtual = cadastroEstadoService.salvar(estadoAtual);
		 
		 return estadoDTOAssembler.toDTO(estadoAtual);
	}

	@DeleteMapping(value = "/{estadoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long estadoId) {
		cadastroEstadoService.excluir(estadoId);
	}
}
