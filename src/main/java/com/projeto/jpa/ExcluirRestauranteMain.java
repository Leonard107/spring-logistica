package com.projeto.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.projeto.Application;
import com.projeto.domain.model.Cozinha;
import com.projeto.domain.model.Restaurante;
import com.projeto.domain.repository.CozinhaRepository;
import com.projeto.domain.repository.RestauranteRepository;

public class ExcluirRestauranteMain {
	
	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(Application.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		RestauranteRepository cozinhaRepository =  applicationContext.getBean(RestauranteRepository.class);
		
		Restaurante restaurante = new Restaurante();
		restaurante.setId(1L);
		
		
		cozinhaRepository.remover(restaurante);


	}

}
