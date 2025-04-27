package org.qwerris.filmsreviews.dao;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class BaseRepository<E, K extends Serializable> implements Repository<E, K> {
    private final Class<E> clazz;
    @Getter
    private final EntityManager em;

    @Override
    public E save(E entity) {
        em.persist(entity);
        return entity;
    }

    @Override
    public Optional<E> findById(K id) {
        return Optional.ofNullable(em.find(clazz, id));
    }

    @Override
    public List<E> findAll() {
        CriteriaQuery<E> criteriaQuery = em.getCriteriaBuilder().createQuery(clazz);
        Root<E> root = criteriaQuery.from(clazz);
        return em.createQuery(criteriaQuery.select(root)).getResultList();
    }

    @Override
    public void delete(E entity) {
        em.remove(entity);
        em.flush();
    }

    @Override
    public void update(E entity) {
        em.merge(entity);
    }
}
