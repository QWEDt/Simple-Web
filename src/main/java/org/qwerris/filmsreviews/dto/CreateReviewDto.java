package org.qwerris.filmsreviews.dto;

import lombok.Builder;
import lombok.Value;
import org.qwerris.filmsreviews.entity.Film;

import java.time.LocalDate;

@Value
@Builder
public class CreateReviewDto {
    int filmId;
    UserDto user;
    String text;
    int score;
}
