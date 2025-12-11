package com.example.Hotel.controller;

import com.example.Hotel.model.Venda;
import com.example.Hotel.service.VendaService;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/vendas")
@RequiredArgsConstructor
public class VendaController {

    private final VendaService vendaService;

    // ======================================
    // Registrar nova venda (com auditoria)
    // ======================================
    @PostMapping
    public ResponseEntity<?> registrarVenda(
            @RequestBody Venda venda,
            @RequestHeader("usuario-login") String usuarioLogin) {

        try {
            Venda novaVenda = vendaService.registrarVenda(venda, usuarioLogin);
            return ResponseEntity.ok(novaVenda);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ======================================
    // Buscar venda por ID
    // ======================================
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(vendaService.buscarPorId(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ======================================
    // Listar todas as vendas
    // ======================================
    @GetMapping
    public List<Venda> listarTodas() {
        return vendaService.listarTodos();
    }

    // ======================================
    // Listar vendas por usuário
    // ======================================
    @GetMapping("/usuario/{usuarioId}")
    public List<Venda> listarPorUsuario(@PathVariable Long usuarioId) {
        return vendaService.listarPorUsuario(usuarioId);
    }

    // ======================================
    // Deletar venda
    // ======================================
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            vendaService.deletar(id);
            return ResponseEntity.ok("Venda deletada com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}