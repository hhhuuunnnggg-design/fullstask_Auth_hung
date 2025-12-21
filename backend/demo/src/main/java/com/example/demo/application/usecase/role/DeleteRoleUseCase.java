package com.example.demo.application.usecase.role;

import com.example.demo.domain.port.RoleRepositoryPort;
import com.example.demo.util.error.IdInvalidException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteRoleUseCase {
    private final RoleRepositoryPort roleRepository;

    public void execute(Long id) throws IdInvalidException {
        if (!roleRepository.findById(id).isPresent()) {
            throw new IdInvalidException("Role với id = " + id + " không tồn tại");
        }
        roleRepository.deleteById(id);
    }
}

