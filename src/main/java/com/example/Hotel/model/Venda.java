package com.example.Hotel.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    private int quantidade;

    private Double valorUnitario;     // valor do produto
    private Double valorOriginal;     // valor correto da venda
    private Double valorRecebido;     // valor pago pelo cliente
    private Double valorDiferenca;    // diferença (desconto/acerto)

    private String motivoDiferenca;   // "cliente pegou fiado", "faltou 2 reais", etc.

    private String formaPagamento;
    private LocalDateTime dataHora;

    @PrePersist
    public void prePersist() {
        this.valorUnitario = produto.getPrecoUnitario();
        this.valorOriginal = this.valorUnitario * this.quantidade;

        // Calcula diferença automaticamente
        if (valorRecebido != null) {
            this.valorDiferenca = valorRecebido - valorOriginal;
        } else {
            this.valorRecebido = valorOriginal; // caso não informe nada, assume valor normal
            this.valorDiferenca = 0.0;
        }

        this.dataHora = LocalDateTime.now();
    }

    // getters e setters...
}
