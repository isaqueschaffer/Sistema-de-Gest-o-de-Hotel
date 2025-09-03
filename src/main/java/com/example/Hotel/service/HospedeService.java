package com.example.Hotel.service;

import com.example.Hotel.model.Hospede;
import com.example.Hotel.repository.HospedeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HospedeService {
    @Autowired
    private HospedeRepository hospedeRepository;

    public List<Hospede> listarTodos() {
        return hospedeRepository.findAll();
    }

    public Optional<Hospede> buscarPorId(Long id) {
        return hospedeRepository.findById(id);
    }

    public Hospede salvar(Hospede hospede) {
        return hospedeRepository.save(hospede);
    }

    public void deletar(Long id) {
        hospedeRepository.deleteById(id);
    }
}
