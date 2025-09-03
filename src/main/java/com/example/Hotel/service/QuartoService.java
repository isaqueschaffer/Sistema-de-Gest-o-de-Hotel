package com.example.Hotel.service;

import com.example.Hotel.model.Quarto;
import com.example.Hotel.repository.QuartoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuartoService {
    @Autowired
    private QuartoRepository quartoRepository;

    public List<Quarto> listarTodos() {
        return quartoRepository.findAll();
    }

    public Optional<Quarto> buscarPorId(Long id) {
        return quartoRepository.findById(id);
    }

    public Optional<Quarto> buscarPorNumero(Integer numero) {
        return quartoRepository.findByNumero(numero);
    }

    public boolean existsByNumero(Integer numero) {
        return quartoRepository.existsByNumero(numero);
    }   

    public Quarto salvar(Quarto quarto) {
        return quartoRepository.save(quarto);
    }

    public void deletar(Long id) {
        quartoRepository.deleteById(id);
    }
}
