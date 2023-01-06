package com.attornatus.techtest.gestao.domain.controllers;

import com.attornatus.techtest.gestao.domain.dto.EnderecoDTO;
import com.attornatus.techtest.gestao.domain.models.Endereco;
import com.attornatus.techtest.gestao.domain.services.EnderecoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.text.ParseException;
import java.util.List;

public class EnderecoController {
    private final EnderecoService enderecoService;

    public EnderecoController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<EnderecoDTO> buscarEndereco(@PathVariable Long id){
        Endereco endereco = enderecoService.buscarPorID(id);
        var novoEndereco = new EnderecoDTO(endereco);

        return ResponseEntity.ok(novoEndereco);
    }

    @GetMapping
    public ResponseEntity<List<EnderecoDTO>> buscarTodosEnderecos(){
        List<EnderecoDTO> listEndereco = enderecoService.buscarTodosEnderecos();

        return ResponseEntity.ok(listEndereco);
    }

    @PostMapping
    public ResponseEntity<Void> inserirEnderecoParaPessoa(@RequestBody EnderecoDTO endereco) throws ParseException {
        var id = enderecoService.inserirEnderecoParaPessoa(endereco).getId();
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();

        return ResponseEntity.created(uri).build();
    }
}
