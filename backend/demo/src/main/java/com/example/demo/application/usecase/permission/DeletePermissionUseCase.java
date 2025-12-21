package com.example.demo.application.usecase.permission;

import com.example.demo.domain.port.PermissionRepositoryPort;
import com.example.demo.util.error.IdInvalidException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeletePermissionUseCase {
    private final PermissionRepositoryPort permissionRepository;

    public void execute(Long id) throws IdInvalidException {
        if (!permissionRepository.findById(id).isPresent()) {
            throw new IdInvalidException("Permission với id = " + id + " không tồn tại.");
        }
        permissionRepository.deleteById(id);
    }
}

