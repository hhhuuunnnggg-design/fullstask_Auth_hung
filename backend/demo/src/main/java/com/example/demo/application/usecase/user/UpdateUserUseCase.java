package com.example.demo.application.usecase.user;

import com.example.demo.application.dto.request.UpdateUserRequest;
import com.example.demo.application.dto.response.ResUpdateUserDTO;
import com.example.demo.application.mapper.UserDtoMapper;
import com.example.demo.domain.entity.User;
import com.example.demo.domain.port.RoleRepositoryPort;
import com.example.demo.domain.port.UserRepositoryPort;
import com.example.demo.util.error.IdInvalidException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateUserUseCase {
    private final UserRepositoryPort userRepository;
    private final RoleRepositoryPort roleRepository;

    public ResUpdateUserDTO execute(Long id, UpdateUserRequest request) throws IdInvalidException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IdInvalidException("User với id = " + id + " không tồn tại"));

        // Validate role if provided
        if (request.getRoleId() != null) {
            roleRepository.findById(request.getRoleId())
                    .orElseThrow(() -> new IdInvalidException("Role với id = " + request.getRoleId() + " không tồn tại"));
        }

        // Update user
        UserDtoMapper.updateUserFromRequest(user, request);
        User updatedUser = userRepository.save(user);

        return UserDtoMapper.toResUpdateUserDTO(updatedUser);
    }
}

