package com.kolya.it_1.controllers;

import com.kolya.it_1.dto.UserDto;
import com.kolya.it_1.mappers.CredentialsMapper;
import com.kolya.it_1.services.UserService;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class RegisterController {

    private final CredentialsMapper credentialsMapper = Mappers.getMapper(CredentialsMapper.class);
    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new UserDto());
        return "register-page";
    }

    @PostMapping("/register")
    public String registrationUser(@ModelAttribute(name = "user") @Valid UserDto userDto,
                                   BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors())
            return "register-page";
        userService.addUser(credentialsMapper.convert(userDto));
        return "redirect:/";
    }

}
