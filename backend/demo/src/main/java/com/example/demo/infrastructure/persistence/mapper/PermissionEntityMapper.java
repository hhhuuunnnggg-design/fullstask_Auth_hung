package com.example.demo.infrastructure.persistence.mapper;

import com.example.demo.domain.entity.Permission;
import com.example.demo.infrastructure.persistence.entity.PermissionEntity;

public class PermissionEntityMapper {
    public static Permission toDomain(PermissionEntity entity) {
        if (entity == null)
            return null;
        return Permission.builder()
                .id(entity.getId())
                .name(entity.getName())
                .apiPath(entity.getApiPath())
                .method(entity.getMethod())
                .module(entity.getModule())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .createdBy(entity.getCreatedBy())
                .updatedBy(entity.getUpdatedBy())
                .build();
    }

    public static PermissionEntity toEntity(Permission domain) {
        if (domain == null)
            return null;
        return PermissionEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .apiPath(domain.getApiPath())
                .method(domain.getMethod())
                .module(domain.getModule())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .createdBy(domain.getCreatedBy())
                .updatedBy(domain.getUpdatedBy())
                .build();
    }
}
