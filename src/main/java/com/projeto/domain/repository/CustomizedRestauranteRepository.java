package com.projeto.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import com.projeto.domain.model.Restaurante;

public interface CustomizedRestauranteRepository {

	List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);

}
