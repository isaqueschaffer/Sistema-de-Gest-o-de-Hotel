package com.example.Hotel.service;

import com.example.Hotel.model.Recepcionista;
import com.example.Hotel.repository.RecepcionistaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecepcionistaService {

    private final RecepcionistaRepository recepcionistaRepository;

    public RecepcionistaService(RecepcionistaRepository recepcionistaRepository) {
        this.recepcionistaRepository = recepcionistaRepository;
    }

    public List<Recepcionista> listarTodos() {
        return recepcionistaRepository.findAll();
    }

    public Optional<Recepcionista> buscarPorId(Long id) {
        return recepcionistaRepository.findById(id);
    }

    public Recepcionista salvar(Recepcionista recepcionista) {
        return recepcionistaRepository.save(recepcionista);
    }

    public void excluir(Long id) {
        recepcionistaRepository.deleteById(id);
    }
}
