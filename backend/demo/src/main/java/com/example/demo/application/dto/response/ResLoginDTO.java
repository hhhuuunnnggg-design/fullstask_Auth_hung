package com.example.demo.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResLoginDTO {
    @JsonProperty("access_token")
    String accessToken;

    UserLogin user;

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserLogin {
        long id;
        String email;
        String fullname;
        Boolean is_admin;
        String avatar;
        String coverPhoto;
        Boolean is_blocked;
        ResRoleDTO role;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserGetAccount {
        private UserLogin user;
    }
}
