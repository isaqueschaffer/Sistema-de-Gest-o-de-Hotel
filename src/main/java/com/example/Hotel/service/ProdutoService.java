package com.example.Hotel.service;

import com.example.Hotel.model.Produto;
import com.example.Hotel.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository produtoRepository;

    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    public Optional<Produto> buscarPorId(Long id) {
        return produtoRepository.findById(id);
    }

    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }

    public void deletar(Long id) {
        produtoRepository.deleteById(id);
    }

    public Optional<Produto> buscarPorCodigoProduto(long codigoProduto) {
        return produtoRepository.findBycodigoProduto(codigoProduto);
    }   


    public Double calcularPrecoTotal() {
        return produtoRepository.calcularValorTotalEstoque();
    }

    public Double calcularPrecoTotalPorCodigo(Long codigo) {
        return produtoRepository.calcularTotalPorCodigo(codigo);
    }   

    public Double calcularPrecoTotalPorNome(String nome) {
        return produtoRepository.calcularTotalPorNome(nome);
    }       

}
