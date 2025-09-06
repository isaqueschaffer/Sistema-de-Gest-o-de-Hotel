package com.example.Hotel.controller;

import com.example.Hotel.model.Recepcionista;
import com.example.Hotel.service.RecepcionistaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recepcionistas")
public class RecepcionistaController {

    private final RecepcionistaService recepcionistaService;

    public RecepcionistaController(RecepcionistaService recepcionistaService) {
        this.recepcionistaService = recepcionistaService;
    }

    @GetMapping
    public List<Recepcionista> listarTodos() {
        return recepcionistaService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recepcionista> buscarPorId(@PathVariable Long id) {
        return recepcionistaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Recepcionista salvar(@RequestBody Recepcionista recepcionista) {
        return recepcionistaService.salvar(recepcionista);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Recepcionista> atualizar(@PathVariable Long id, @RequestBody Recepcionista recepcionista) {
        if (!recepcionistaService.buscarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        recepcionista.setId(id);
        return ResponseEntity.ok(recepcionistaService.salvar(recepcionista));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (!recepcionistaService.buscarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        recepcionistaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
