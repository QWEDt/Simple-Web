package org.qwerris.filmsreviews.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.qwerris.filmsreviews.dto.ReviewDto;
import org.qwerris.filmsreviews.entity.Review;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewMapper implements Mapper<Review, ReviewDto> {
    private static final ReviewMapper INSTANCE = new ReviewMapper();
    private final UserDtoMapper userDtoMapper = UserDtoMapper.getInstance();

    public static ReviewMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public ReviewDto map(Review review) {
        return ReviewDto.builder()
                .id(review.getId())
                .user(userDtoMapper.map(review.getUser()))
                .text(review.getText())
                .score(review.getScore())
                .publicationDate(review.getPublicationDate())
                .build();
    }
}
