package com.projeto.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.projeto.domain.model.Permissao;
import com.projeto.domain.model.DTO.PermissaoDTO;

@Component
public class PermissaoDTOAssembler {
	
	@Autowired
    private ModelMapper modelMapper;
    
    public PermissaoDTO toModel(Permissao permissao) {
        return modelMapper.map(permissao, PermissaoDTO.class);
    }
    
    public List<PermissaoDTO> toCollectionModel(Collection<Permissao> permissaos) {
        return permissaos.stream()
                .map(permissao -> toModel(permissao))
                .collect(Collectors.toList());
    }

}
