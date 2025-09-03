package com.example.Hotel.repository;

import com.example.Hotel.model.Hospedagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface HospedagemRepository extends JpaRepository<Hospedagem, Long> {

Optional<Hospedagem> findByQuartoId(Long quartoId);

}
