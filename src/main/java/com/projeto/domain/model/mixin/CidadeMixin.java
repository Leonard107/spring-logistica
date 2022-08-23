package com.projeto.domain.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.projeto.domain.model.Estado;

public abstract class CidadeMixin {
	
	@JsonIgnoreProperties(value = "nome", allowGetters = true)
	private Estado estado;

}
