package com.example.demo.application.dto.response;

import com.example.demo.domain.Enum.genderEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResUserDTO {
    Long id;
    String email;
    String avatar;
    String coverPhoto;
    String fullname;
    LocalDate dateOfBirth;
    genderEnum gender;
    String work;
    String education;
    String currentCity;
    String hometown;
    String bio;
    Instant createdAt;
    boolean isAdmin;
    boolean isBlocked;
    RoleUser role;

    @Data
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class RoleUser {
        long id;
        String name;
    }
}

