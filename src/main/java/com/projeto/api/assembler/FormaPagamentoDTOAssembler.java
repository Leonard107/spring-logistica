package com.projeto.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.projeto.domain.model.FormaPagamento;
import com.projeto.domain.model.DTO.FormaPagamentoDTO;

@Component
public class FormaPagamentoDTOAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public FormaPagamentoDTO toDTO(FormaPagamento formaPagamento) {
		return modelMapper.map(formaPagamento, FormaPagamentoDTO.class);
	}
	
	public List<FormaPagamentoDTO> toCollectionDTO(List<FormaPagamento> formasPagamentos){
		return formasPagamentos.stream()
				.map(formaPagamento -> toDTO(formaPagamento))
				.collect(Collectors.toList());
	}

}
