package com.example.demo.domain.port;

import java.util.List;
import java.util.Optional;

import com.example.demo.domain.entity.Permission;

public interface PermissionRepositoryPort {
    Permission save(Permission permission);

    Optional<Permission> findById(Long id);

    void deleteById(Long id);

    List<Permission> findByIdIn(List<Long> ids);

    List<Permission> findAll();

    boolean existsByModuleAndApiPathAndMethod(String module, String apiPath, String method);
}
