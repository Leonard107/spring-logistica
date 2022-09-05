package com.projeto.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.projeto.domain.model.Restaurante;
import com.projeto.domain.model.DTO.RestauranteDTO;

@Component
public class RestauranteDTOAssembler {
	
	@Autowired
	private ModelMapper moldeMapper;
	
	public RestauranteDTO toDTO(Restaurante restaurante) {
		return moldeMapper.map(restaurante, RestauranteDTO.class);
	}
	
	public List<RestauranteDTO> toCollectionDTO(List<Restaurante> restaurantes){
		return restaurantes.stream()
				.map(restaurante -> toDTO(restaurante))
				.collect(Collectors.toList());
	}

}
