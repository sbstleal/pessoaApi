package com.attornatus.techtest.gestao.domain.controllers;

import com.attornatus.techtest.gestao.domain.dto.NovaPessoaDTO;
import com.attornatus.techtest.gestao.domain.dto.PessoaAtualizarDTO;
import com.attornatus.techtest.gestao.domain.dto.PessoaDTO;
import com.attornatus.techtest.gestao.domain.dto.TodasPessoasDTO;
import com.attornatus.techtest.gestao.domain.models.Pessoa;
import com.attornatus.techtest.gestao.domain.services.PessoaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/pessoa")
public class PessoaController {
    private final PessoaService pessoaService;

    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PessoaDTO> findPerson(@PathVariable Long id){
        Pessoa pessoa = pessoaService.buscarPorID(id);
        var newPerson = new PessoaDTO(pessoa);

        return ResponseEntity.ok(newPerson);
    }

    @GetMapping
    public ResponseEntity<List<TodasPessoasDTO>> findListPersons(){
        List<TodasPessoasDTO> listaPessoas = pessoaService.buscarTodasPessoas();

        return ResponseEntity.ok(listaPessoas);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<PessoaAtualizarDTO> atualizarPessoa(@PathVariable Long id, @Valid @RequestBody PessoaAtualizarDTO pessoa){
        pessoa.setId(id);
        var novaPessoa = pessoaService.atualizarPessoa(pessoa);
        var novaPessoaAtualizada = new PessoaAtualizarDTO(novaPessoa);

        return ResponseEntity.accepted().body(novaPessoaAtualizada);
    }

    @PostMapping
    public ResponseEntity<Void> insertPerson(@Valid @RequestBody NovaPessoaDTO pessoa){
        var id = pessoaService.criar(pessoa).getId();
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();

        return ResponseEntity.created(uri).build();
    }

}
