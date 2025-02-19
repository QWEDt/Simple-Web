package org.qwerris.filmsreviews.dao;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.qwerris.filmsreviews.entity.Film;
import org.qwerris.filmsreviews.entity.User;
import org.qwerris.filmsreviews.utils.ConnectionManager;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FilmDao implements Dao<Long, Film> {
    private static final FilmDao INSTANCE = new FilmDao();
    private static final String SAVE_SQL = """
            INSERT INTO public.films(name, description, release_day, length)
            VALUES (?, ?, ?, ?)
            """;
    private static final String DELETE_SQL = """
            DELETE FROM public.films WHERE id = ?
            """;
    private static final String UPDATE_SQL = """
            UPDATE public.films SET name = ?, description = ?, release_day = ?, length = ? WHERE id = ?
            """;
    private static final String FIND_BY_ID_SQL = """
            SELECT id, name, description, release_day, length FROM public.films WHERE id = ?
            """;
    private static final String FIND_ALL_SQL = """
            SELECT id, name, description, release_day, length FROM public.films
            """;

    public static FilmDao getInstance() {
        return INSTANCE;
    }

    @Override
    public Optional<Film> save(Film entity) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(SAVE_SQL)) {
            ps.setString(1, entity.getTitle());
            ps.setString(2, entity.getDescription());
            ps.setDate(3, Date.valueOf(entity.getReleaseDate()));
            ps.setTime(4, Time.valueOf(entity.getLength()));

            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                entity.setId(resultSet.getInt("id"));
            }

            return Optional.of(entity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Film entity) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_SQL)) {
            ps.setString(1, entity.getTitle());
            ps.setString(2, entity.getDescription());
            ps.setDate(3, Date.valueOf(entity.getReleaseDate()));
            ps.setTime(4, Time.valueOf(entity.getLength()));
            ps.setLong(5, entity.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Film entity) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE_SQL)) {
            ps.setLong(1, entity.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Film> findById(Long id) {
        try (Connection connection = ConnectionManager.getConnection()) {
            return findById(id, connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Film> findById(Long id, Connection connection) {
        try (PreparedStatement ps = connection.prepareStatement(FIND_BY_ID_SQL)) {
            ps.setLong(1, id);

            ResultSet resultSet = ps.executeQuery();

            Film film = null;
            if (resultSet.next()) {
                film = FilmBuilder(resultSet);
            }

            return Optional.ofNullable(film);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Film> findAll() {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = ps.executeQuery();

            List<Film> films = new ArrayList<>();

            while (resultSet.next()) {
                films.add(FilmBuilder(resultSet));
            }

            return films;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Film FilmBuilder(ResultSet resultSet) throws SQLException {
        return Film.builder()
                .id(resultSet.getInt("id"))
                .title(resultSet.getString("name"))
                .description(resultSet.getString("description"))
                .releaseDate(resultSet.getDate("release_day").toLocalDate())
                .length(resultSet.getTime("length").toLocalTime())
                .build();
    }
}
