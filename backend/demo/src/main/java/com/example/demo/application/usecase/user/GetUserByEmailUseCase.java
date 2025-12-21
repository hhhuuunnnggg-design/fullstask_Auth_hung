package com.example.demo.application.usecase.user;

import com.example.demo.domain.entity.User;
import com.example.demo.domain.port.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetUserByEmailUseCase {
    private final UserRepositoryPort userRepository;

    public Optional<User> execute(String email) {
        return userRepository.findByEmail(email);
    }
}

