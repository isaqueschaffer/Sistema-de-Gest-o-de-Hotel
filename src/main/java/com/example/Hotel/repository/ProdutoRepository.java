package com.example.Hotel.repository;

import com.example.Hotel.model.Produto;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

        Optional<Produto> findBycodigoProduto(Long codigoProduto);


     // 1 - Total geral de todos os produtos (precoUnitario * quantidade)
    @Query("SELECT SUM(p.precoUnitario * p.quantidade) FROM Produto p")
    Double calcularValorTotalEstoque();

    // 2 - Total baseado no código do produto
    @Query("SELECT SUM(p.precoUnitario * p.quantidade) FROM Produto p WHERE p.codigoProduto = :codigo")
    Double calcularTotalPorCodigo(@Param("codigo") Long codigoProduto);

    // 3 - Total baseado no nome do produto
    @Query("SELECT SUM(p.precoUnitario * p.quantidade) FROM Produto p WHERE LOWER(p.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    Double calcularTotalPorNome(@Param("nome") String nome);

}
