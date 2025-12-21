package com.example.demo.infrastructure.persistence.entity;

import java.time.Instant;
import java.time.LocalDate;

import com.example.demo.domain.Enum.genderEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotBlank(message = "email không được để trống")
    String email;

    String password;
    String avatar;
    String coverPhoto;
    String firstName;
    String lastName;
    LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    genderEnum gender;

    @Column(columnDefinition = "MEDIUMTEXT")
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

    @ManyToOne
    @JoinColumn(name = "role_id")
    RoleEntity role;

    @Transient
    public boolean isBlocked() {
        return is_blocked != null && is_blocked;
    }

    @PrePersist
    public void handleBeforeCreate() {
        this.createdAt = Instant.now();
    }
}
