package org.qwerris.filmsreviews.dao;

import org.qwerris.filmsreviews.entity.Review;

import javax.persistence.EntityManager;

public class ReviewRepository extends BaseRepository<Review, Integer> {
    public ReviewRepository(EntityManager em) {
        super(Review.class, em);
    }
}
