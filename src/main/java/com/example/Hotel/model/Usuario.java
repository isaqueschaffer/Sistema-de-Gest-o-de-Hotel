package com.example.Hotel.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String login; // nome de usuário para acessar o sistema

    @Column(nullable = false)
    private String senha; // será criptografada futuramente com BCrypt

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true, length = 14)
    private String cpf;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Perfil perfil; // ADMIN, RECEPCIONISTA, GERENTE...

    // Campos opcionais, usados dependendo do tipo de usuário
    private String turno;        // Para recepcionista
  

}
