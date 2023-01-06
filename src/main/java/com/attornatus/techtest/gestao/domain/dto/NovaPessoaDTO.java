package com.attornatus.techtest.gestao.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class NovaPessoaDTO {
    @NotEmpty(message = "Este campo não pode ser vazio")
    @Length(min = 3, max = 150)
    private String nome;
    @NotNull
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dataNascimento;
    @NotEmpty(message = "Este campo não pode ser vazio")
    @Length(min = 3, max = 150)
    private String logradouro;
    @NotEmpty(message = "Este campo não pode ser vazio")
    @Length(min = 8, max = 8)
    private String cep;
    @NotEmpty(message = "Este campo não pode ser vazio")
    @Length(min = 1, max = 10)
    private Integer numero;
    @NotEmpty(message = "Este campo não pode ser vazio")
    @Length(min = 1, max = 10)
    private String cidade;
    @NotEmpty(message = "Este campo não pode ser vazio")
    private Boolean enderecoPrincipal;

    public NovaPessoaDTO(String nome, LocalDate dataNascimento, String logradouro, String cep, Integer numero, String cidade, Boolean enderecoPrincipal) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.logradouro = logradouro;
        this.cep = cep;
        this.numero = numero;
        this.cidade = cidade;
        this.enderecoPrincipal = enderecoPrincipal;
    }
}
