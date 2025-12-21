package com.example.demo.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class CreateRoleRequest {
    @NotBlank(message = "name không được để trống")
    private String name;
    private String description;
    private Boolean active;
    private List<Long> permissionIds;
}

