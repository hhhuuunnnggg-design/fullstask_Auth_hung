package com.example.demo.application.usecase.permission;

import org.springframework.stereotype.Service;

import com.example.demo.application.dto.request.CreatePermissionRequest;
import com.example.demo.application.dto.response.ResPermissionDTO;
import com.example.demo.application.mapper.PermissionDtoMapper;
import com.example.demo.domain.entity.Permission;
import com.example.demo.domain.repository.PermissionRepository;
import com.example.demo.util.error.IdInvalidException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreatePermissionUseCase {
    private final PermissionRepository permissionRepository;

    public ResPermissionDTO execute(CreatePermissionRequest request) throws IdInvalidException {
        // Check if permission already exists
        if (permissionRepository.existsByModuleAndApiPathAndMethod(
                request.getModule(), request.getApiPath(), request.getMethod())) {
            throw new IdInvalidException("Permission đã tồn tại.");
        }

        Permission permission = PermissionDtoMapper.toDomain(request);
        Permission saved = permissionRepository.save(permission);
        return PermissionDtoMapper.toResPermissionDTO(saved);
    }
}
