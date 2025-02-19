package org.qwerris.filmsreviews.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.qwerris.filmsreviews.dao.ReviewDao;
import org.qwerris.filmsreviews.dto.PreviewFilmDto;
import org.qwerris.filmsreviews.entity.Film;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PreviewFilmMapper implements Mapper<Film, PreviewFilmDto> {
    private static final PreviewFilmMapper INSTANCE = new PreviewFilmMapper();
    private final ReviewDao reviewDao = ReviewDao.getInstance();

    public static PreviewFilmMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public PreviewFilmDto map(Film film) {
        return PreviewFilmDto.builder()
                .id(film.getId())
                .title(film.getTitle())
                .description(film.getDescription())
                .rating(reviewDao.getFilmScore((long) film.getId()))
                .build();
    }
}
