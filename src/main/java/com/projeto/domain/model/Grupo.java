package com.projeto.domain.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Grupo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String nome;
	
	@ManyToMany
	@JoinTable(name = "grupo_permissao",
	joinColumns = @JoinColumn(name = "grupo_id"),
	inverseJoinColumns = @JoinColumn(name= "permissao_id"))
	private Set<Permissao> permissaos = new HashSet<>();
	
	public boolean removerPermissao(Permissao permissao) {
		return getPermissaos().remove(permissao);
	}
	
	public boolean adicionarPermissao(Permissao permissao) {
		return getPermissaos().add(permissao);
	}
}
