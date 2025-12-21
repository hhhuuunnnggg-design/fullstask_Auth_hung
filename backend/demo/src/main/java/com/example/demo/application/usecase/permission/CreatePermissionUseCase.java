package com.example.demo.application.usecase.permission;

import com.example.demo.application.dto.request.CreatePermissionRequest;
import com.example.demo.application.dto.response.ResPermissionDTO;
import com.example.demo.application.mapper.PermissionDtoMapper;
import com.example.demo.domain.entity.Permission;
import com.example.demo.domain.port.PermissionRepositoryPort;
import com.example.demo.util.error.IdInvalidException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreatePermissionUseCase {
    private final PermissionRepositoryPort permissionRepository;

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

