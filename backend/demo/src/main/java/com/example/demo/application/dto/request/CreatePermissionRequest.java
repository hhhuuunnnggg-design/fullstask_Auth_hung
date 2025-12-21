package com.example.demo.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreatePermissionRequest {
    @NotBlank(message = "name không được để trống")
    private String name;

    @NotBlank(message = "apiPath không được để trống")
    private String apiPath;

    @NotBlank(message = "method không được để trống")
    private String method;

    @NotBlank(message = "module không được để trống")
    private String module;
}
