package com.kolya.it_1.controllers;

import com.kolya.it_1.dto.UserDto;
import com.kolya.it_1.mappers.CredentialsMapper;
import com.kolya.it_1.services.UserService;
import org.mapstruct.factory.Mappers;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class RegisterController {

    private final CredentialsMapper credentialsMapper = Mappers.getMapper(CredentialsMapper.class);
    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String register(Model model, Principal principal) {
        model.addAttribute("user", new UserDto());
        return "register-page";
    }

    @PostMapping("/register")
    public String registrationUser(@ModelAttribute(name = "user") UserDto userDto) {
        userService.addUser(credentialsMapper.convert(userDto));
        return "redirect:/";
    }

}
