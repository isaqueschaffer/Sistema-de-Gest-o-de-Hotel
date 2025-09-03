package com.example.Hotel.controller;

import com.example.Hotel.model.Venda;
import com.example.Hotel.service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/vendas")
public class VendaController {
    @Autowired
    private VendaService vendaService;

    @GetMapping
    public List<Venda> listarTodos() {
        return vendaService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venda> buscarPorId(@PathVariable Long id) {
        Optional<Venda> venda = vendaService.buscarPorId(id);
        return venda.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Venda salvar(@RequestBody Venda venda) {
        return vendaService.salvar(venda);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Venda> atualizar(@PathVariable Long id, @RequestBody Venda venda) {
        if (!vendaService.buscarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
    
        return ResponseEntity.ok(vendaService.salvar(venda));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!vendaService.buscarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        vendaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
