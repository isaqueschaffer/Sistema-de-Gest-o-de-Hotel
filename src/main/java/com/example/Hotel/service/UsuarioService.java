package com.example.Hotel.service;

import com.example.Hotel.model.Usuario;
import com.example.Hotel.dto.UsuarioRequest;
import com.example.Hotel.model.Perfil;
import com.example.Hotel.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

   private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // ================================
    //   MÉTODOS PRINCIPAIS
    // ================================

    // Criar usuário
    public Usuario criarUsuario(Usuario usuario) {

        validarConflitos(usuario.getLogin(), usuario.getCpf(), null);

        usuario.setSenha(criptografarSenha(usuario.getSenha()));

        return usuarioRepository.save(usuario);
    }

    // Criar usuario via DTO
    public Usuario cadastrarUsuario(UsuarioRequest dto) {

        validarConflitos(dto.login(), dto.cpf(), null);

        Usuario usuario = new Usuario();
        usuario.setLogin(dto.login());
        usuario.setSenha(criptografarSenha(dto.senha()));
        usuario.setNome(dto.nome());
        usuario.setCpf(dto.cpf());
        usuario.setPerfil(dto.perfil());
        usuario.setTurno(dto.turno());

        return usuarioRepository.save(usuario);
    }

    // Atualizar
    public Usuario atualizarUsuario(Long id, Usuario dados) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        validarConflitos(dados.getLogin(), dados.getCpf(), id);

        usuario.setNome(dados.getNome());
        usuario.setLogin(dados.getLogin());
        usuario.setCpf(dados.getCpf());
        usuario.setPerfil(dados.getPerfil());
        usuario.setTurno(dados.getTurno());

        if (dados.getSenha() != null && !dados.getSenha().isBlank()) {
            usuario.setSenha(criptografarSenha(dados.getSenha()));
        }

        return usuarioRepository.save(usuario);
    }

    // Login
    public Usuario login(String login, String senhaDigitada) {

        Usuario usuario = usuarioRepository.findByLogin(login)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        if (!passwordEncoder.matches(senhaDigitada, usuario.getSenha())) {
            throw new RuntimeException("Senha incorreta.");
        }

        return usuario;
    }

    // Buscar por login
    public Usuario buscarPorLogin(String login) {
        return usuarioRepository.findByLogin(login).orElse(null);
    }


    // ================================
    //   MÉTODOS AUXILIARES (PRIVADOS)
    // ================================

    // criptografar senha
    private String criptografarSenha(String senha) {
        return passwordEncoder.encode(senha);
    }

    // Valida se login/CPF já existem
    private void validarConflitos(String login, String cpf, Long idAtual) {

        // LOGIN
        usuarioRepository.findByLogin(login).ifPresent(u -> {
            if (idAtual == null || !u.getId().equals(idAtual)) {
                throw new RuntimeException("Login já está em uso.");
            }
        });

        // CPF
        usuarioRepository.findByCpf(cpf).ifPresent(u -> {
            if (idAtual == null || !u.getId().equals(idAtual)) {
                throw new RuntimeException("CPF já está cadastrado.");
            }
        });
    }

    public boolean validarSenha(String senhaDigitada, String senhaHash) {
    return passwordEncoder.matches(senhaDigitada, senhaHash);
}

public List<Usuario> buscarTodos() {
    return usuarioRepository.findAll();
}

public Usuario buscarPorId(Long id) {
    return usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
}

public void deletarUsuario(Long id) {
    Usuario usuario = buscarPorId(id);
    usuarioRepository.delete(usuario);
}




}
