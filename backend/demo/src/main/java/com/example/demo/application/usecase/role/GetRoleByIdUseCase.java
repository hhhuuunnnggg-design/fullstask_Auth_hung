package com.example.demo.application.usecase.role;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.application.dto.response.ResRoleDTO;
import com.example.demo.application.mapper.RoleDtoMapper;
import com.example.demo.domain.entity.Permission;
import com.example.demo.domain.entity.Role;
import com.example.demo.domain.repository.PermissionRepository;
import com.example.demo.domain.repository.RoleRepository;
import com.example.demo.util.error.IdInvalidException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetRoleByIdUseCase {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

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
