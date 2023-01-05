package com.attornatus.techtest.gestao.domain.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_pessoa")
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private LocalDate dataNascimento;
    @OneToMany(mappedBy = "pessoa", fetch = FetchType.EAGER)
    private List<Endereco> enderecos = new ArrayList<>();
    public Pessoa setEndederecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
        return this;
    }
}
