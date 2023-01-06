package com.attornatus.techtest.gestao.domain.dto;

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

@Data
public class PessoaAtualizarDTO {
    @JsonIgnore
    private Long id;
    private String name;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dataNascimento;
    private Boolean enderecoPrincipal;

    public PessoaAtualizarDTO(Long id, String name, LocalDate dataNascimento, Boolean enderecoPrincipal) {
        this.id = id;
        this.name = name;
        this.dataNascimento = dataNascimento;
        this.enderecoPrincipal = enderecoPrincipal;
    }

    public PessoaAtualizarDTO(Pessoa pessoa) {
        this.id = pessoa.getId();
        this.name = pessoa.getNome();
        this.dataNascimento = pessoa.getDataNascimento();
    }
}
