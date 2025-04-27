package org.qwerris.filmsreviews.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface Repository<E, K extends Serializable> {
    E save(E entity);

    Optional<E> findById(K id);

    List<E> findAll();

    void delete(E entity);

    void update(E entity);
}
