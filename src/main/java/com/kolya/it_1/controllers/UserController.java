package com.kolya.it_1.controllers;

import com.kolya.it_1.dao.CustomUserDetails;
import com.kolya.it_1.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class UserController {
    public final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/blockUsers")
    public void blockUsers(@RequestBody List<String> emails, Authentication authentication) {
        CustomUserDetails authenticatedUser = (CustomUserDetails) authentication.getPrincipal();
        if (emails.stream().anyMatch(email -> email.equals(authenticatedUser.getEmail()))) {
            (authenticatedUser).setStatus("BLOCKED");
            authentication.setAuthenticated(false);
        }
        emails.forEach(userService::blockUserByEmail);
    }

    @PostMapping("/unblockUsers")
    public void unblockUsers(@RequestBody List<String> emails) {
        emails.forEach(userService::unblockUserByEmail);
    }

    @PostMapping("/deleteUsers")
    public void deleteUsers(@RequestBody List<String> emails, Authentication authentication) {
        CustomUserDetails authenticatedUser = (CustomUserDetails) authentication.getPrincipal();
        if (emails.stream().anyMatch(email -> email.equals(authenticatedUser.getEmail()))) {
            SecurityContextHolder.clearContext();
        }
        emails.forEach(userService::deleteUserByEmail);
    }

}
