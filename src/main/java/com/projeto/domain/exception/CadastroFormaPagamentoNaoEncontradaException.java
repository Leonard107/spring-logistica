package com.projeto.domain.exception;

public class CadastroFormaPagamentoNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;
	
	public CadastroFormaPagamentoNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	
	public CadastroFormaPagamentoNaoEncontradaException(Long cozinhaId) {
		this(String.format("Não existe um cadastro de cozinha com código %d", cozinhaId));
		
	}


}
