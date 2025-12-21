package com.example.demo.application.usecase.user;

import org.springframework.stereotype.Service;

import com.example.demo.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IsEmailExistUseCase {
    private final UserRepository userRepository;

    public boolean execute(String email) {
        return userRepository.existsByEmail(email);
    }
}
