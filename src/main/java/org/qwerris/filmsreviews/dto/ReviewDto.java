package org.qwerris.filmsreviews.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class ReviewDto {
    int id;
    UserDto user;
    String text;
    int score;
    LocalDate publicationDate;
}
