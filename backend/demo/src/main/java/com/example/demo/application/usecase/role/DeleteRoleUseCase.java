package com.example.demo.application.usecase.role;

import org.springframework.stereotype.Service;

import com.example.demo.domain.repository.RoleRepository;
import com.example.demo.util.error.IdInvalidException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeleteRoleUseCase {
    private final RoleRepository roleRepository;

    public void execute(Long id) throws IdInvalidException {
        if (!roleRepository.findById(id).isPresent()) {
            throw new IdInvalidException("Role với id = " + id + " không tồn tại");
        }
        roleRepository.deleteById(id);
    }
}
