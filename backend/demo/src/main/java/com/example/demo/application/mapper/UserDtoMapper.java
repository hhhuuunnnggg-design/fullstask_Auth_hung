package com.example.demo.application.mapper;

import com.example.demo.application.dto.request.CreateUserRequest;
import com.example.demo.application.dto.request.UpdateUserRequest;
import com.example.demo.application.dto.response.ResCreateUserDTO;
import com.example.demo.application.dto.response.ResUpdateUserDTO;
import com.example.demo.application.dto.response.ResUserDTO;
import com.example.demo.domain.entity.Role;
import com.example.demo.domain.entity.User;

public class UserDtoMapper {
    public static User toDomain(CreateUserRequest request) {
        return User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .dateOfBirth(request.getDateOfBirth())
                .gender(request.getGender())
                .work(request.getWork())
                .education(request.getEducation())
                .current_city(request.getCurrent_city())
                .hometown(request.getHometown())
                .bio(request.getBio())
                .roleId(request.getRoleId())
                .is_admin(false)
                .is_blocked(false)
                .build();
    }

    public static ResCreateUserDTO toResCreateUserDTO(User user) {
        ResCreateUserDTO dto = new ResCreateUserDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setGender(user.getGender());
        dto.setFullName(user.getLastName() + " " + user.getFirstName());
        dto.setCreatedAt(user.getCreatedAt());
        return dto;
    }

    public static ResUserDTO toResUserDTO(User user, Role role) {
        ResUserDTO.RoleUser roleUser = null;
        if (role != null) {
            roleUser = ResUserDTO.RoleUser.builder()
                    .id(role.getId())
                    .name(role.getName())
                    .build();
        }

        return ResUserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .avatar(user.getAvatar())
                .coverPhoto(user.getCoverPhoto())
                .fullname(user.getFirstName() + " " + user.getLastName())
                .dateOfBirth(user.getDateOfBirth())
                .gender(user.getGender())
                .work(user.getWork())
                .education(user.getEducation())
                .currentCity(user.getCurrent_city())
                .hometown(user.getHometown())
                .bio(user.getBio())
                .createdAt(user.getCreatedAt())
                .isAdmin(user.getIs_admin() != null && user.getIs_admin())
                .isBlocked(user.isBlocked())
                .role(roleUser)
                .build();
    }

    public static ResUpdateUserDTO toResUpdateUserDTO(User user) {
        return ResUpdateUserDTO.builder()
                .email(user.getEmail())
                .fullname(user.getFirstName() + " " + user.getLastName())
                .avatar(user.getAvatar())
                .coverPhoto(user.getCoverPhoto())
                .dateOfBirth(user.getDateOfBirth())
                .gender(user.getGender())
                .work(user.getWork())
                .education(user.getEducation())
                .current_city(user.getCurrent_city())
                .hometown(user.getHometown())
                .bio(user.getBio())
                .build();
    }

    public static void updateUserFromRequest(User user, UpdateUserRequest request) {
        if (request.getEmail() != null && !request.getEmail().isBlank()) {
            user.setEmail(request.getEmail());
        }
        if (request.getFirstName() != null && !request.getFirstName().isBlank()) {
            user.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null && !request.getLastName().isBlank()) {
            user.setLastName(request.getLastName());
        }
        if (request.getDateOfBirth() != null) {
            user.setDateOfBirth(request.getDateOfBirth());
        }
        if (request.getGender() != null) {
            user.setGender(request.getGender());
        }
        if (request.getWork() != null && !request.getWork().isBlank()) {
            user.setWork(request.getWork());
        }
        if (request.getEducation() != null && !request.getEducation().isBlank()) {
            user.setEducation(request.getEducation());
        }
        if (request.getCurrent_city() != null && !request.getCurrent_city().isBlank()) {
            user.setCurrent_city(request.getCurrent_city());
        }
        if (request.getHometown() != null && !request.getHometown().isBlank()) {
            user.setHometown(request.getHometown());
        }
        if (request.getBio() != null && !request.getBio().isBlank()) {
            user.setBio(request.getBio());
        }
        if (request.getAvatar() != null && !request.getAvatar().isBlank()) {
            user.setAvatar(request.getAvatar());
        }
        if (request.getCoverPhoto() != null && !request.getCoverPhoto().isBlank()) {
            user.setCoverPhoto(request.getCoverPhoto());
        }
        if (request.getRoleId() != null) {
            user.setRoleId(request.getRoleId());
        }
    }
}
