package com.example.demo.application.usecase.permission;

import com.example.demo.application.dto.response.ResPermissionDTO;
import com.example.demo.application.mapper.PermissionDtoMapper;
import com.example.demo.domain.entity.Permission;
import com.example.demo.domain.port.PermissionRepositoryPort;
import com.example.demo.util.error.IdInvalidException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetPermissionByIdUseCase {
    private final PermissionRepositoryPort permissionRepository;

    public ResPermissionDTO execute(Long id) throws IdInvalidException {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new IdInvalidException("Permission với id = " + id + " không tồn tại."));
        return PermissionDtoMapper.toResPermissionDTO(permission);
    }
}

