package org.qwerris.filmsreviews.validator;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.qwerris.filmsreviews.dao.UserDao;
import org.qwerris.filmsreviews.dto.CreateUserDto;
import org.qwerris.filmsreviews.dto.UserFilter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateUserValidator implements Validator<CreateUserDto> {
    private final static CreateUserValidator INSTANCE = new CreateUserValidator();
    private final UserDao userDao = UserDao.getInstance();

    public static CreateUserValidator getInstance() {
        return INSTANCE;
    }

    @Override
    public ValidationResult isValid(CreateUserDto createUserDto) {
        ValidationResult validationResult = new ValidationResult();

        if (createUserDto.getUsername().length() < 3) {
            validationResult.add(Error.of("invalid.username.length", "Username must be at least 3 characters"));
        } else if (!userDao.findAll(UserFilter.builder().username(createUserDto.getUsername()).build()).isEmpty()) {
            validationResult.add(Error.of("invalid.username", "Username already exists"));
        }
        if (createUserDto.getPassword().length() < 5) {
            validationResult.add(Error.of("invalid.password.length", "Password must be at least 5 characters"));
        }

        return validationResult;
    }
}
