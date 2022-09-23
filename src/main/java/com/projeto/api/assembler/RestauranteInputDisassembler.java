package com.projeto.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.projeto.domain.model.Cidade;
import com.projeto.domain.model.Cozinha;
import com.projeto.domain.model.Endereco;
import com.projeto.domain.model.Restaurante;
import com.projeto.domain.model.input.RestauranteInput;

@Component
public class RestauranteInputDisassembler {
	
	@Autowired
	private ModelMapper moldeMapper;
	
	public Restaurante toDomainObject(RestauranteInput restauranteInput) {
		return moldeMapper.map(restauranteInput, Restaurante.class);
	}
	
	public void copyToDomainObejct(RestauranteInput restauranteInput, Restaurante restaurante) {
		
		// Para evitar org.hibernate.HibernateException
		restaurante.setCozinha(new Cozinha());
		
		if(restaurante.getEndereco() != null) {
			restaurante.getEndereco().setCidade(new Cidade());
		}
		
		
		moldeMapper.map(restauranteInput, restaurante);
	}

}
