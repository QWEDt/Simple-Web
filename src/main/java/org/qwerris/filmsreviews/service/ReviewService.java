package org.qwerris.filmsreviews.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.qwerris.filmsreviews.dao.ReviewDao;
import org.qwerris.filmsreviews.dto.CreateReviewDto;
import org.qwerris.filmsreviews.dto.ReviewFilter;
import org.qwerris.filmsreviews.entity.Film;
import org.qwerris.filmsreviews.entity.User;
import org.qwerris.filmsreviews.mapper.ReviewDtoMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewService {
    private static final ReviewService INSTANCE = new ReviewService();
    private final ReviewDao reviewDao = ReviewDao.getInstance();
    private final ReviewDtoMapper reviewDtoMapper = ReviewDtoMapper.getInstance();

    public static ReviewService getInstance() {
        return INSTANCE;
    }

    public void save(CreateReviewDto createReviewDto) {
        ReviewFilter reviewFilter = ReviewFilter.builder()
                .film(Film.builder()
                        .id(createReviewDto.getFilmId())
                        .build())
                .user(User.builder()
                        .id(createReviewDto.getUser().getId())
                        .build())
                .build();

        var foundReview = reviewDao.findAll(reviewFilter);
        if (foundReview.isEmpty()) {
            reviewDao.save(reviewDtoMapper.map(createReviewDto));
        } else {
            var review = reviewDtoMapper.map(createReviewDto);
            review.setId(foundReview.iterator().next().getId());
            reviewDao.update(review);
        }
    }
}
