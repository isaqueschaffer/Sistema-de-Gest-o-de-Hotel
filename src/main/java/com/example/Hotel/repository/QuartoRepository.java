package com.example.Hotel.repository;

import com.example.Hotel.model.Quarto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuartoRepository extends JpaRepository<Quarto, Long> {

    // Busca um quarto pelo número
    Optional<Quarto> findByNumero(Integer numero);

    // Se quiser verificar se já existe um quarto com esse número
    boolean existsByNumero(Integer numero);

    boolean existsByNumeroAndStatus(Integer numero, String status);
}
