package com.projeto.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.projeto.domain.model.Restaurante;

@Repository
public interface RestauranteRepository extends CustomJpaRepository<Restaurante, Long>, CustomizedRestauranteRepository,
JpaSpecificationExecutor<Restaurante>{
	
	@Query("from Restaurante r join fetch r.cozinha left join fetch r.formasPagamento")
	List<Restaurante> findAll();
	
	List<Restaurante> findBytaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);
	
	//@Query("from Restaurante where nome like %:nome% and cozinha.id = :id")
	List<Restaurante> consultarPorNome(String nome, @Param("id") Long cozinhaId);
	

	//List<Restaurante> findByNomeContainingAndCozinhaId(String nome, Long cozinhaId);

}
