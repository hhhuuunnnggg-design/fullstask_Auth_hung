package com.example.demo.infrastructure.persistence.mapper;

import java.util.stream.Collectors;

import com.example.demo.domain.entity.Role;
import com.example.demo.infrastructure.persistence.entity.RoleEntity;

public class RoleEntityMapper {
    public static Role toDomain(RoleEntity entity) {
        if (entity == null)
            return null;
        return Role.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .active(entity.getActive())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .createdBy(entity.getCreatedBy())
                .updatedBy(entity.getUpdatedBy())
                .permissionIds(entity.getPermissions() != null
                        ? entity.getPermissions().stream().map(p -> p.getId()).collect(Collectors.toList())
                        : null)
                .build();
    }

    public static RoleEntity toEntity(Role domain) {
        if (domain == null)
            return null;
        return RoleEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .description(domain.getDescription())
                .active(domain.getActive())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .createdBy(domain.getCreatedBy())
                .updatedBy(domain.getUpdatedBy())
                .build();
    }
}
