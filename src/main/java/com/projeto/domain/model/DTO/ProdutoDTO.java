package com.projeto.domain.model.DTO;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProdutoDTO {
	
	private Long id;
	private String nome;
	private String descricao;
	private BigDecimal preco;
	private BigDecimal ativo;

}
