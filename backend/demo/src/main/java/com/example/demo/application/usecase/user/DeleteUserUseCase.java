package com.example.demo.application.usecase.user;

import com.example.demo.domain.port.UserRepositoryPort;
import com.example.demo.util.error.IdInvalidException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteUserUseCase {
    private final UserRepositoryPort userRepository;

    public void execute(Long id) throws IdInvalidException {
        if (!userRepository.findById(id).isPresent()) {
            throw new IdInvalidException("User với id = " + id + " không tồn tại");
        }
        userRepository.deleteById(id);
    }
}

