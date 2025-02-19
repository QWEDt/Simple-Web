package org.qwerris.filmsreviews.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.qwerris.filmsreviews.dto.CreateReviewDto;
import org.qwerris.filmsreviews.entity.Film;
import org.qwerris.filmsreviews.entity.Review;
import org.qwerris.filmsreviews.entity.User;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewDtoMapper implements Mapper<CreateReviewDto, Review> {
    private static final ReviewDtoMapper INSTANCE = new ReviewDtoMapper();

    public static ReviewDtoMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public Review map(CreateReviewDto createReviewDto) {
        return Review.builder()
                .film(Film.builder()
                        .id(createReviewDto.getFilmId())
                        .build())
                .user(User.builder()
                        .id(createReviewDto.getUser().getId())
                        .build())
                .score(createReviewDto.getScore())
                .text(createReviewDto.getText())
                .build();
    }
}
