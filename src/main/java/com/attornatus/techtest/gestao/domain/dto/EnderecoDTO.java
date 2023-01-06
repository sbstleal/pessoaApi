package com.attornatus.techtest.gestao.domain.dto;

import com.attornatus.techtest.gestao.domain.models.Endereco;
import lombok.Data;

@Data
public class EnderecoDTO {
    private Long id;
    private String logradouro;
    private String cep;
    private Integer numero;
    private String cidade;
    private Boolean enderecoPrincipal;
    private Long pessoaId;

    public EnderecoDTO(Long id, String logradouro, String cep, Integer numero, String cidade, Boolean enderecoPrincipal) {
        this.id = id;
        this.logradouro = logradouro;
        this.cep = cep;
        this.numero = numero;
        this.cidade = cidade;
        this.enderecoPrincipal = enderecoPrincipal;
    }

    public EnderecoDTO(Endereco endereco) {
        this.id = endereco.getId();
        this.logradouro = endereco.getLogradouro();
        this.cep = endereco.getCep();
        this.numero = endereco.getNumero();
        this.cidade = endereco.getCidade();
        this.enderecoPrincipal = endereco.getEnderecoPrincipal();
    }

    public EnderecoDTO(Long id, String logradouro, String cep, Integer numero, String cidade, Boolean enderecoPrincipal, Long pessoaId) {
        this.id = id;
        this.logradouro = logradouro;
        this.cep = cep;
        this.numero = numero;
        this.cidade = cidade;
        this.enderecoPrincipal = enderecoPrincipal;
        this.pessoaId = pessoaId;
    }
}
