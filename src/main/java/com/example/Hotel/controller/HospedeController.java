package com.example.Hotel.controller;

import com.example.Hotel.model.Hospede;
import com.example.Hotel.service.HospedeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/hospedes")
public class HospedeController {
    @Autowired
    private HospedeService hospedeService;

    @GetMapping
    public List<Hospede> listarTodos() {
        return hospedeService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hospede> buscarPorId(@PathVariable Long id) {
        Optional<Hospede> hospede = hospedeService.buscarPorId(id);
        return hospede.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Hospede salvar(@RequestBody Hospede hospede) {
        return hospedeService.salvar(hospede);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Hospede> atualizar(@PathVariable Long id, @RequestBody Hospede hospede) {
        if (!hospedeService.buscarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
     
        return ResponseEntity.ok(hospedeService.salvar(hospede));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!hospedeService.buscarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        hospedeService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
