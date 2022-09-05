package com.projeto.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.projeto.domain.model.Cozinha;
import com.projeto.domain.model.Restaurante;
import com.projeto.domain.model.input.RestauranteInput;

@Component
public class RestauranteInputDisassembler {
	
	@Autowired
	private ModelMapper moldeMapper;
	
	public Restaurante toDomainObject(RestauranteInput restauranteInput) {
		return moldeMapper.map(restauranteInput, Restaurante.class);
	}

}
