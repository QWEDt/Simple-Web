package org.qwerris.filmsreviews.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    private int id;
    private Film film;
    private User user;
    private String text;
    private int score;
    private LocalDate publicationDate;
}
