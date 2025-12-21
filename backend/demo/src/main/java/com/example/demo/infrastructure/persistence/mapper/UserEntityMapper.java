package com.example.demo.infrastructure.persistence.mapper;

import java.util.Optional;

import com.example.demo.domain.entity.User;
import com.example.demo.infrastructure.persistence.entity.RoleEntity;
import com.example.demo.infrastructure.persistence.entity.UserEntity;
import com.example.demo.infrastructure.persistence.repository.RoleJpaRepository;

public class UserEntityMapper {
    public static User toDomain(UserEntity entity) {
        if (entity == null)
            return null;
        return User.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .avatar(entity.getAvatar())
                .coverPhoto(entity.getCoverPhoto())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .dateOfBirth(entity.getDateOfBirth())
                .gender(entity.getGender())
                .refreshToken(entity.getRefreshToken())
                .work(entity.getWork())
                .education(entity.getEducation())
                .current_city(entity.getCurrent_city())
                .hometown(entity.getHometown())
                .bio(entity.getBio())
                .is_admin(entity.getIs_admin())
                .is_blocked(entity.getIs_blocked())
                .createdAt(entity.getCreatedAt())
                .roleId(entity.getRole() != null ? entity.getRole().getId() : null)
                .build();
    }

    public static UserEntity toEntity(User domain, RoleJpaRepository roleRepository) {
        if (domain == null)
            return null;
        UserEntity entity = UserEntity.builder()
                .id(domain.getId())
                .email(domain.getEmail())
                .password(domain.getPassword())
                .avatar(domain.getAvatar())
                .coverPhoto(domain.getCoverPhoto())
                .firstName(domain.getFirstName())
                .lastName(domain.getLastName())
                .dateOfBirth(domain.getDateOfBirth())
                .gender(domain.getGender())
                .refreshToken(domain.getRefreshToken())
                .work(domain.getWork())
                .education(domain.getEducation())
                .current_city(domain.getCurrent_city())
                .hometown(domain.getHometown())
                .bio(domain.getBio())
                .is_admin(domain.getIs_admin())
                .is_blocked(domain.getIs_blocked())
                .createdAt(domain.getCreatedAt())
                .build();

        // Set role if roleId is provided
        if (domain.getRoleId() != null && roleRepository != null) {
            Long roleId = domain.getRoleId();
            Optional<RoleEntity> roleOpt = roleRepository.findById(roleId);
            roleOpt.ifPresent(entity::setRole);
        }

        return entity;
    }
}
