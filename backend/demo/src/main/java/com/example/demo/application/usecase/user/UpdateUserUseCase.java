package com.example.demo.application.usecase.user;

import org.springframework.stereotype.Service;

import com.example.demo.application.dto.request.UpdateUserRequest;
import com.example.demo.application.dto.response.ResUpdateUserDTO;
import com.example.demo.application.mapper.UserDtoMapper;
import com.example.demo.domain.entity.User;
import com.example.demo.domain.repository.RoleRepository;
import com.example.demo.domain.repository.UserRepository;
import com.example.demo.util.error.IdInvalidException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateUserUseCase {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public ResUpdateUserDTO execute(Long id, UpdateUserRequest request) throws IdInvalidException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IdInvalidException("User với id = " + id + " không tồn tại"));

        // Validate role if provided
        if (request.getRoleId() != null) {
            roleRepository.findById(request.getRoleId())
                    .orElseThrow(
                            () -> new IdInvalidException("Role với id = " + request.getRoleId() + " không tồn tại"));
        }

        // Update user
        UserDtoMapper.updateUserFromRequest(user, request);
        User updatedUser = userRepository.save(user);

        return UserDtoMapper.toResUpdateUserDTO(updatedUser);
    }
}
