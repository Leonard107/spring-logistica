package com.projeto.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.domain.repository.CozinhaRepository;

@RestController
@RequestMapping(value = "/teste")
public class TesteController {
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	//@GetMapping(value = "/cozinhas/por-nome")
	//public List<Cozinha> cozinhasPorNome(@RequestParam("nome") String nome){
	//	return cozinhaRepository.consultarPorNome(nome);
	//}

}
