package com.projeto.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.projeto.domain.model.Usuario;
import com.projeto.domain.model.input.UsuarioInput;

@Component
public class UsuarioInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Usuario toDOmainObejct(UsuarioInput usuarioInput) {
		return modelMapper.map(usuarioInput, Usuario.class);
	}
	
	public void copyToDomainObject(UsuarioInput usuarioInput, Usuario usuario) {
		modelMapper.map(usuarioInput, usuario);
	}

}
