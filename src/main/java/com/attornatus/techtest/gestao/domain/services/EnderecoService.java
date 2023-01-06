package com.attornatus.techtest.gestao.domain.services;

import com.attornatus.techtest.gestao.domain.dto.EnderecoDTO;
import com.attornatus.techtest.gestao.domain.models.Endereco;
import com.attornatus.techtest.gestao.domain.models.Pessoa;
import com.attornatus.techtest.gestao.domain.services.excepctions.ObjetoNaoEncontradoException;
import com.attornatus.techtest.gestao.repositories.EnderecoRepository;
import com.attornatus.techtest.gestao.repositories.PessoaRepository;
import org.springframework.stereotype.Service;

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
        Optional<Endereco> endereco = Optional.of(enderecoRepository.getReferenceById(id));
        if (!endereco.isPresent())
            throw new ObjetoNaoEncontradoException("Erro: Pessoa não encontrada.");
        else
            return endereco.get();
    }
    public List<EnderecoDTO> buscarTodosEnderecos(){
        List<EnderecoDTO> listAddress = findAllAddress();
        if(listAddress.isEmpty()){
            throw new ObjetoNaoEncontradoException("Error: Nenhum endereço encontrado.");
        } else return listAddress;
    }
    private List<EnderecoDTO> findAllAddress() {
        return enderecoRepository.findAll().stream().map(obj -> new EnderecoDTO(obj)).collect(Collectors.toList());
    }
    private void buscarEnderecoPrincipal(Pessoa pessoa, EnderecoDTO enderecoDTO) {
        for (Endereco x : pessoa.getEnderecos()) {
            if (x.getEnderecoPrincipal() == true && enderecoDTO.getEnderecoPrincipal() == true) {
                throw new ObjetoNaoEncontradoException("Esta pessoa já tem um endereço principal cadastrado, por favor, altere o endereço principal.");
            }
            return;
        }
    }
}
