package com.example.demo.application.usecase.user;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.domain.entity.User;
import com.example.demo.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetUserByEmailUseCase {
    private final UserRepository userRepository;

    public Optional<User> execute(String email) {
        return userRepository.findByEmail(email);
    }
}
