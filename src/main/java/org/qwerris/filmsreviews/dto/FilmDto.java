package org.qwerris.filmsreviews.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Value
@Builder
public class FilmDto {
    int id;
    String name;
    String description;
    LocalDate releaseDate;
    LocalTime length;
    double score;
    List<ReviewDto> reviewDtoList;
}
