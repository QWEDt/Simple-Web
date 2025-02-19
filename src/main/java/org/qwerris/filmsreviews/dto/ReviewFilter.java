package org.qwerris.filmsreviews.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.qwerris.filmsreviews.entity.Film;
import org.qwerris.filmsreviews.entity.User;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewFilter {
    private Film film;
    private User user;
}
