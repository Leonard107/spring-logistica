package com.projeto.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.projeto.Application;
import com.projeto.domain.model.Cozinha;
import com.projeto.domain.repository.CozinhaRepository;

public class ExcluirCozinhaMain {
	
	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(Application.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		CozinhaRepository cozinhaRepository =  applicationContext.getBean(CozinhaRepository.class);
		
		Cozinha cozinha = new Cozinha();
			
		cozinhaRepository.deleteById(cozinha.getId());;

	}

}
