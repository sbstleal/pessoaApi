package com.attornatus.techtest.gestao.domain.services;

import com.attornatus.techtest.gestao.domain.dto.NovaPessoaDTO;
import com.attornatus.techtest.gestao.domain.dto.PessoaAtualizarDTO;
import com.attornatus.techtest.gestao.domain.dto.TodasPessoasDTO;
import com.attornatus.techtest.gestao.domain.models.Endereco;
import com.attornatus.techtest.gestao.domain.models.Pessoa;
import com.attornatus.techtest.gestao.domain.services.excepctions.ObjetoNaoEncontradoException;
import com.attornatus.techtest.gestao.repositories.EnderecoRepository;
import com.attornatus.techtest.gestao.repositories.PessoaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PessoaService {
    private final PessoaRepository pessoaRepository;
    private final EnderecoRepository enderecoRepository;

    public PessoaService(PessoaRepository pessoaRepository, EnderecoRepository enderecoRepository) {
        this.pessoaRepository = pessoaRepository;
        this.enderecoRepository = enderecoRepository;
    }

    @Transactional(readOnly = true)
    public Pessoa buscarPorID(Long id) {
        Optional<Pessoa> pessoa = Optional.of(pessoaRepository.getReferenceById(id));
        if (!pessoa.isPresent())
            throw new ObjetoNaoEncontradoException("Erro: Pessoa não encontrada.");
        else
            return pessoa.get();
    }

    @Transactional(readOnly = true)
    public List<TodasPessoasDTO> buscarTodasPessoas() {
        List<TodasPessoasDTO> listaPess = pessoaRepository
                .findAll()
                .stream()
                .map(obj -> new TodasPessoasDTO(obj)).collect(Collectors.toList());
        if (listaPess.isEmpty())
            throw new ObjetoNaoEncontradoException("Erro: Nenhuma pessoa localizada.");
        else
            return listaPess;
    }

    @Transactional(rollbackFor = Exception.class)
    public Pessoa criar(NovaPessoaDTO novaPessoa) {
        Pessoa pessoa = passagemDTO(novaPessoa);
        pessoaRepository.save(pessoa);
        enderecoRepository.saveAll(pessoa.getEnderecos());

        return pessoa;
    }

    private Pessoa passagemDTO(NovaPessoaDTO pessoa){
        Pessoa pessoaUp = new Pessoa();
        Endereco endereco = new Endereco(null, pessoa.getLogradouro(), pessoa.getCep(), pessoa.getNumero(), pessoa.getCidade(), pessoa.getEnderecoPrincipal(), pessoaUp);
        pessoaUp.setId(null);
        pessoaUp.setNome(pessoa.getNome());
        pessoaUp.setDataNascimento(pessoa.getDataNascimento());
        pessoaUp.getEnderecos().add(endereco);

        return pessoaUp;
    }
    @Transactional(rollbackFor = Exception.class)
    public Pessoa atualizarPessoa(PessoaAtualizarDTO atualizarPessoa){
        Pessoa pessoa = buscarPorID(atualizarPessoa.getId());
        atualizar(pessoa, atualizarPessoa);
        pessoaRepository.save(pessoa);

        return pessoa;
    }

    private void atualizar(Pessoa p1, PessoaAtualizarDTO p2){
        p1.setNome(p2.getNome());
        p1.setDataNascimento(p2.getDataNascimento());
        buscarEnderecoPrincipal(p1, p2);
        if (p2.getEnderecoId() > p1.getEnderecos().size() || p2.getEnderecoId() < p1.getEnderecos().size())
            throw new ObjetoNaoEncontradoException("Endereço Id inexistente");
        p1.getEnderecos().get((int) (p2.getEnderecoId() -1)).setEnderecoPrincipal(p2.getEnderecoPrincipal());
    }

    public PessoaAtualizarDTO mudarPessoa(Long id, PessoaAtualizarDTO pessoa) {
        pessoa.setId(id);
        var novaPessoa = atualizarPessoa(pessoa);
        return new PessoaAtualizarDTO(novaPessoa);
    }

    private void buscarEnderecoPrincipal(Pessoa pessoaEmBase, PessoaAtualizarDTO pessoa) {
        for (Endereco x : pessoaEmBase.getEnderecos()) {
            if (x.getEnderecoPrincipal() == true && pessoa.getEnderecoPrincipal() == true) {
                throw new ObjetoNaoEncontradoException(
                        "Esta pessoa já tem um endereço principal cadastrado, por favor, altere o endereço principal.");
            }
            return;
        }
    }
}


