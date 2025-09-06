package com.example.Hotel.controller;

import com.example.Hotel.model.Admin;
import com.example.Hotel.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admins")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    public List<Admin> listarTodos() {
        return adminService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Admin> buscarPorId(@PathVariable Long id) {
        return adminService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Admin salvar(@RequestBody Admin admin) {
        return adminService.salvar(admin);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Admin> atualizar(@PathVariable Long id, @RequestBody Admin admin) {
        if (!adminService.buscarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        admin.setId(id);
        return ResponseEntity.ok(adminService.salvar(admin));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (!adminService.buscarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        adminService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
