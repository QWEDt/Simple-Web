package org.qwerris.filmsreviews.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.qwerris.filmsreviews.dao.FilmDao;
import org.qwerris.filmsreviews.dto.FilmDto;
import org.qwerris.filmsreviews.dto.PreviewFilmDto;
import org.qwerris.filmsreviews.mapper.FilmMapper;
import org.qwerris.filmsreviews.mapper.PreviewFilmMapper;

import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FilmService {
    private static final FilmService INSTANCE = new FilmService();
    private final FilmDao filmDao = FilmDao.getInstance();
    private final PreviewFilmMapper previewFilmMapper = PreviewFilmMapper.getInstance();
    private final FilmMapper filmMapper = FilmMapper.getInstance();

    public static FilmService getInstance() {
        return INSTANCE;
    }

    public List<PreviewFilmDto> getPreviewFilms() {
        return filmDao.findAll().stream().map(previewFilmMapper::map).toList();
    }

    public Optional<FilmDto> getFilmById(int id) {
        return filmDao.findById((long) id).map(filmMapper::map);
    }
}
