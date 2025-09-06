package com.example.Hotel.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Recepcionista extends Usuario {
    
    private String turno; // Exemplo: "Manhã", "Tarde", "Noite"
}
