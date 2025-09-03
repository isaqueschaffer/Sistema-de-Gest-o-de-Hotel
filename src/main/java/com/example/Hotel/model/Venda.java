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
    private Produto produto;

    private int quantidade;
    private Double valorTotal;
    private String formaPagamento; // Dinheiro, Pix, Cartão
    private LocalDateTime dataHora;

    // Getters e Setters
    // ...
}
