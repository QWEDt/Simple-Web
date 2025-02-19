package org.qwerris.filmsreviews.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.qwerris.filmsreviews.dao.UserDao;
import org.qwerris.filmsreviews.dto.CreateUserDto;
import org.qwerris.filmsreviews.dto.UserDto;
import org.qwerris.filmsreviews.dto.UserFilter;
import org.qwerris.filmsreviews.entity.User;
import org.qwerris.filmsreviews.exceptions.ValidationException;
import org.qwerris.filmsreviews.mapper.CreateUserMapper;
import org.qwerris.filmsreviews.mapper.UserDtoMapper;
import org.qwerris.filmsreviews.validator.CreateUserValidator;
import org.qwerris.filmsreviews.validator.ValidationResult;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserService {
    private final static UserService INSTANCE = new UserService();
    private final UserDao userDao = UserDao.getInstance();
    private final CreateUserValidator createUserValidator = CreateUserValidator.getInstance();
    private final CreateUserMapper createUserMapper = CreateUserMapper.getInstance();
    private final UserDtoMapper userDtoMapper = UserDtoMapper.getInstance();

    public static UserService getInstance() {
        return INSTANCE;
    }

    public void createUser(CreateUserDto createUserDto) throws ValidationException {
        ValidationResult result = createUserValidator.isValid(createUserDto);

        if (!result.isValid()) {
            throw new ValidationException(result.getErrors());
        }

        User user = createUserMapper.map(createUserDto);
        userDao.save(user);
    }

    public Optional<UserDto> login(String username, String password) {
        return userDao.findByUsernameAndPassword(username, password).map(userDtoMapper::map);
    }
}
