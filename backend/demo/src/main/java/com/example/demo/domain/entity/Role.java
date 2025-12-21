package com.example.demo.domain.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Role {
    Long id;
    String name;
    String description;
    Boolean active;
    Instant createdAt;
    Instant updatedAt;
    String createdBy;
    String updatedBy;
    List<Long> permissionIds; // Reference to permissions, not direct entities
}
