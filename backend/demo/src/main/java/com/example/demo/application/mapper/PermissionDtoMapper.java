package com.example.demo.application.mapper;

import com.example.demo.application.dto.request.CreatePermissionRequest;
import com.example.demo.application.dto.request.UpdatePermissionRequest;
import com.example.demo.application.dto.response.ResPermissionDTO;
import com.example.demo.domain.entity.Permission;

public class PermissionDtoMapper {
    public static Permission toDomain(CreatePermissionRequest request) {
        return Permission.builder()
                .name(request.getName())
                .apiPath(request.getApiPath())
                .method(request.getMethod())
                .module(request.getModule())
                .build();
    }

    public static ResPermissionDTO toResPermissionDTO(Permission permission) {
        return ResPermissionDTO.builder()
                .id(permission.getId())
                .name(permission.getName())
                .apiPath(permission.getApiPath())
                .method(permission.getMethod())
                .module(permission.getModule())
                .build();
    }

    public static void updatePermissionFromRequest(Permission permission, UpdatePermissionRequest request) {
        if (request.getName() != null && !request.getName().isBlank()) {
            permission.setName(request.getName());
        }
        if (request.getApiPath() != null && !request.getApiPath().isBlank()) {
            permission.setApiPath(request.getApiPath());
        }
        if (request.getMethod() != null && !request.getMethod().isBlank()) {
            permission.setMethod(request.getMethod());
        }
        if (request.getModule() != null && !request.getModule().isBlank()) {
            permission.setModule(request.getModule());
        }
    }
}
