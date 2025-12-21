package com.example.demo.domain.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Permission {
    Long id;
    String name;
    String apiPath;
    String method;
    String module;
    Instant createdAt;
    Instant updatedAt;
    String createdBy;
    String updatedBy;
}

