package org.qwerris.filmsreviews.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<K, E> {
    Optional<E> save(E entity);
    boolean update(E entity);
    boolean delete(E entity);
    Optional<E> findById(K id);
    List<E> findAll();
}
