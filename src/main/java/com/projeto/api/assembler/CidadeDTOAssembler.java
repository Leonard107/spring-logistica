package com.projeto.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.projeto.domain.model.Cidade;
import com.projeto.domain.model.DTO.CidadeDTO;

@Component
public class CidadeDTOAssembler {
	
	@Autowired
    private ModelMapper modelMapper;
    
    public CidadeDTO toModel(Cidade cidade) {
        return modelMapper.map(cidade, CidadeDTO.class);
    }
    
    public List<CidadeDTO> toCollectionModel(List<Cidade> cidades) {
        return cidades.stream()
                .map(cidade -> toModel(cidade))
                .collect(Collectors.toList());
    }

}
