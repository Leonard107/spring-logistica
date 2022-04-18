package com.projeto.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

import com.projeto.domain.model.Estado;
import com.projeto.domain.repository.EstadoRepository;

@Component
public class EstadoRepositoryImpl implements EstadoRepository  {
	
	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Estado> listar() {
		return manager.createQuery("from Estado", Estado.class).getResultList();
	}

}
