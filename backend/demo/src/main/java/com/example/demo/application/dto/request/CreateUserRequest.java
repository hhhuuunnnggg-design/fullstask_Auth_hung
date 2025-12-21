package com.example.demo.application.dto.request;

import com.example.demo.domain.Enum.genderEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateUserRequest {
    @NotBlank(message = "email không được để trống")
    private String email;
    
    @NotBlank(message = "password không được để trống")
    private String password;
    
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private genderEnum gender;
    private String work;
    private String education;
    private String current_city;
    private String hometown;
    private String bio;
    private Long roleId;
}

