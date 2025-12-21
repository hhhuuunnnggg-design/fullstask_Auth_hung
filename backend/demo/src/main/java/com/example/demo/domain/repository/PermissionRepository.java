package com.example.demo.domain.repository;

import java.util.List;
import java.util.Optional;

import com.example.demo.domain.entity.Permission;

public interface PermissionRepository {
    Permission save(Permission permission);

    Optional<Permission> findById(Long id);

    void deleteById(Long id);

    List<Permission> findByIdIn(List<Long> ids);

    List<Permission> findAll();

    boolean existsByModuleAndApiPathAndMethod(String module, String apiPath, String method);
}
