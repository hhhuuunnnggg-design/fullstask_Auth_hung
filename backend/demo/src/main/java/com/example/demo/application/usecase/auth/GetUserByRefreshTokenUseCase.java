package com.example.demo.application.usecase.auth;

import com.example.demo.domain.entity.User;
import com.example.demo.domain.port.UserRepositoryPort;
import com.example.demo.util.error.IdInvalidException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetUserByRefreshTokenUseCase {
    private final UserRepositoryPort userRepository;

    public User execute(String token, String email) throws IdInvalidException {
        Optional<User> userOpt = userRepository.findByRefreshTokenAndEmail(token, email);
        if (userOpt.isEmpty()) {
            throw new IdInvalidException("Refresh Token không hợp lệ");
        }
        return userOpt.get();
    }
}

