package com.kolya.it_1.services;

import com.kolya.it_1.exceptions.UserNotFoundException;
import com.kolya.it_1.mappers.UserDetailsMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    public final UserDetailsMapper detailsMapper = Mappers.getMapper(UserDetailsMapper.class);
    private final UserService userService;

    @Autowired
    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return detailsMapper.convert(userService.findUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(
                        String.format("User with email %s doesn't exist", email))));
    }
}
