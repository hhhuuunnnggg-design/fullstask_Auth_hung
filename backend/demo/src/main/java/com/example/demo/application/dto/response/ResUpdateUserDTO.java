package com.example.demo.application.dto.response;

import com.example.demo.domain.Enum.genderEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResUpdateUserDTO {
    String email;
    String fullname;
    String avatar;
    String coverPhoto;
    LocalDate dateOfBirth;
    genderEnum gender;
    String work;
    String education;
    String current_city;
    String hometown;
    String bio;
}

