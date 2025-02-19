package org.qwerris.filmsreviews.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.qwerris.filmsreviews.entity.Gender;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserFilter {
    String username;
    Gender gender;
}
