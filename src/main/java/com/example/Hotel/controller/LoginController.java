package com.example.Hotel.controller;

import com.example.Hotel.model.Usuario;
import com.example.Hotel.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController {

    private final UsuarioService usuarioService;

    // DTO para receber login e senha
    public record LoginRequest(String login, String senha) {}

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        Usuario usuario = usuarioService.buscarPorLogin(request.login());

        if (usuario == null) {
            return ResponseEntity.status(401).body("Usuário não encontrado");
        }

        boolean senhaConfere = usuarioService.validarSenha(request.senha(), usuario.getSenha());

        if (!senhaConfere) {
            return ResponseEntity.status(401).body("Senha incorreta");
        }

        // Aqui você pode gerar um token futuramente
        return ResponseEntity.ok("Login realizado com sucesso! Usuário: " + usuario.getNome());
    }

    
}
