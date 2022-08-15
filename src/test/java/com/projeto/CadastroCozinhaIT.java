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
import com.projeto.util.ResourceUtils;

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
	
	private static final int COZINHA_ID_INEXISTENTE = 100;
	
	private Cozinha cozinhaAmericana;
	private int quantidadeCozinhasCadastradas;
	private String jsonCorretoCozinhaChinesa;

	
	@BeforeEach
	public void setUp() {
		
		jsonCorretoCozinhaChinesa = ResourceUtils.getContentFromResource(
				"/json/correto/cozinha-chinesa.json");
		
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/cozinhas";
		
		dataBaseCleaner.clearTables();
		
		prepararDados();
				
	}
	
	private void prepararDados() {
		Cozinha cozinhaTailandesa = new Cozinha();
		cozinhaTailandesa.setNome("Tailandesa");
		cozinhaRepository.save(cozinhaTailandesa);
		
		cozinhaAmericana = new Cozinha();
		cozinhaAmericana.setNome("Americana");
		cozinhaRepository.save(cozinhaAmericana);
		
	    quantidadeCozinhasCadastradas = (int) cozinhaRepository.count();

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
	public void deveRetornarQUantidadeCorretaCozinhas_QuandoConsultarCozinhas() {
				
		RestAssured.given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()	
			.body("", Matchers.hasSize(quantidadeCozinhasCadastradas));
	}
	
	@Test
	public void deveRetornarStatus201_QuandoCadastrarCOzinha() {
		
		RestAssured.given()
			.body(jsonCorretoCozinhaChinesa)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
		
	@Test
	public void deveRetornarRespostaStatusCorretos_QuandoConsultarCozinhaExistente() {
		
		RestAssured.given()
		.pathParam("CozinhaId", cozinhaAmericana.getId())
		.accept(ContentType.JSON)
	.when()
		.get("/{CozinhaId}")
	.then()	
		.statusCode(HttpStatus.OK.value())
		.body("nome", equalTo(cozinhaAmericana.getNome()));
	}
	
	@Test
	public void deveRetornarStatus404_QuandoConsultarCozinhaInexistente() {
		
		RestAssured.given()
		.pathParam("CozinhaId", COZINHA_ID_INEXISTENTE)
		.accept(ContentType.JSON)
	.when()
		.get("/{CozinhaId}")
	.then()	
		.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	
}
