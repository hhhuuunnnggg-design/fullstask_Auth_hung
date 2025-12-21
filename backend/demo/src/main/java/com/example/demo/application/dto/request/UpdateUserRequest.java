package com.example.demo.application.dto.request;

import com.example.demo.domain.Enum.genderEnum;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateUserRequest {
    private String email;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private genderEnum gender;
    private String work;
    private String education;
    private String current_city;
    private String hometown;
    private String bio;
    private String avatar;
    private String coverPhoto;
    private Long roleId;
}

