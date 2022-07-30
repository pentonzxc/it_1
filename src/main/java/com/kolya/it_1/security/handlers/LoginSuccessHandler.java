package com.kolya.it_1.security.handlers;

import com.kolya.it_1.dao.CustomUserDetails;
import com.kolya.it_1.domain.User;
import com.kolya.it_1.exceptions.UserNotFoundException;
import com.kolya.it_1.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.Date;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    public final UserService userService;


    public LoginSuccessHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userService.findUserByEmail(userDetails.getEmail())
                .orElseThrow(() -> new UserNotFoundException(
                        String.format("User with email %s doesn't exist" , userDetails.getEmail())));
        user.setLoginDate(Date.from(Instant.now()));
        userService.saveUser(user);
        response.sendRedirect("/");
    }
}
