package com.example.KRspring.Controllers;

import com.example.KRspring.Models.User;
import com.example.KRspring.Services.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    @Autowired
    private UserService userService;

    @GetMapping
    public ModelAndView showRegistrationForm() {
        ModelAndView modelAndView = new ModelAndView("register");
        modelAndView.addObject("user", new User());
        return modelAndView;
    }

    @PostMapping
    public ModelAndView registerUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("register");

        if (bindingResult.hasErrors()) {
            logger.error("Validation errors: {}", bindingResult.getAllErrors());
            return modelAndView;
        }

        logger.info("Registering user: {}, with role: {}", user.getUsername(), user.getRole());
        userService.saveUser(user);
        modelAndView.setViewName("login");
        return modelAndView;
    }
}

