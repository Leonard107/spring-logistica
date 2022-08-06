package com.projeto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

import javax.validation.ConstraintViolationException;

import com.projeto.domain.model.Cozinha;
import com.projeto.domain.service.CadastroCozinhaService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ApplicationTests {
	
	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;
	
	@Test
	public void testarCadastroCozinhaComSucesso() {
		
		//Cenário
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome("Chinesa");

		//Ação
		novaCozinha = cadastroCozinhaService.salvar(novaCozinha);
		
		//Validação
		assertThat(novaCozinha).isNotNull();
		assertThat(novaCozinha.getId()).isNotNull();
		
	}
	
	@Test
	public void testarCadastroCozinhaSemNome() {
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome(null);
		ConstraintViolationException erroEsperado = 
				Assertions.assertThrows(ConstraintViolationException.class, () -> {
					cadastroCozinhaService.salvar(novaCozinha);
				});
		
		assertThat(erroEsperado).isNotNull();
	}
	
	

}
