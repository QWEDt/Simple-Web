package org.qwerris.filmsreviews.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.qwerris.filmsreviews.dto.UserDto;
import org.qwerris.filmsreviews.entity.User;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDtoMapper implements Mapper<User, UserDto> {
    private static final UserDtoMapper INSTANCE = new UserDtoMapper();

    public static UserDtoMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public UserDto map(User from) {
        return UserDto.builder()
                .id(from.getId())
                .username(from.getUsername())
                .gender(from.getGender())
                .role(from.getRole())
                .registrationDate(from.getRegistrationDate())
                .build();
    }
}
