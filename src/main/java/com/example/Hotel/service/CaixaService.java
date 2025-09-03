package com.example.Hotel.service;

import com.example.Hotel.model.Caixa;
import com.example.Hotel.repository.CaixaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CaixaService {
    @Autowired
    private CaixaRepository caixaRepository;

    public List<Caixa> listarTodos() {
        return caixaRepository.findAll();
    }

    public Optional<Caixa> buscarPorId(Long id) {
        return caixaRepository.findById(id);
    }

    public Caixa salvar(Caixa caixa) {
        return caixaRepository.save(caixa);
    }

    public void deletar(Long id) {
        caixaRepository.deleteById(id);
    }
}
