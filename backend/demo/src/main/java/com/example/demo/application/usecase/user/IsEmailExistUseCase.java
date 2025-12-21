package com.example.demo.application.usecase.user;

import com.example.demo.domain.port.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IsEmailExistUseCase {
    private final UserRepositoryPort userRepository;

    public boolean execute(String email) {
        return userRepository.existsByEmail(email);
    }
}

