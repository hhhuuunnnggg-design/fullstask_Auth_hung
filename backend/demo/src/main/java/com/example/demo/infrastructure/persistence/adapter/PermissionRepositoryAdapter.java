package com.example.demo.infrastructure.persistence.adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.demo.domain.entity.Permission;
import com.example.demo.domain.port.PermissionRepositoryPort;
import com.example.demo.infrastructure.persistence.mapper.PermissionEntityMapper;
import com.example.demo.infrastructure.persistence.repository.PermissionJpaRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PermissionRepositoryAdapter implements PermissionRepositoryPort {
    private final PermissionJpaRepository jpaRepository;

    @Override
    public Permission save(Permission permission) {
        return PermissionEntityMapper.toDomain(
                jpaRepository.save(PermissionEntityMapper.toEntity(permission)));
    }

    @Override
    public Optional<Permission> findById(Long id) {
        return jpaRepository.findById(id)
                .map(PermissionEntityMapper::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public List<Permission> findByIdIn(List<Long> ids) {
        return jpaRepository.findByIdIn(ids).stream()
                .map(PermissionEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Permission> findAll() {
        return jpaRepository.findAll().stream()
                .map(PermissionEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByModuleAndApiPathAndMethod(String module, String apiPath, String method) {
        return jpaRepository.existsByModuleAndApiPathAndMethod(module, apiPath, method);
    }
}
