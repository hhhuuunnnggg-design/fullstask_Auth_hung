package com.example.demo.application.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class UpdateRoleRequest {
    private String name;
    private String description;
    private Boolean active;
    private List<Long> permissionIds;
}

