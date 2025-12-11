package com.example.Hotel.repository;

import com.example.Hotel.model.Venda;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {

    // 1) Buscar vendas por data
    @Query("SELECT v FROM Venda v WHERE DATE(v.dataHora) = :data")
    List<Venda> findByData(@Param("data") LocalDate data);


    // 2) Buscar vendas entre duas datas (relatórios)
    @Query("SELECT v FROM Venda v WHERE v.dataHora BETWEEN :inicio AND :fim")
    List<Venda> findByPeriodo(
        @Param("inicio") LocalDateTime inicio,
        @Param("fim") LocalDateTime fim
    );


    // 3) Listar vendas de um produto específico
    @Query("SELECT v FROM Venda v WHERE v.produto.id = :produtoId")
    List<Venda> findByProduto(@Param("produtoId") Long produtoId);


    // 4) Somar faturamento total do dia
    @Query("SELECT SUM(v.valorRecebido) FROM Venda v WHERE DATE(v.dataHora) = :data")
    Double totalFaturadoNoDia(@Param("data") LocalDate data);


    // 5) Somar apenas o valor original (sem desconto/acerto)
    @Query("SELECT SUM(v.valorOriginal) FROM Venda v WHERE DATE(v.dataHora) = :data")
    Double totalOriginalNoDia(@Param("data") LocalDate data);


    // 6) Somar todas as diferenças de valor (desconto, fiado, ajuste)
    @Query("SELECT SUM(v.valorDiferenca) FROM Venda v WHERE DATE(v.dataHora) = :data")
    Double totalDiferencasNoDia(@Param("data") LocalDate data);


    // 7) Buscar vendas que tiveram diferença (positivo ou negativo)
    @Query("SELECT v FROM Venda v WHERE v.valorDiferenca <> 0")
    List<Venda> findVendasComDiferenca();


    // 8) Buscar vendas por forma de pagamento
    @Query("SELECT v FROM Venda v WHERE v.formaPagamento = :fp")
    List<Venda> findByFormaPagamento(@Param("fp") String formaPagamento);


    // 9) Relatório: total por produto
    @Query("SELECT v.produto.nome, SUM(v.quantidade), SUM(v.valorRecebido) " +
           "FROM Venda v GROUP BY v.produto.nome")
    List<Object[]> totalPorProduto();


    // 10) Relatório: vendas com motivo de diferença
    @Query("SELECT v FROM Venda v WHERE v.motivoDiferenca IS NOT NULL AND v.motivoDiferenca <> ''")
    List<Venda> findVendasComMotivo();

    List<Venda> findByCriadoPorId(Long usuarioId);

}
