package org.qwerris.filmsreviews.validator;

public interface Validator<T> {
    ValidationResult isValid(T obj);
}
