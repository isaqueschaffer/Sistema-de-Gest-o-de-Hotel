package com.example.Hotel.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED) // cada filho terá sua própria tabela
public abstract class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String login; // usuário de acesso

    @Column(nullable = false)
    private String senha; // senha (depois pode criptografar)

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cpf;
}
