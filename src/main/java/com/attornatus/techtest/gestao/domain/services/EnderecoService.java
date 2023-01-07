package com.attornatus.techtest.gestao.domain.services;

import com.attornatus.techtest.gestao.domain.dto.EnderecoDTO;
import com.attornatus.techtest.gestao.domain.models.Endereco;
import com.attornatus.techtest.gestao.domain.models.Pessoa;
import com.attornatus.techtest.gestao.domain.services.excepctions.ObjetoNaoEncontradoException;
import com.attornatus.techtest.gestao.repositories.EnderecoRepository;
import com.attornatus.techtest.gestao.repositories.PessoaRepository;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EnderecoService {
    private final PessoaRepository pessoaRepository;
    private final EnderecoRepository enderecoRepository;
    public EnderecoService(PessoaRepository pessoaRepository, EnderecoRepository enderecoRepository) {
        this.pessoaRepository = pessoaRepository;
        this.enderecoRepository = enderecoRepository;
    }

    public Endereco buscarPorID(Long id) {
        return enderecoRepository.findById(id).orElseThrow( () -> {
            return new ObjetoNaoEncontradoException("Erro: Pessoa não encontrada.");
        });
    }

    public List<Endereco>  buscarTodosPorID(Long id) {
        Pessoa pessoa = pessoaRepository.getById(id);
        List<Endereco> enderecos = pessoa.getEnderecos();
        if(enderecos.isEmpty())
            throw new ObjetoNaoEncontradoException("Error: Nenhum endereço encontrado.");
        else
            return enderecos;
    }
    public List<EnderecoDTO> buscarTodosEnderecos(){
        List<EnderecoDTO> listaEnderecos = buscarEnderecos();
        if(listaEnderecos.isEmpty()){
            throw new ObjetoNaoEncontradoException("Error: Nenhum endereço encontrado.");
        } else return listaEnderecos;
    }
    private List<EnderecoDTO> buscarEnderecos() {
        return enderecoRepository.findAll().stream().map(obj -> new EnderecoDTO(obj)).collect(Collectors.toList());
    }

    public Endereco informarEnderecoPrincipal(Long id) {
        var ref = new Object() {
            Long id;
        };
        Pessoa pessoa = pessoaRepository.getById(id);
        pessoa.getEnderecos().forEach((endereco -> {
            if (endereco.getEnderecoPrincipal() == true) {
                ref.id = endereco.getId();
            }
        }));
        return this.buscarPorID(ref.id);
    }

    public Endereco inserirEnderecoParaPessoa(EnderecoDTO endereco) throws ParseException {
        Endereco end = passagemDTO(endereco);
        enderecoRepository.save(end);

        return end;
    }

    private Endereco passagemDTO(EnderecoDTO novoEndereco) throws ParseException {
        Endereco endereco = new Endereco(null, novoEndereco.getLogradouro(), novoEndereco.getCep(), novoEndereco.getNumero(), novoEndereco.getCidade(), novoEndereco.getEnderecoPrincipal(), pessoaRepository.getById(novoEndereco.getPessoaId()));
        buscarEnderecoPrincipal(pessoaRepository.getById(novoEndereco.getPessoaId()), novoEndereco);

        return endereco;
    }

    private void buscarEnderecoPrincipal(Pessoa pessoa, EnderecoDTO enderecoDTO) {
        if(enderecoDTO.getEnderecoPrincipal() == false) return;
        pessoa.getEnderecos().forEach((endereco -> {
            if (endereco.getEnderecoPrincipal() == true) {
                throw new ObjetoNaoEncontradoException("Esta pessoa já tem um endereço principal cadastrado, " +
                        "por favor, altere o endereço principal.");
            }
        }));
    }

}
