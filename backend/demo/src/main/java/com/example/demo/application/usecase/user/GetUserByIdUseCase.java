package com.example.demo.application.usecase.user;

import org.springframework.stereotype.Service;

import com.example.demo.application.dto.response.ResUserDTO;
import com.example.demo.application.mapper.UserDtoMapper;
import com.example.demo.domain.entity.Role;
import com.example.demo.domain.entity.User;
import com.example.demo.domain.repository.RoleRepository;
import com.example.demo.domain.repository.UserRepository;
import com.example.demo.util.error.IdInvalidException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetUserByIdUseCase {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public ResUserDTO execute(Long id) throws IdInvalidException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IdInvalidException("User với id = " + id + " không tồn tại"));

        Role role = null;
        if (user.getRoleId() != null) {
            role = roleRepository.findById(user.getRoleId()).orElse(null);
        }

        return UserDtoMapper.toResUserDTO(user, role);
    }
}
