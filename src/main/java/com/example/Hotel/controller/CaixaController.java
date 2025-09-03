package com.example.Hotel.controller;

import com.example.Hotel.model.Caixa;
import com.example.Hotel.service.CaixaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/caixas")
public class CaixaController {
    @Autowired
    private CaixaService caixaService;

    @GetMapping
    public List<Caixa> listarTodos() {
        return caixaService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Caixa> buscarPorId(@PathVariable Long id) {
        Optional<Caixa> caixa = caixaService.buscarPorId(id);
        return caixa.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Caixa salvar(@RequestBody Caixa caixa) {
        return caixaService.salvar(caixa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Caixa> atualizar(@PathVariable Long id, @RequestBody Caixa caixa) {
        if (!caixaService.buscarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        caixa.setId(id);
        return ResponseEntity.ok(caixaService.salvar(caixa));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!caixaService.buscarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        caixaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
