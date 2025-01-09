package com.example.KRspring.Controllers;

import com.example.KRspring.Models.Customer;
import com.example.KRspring.Models.Foreman;
import com.example.KRspring.Models.User;
import com.example.KRspring.Services.CustomerService;
import com.example.KRspring.Services.ForemanService;
import com.example.KRspring.Services.UserService;
import com.example.KRspring.validation.CustomerValidationGroup;
import com.example.KRspring.validation.ForemanValidationGroup;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ForemanService foremanService;

    @GetMapping
    public ModelAndView showRegistrationForm() {
        logger.info("Showing registration form");
        ModelAndView modelAndView = new ModelAndView("register");
        modelAndView.addObject("user", new User());
        return modelAndView;
    }

    @PostMapping
    public ModelAndView registerUser(@Validated User user, BindingResult bindingResult) {
        logger.info("Registering user: {}", user.getUsername());
        ModelAndView modelAndView = new ModelAndView("register");

        if ("ROLE_CUSTOMER".equals(user.getRole().name())) {
            user = validateCustomer(user, bindingResult);
        } else if ("ROLE_FOREMAN".equals(user.getRole().name())) {
            user = validateForeman(user, bindingResult);
        }

        if (bindingResult.hasErrors()) {
            logger.error("Validation errors: {}", bindingResult.getAllErrors());
            return modelAndView;
        }

        Optional<User> existingUser = userService.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            bindingResult.rejectValue("username", "error.user", "Это имя пользователя уже занято");
            return modelAndView;
        }

        if ("ROLE_CUSTOMER".equals(user.getRole().name())) {
            Customer customer = new Customer();
            customer.setCustomerName(user.getCustomerName());
            customer.setCustomerSurname(user.getCustomerSurname());
            customer.setCustomerPatronymic(user.getCustomerPatronymic());
            customer.setCustomerPhoneNumber(user.getCustomerPhoneNumber());
            customer.setUsername(user.getUsername()); // Устанавливаем username для Customer
            customerService.saveCustomer(customer);

            // Clear foreman fields
            user.setForemanName(null);
            user.setForemanSurname(null);
            user.setForemanPatronymic(null);
            user.setForemanPhoneNumber(null);
            user.setSpecialization(null);
            user.setQualification(null);

        } else if ("ROLE_FOREMAN".equals(user.getRole().name())) {
            Foreman foreman = new Foreman();
            foreman.setForemanName(user.getForemanName());
            foreman.setForemanSurname(user.getForemanSurname());
            foreman.setForemanPatronymic(user.getForemanPatronymic());
            foreman.setForemanPhoneNumber(user.getForemanPhoneNumber());
            foreman.setSpecialization(user.getSpecialization());
            foreman.setQualification(user.getQualification());
            foreman.setUsername(user.getUsername()); // Устанавливаем username для Foreman
            foremanService.saveForeman(foreman);

            // Clear customer fields
            user.setCustomerName(null);
            user.setCustomerSurname(null);
            user.setCustomerPatronymic(null);
            user.setCustomerPhoneNumber(null);
        }

        logger.info("Registering user: {}, with role: {}", user.getUsername(), user.getRole());
        userService.saveUser(user);
        modelAndView.setViewName("login");
        return modelAndView;
    }


    private User validateCustomer(User user, BindingResult bindingResult) {
        if (user.getCustomerName() == null || user.getCustomerName().isEmpty()) {
            bindingResult.rejectValue("customerName", "error.customerName", "Имя не может быть пустым");
        }
        if (user.getCustomerSurname() == null || user.getCustomerSurname().isEmpty()) {
            bindingResult.rejectValue("customerSurname", "error.customerSurname", "Фамилия не может быть пустой");
        }
        if (user.getCustomerPatronymic() == null || user.getCustomerPatronymic().isEmpty()) {
            bindingResult.rejectValue("customerPatronymic", "error.customerPatronymic", "Отчество не может быть пустым");
        }
        if (user.getCustomerPhoneNumber() == null || user.getCustomerPhoneNumber().isEmpty()) {
            bindingResult.rejectValue("customerPhoneNumber", "error.customerPhoneNumber", "Номер телефона не может быть пустым");
        }
        return user;
    }

    private User validateForeman(User user, BindingResult bindingResult) {
        if (user.getForemanName() == null || user.getForemanName().isEmpty()) {
            bindingResult.rejectValue("foremanName", "error.foremanName", "Имя не может быть пустым");
        }
        if (user.getForemanSurname() == null || user.getForemanSurname().isEmpty()) {
            bindingResult.rejectValue("foremanSurname", "error.foremanSurname", "Фамилия не может быть пустой");
        }
        if (user.getForemanPatronymic() == null || user.getForemanPatronymic().isEmpty()) {
            bindingResult.rejectValue("foremanPatronymic", "error.foremanPatronymic", "Отчество не может быть пустым");
        }
        if (user.getForemanPhoneNumber() == null || user.getForemanPhoneNumber().isEmpty()) {
            bindingResult.rejectValue("foremanPhoneNumber", "error.foremanPhoneNumber", "Номер телефона не может быть пустым");
        }
        if (user.getSpecialization() == null || user.getSpecialization().isEmpty()) {
            bindingResult.rejectValue("specialization", "error.specialization", "Специализация не может быть пустой");
        }
        if (user.getQualification() == null || user.getQualification().isEmpty()) {
            bindingResult.rejectValue("qualification", "error.qualification", "Квалификация не может быть пустой");
        }
        return user;
    }
}
