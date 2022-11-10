package com.projeto.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.api.assembler.UsuarioDTOAssembler;
import com.projeto.api.assembler.UsuarioInputDisassembler;
import com.projeto.domain.model.Usuario;
import com.projeto.domain.model.DTO.UsuarioDTO;
import com.projeto.domain.model.input.SenhaInput;
import com.projeto.domain.model.input.UsuarioComSenhaInput;
import com.projeto.domain.model.input.UsuarioInput;
import com.projeto.domain.repository.UsuarioRepository;
import com.projeto.domain.service.CadastroUsuarioService;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private CadastroUsuarioService  cadastroUsuarioService;

	@Autowired
	private UsuarioDTOAssembler usuarioDTOAssembler;

	@Autowired
	private UsuarioInputDisassembler usuarioInputDisassembler;
	
	@GetMapping
    public List<UsuarioDTO> listar() {
        List<Usuario> todasUsuarios = usuarioRepository.findAll();
        
        return usuarioDTOAssembler.toCollectionDTO(todasUsuarios);
    }
	
	@GetMapping("/{usuarioId}")
    public UsuarioDTO buscar(@PathVariable Long usuarioId) {
        Usuario usuario = cadastroUsuarioService.buscarOuFalhar(usuarioId);
        
        return usuarioDTOAssembler.toDTO(usuario);
    }
	
	@PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioDTO adicionar(@RequestBody @Valid UsuarioComSenhaInput usuarioInput) {
        Usuario usuario = usuarioInputDisassembler.toDOmainObejct(usuarioInput);
		usuario = cadastroUsuarioService.salvar(usuario);
        
        return usuarioDTOAssembler.toDTO(usuario);
    }
	
	@PutMapping("/{usuarioId}")
    public UsuarioDTO atualizar(@PathVariable Long usuarioId,
            @RequestBody @Valid UsuarioInput usuarioInput) {
        Usuario usuarioAtual = cadastroUsuarioService.buscarOuFalhar(usuarioId);
        usuarioInputDisassembler.copyToDomainObject(usuarioInput, usuarioAtual);
        usuarioAtual = cadastroUsuarioService.salvar(usuarioAtual);
        
        return usuarioDTOAssembler.toDTO(usuarioAtual);
    }
	
	@PutMapping("/{usuarioId}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alterarSenha(@PathVariable Long usuarioId, @RequestBody @Valid SenhaInput senha) {
		cadastroUsuarioService.alterarSenha(usuarioId, senha.getSenhaAtual(), senha.getNovaSenha());
    }
	
	
	
	

}
