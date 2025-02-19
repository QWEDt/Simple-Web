package org.qwerris.filmsreviews.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
public class PreviewFilmDto {
    int id;
    String title;
    String description;
    double rating;
}
