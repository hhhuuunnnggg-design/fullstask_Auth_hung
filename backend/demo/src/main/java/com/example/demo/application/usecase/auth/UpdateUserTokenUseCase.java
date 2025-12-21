package com.example.demo.application.usecase.auth;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.application.usecase.user.GetUserByEmailUseCase;
import com.example.demo.domain.entity.User;
import com.example.demo.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateUserTokenUseCase {
    private final GetUserByEmailUseCase getUserByEmailUseCase;
    private final UserRepository userRepository;

    public void execute(String token, String email) {
        Optional<User> userOpt = getUserByEmailUseCase.execute(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setRefreshToken(token);
            userRepository.save(user);
        }
    }
}
