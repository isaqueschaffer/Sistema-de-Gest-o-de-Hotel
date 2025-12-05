package com.example.Hotel.repository;

import com.example.Hotel.model.Hospedagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface HospedagemRepository extends JpaRepository<Hospedagem, Long> {

Optional<Hospedagem> findByQuartoId(Long quartoId);


 @Query("SELECT h FROM Hospedagem h " +
           "JOIN FETCH h.hospede " +
           "JOIN FETCH h.quarto")
    List<Hospedagem> findAllWithRelations();

}
