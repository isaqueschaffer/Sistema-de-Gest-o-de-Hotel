package com.example.Hotel.controller;

import com.example.Hotel.model.Hospedagem;
import com.example.Hotel.service.HospedagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/hospedagens")
public class HospedagemController {
    @Autowired
    private HospedagemService hospedagemService;

    @GetMapping
    public List<Hospedagem> listarTodos() {
        return hospedagemService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hospedagem> buscarPorId(@PathVariable Long id) {
        Optional<Hospedagem> hospedagem = hospedagemService.buscarPorId(id);
        return hospedagem.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


     // Buscar hospedagem pelo quarto
    @GetMapping("/quarto/{quartoId}")
    public ResponseEntity<Hospedagem> getByQuartoId(@PathVariable Long quartoId) {
        Optional<Hospedagem> hospedagem = hospedagemService.findByQuartoId(quartoId);
        return hospedagem.map(ResponseEntity::ok)
                         .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("existe{quartoId}")
    public boolean existeHospedagemAtivaNoQuarto(@PathVariable Long quartoId) {
        List<Hospedagem> hospedagens = hospedagemService.listarTodos();
        for (Hospedagem h : hospedagens) {
            if (h.getQuarto().getId().equals(quartoId) && h.getDataSaida() == null) {
                return true; // Existe uma hospedagem ativa no quarto
            }
        }
        return false; // Não existe hospedagem ativa no quarto
    }   

    @PostMapping
    public Hospedagem salvar(@RequestBody Hospedagem hospedagem) {
        return hospedagemService.salvar(hospedagem);
    }

    @PutMapping("/{id}")
public ResponseEntity<Hospedagem> atualizar(@PathVariable Long id, @RequestBody Hospedagem hospedagem) {
    return hospedagemService.buscarPorId(id)
        .map(hospedagemExistente -> {
            // Força o ID recebido na URL
            hospedagem.setId(id);
            return ResponseEntity.ok(hospedagemService.salvar(hospedagem));
        })
        .orElse(ResponseEntity.notFound().build());
}

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!hospedagemService.buscarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        hospedagemService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
