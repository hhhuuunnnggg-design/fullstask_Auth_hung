package com.example.demo.application.usecase.auth;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.domain.entity.User;
import com.example.demo.domain.repository.UserRepository;
import com.example.demo.util.error.IdInvalidException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetUserByRefreshTokenUseCase {
    private final UserRepository userRepository;

    public User execute(String token, String email) throws IdInvalidException {
        Optional<User> userOpt = userRepository.findByRefreshTokenAndEmail(token, email);
        if (userOpt.isEmpty()) {
            throw new IdInvalidException("Refresh Token không hợp lệ");
        }
        return userOpt.get();
    }
}
