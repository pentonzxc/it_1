package com.kolya.it_1.mappers;

import com.kolya.it_1.domain.User;
import com.kolya.it_1.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.util.Date;

@Mapper(imports = {Instant.class, Date.class})
public interface CredentialsMapper {
    @Mapping(target = "status", defaultValue = "ACTIVE")
    @Mapping(target = "registrationDate", defaultExpression = "java(Date.from(Instant.now()))")
    User convert(UserDto userDto);
}

// тут можно сразу пароль как бы encode
