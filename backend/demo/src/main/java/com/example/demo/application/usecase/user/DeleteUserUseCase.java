package com.example.demo.application.usecase.user;

import org.springframework.stereotype.Service;

import com.example.demo.domain.repository.UserRepository;
import com.example.demo.util.error.IdInvalidException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeleteUserUseCase {
    private final UserRepository userRepository;

    public void execute(Long id) throws IdInvalidException {
        if (!userRepository.findById(id).isPresent()) {
            throw new IdInvalidException("User với id = " + id + " không tồn tại");
        }
        userRepository.deleteById(id);
    }
}
