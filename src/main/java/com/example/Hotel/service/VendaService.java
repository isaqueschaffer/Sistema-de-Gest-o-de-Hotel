package com.example.Hotel.service;

import com.example.Hotel.model.Produto;
import com.example.Hotel.model.Usuario;
import com.example.Hotel.model.Venda;
import com.example.Hotel.repository.ProdutoRepository;
import com.example.Hotel.repository.UsuarioRepository;
import com.example.Hotel.repository.VendaRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VendaService {

    

    @Autowired
  
    private final VendaRepository vendaRepository;
    private final ProdutoRepository produtoRepository;
    private final UsuarioRepository usuarioRepository;

    public List<Venda> listarTodos() {
        return vendaRepository.findAll();
    }

    public Optional<Venda> buscarPorId(Long id) {
        return vendaRepository.findById(id);
    }

    public Venda salvar(Venda venda) {
        return vendaRepository.save(venda);
    }

    public void deletar(Long id) {
        vendaRepository.deleteById(id);
    }

      // ===========================
    //  Registrar Venda (com auditoria)
    // ===========================
    public Venda registrarVenda(Venda venda, String loginUsuario) {

        // Validar usuário que está realizando a venda
        Usuario usuario = usuarioRepository.findByLogin(loginUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        venda.setCriadoPor(usuario);

        // Validar produto
        Produto produto = produtoRepository.findById(venda.getProduto().getId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado."));

        venda.setProduto(produto);

        // O cálculo de valores é feito no @PrePersist
        return vendaRepository.save(venda);
    }

     // ===========================
    //   Buscar vendas por Usuário
    // ===========================
    public List<Venda> listarPorUsuario(Long usuarioId) {
        return vendaRepository.findByCriadoPorId(usuarioId);
    }


    
}
