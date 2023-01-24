package com.projeto.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.domain.model.Permissao;

public interface PermissaoRepository extends JpaRepository<Permissao, Long> {

}
