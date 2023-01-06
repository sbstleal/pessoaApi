package com.attornatus.techtest.gestao.domain.dto;

import com.attornatus.techtest.gestao.domain.models.Endereco;
import lombok.Data;

@Data
public class TodosEnderecosDTO {
    private Long id;
    private String logradouro;
    private String cep;
    private Integer numero;
    private String cidade;

    public TodosEnderecosDTO(Long id, String logradouro, String cep, Integer numero, String cidade) {
        this.id = id;
        this.logradouro = logradouro;
        this.cep = cep;
        this.numero = numero;
        this.cidade = cidade;
    }

    public TodosEnderecosDTO(Endereco endereco) {
        this.id = endereco.getId();
        this.logradouro = endereco.getLogradouro();
        this.cep = endereco.getCep();
        this.numero = endereco.getNumero();
        this.cidade = endereco.getCidade();
    }
}
