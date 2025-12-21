package com.example.demo.application.usecase.permission;

import org.springframework.stereotype.Service;

import com.example.demo.application.dto.response.ResPermissionDTO;
import com.example.demo.application.mapper.PermissionDtoMapper;
import com.example.demo.domain.entity.Permission;
import com.example.demo.domain.repository.PermissionRepository;
import com.example.demo.util.error.IdInvalidException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetPermissionByIdUseCase {
    private final PermissionRepository permissionRepository;

    public ResPermissionDTO execute(Long id) throws IdInvalidException {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new IdInvalidException("Permission với id = " + id + " không tồn tại."));
        return PermissionDtoMapper.toResPermissionDTO(permission);
    }
}
