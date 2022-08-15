package com.projeto;

import org.flywaydb.core.Flyway;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.projeto.domain.model.Cozinha;
import com.projeto.domain.repository.CozinhaRepository;
import com.projeto.util.DatabaseCleaner;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static org.hamcrest.Matchers.equalTo;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
class CadastroCozinhaIT {
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private DatabaseCleaner dataBaseCleaner;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@BeforeEach
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/cozinhas";
		
		dataBaseCleaner.clearTables();
		
		prepararDados();
				
	}
		
	@Test
	public void deveRetornarStatus200_QuandoConsultarCozinhas() {
				
		RestAssured.given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());	
	}
	
	@Test
	public void deveConter2Cozinhas_QuandoConsultarCozinhas() {
				
		RestAssured.given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()	
			.body("", Matchers.hasSize(2))
			.body("nome", Matchers.hasItems("Tailandesa","Americana"));
	}
	
	@Test
	public void deveRetornarStatus201_QuandoCadastrarCOzinha() {
		
		RestAssured.given()
			.body("{ \"nome\": \"Chinesa\"  }")
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
	
	private void prepararDados() {
		Cozinha cozinha = new Cozinha();
		cozinha.setNome("Tailandesa");
		cozinhaRepository.save(cozinha);
		
		Cozinha cozinha2 = new Cozinha();
		cozinha2.setNome("Americana");
		cozinhaRepository.save(cozinha2);
	}
	
		
	@Test
	public void deveRetornarRespostaStatusCorretos_QuandoConsultarCozinhaExistente() {
		
		RestAssured.given()
		.pathParam("CozinhaId", 2)
		.accept(ContentType.JSON)
	.when()
		.get("/{CozinhaId}")
	.then()	
		.statusCode(HttpStatus.OK.value())
		.body("nome", equalTo("Americana"));
	}
	
	@Test
	public void deveRetornarStatus404_QuandoConsultarCozinhaInexistente() {
		
		RestAssured.given()
		.pathParam("CozinhaId", 100)
		.accept(ContentType.JSON)
	.when()
		.get("/{CozinhaId}")
	.then()	
		.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	
}
