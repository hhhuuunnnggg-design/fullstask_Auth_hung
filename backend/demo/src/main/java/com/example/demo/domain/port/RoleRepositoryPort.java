package com.example.demo.domain.port;

import com.example.demo.domain.entity.Role;

import java.util.Optional;

import java.util.List;

public interface RoleRepositoryPort {
    Role save(Role role);
    Optional<Role> findById(Long id);
    void deleteById(Long id);
    boolean existsByName(String name);
    List<Role> findAll();
}
