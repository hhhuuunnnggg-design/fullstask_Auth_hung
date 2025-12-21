package com.example.demo.application.usecase.role;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.demo.application.dto.response.ResRoleDTO;
import com.example.demo.application.dto.response.ResultPaginationDTO;
import com.example.demo.application.mapper.RoleDtoMapper;
import com.example.demo.domain.entity.Permission;
import com.example.demo.domain.entity.Role;
import com.example.demo.domain.port.PermissionRepositoryPort;
import com.example.demo.domain.port.RoleRepositoryPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetAllRolesUseCase {
    private final RoleRepositoryPort roleRepository;
    private final PermissionRepositoryPort permissionRepository;

    public ResultPaginationDTO execute(Specification<?> spec, int page, int size) {
        // Note: Simplified version - need proper Specification handling
        List<Role> roles = roleRepository.findAll();

        ResultPaginationDTO result = new ResultPaginationDTO();
        ResultPaginationDTO.Meta meta = new ResultPaginationDTO.Meta();
        meta.setPage(page);
        meta.setPageSize(size);
        meta.setTotal(roles.size());
        meta.setPages((int) Math.ceil((double) roles.size() / size));
        result.setMeta(meta);

        List<ResRoleDTO> roleDTOs = roles.stream()
                .map(role -> {
                    List<Permission> permissions = null;
                    if (role.getPermissionIds() != null && !role.getPermissionIds().isEmpty()) {
                        permissions = permissionRepository.findByIdIn(role.getPermissionIds());
                    }
                    return RoleDtoMapper.toResRoleDTO(role, permissions);
                })
                .collect(Collectors.toList());

        result.setResult(roleDTOs);
        return result;
    }
}
