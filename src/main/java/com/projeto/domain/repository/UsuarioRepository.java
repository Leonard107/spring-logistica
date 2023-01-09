package com.projeto.domain.repository;

import java.util.Optional;

import com.projeto.domain.model.Usuario;

public interface UsuarioRepository extends CustomJpaRepository<Usuario, Long> {
	
	Optional<Usuario> findByEmail(String email);
}
