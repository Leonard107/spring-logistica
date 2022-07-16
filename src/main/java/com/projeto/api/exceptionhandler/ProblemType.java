package com.projeto.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {
	
	ENTIDADE_NAO_ENCONTRADA("/entidade-nao-encontrada", "Entidade não encontrada"),
	ERRO_NEGOCIO("/erro-negocio", "violação de regra de negócio"),
	ENTIDADE_EM_USO("/erro-negocio", "Entidade em uso");
	
	private String title;
	private String uri;
	
	private ProblemType(String path, String title) {
		this.uri = "https://logistica.com.br" + path;
		this.title = title;
	}
}
