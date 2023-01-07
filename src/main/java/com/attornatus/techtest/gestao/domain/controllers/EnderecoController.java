package com.attornatus.techtest.gestao.domain.controllers;

import com.attornatus.techtest.gestao.domain.dto.EnderecoDTO;
import com.attornatus.techtest.gestao.domain.models.Endereco;
import com.attornatus.techtest.gestao.domain.services.EnderecoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping(value = "/endereco")
public class EnderecoController {
    private final EnderecoService enderecoService;

    public EnderecoController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<EnderecoDTO> buscarPorID(@PathVariable Long id){
        Endereco endereco = enderecoService.buscarPorID(id);
        var novoEndereco = new EnderecoDTO(endereco);

        return ResponseEntity.ok(novoEndereco);
    }

    @GetMapping(value = "todos/{id}")
    public ResponseEntity<List<Endereco>> buscarEnderecosDaPessoa(@PathVariable Long id){
        List<Endereco> enderecos = enderecoService.buscarTodosPorID(id);
        return ResponseEntity.ok(enderecos);
    }

    @GetMapping(value = "principal/{id}")
    public ResponseEntity<Endereco> buscarEnderecoPrincipal(@PathVariable Long id){
        Endereco endereco = enderecoService.informarEnderecoPrincipal(id);
        return ResponseEntity.ok(endereco);
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
