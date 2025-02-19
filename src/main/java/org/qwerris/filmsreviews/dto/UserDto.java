package org.qwerris.filmsreviews.dto;

import lombok.Builder;
import lombok.Value;
import org.qwerris.filmsreviews.entity.Gender;
import org.qwerris.filmsreviews.entity.Role;

import java.time.LocalDate;

@Value
@Builder
public class UserDto {
    int id;
    String username;
    Gender gender;
    Role role;
    LocalDate registrationDate;
}
