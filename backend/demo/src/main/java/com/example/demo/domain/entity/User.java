package com.example.demo.domain.entity;

import java.time.Instant;
import java.time.LocalDate;

import com.example.demo.domain.Enum.genderEnum;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    Long id;
    String email;
    String password;
    String avatar;
    String coverPhoto;
    String firstName;
    String lastName;
    LocalDate dateOfBirth;
    genderEnum gender;
    String refreshToken;
    String work;
    String education;
    String current_city;
    String hometown;
    String bio;
    @Builder.Default
    Boolean is_admin = false;
    @Builder.Default
    Boolean is_blocked = false;
    Instant createdAt;
    Long roleId; // Reference to role, not direct entity

    public boolean isBlocked() {
        return is_blocked != null && is_blocked;
    }
}
