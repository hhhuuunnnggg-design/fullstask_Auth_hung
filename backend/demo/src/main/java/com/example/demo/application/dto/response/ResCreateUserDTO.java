package com.example.demo.application.dto.response;

import com.example.demo.domain.Enum.genderEnum;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResCreateUserDTO {
    long id;
    String email;
    genderEnum gender;
    String fullName;
    Instant createdAt;
}

