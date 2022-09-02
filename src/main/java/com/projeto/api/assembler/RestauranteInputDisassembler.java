package com.projeto.api.assembler;

import com.projeto.domain.model.Cozinha;
import com.projeto.domain.model.Restaurante;
import com.projeto.domain.model.input.RestauranteInput;

public class RestauranteInputDisassembler {
	
	public Restaurante toDomainObject(RestauranteInput restauranteInput) {
		Restaurante restaurante = new Restaurante();
		restaurante.setNome(restauranteInput.getNome());
		restaurante.setTaxaFrete(restauranteInput.getTaxaFrete());
		
		Cozinha cozinha = new Cozinha();
		cozinha.setId(restauranteInput.getCozinha().getId());
		
		restaurante.setCozinha(cozinha);
		
		return restaurante;
	}

}
