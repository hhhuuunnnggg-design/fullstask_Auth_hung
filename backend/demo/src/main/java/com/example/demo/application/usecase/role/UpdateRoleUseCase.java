package com.example.demo.application.usecase.role;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.application.dto.request.UpdateRoleRequest;
import com.example.demo.application.dto.response.ResRoleDTO;
import com.example.demo.application.mapper.RoleDtoMapper;
import com.example.demo.domain.entity.Permission;
import com.example.demo.domain.entity.Role;
import com.example.demo.domain.port.PermissionRepositoryPort;
import com.example.demo.domain.port.RoleRepositoryPort;
import com.example.demo.util.error.IdInvalidException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateRoleUseCase {
    private final RoleRepositoryPort roleRepository;
    private final PermissionRepositoryPort permissionRepository;

    public ResRoleDTO execute(Long id, UpdateRoleRequest request) throws IdInvalidException {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new IdInvalidException("Role với id = " + id + " không tồn tại"));

        RoleDtoMapper.updateRoleFromRequest(role, request);
        Role updated = roleRepository.save(role);

        // Load permissions
        List<Permission> permissions = null;
        if (updated.getPermissionIds() != null && !updated.getPermissionIds().isEmpty()) {
            permissions = permissionRepository.findByIdIn(updated.getPermissionIds());
        }

        return RoleDtoMapper.toResRoleDTO(updated, permissions);
    }
}
