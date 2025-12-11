package com.example.Hotel.repository;

import com.example.Hotel.model.Usuario;
import com.example.Hotel.model.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // 🔐 Login (autenticação)
    Optional<Usuario> findByLogin(String login);

    // ✔ Busca por CPF
    Optional<Usuario> findByCpf(String cpf);

    // ✔ Verifica se login já existe
    boolean existsByLogin(String login);

    // ✔ Verifica se CPF já existe
    boolean existsByCpf(String cpf);

    // ✔ Buscar todos os usuários por perfil (ADMIN, RECEPCIONISTA, GERENTE)
    List<Usuario> findByPerfil(Perfil perfil);

    // ✔ Buscar recepcionistas por turno (se você quiser filtrar isso)
    List<Usuario> findByTurno(String turno);

    // ✔ Buscar por nome contendo (para filtros em tabelas)
    List<Usuario> findByNomeContainingIgnoreCase(String nome);

    
}
