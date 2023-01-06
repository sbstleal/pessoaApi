package com.attornatus.techtest.gestao.domain.dto;

import com.attornatus.techtest.gestao.domain.models.Endereco;
import com.attornatus.techtest.gestao.domain.models.Pessoa;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class PessoaDTO {
    @JsonIgnore
    private Long id;
    @NotEmpty(message = "Este campo não pode ser vazio")
    @Length(min = 3, max = 150)
    private String name;
    @NotNull
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dataNascimento;
    private List<Endereco> enderecos = new ArrayList<>();

    public PessoaDTO(Long id, String name, LocalDate dataNascimento) {
        this.id = id;
        this.name = name;
        this.dataNascimento = dataNascimento;
    }

    public PessoaDTO(Pessoa pessoa) {
        this.id = pessoa.getId();
        this.name = pessoa.getNome();
        this.dataNascimento = pessoa.getDataNascimento();
        this.enderecos = pessoa.getEnderecos();
    }
}
