package org.qwerris.filmsreviews.dao;

import org.qwerris.filmsreviews.entity.Film;

import javax.persistence.EntityManager;

public class FilmRepository extends BaseRepository<Film, Integer> {
    public FilmRepository(EntityManager em) {
        super(Film.class, em);
    }
}
