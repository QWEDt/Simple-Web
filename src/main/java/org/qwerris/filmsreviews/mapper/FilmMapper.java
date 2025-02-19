package org.qwerris.filmsreviews.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.qwerris.filmsreviews.dao.ReviewDao;
import org.qwerris.filmsreviews.dto.FilmDto;
import org.qwerris.filmsreviews.dto.ReviewFilter;
import org.qwerris.filmsreviews.entity.Film;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FilmMapper implements Mapper<Film, FilmDto> {
    private static final FilmMapper INSTANCE = new FilmMapper();
    private final ReviewDao reviewDao = ReviewDao.getInstance();
    private final ReviewMapper reviewMapper = ReviewMapper.getInstance();

    public static FilmMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public FilmDto map(Film film) {
        return FilmDto.builder()
                .id(film.getId())
                .name(film.getTitle())
                .description(film.getDescription())
                .releaseDate(film.getReleaseDate())
                .length(film.getLength())
                .reviewDtoList(reviewDao.findAll(
                        ReviewFilter.builder()
                                .film(film)
                                .build()
                ).stream().map(reviewMapper::map).toList())
                .score(reviewDao.getFilmScore((long) film.getId()))
                .build();
    }
}
