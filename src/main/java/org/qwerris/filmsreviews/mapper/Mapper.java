package org.qwerris.filmsreviews.mapper;

public interface Mapper<F, T> {
    T map(F from);
}
