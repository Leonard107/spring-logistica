package com.projeto.domain.model.DTO;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestauranteDTO {
	
	private Long id;
	private String nome;
	private BigDecimal precoFrete;
	private CozinhaDTO cozinha;

}
