package com.example.demo.application.usecase.role;

import com.example.demo.application.dto.response.ResRoleDTO;
import com.example.demo.application.mapper.RoleDtoMapper;
import com.example.demo.domain.entity.Permission;
import com.example.demo.domain.entity.Role;
import com.example.demo.domain.port.PermissionRepositoryPort;
import com.example.demo.domain.port.RoleRepositoryPort;
import com.example.demo.util.error.IdInvalidException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetRoleByIdUseCase {
    private final RoleRepositoryPort roleRepository;
    private final PermissionRepositoryPort permissionRepository;

    public ResRoleDTO execute(Long id) throws IdInvalidException {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new IdInvalidException("Role với id = " + id + " không tồn tại"));

        // Load permissions
        List<Permission> permissions = null;
        if (role.getPermissionIds() != null && !role.getPermissionIds().isEmpty()) {
            permissions = permissionRepository.findByIdIn(role.getPermissionIds());
        }

        return RoleDtoMapper.toResRoleDTO(role, permissions);
    }
}

