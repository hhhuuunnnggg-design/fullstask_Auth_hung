package com.example.demo.application.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.application.dto.request.CreateRoleRequest;
import com.example.demo.application.dto.request.UpdateRoleRequest;
import com.example.demo.application.dto.response.ResRoleDTO;
import com.example.demo.domain.entity.Permission;
import com.example.demo.domain.entity.Role;

public class RoleDtoMapper {
    public static Role toDomain(CreateRoleRequest request) {
        return Role.builder()
                .name(request.getName())
                .description(request.getDescription())
                .active(request.getActive())
                .permissionIds(request.getPermissionIds())
                .build();
    }

    public static ResRoleDTO toResRoleDTO(Role role, List<Permission> permissions) {
        List<com.example.demo.application.dto.response.ResPermissionDTO> permissionDTOs = null;
        if (permissions != null && !permissions.isEmpty()) {
            permissionDTOs = permissions.stream()
                    .map(p -> com.example.demo.application.dto.response.ResPermissionDTO.builder()
                            .id(p.getId())
                            .name(p.getName())
                            .apiPath(p.getApiPath())
                            .method(p.getMethod())
                            .module(p.getModule())
                            .build())
                    .collect(Collectors.toList());
        }

        return ResRoleDTO.builder()
                .id(role.getId())
                .name(role.getName())
                .description(role.getDescription())
                .active(role.getActive())
                .permissions(permissionDTOs)
                .build();
    }

    public static void updateRoleFromRequest(Role role, UpdateRoleRequest request) {
        if (request.getName() != null && !request.getName().isBlank()) {
            role.setName(request.getName());
        }
        if (request.getDescription() != null) {
            role.setDescription(request.getDescription());
        }
        if (request.getActive() != null) {
            role.setActive(request.getActive());
        }
        if (request.getPermissionIds() != null) {
            role.setPermissionIds(request.getPermissionIds());
        }
    }
}
