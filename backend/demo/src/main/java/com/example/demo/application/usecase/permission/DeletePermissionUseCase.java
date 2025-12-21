package com.example.demo.application.usecase.permission;

import org.springframework.stereotype.Service;

import com.example.demo.domain.repository.PermissionRepository;
import com.example.demo.util.error.IdInvalidException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeletePermissionUseCase {
    private final PermissionRepository permissionRepository;

    public void execute(Long id) throws IdInvalidException {
        if (!permissionRepository.findById(id).isPresent()) {
            throw new IdInvalidException("Permission với id = " + id + " không tồn tại.");
        }
        permissionRepository.deleteById(id);
    }
}
