package com.example.Hotel.model;

import jakarta.persistence.*;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(
    name = "quarto",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "numero") // Garante que o número não se repita
    }
)
public class Quarto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true) // Também reforça a unicidade aqui
    private Integer numero;

    @NotBlank
    @Column(nullable = false)
    private String tipo; // Simples ou Apartamento

  
    @Column(nullable = false)
    private Integer quantidadeCamas;

    @NotBlank
    @Column(nullable = false)
    private String tipoCamas;

  
    @Column(nullable = false)
    private Boolean tv;

    @Column(nullable = false)
    private Boolean ar;

   
    @Column(nullable = false)
    private Boolean ventilador;

    
    @Column(nullable = false)
    private Boolean frigobar;

    
    @Column(nullable = false)
    private Boolean banheiroPrivativo;

    
    
    private String status; // Livre, Ocupado, Manutenção
}
