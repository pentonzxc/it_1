package com.kolya.it_1.mappers;


import com.kolya.it_1.dao.CustomUserDetails;
import com.kolya.it_1.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserDetailsMapper {
    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "status", source = "status")
    CustomUserDetails convert(User user);
}
