package com.example.demo.application.usecase.role;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.application.dto.request.CreateRoleRequest;
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
public class CreateRoleUseCase {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public ResRoleDTO execute(CreateRoleRequest request) throws IdInvalidException {
        if (roleRepository.existsByName(request.getName())) {
            throw new IdInvalidException("Role với name = " + request.getName() + " đã tồn tại");
        }
        Role role = RoleDtoMapper.toDomain(request);
        Role saved = roleRepository.save(role);

        // Load permissions
        List<Permission> permissions = null;
        if (saved.getPermissionIds() != null && !saved.getPermissionIds().isEmpty()) {
            permissions = permissionRepository.findByIdIn(saved.getPermissionIds());
        }

        return RoleDtoMapper.toResRoleDTO(saved, permissions);
    }
}
