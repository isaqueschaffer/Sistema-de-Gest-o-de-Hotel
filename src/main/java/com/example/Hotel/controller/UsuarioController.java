package com.example.Hotel.controller;

import com.example.Hotel.model.Usuario;
import com.example.Hotel.dto.UsuarioRequest;
import com.example.Hotel.service.UsuarioService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    // CREATE (via DTO)
    @PostMapping
    public ResponseEntity<?> criar(@RequestBody UsuarioRequest dto) {
        Usuario criado = usuarioService.cadastrarUsuario(dto);
        return ResponseEntity.ok(criado);
    }

    // CREATE (via entidade normal)
    @PostMapping("/entidade")
    public ResponseEntity<?> criarCompleto(@RequestBody Usuario usuario) {
        Usuario criado = usuarioService.criarUsuario(usuario);
        return ResponseEntity.ok(criado);
    }

    // READ - Buscar todos
    @GetMapping
    public ResponseEntity<?> listarTodos() {
        List<Usuario> usuarios = usuarioService.buscarTodos();
        return ResponseEntity.ok(usuarios);
    }

    // READ - Buscar por id
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(usuario);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(
            @PathVariable Long id,
            @RequestBody Usuario usuario) {

        Usuario atualizado = usuarioService.atualizarUsuario(id, usuario);
        return ResponseEntity.ok(atualizado);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.ok("Usuário removido com sucesso.");
    }
}
