package com.example.Hotel.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter

@Entity
public class Hospedagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
private Hospede hospede;

@ManyToOne(fetch = FetchType.EAGER)
private Quarto quarto;

    private LocalDateTime dataEntrada;
    private LocalDateTime dataSaida;
    private String tipoHospedagem; // Diária, Empresa
    private Double valorTotal;
    private String status; // Ativa, Finalizada

    // Getters e Setters
    // ...
}
