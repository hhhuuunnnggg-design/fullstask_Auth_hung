package com.example.demo.domain.repository;

import java.util.List;
import java.util.Optional;

import com.example.demo.domain.entity.Role;

public interface RoleRepository {
    Role save(Role role);

    Optional<Role> findById(Long id);

    void deleteById(Long id);

    boolean existsByName(String name);

    List<Role> findAll();
}
