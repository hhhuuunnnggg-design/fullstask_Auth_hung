package com.example.demo.application.usecase.user;

import com.example.demo.domain.entity.User;
import com.example.demo.domain.port.UserRepositoryPort;
import com.example.demo.util.error.IdInvalidException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChangeActivityUserUseCase {
    private final UserRepositoryPort userRepository;

    public User execute(Long id) throws IdInvalidException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IdInvalidException("User với id = " + id + " không tồn tại"));

        user.setIs_blocked(!user.isBlocked());
        return userRepository.save(user);
    }
}

