package com.example.demo.application.usecase.permission;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.application.dto.request.UpdatePermissionRequest;
import com.example.demo.application.dto.response.ResPermissionDTO;
import com.example.demo.application.mapper.PermissionDtoMapper;
import com.example.demo.domain.entity.Permission;
import com.example.demo.domain.repository.PermissionRepository;
import com.example.demo.util.error.IdInvalidException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdatePermissionUseCase {
    private final PermissionRepository permissionRepository;

    public ResPermissionDTO execute(Long id, UpdatePermissionRequest request) throws IdInvalidException {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new IdInvalidException("Permission với id = " + id + " không tồn tại."));

        // Check if another permission with same module/apiPath/method exists (excluding
        // current)
        if (request.getModule() != null && request.getApiPath() != null && request.getMethod() != null) {
            if (permissionRepository.existsByModuleAndApiPathAndMethod(
                    request.getModule(), request.getApiPath(), request.getMethod())) {
                // Check if it's not the same permission
                Optional<Permission> existing = permissionRepository.findAll().stream()
                        .filter(p -> p.getModule().equals(request.getModule())
                                && p.getApiPath().equals(request.getApiPath())
                                && p.getMethod().equals(request.getMethod())
                                && !p.getId().equals(id))
                        .findFirst();
                if (existing.isPresent()) {
                    throw new IdInvalidException("Permission đã tồn tại.");
                }
            }
        }

        PermissionDtoMapper.updatePermissionFromRequest(permission, request);
        Permission updated = permissionRepository.save(permission);
        return PermissionDtoMapper.toResPermissionDTO(updated);
    }
}
