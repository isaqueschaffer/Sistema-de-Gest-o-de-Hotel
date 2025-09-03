package com.example.Hotel.model;

import jakarta.persistence.*;

import org.antlr.v4.runtime.misc.NotNull;
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

    @NotNull
    @Column(nullable = false, unique = true) // Também reforça a unicidade aqui
    private Integer numero;

    @NotBlank
    @Column(nullable = false)
    private String tipo; // Simples ou Apartamento

    @NotNull
    @Column(nullable = false)
    private Integer quantidadeCamas;

    @NotBlank
    @Column(nullable = false)
    private String tipoCamas;

    @NotNull
    @Column(nullable = false)
    private Boolean tv;

    @NotNull
    @Column(nullable = false)
    private Boolean frigobar;

    @NotNull
    @Column(nullable = false)
    private Boolean banheiroPrivativo;

    
    
    private String status; // Livre, Ocupado, Manutenção
}
