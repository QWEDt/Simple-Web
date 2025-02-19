package org.qwerris.filmsreviews.dao;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.qwerris.filmsreviews.dto.ReviewFilter;
import org.qwerris.filmsreviews.entity.Review;
import org.qwerris.filmsreviews.utils.ConnectionManager;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewDao implements Dao<Long, Review> {
    private static final ReviewDao INSTANCE = new ReviewDao();
    private static final String SAVE_SQL = """
            INSERT INTO public.reviews(film_id, user_id, text, score, publication_date)
            VALUES (?, ?, ?, ?, ?)
            """;
    private static final String DELETE_SQL = """
            DELETE FROM public.reviews WHERE id = ?
            """;
    private static final String UPDATE_SQL = """
            UPDATE public.reviews SET text = ?, score = ?, publication_date = ? WHERE id = ?
            """;
    private static final String FIND_BY_ID_SQL = """
            SELECT id, film_id, user_id, text, score, publication_date FROM public.reviews WHERE id = ?
            """;
    private static final String FIND_ALL_SQL = """
            SELECT id, film_id, user_id, text, score, publication_date FROM public.reviews
            """;
    private static final String GET_AVG_SCORE_SQL = """
            SELECT avg(score) from reviews WHERE film_id = ?
            """;

    public static ReviewDao getInstance() {
        return INSTANCE;
    }

    @Override
    public Optional<Review> save(Review entity) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(SAVE_SQL)) {
            ps.setLong(1, entity.getFilm().getId());
            ps.setLong(2, entity.getUser().getId());
            ps.setString(3, entity.getText());
            ps.setDouble(4, entity.getScore());
            ps.setDate(5, Date.valueOf(LocalDate.now()));

            ps.executeUpdate();

            ResultSet resultSet = ps.getGeneratedKeys();

            if (resultSet.next()) {
                entity.setId((int) resultSet.getLong("id"));
            }

            return Optional.of(entity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Review entity) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_SQL)) {
            ps.setString(1, entity.getText());
            ps.setLong(2, entity.getScore());
            ps.setDate(3, Date.valueOf(LocalDate.now()));
            ps.setLong(4, entity.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Review entity) {
        return false;
    }

    @Override
    public Optional<Review> findById(Long id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_BY_ID_SQL)) {
            ps.setLong(1, id);

            ResultSet resultSet = ps.executeQuery();

            Review review = null;
            if (resultSet.next()) {
                review = ReviewBuilder(resultSet);
            }

            return Optional.ofNullable(review);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Review> findAll() {
        return findAll(FIND_ALL_SQL);
    }

    public List<Review> findAll(@NonNull ReviewFilter filter) {
        StringJoiner joiner = new StringJoiner(" AND ", " WHERE ", "");

        if (filter.getUser() != null) {
            joiner.add("user_id = " + filter.getUser().getId());
        }
        if (filter.getFilm() != null) {
            joiner.add("film_id = " + filter.getFilm().getId());
        }

        String sql = FIND_ALL_SQL + joiner;

        return findAll(sql);
    }

    private List<Review> findAll(String sql) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet resultSet = ps.executeQuery();

            List<Review> reviews = new ArrayList<>();

            while (resultSet.next()) {
                reviews.add(ReviewBuilder(resultSet));
            }

            return reviews;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Double getFilmScore(Long id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(GET_AVG_SCORE_SQL)) {
            ps.setLong(1, id);

            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                return resultSet.getDouble("avg");
            }

            return 0D;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Review ReviewBuilder(ResultSet resultSet) throws SQLException {
        return Review.builder()
                .id(resultSet.getInt("id"))
                .film(FilmDao.getInstance().findById(resultSet.getLong("film_id"),
                        resultSet.getStatement().getConnection()).orElse(null))
                .user(UserDao.getInstance().findById(resultSet.getLong("user_id"),
                        resultSet.getStatement().getConnection()).orElse(null))
                .text(resultSet.getString("text"))
                .score(resultSet.getInt("score"))
                .publicationDate(resultSet.getDate("publication_date").toLocalDate())
                .build();
    }
}
