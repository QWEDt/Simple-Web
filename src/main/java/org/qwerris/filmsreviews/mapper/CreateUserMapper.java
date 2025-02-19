package org.qwerris.filmsreviews.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.qwerris.filmsreviews.dto.CreateUserDto;
import org.qwerris.filmsreviews.entity.Gender;
import org.qwerris.filmsreviews.entity.Role;
import org.qwerris.filmsreviews.entity.User;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateUserMapper implements Mapper<CreateUserDto, User> {
    private static final CreateUserMapper INSTANCE = new CreateUserMapper();

    public static CreateUserMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public User map(CreateUserDto createUserDto) {
        return User.builder()
                .username(createUserDto.getUsername())
                .password(createUserDto.getPassword())
                .gender(createUserDto.getGender() == null ? Gender.NOT_SPECIFIED : Gender.valueOf(createUserDto.getGender()))
                .role(Role.USER)
                .build();
    }
}
