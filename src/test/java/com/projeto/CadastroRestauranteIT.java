package com.projeto;

import static org.hamcrest.Matchers.equalTo;

import java.math.BigDecimal;

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
import com.projeto.domain.model.Restaurante;
import com.projeto.domain.repository.CozinhaRepository;
import com.projeto.domain.repository.RestauranteRepository;
import com.projeto.util.DatabaseCleaner;
import com.projeto.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
class CadastroRestauranteIT {
	
    private static final int RESTAURANTE_ID_INEXISTENTE = 100;

	
	@LocalServerPort
	private int port;
	
	@Autowired
	private DatabaseCleaner dataBaseCleaner;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
    private RestauranteRepository restauranteRepository;
	
	private String jsonRestauranteCorreto;
    private String jsonRestauranteSemFrete;
    private String jsonRestauranteSemCozinha;
    private String jsonRestauranteComCozinhaInexistente;
	
    private Restaurante burgerTopRestaurante;

	
	@BeforeEach
	public void setUp() {
		
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/restaurantes";
		
		jsonRestauranteCorreto = ResourceUtils.getContentFromResource("/json/correto/restaurante-new-york-barbecue.json");
        
        jsonRestauranteSemFrete = ResourceUtils.getContentFromResource("/json/incorreto/restaurante-new-york-barbecue-sem-frete.json");
        
        jsonRestauranteSemCozinha = ResourceUtils.getContentFromResource("/json/incorreto/restaurante-new-york-barbecue-sem-cozinha.json");
        
        jsonRestauranteComCozinhaInexistente = ResourceUtils.getContentFromResource("/json/incorreto/restaurante-new-york-barbecue-com-cozinha-inexistente.json");
		
		dataBaseCleaner.clearTables();
		prepararDados();
				
	}
	
	private void prepararDados() {
		Cozinha cozinhaBrasileira = new Cozinha();
        cozinhaBrasileira.setNome("Brasileira");
        cozinhaRepository.save(cozinhaBrasileira);

        Cozinha cozinhaAmericana = new Cozinha();
        cozinhaAmericana.setNome("Americana");
        cozinhaRepository.save(cozinhaAmericana);
        
        burgerTopRestaurante = new Restaurante();
        burgerTopRestaurante.setNome("Burger Top");
        burgerTopRestaurante.setTaxaFrete(new BigDecimal(10));
        burgerTopRestaurante.setCozinha(cozinhaAmericana);
        restauranteRepository.save(burgerTopRestaurante);
        
        Restaurante comidaMineiraRestaurante = new Restaurante();
        comidaMineiraRestaurante.setNome("Comida Mineira");
        comidaMineiraRestaurante.setTaxaFrete(new BigDecimal(10));
        comidaMineiraRestaurante.setCozinha(cozinhaBrasileira);
        restauranteRepository.save(comidaMineiraRestaurante);

	}
		
	@Test
	public void deveRetornarStatus200_QuandoConsultarRestaurantes() {
				
		RestAssured.given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());	
	}
	
	@Test
	public void deveRetornarStatus201_QuandoCadastrarRestaurante() {
				
		RestAssured.given()
			.body(jsonRestauranteCorreto)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()	
			.statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	public void deveRetornarStatus400_QuandoCadastrarRestauranteSemTaxaFrete() {
		
		RestAssured.given()
		.body(jsonRestauranteSemFrete)
		.contentType(ContentType.JSON)
		.accept(ContentType.JSON)
	.when()
		.post()
	.then()	
		.statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
    public void deveRetornarStatus400_QuandoCadastrarRestauranteSemCozinha() {
		RestAssured.given()
            .body(jsonRestauranteSemCozinha)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }
	
	
	
	@Test
	public void deveRetornarStatus400_QuandoCadastrarRestauranteComCozinhaInexistente() {
		
		RestAssured.given()
		.body(jsonRestauranteComCozinhaInexistente)
		.contentType(ContentType.JSON)
		.accept(ContentType.JSON)
	.when()
		.post()
	.then()	
		.statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
    public void deveRetornarRespostaEStatusCorretos_QuandoConsultarRestauranteExistente() {
		RestAssured.given()
            .pathParam("restauranteId", burgerTopRestaurante.getId())
            .accept(ContentType.JSON)
        .when()
            .get("/{restauranteId}")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("nome", equalTo(burgerTopRestaurante.getNome()));
    }
	
	@Test
    public void deveRetornarStatus404_QuandoConsultarRestauranteInexistente() {
		RestAssured.given()
            .pathParam("restauranteId", RESTAURANTE_ID_INEXISTENTE)
            .accept(ContentType.JSON)
        .when()
            .get("/{restauranteId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }
	
	
}
