package com.example.demo.application.usecase.auth;

import com.example.demo.application.dto.request.CreateUserRequest;
import com.example.demo.application.dto.response.ResCreateUserDTO;
import com.example.demo.application.usecase.user.CreateUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterUseCase {
    private final CreateUserUseCase createUserUseCase;

    public ResCreateUserDTO execute(CreateUserRequest request) throws Exception {
        return createUserUseCase.execute(request);
    }
}

