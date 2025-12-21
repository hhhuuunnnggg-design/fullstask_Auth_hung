package com.example.demo.application.usecase.user;

import org.springframework.stereotype.Service;

import com.example.demo.domain.entity.User;
import com.example.demo.domain.repository.UserRepository;
import com.example.demo.util.error.IdInvalidException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChangeActivityUserUseCase {
    private final UserRepository userRepository;

    public User execute(Long id) throws IdInvalidException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IdInvalidException("User với id = " + id + " không tồn tại"));

        user.setIs_blocked(!user.isBlocked());
        return userRepository.save(user);
    }
}
