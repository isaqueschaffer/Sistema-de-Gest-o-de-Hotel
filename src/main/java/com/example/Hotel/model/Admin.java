package com.example.Hotel.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Admin extends Usuario {
    
    private String nivelAcesso; // Exemplo: "TOTAL" ou permissões específicas
}
