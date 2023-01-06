package com.attornatus.techtest.gestao.domain.dto;

import com.attornatus.techtest.gestao.domain.models.Pessoa;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Data
public class TodasPessoasDTO {
    private Long id;
    private String name;
    @Temporal(TemporalType.DATE)
    private LocalDate dataNascimento;
    public TodasPessoasDTO(Long id, String name, LocalDate dataNascimento) {
        this.id = id;
        this.name = name;
        this.dataNascimento = dataNascimento;
    }
    public TodasPessoasDTO(Pessoa pessoa) {
        this.id = pessoa.getId();
        this.name = pessoa.getNome();
        this.dataNascimento = pessoa.getDataNascimento();
    }
}
