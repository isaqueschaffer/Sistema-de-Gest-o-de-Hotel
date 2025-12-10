package com.example.Hotel.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter

@Entity
public class Caixa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate data;
    private Double totalEntradas;
    private Double totalSaidas;
    private Double saldoFinal;

    
    public void calcularSaldoFinal() {
        if (totalEntradas == null) totalEntradas = 0.0;
        if (totalSaidas == null) totalSaidas = 0.0;
        this.saldoFinal = totalEntradas - totalSaidas;
    }
}
