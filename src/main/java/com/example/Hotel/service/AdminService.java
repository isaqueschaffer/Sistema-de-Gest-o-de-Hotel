package com.example.Hotel.service;

import com.example.Hotel.model.Admin;
import com.example.Hotel.repository.AdminRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public List<Admin> listarTodos() {
        return adminRepository.findAll();
    }

    public Optional<Admin> buscarPorId(Long id) {
        return adminRepository.findById(id);
    }

    public Admin salvar(Admin admin) {
        return adminRepository.save(admin);
    }

    public void excluir(Long id) {
        adminRepository.deleteById(id);
    }
}
