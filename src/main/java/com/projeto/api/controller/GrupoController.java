package com.projeto.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.api.assembler.GrupoDTOAssembler;
import com.projeto.api.assembler.GrupoInputDissassembler;
import com.projeto.domain.model.Grupo;
import com.projeto.domain.model.DTO.GrupoDTO;
import com.projeto.domain.model.input.GrupoInput;
import com.projeto.domain.repository.GrupoRepository;
import com.projeto.domain.service.CadastroGrupoService;

@RestController
@RequestMapping("/grupos")
public class GrupoController {
	
	@Autowired
    private GrupoRepository grupoRepository;
    
    @Autowired
    private CadastroGrupoService cadastroGrupoService;
    
    @Autowired
    private GrupoDTOAssembler grupoDTOAssembler;
    
    @Autowired
    private GrupoInputDissassembler grupoInputDissassembler;
    
    @GetMapping
    public List<GrupoDTO> listar(){
    	
    	List<Grupo> todosGrupos = grupoRepository.findAll();
    	
    	return grupoDTOAssembler.toCollectionDTO(todosGrupos);
    	
    }
    
    @GetMapping(value = "/{grupoId}")
    public GrupoDTO buscar(@PathVariable Long grupoId) {
        Grupo grupo = cadastroGrupoService.buscarOuFalhar(grupoId);

        return grupoDTOAssembler.toDTO(grupo);

    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoDTO adicionar(@RequestBody @Valid GrupoInput grupoInput) {
        Grupo grupo = grupoInputDissassembler.toDomainObject(grupoInput);
        
        grupo = cadastroGrupoService.salvar(grupo);
        
        return grupoDTOAssembler.toDTO(grupo);
    }
    
    @PutMapping("/{grupoId}")
    public GrupoDTO atualizar(@PathVariable Long grupoId,
            @RequestBody @Valid GrupoInput grupoInput) {
        Grupo grupoAtual = cadastroGrupoService.buscarOuFalhar(grupoId);
        
        grupoInputDissassembler.copyToDomainObject(grupoInput, grupoAtual);
        
        grupoAtual = cadastroGrupoService.salvar(grupoAtual);
        
        return grupoDTOAssembler.toDTO(grupoAtual);
    }
    
    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long grupoId) {
    	cadastroGrupoService.excluir(grupoId);	
    }   

}
