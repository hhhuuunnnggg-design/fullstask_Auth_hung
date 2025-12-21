package com.example.demo.application.dto.request;

import lombok.Data;

@Data
public class UpdatePermissionRequest {
    private String name;
    private String apiPath;
    private String method;
    private String module;
}

