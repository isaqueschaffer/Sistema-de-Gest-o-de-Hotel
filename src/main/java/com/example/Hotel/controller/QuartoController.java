package com.example.Hotel.controller;

import com.example.Hotel.model.Quarto;
import com.example.Hotel.service.QuartoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/quartos")
@CrossOrigin(origins = "http://127.0.0.1:5500") // se precisar do CORS
public class QuartoController {

    @Autowired
    private QuartoService quartoService;

    @GetMapping
    public java.util.List<Quarto> listarTodos() {
        return quartoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Quarto> buscarPorId(@PathVariable Long id) {
        Optional<Quarto> quarto = quartoService.buscarPorId(id);
        return quarto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Quarto salvar(@RequestBody Quarto quarto) {
        return quartoService.salvar(quarto);
    }

    // 🔧 Corrija o PUT para setar o id (se continuar usando PUT completo)
    @PutMapping("/{id}")
    public ResponseEntity<Quarto> atualizar(@PathVariable Long id, @RequestBody Quarto quarto) {
        if (!quartoService.buscarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        quarto.setId(id); // importante!
        return ResponseEntity.ok(quartoService.salvar(quarto));
    }

    // ✅ Novo endpoint para atualizar APENAS o status
    @PatchMapping("/{id}/status")
    public ResponseEntity<Quarto> atualizarStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String status = body.get("status");
        if (status == null || status.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        return quartoService.buscarPorId(id)
                .map(q -> {
                    q.setStatus(status);
                    Quarto salvo = quartoService.salvar(q);
                    return ResponseEntity.ok(salvo);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!quartoService.buscarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        quartoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
