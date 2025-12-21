package com.example.demo.application.usecase.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.application.dto.request.CreateUserRequest;
import com.example.demo.application.dto.response.ResCreateUserDTO;
import com.example.demo.application.mapper.UserDtoMapper;
import com.example.demo.domain.entity.User;
import com.example.demo.domain.port.RoleRepositoryPort;
import com.example.demo.domain.port.UserRepositoryPort;
import com.example.demo.util.error.IdInvalidException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateUserUseCase {
    private final UserRepositoryPort userRepository;
    private final RoleRepositoryPort roleRepository;
    private final PasswordEncoder passwordEncoder;

    public ResCreateUserDTO execute(CreateUserRequest request) throws IdInvalidException {
        // Check if email exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IdInvalidException(
                    "Email " + request.getEmail() + " đã tồn tại, vui lòng sử dụng email khác.");
        }

        // Validate role if provided
        if (request.getRoleId() != null) {
            roleRepository.findById(request.getRoleId())
                    .orElseThrow(
                            () -> new IdInvalidException("Role với id = " + request.getRoleId() + " không tồn tại"));
        }

        // Create user
        User user = UserDtoMapper.toDomain(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        User savedUser = userRepository.save(user);

        return UserDtoMapper.toResCreateUserDTO(savedUser);
    }
}
