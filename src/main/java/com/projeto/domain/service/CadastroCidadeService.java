package com.projeto.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.projeto.domain.exception.EntidadeEmUsoException;
import com.projeto.domain.exception.EntidadeNaoEncontradaException;
import com.projeto.domain.model.Cidade;
import com.projeto.domain.model.Estado;
import com.projeto.domain.repository.CidadeRepository;
import com.projeto.domain.repository.EstadoRepository;

@Service
public class CadastroCidadeService {
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	public Cidade salvar(Cidade cidade) {
		Long estadoId = cidade.getEstado().getId();
		Estado estado = estadoRepository.buscar(estadoId);
		
		if (estado == null) {
			throw new EntidadeNaoEncontradaException(String.format("Não existe cidade com esse código %d ", estadoId));
		}
		
		cidade.setEstado(estado);
		
		return cidadeRepository.salvar(cidade);
	}
	
	public void excluir(Long cidadeId) {
		try {
			cidadeRepository.Remover(cidadeId);
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe um cadastro de cidade com código %d", cidadeId));
		} catch (DataIntegrityViolationException  e) {
			throw new EntidadeEmUsoException(
					String.format("Cidade de código %d não pode ser removida, pois está em uso", cidadeId));
		}
	}

}
