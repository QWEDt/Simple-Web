package org.qwerris.filmsreviews.dao;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.qwerris.filmsreviews.dto.UserFilter;
import org.qwerris.filmsreviews.entity.Gender;
import org.qwerris.filmsreviews.entity.Role;
import org.qwerris.filmsreviews.entity.User;
import org.qwerris.filmsreviews.utils.ConnectionManager;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDao implements Dao<Long, User> {
    private static final UserDao INSTANCE = new UserDao();
    private static final String SAVE_SQL = """
            INSERT INTO public.users (username, password, gender, role, registration_date)
            VALUES (?, ?, ?, ?, ?)
            """;
    private static final String DELETE_SQL = """
            DELETE FROM public.users WHERE id = ?
            """;
    private static final String UPDATE_SQL = """
            UPDATE public.users SET password = ?, gender = ?, role = ? WHERE id = ?
            """;
    private static final String FIND_BY_ID_SQL = """
            SELECT id, username, password, gender, role, registration_date FROM public.users WHERE id = ?
            """;
    private static final String FIND_ALL_SQL = """
            SELECT id, username, password, gender, role, registration_date FROM public.users
            """;
    private static final String FIND_BY_USERNAME_AND_PASSWORD_SQL = """
            SELECT id, username, password, gender, role, registration_date FROM public.users WHERE username = ? AND password = ?
            """;

    public static UserDao getInstance() {
        return INSTANCE;
    }

    @Override
    public Optional<User> save(User entity) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(SAVE_SQL)) {
            ps.setString(1, entity.getUsername());
            ps.setString(2, entity.getPassword());
            ps.setString(3, entity.getGender().toString());
            ps.setString(4, entity.getRole().toString());
            ps.setDate(5, Date.valueOf(LocalDate.now()));

            ps.executeUpdate();

            ResultSet resultSet = ps.getGeneratedKeys();

            if (resultSet.next()) {
                entity.setId(resultSet.getInt("id"));
            }

            return Optional.of(entity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(User entity) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_SQL)) {
            ps.setString(1, entity.getPassword());
            ps.setString(2, entity.getGender().toString());
            ps.setString(3, entity.getRole().toString());
            ps.setLong(4, entity.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(User entity) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE_SQL)) {
            ps.setLong(1, entity.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        try (Connection connection = ConnectionManager.getConnection()) {
            return findById(id, connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<User> findById(Long id, Connection connection) {
        try (PreparedStatement ps = connection.prepareStatement(FIND_BY_ID_SQL)) {
            ps.setLong(1, id);

            ResultSet resultSet = ps.executeQuery();

            User user = null;
            if (resultSet.next()) {
                user = UserBuilder(resultSet);
            }

            return Optional.ofNullable(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> findAll() {
        return findAll(FIND_ALL_SQL);
    }

    public List<User> findAll(@NonNull UserFilter filter) {
        StringJoiner joiner = new StringJoiner(" AND ", " WHERE ", "");

        if (filter.getUsername() != null) {
            joiner.add("username ilike '%" + filter.getUsername() + "%'");
        }
        if (filter.getGender() != null) {
            joiner.add("gender = '" + filter.getGender() + "'");
        }

        String sql = FIND_ALL_SQL + joiner;

        return findAll(sql);
    }

    private List<User> findAll(String sql) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet resultSet = ps.executeQuery();

            List<User> users = new ArrayList<>();

            while (resultSet.next()) {
                users.add(UserBuilder(resultSet));
            }

            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<User> findByUsernameAndPassword(@NonNull String username, @NonNull String password) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_BY_USERNAME_AND_PASSWORD_SQL)) {
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet resultSet = ps.executeQuery();

            User user = null;

            if (resultSet.next()) {
                user = UserBuilder(resultSet);
            }

            return Optional.ofNullable(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private User UserBuilder(ResultSet resultSet) throws SQLException {
        return User.builder()
                .id((int) resultSet.getLong("id"))
                .username(resultSet.getString("username"))
                .password(resultSet.getString("password"))
                .gender(Gender.valueOf(resultSet.getString("gender")))
                .role(Role.valueOf(resultSet.getString("role")))
                .registrationDate(resultSet.getDate("registration_date").toLocalDate())
                .build();
    }
}
