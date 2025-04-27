package org.qwerris.filmsreviews.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.qwerris.filmsreviews.dao.ReviewRepository;
import org.qwerris.filmsreviews.dto.CreateReviewDto;
import org.qwerris.filmsreviews.entity.Review;
import org.qwerris.filmsreviews.mapper.ReviewDtoMapper;
import org.qwerris.filmsreviews.utils.HibernateUtil;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewService {
    private static final ReviewService INSTANCE = new ReviewService();
    private final ReviewRepository reviewDao = new ReviewRepository(HibernateUtil.getSession());
    private final ReviewDtoMapper reviewDtoMapper = ReviewDtoMapper.getInstance();

    public static ReviewService getInstance() {
        return INSTANCE;
    }

    public void save(CreateReviewDto createReviewDto) {
        Review review = reviewDtoMapper.map(createReviewDto);
        reviewDao.findById(review.getId()).ifPresentOrElse(reviewDao::update, () -> reviewDao.save(review));
    }
}
