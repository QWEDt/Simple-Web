package org.qwerris.filmsreviews.dao;

import org.qwerris.filmsreviews.entity.User;

import javax.persistence.EntityManager;
import java.util.Optional;

public class UserRepository extends BaseRepository<User, Integer> {
    public UserRepository(EntityManager entityManager) {
        super(User.class, entityManager);
    }

    // TODO
    public Optional<User> findByUsernameAndPassword(String username, String password) {
        return null;
    }
}
