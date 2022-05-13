package com.projeto.jpa;

import java.util.Optional;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.projeto.Application;
import com.projeto.domain.model.Cozinha;
import com.projeto.domain.repository.CozinhaRepository;

public class BuscaCozinhaMain {
	
	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(Application.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		CozinhaRepository cozinhaRepository =  applicationContext.getBean(CozinhaRepository.class);
		
		
		
		Optional<Cozinha> cozinhas = cozinhaRepository.findById(1L);
		
			System.out.println(cozinhas.get());
		
	}

}
