package com.example.Hotel.dto;

import com.example.Hotel.model.Perfil;

public record UsuarioRequest(
        String login,
        String senha,
        String nome,
        String cpf,
        Perfil perfil,
        String turno // opcional
) {}
