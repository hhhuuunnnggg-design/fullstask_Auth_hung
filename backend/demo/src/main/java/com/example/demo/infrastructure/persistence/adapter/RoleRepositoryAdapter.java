package com.example.demo.infrastructure.persistence.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.demo.domain.entity.Role;
import com.example.demo.domain.port.RoleRepositoryPort;
import com.example.demo.infrastructure.persistence.entity.PermissionEntity;
import com.example.demo.infrastructure.persistence.entity.RoleEntity;
import com.example.demo.infrastructure.persistence.mapper.RoleEntityMapper;
import com.example.demo.infrastructure.persistence.repository.PermissionJpaRepository;
import com.example.demo.infrastructure.persistence.repository.RoleJpaRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RoleRepositoryAdapter implements RoleRepositoryPort {
    private final RoleJpaRepository jpaRepository;
    private final PermissionJpaRepository permissionJpaRepository;

    @Override
    public Role save(Role role) {
        RoleEntity entity;

        // If updating existing role, load it first to preserve permissions if not
        // provided
        if (role.getId() != null) {
            Optional<RoleEntity> existingOpt = jpaRepository.findById(role.getId());
            if (existingOpt.isPresent()) {
                entity = existingOpt.get();
                // Update fields
                entity.setName(role.getName());
                entity.setDescription(role.getDescription());
                entity.setActive(role.getActive());

                // Only update permissions if permissionIds are provided (not null)
                // Following old logic: if permissionIds is provided, load and set permissions
                if (role.getPermissionIds() != null) {
                    if (role.getPermissionIds().isEmpty()) {
                        // Empty list means clear all permissions
                        entity.setPermissions(new ArrayList<>());
                    } else {
                        // Load permissions from DB and set them
                        List<PermissionEntity> permissions = permissionJpaRepository
                                .findByIdIn(role.getPermissionIds());
                        entity.setPermissions(permissions);
                    }
                }
                // If permissionIds is null, keep existing permissions (don't touch)
            } else {
                // Role ID provided but not found - treat as new
                entity = RoleEntityMapper.toEntity(role);
                // For new entity, set permissions if provided
                if (role.getPermissionIds() != null && !role.getPermissionIds().isEmpty()) {
                    List<PermissionEntity> permissions = permissionJpaRepository.findByIdIn(role.getPermissionIds());
                    entity.setPermissions(permissions);
                } else {
                    entity.setPermissions(new ArrayList<>());
                }
            }
        } else {
            // Creating new role
            entity = RoleEntityMapper.toEntity(role);
            // Load and set permissions if permissionIds are provided
            if (role.getPermissionIds() != null && !role.getPermissionIds().isEmpty()) {
                List<PermissionEntity> permissions = permissionJpaRepository.findByIdIn(role.getPermissionIds());
                entity.setPermissions(permissions);
            } else {
                // New role without permissions - set empty list
                entity.setPermissions(new ArrayList<>());
            }
        }

        RoleEntity saved = jpaRepository.save(entity);
        return RoleEntityMapper.toDomain(saved);
    }

    @Override
    public Optional<Role> findById(Long id) {
        return jpaRepository.findById(id)
                .map(RoleEntityMapper::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return jpaRepository.existsByName(name);
    }

    @Override
    public List<Role> findAll() {
        return jpaRepository.findAll().stream()
                .map(RoleEntityMapper::toDomain)
                .collect(Collectors.toList());
    }
}
