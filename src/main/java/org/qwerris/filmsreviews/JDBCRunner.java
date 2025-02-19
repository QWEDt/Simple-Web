package org.qwerris.filmsreviews;

import org.qwerris.filmsreviews.dao.ReviewDao;
import org.qwerris.filmsreviews.dao.UserDao;
import org.qwerris.filmsreviews.dto.ReviewFilter;
import org.qwerris.filmsreviews.entity.*;

public class JDBCRunner {
    public static void main(String[] args) {
//        FilmDao filmDao = FilmDao.getInstance();
//        filmDao.findById(2L).ifPresent(film -> {
//            film.setTitle("Миссия: Красный");
//            filmDao.update(film);
//        });
        UserDao userDao = UserDao.getInstance();
        ReviewDao reviewDao = ReviewDao.getInstance();
        ReviewFilter filter = ReviewFilter.builder().film(Film.builder().id(2).build()).build();
        System.out.println(reviewDao.findAll(filter).stream().map(Review::getText).toList());
    }
}
