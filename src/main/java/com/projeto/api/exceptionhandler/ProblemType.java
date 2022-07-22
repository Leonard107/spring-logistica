package com.projeto.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {
	
	ERRO_DE_SISTEMA("/erro-sistema","Erro de sistema"),
	PARAMETRO_INVALIDO("/parametro-invalido", "Parametro invalido"),
	MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem incompreensível"),
	RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado"),
	ERRO_NEGOCIO("/erro-negocio", "violação de regra de negócio"),
	ENTIDADE_EM_USO("/erro-negocio", "Entidade em uso");
	
	private String title;
	private String uri;
	
	private ProblemType(String path, String title) {
		this.uri = "https://logistica.com.br" + path;
		this.title = title;
	}
}
