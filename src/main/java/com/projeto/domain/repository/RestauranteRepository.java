package com.projeto.domain.repository;

import java.util.List;

import com.projeto.domain.model.Restaurante;

public interface RestauranteRepository {
	
	List<Restaurante> listar();
	Restaurante buscar(Long id);
	Restaurante salvar(Restaurante restaurante);
	void remover(Restaurante restaurante);

}
