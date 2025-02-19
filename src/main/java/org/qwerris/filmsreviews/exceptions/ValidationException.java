package org.qwerris.filmsreviews.exceptions;

import lombok.Getter;
import org.qwerris.filmsreviews.validator.Error;

import java.util.List;

@Getter
public class ValidationException extends RuntimeException {
    private final List<Error> errors;

    public ValidationException(List<Error> errors) {
        this.errors = errors;
    }
}
