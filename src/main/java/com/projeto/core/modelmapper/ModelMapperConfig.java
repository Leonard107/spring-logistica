package com.projeto.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.projeto.domain.model.Endereco;
import com.projeto.domain.model.Restaurante;
import com.projeto.domain.model.DTO.EnderecoDTO;
import com.projeto.domain.model.DTO.RestauranteDTO;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();

		//modelMapper.createTypeMap(Restaurante.class, RestauranteDTO.class);
		//		.addMapping(Restaurante::getTaxaFrete, RestauranteModel::setPrecoFrete);

		var enderecoToEnderecoModelTypeMap = modelMapper.createTypeMap(Endereco.class, EnderecoDTO.class);

		enderecoToEnderecoModelTypeMap.<String>addMapping(
				origemEndereco -> origemEndereco.getCidade().getEstado().getNome(),
				(destinoEnderecoDTO, valor) -> destinoEnderecoDTO.getCidade().setEstado(valor));

		return modelMapper;
	}

}
