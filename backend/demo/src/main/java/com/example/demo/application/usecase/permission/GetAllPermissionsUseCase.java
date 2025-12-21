package com.example.demo.application.usecase.permission;

import com.example.demo.application.dto.response.ResPermissionDTO;
import com.example.demo.application.dto.response.ResultPaginationDTO;
import com.example.demo.application.mapper.PermissionDtoMapper;
import com.example.demo.domain.entity.Permission;
import com.example.demo.domain.port.PermissionRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetAllPermissionsUseCase {
    private final PermissionRepositoryPort permissionRepository;

    public ResultPaginationDTO execute(Specification<?> spec, int page, int size) {
        // Note: Simplified version - need proper Specification handling
        List<Permission> permissions = permissionRepository.findAll();
        
        ResultPaginationDTO result = new ResultPaginationDTO();
        ResultPaginationDTO.Meta meta = new ResultPaginationDTO.Meta();
        meta.setPage(page);
        meta.setPageSize(size);
        meta.setTotal(permissions.size());
        meta.setPages((int) Math.ceil((double) permissions.size() / size));
        result.setMeta(meta);

        List<ResPermissionDTO> permissionDTOs = permissions.stream()
                .map(PermissionDtoMapper::toResPermissionDTO)
                .collect(Collectors.toList());

        result.setResult(permissionDTOs);
        return result;
    }
}

