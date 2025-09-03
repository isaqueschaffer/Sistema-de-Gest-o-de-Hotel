package com.example.Hotel.service;

import com.example.Hotel.model.Hospedagem;
import com.example.Hotel.repository.HospedagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HospedagemService {
    @Autowired
    private HospedagemRepository hospedagemRepository;

    public List<Hospedagem> listarTodos() {
        return hospedagemRepository.findAll();
    }

    public Optional<Hospedagem> buscarPorId(Long id) {
        return hospedagemRepository.findById(id);
    }

    public Hospedagem salvar(Hospedagem hospedagem) {
        
        return hospedagemRepository.save(hospedagem);
    }

    public void deletar(Long id) {
        hospedagemRepository.deleteById(id);
    }

    public Optional<Hospedagem> findByQuartoId(Long quartoId) {
        return hospedagemRepository.findByQuartoId(quartoId);
    }
}
