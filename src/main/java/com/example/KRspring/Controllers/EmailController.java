package com.example.KRspring.Controllers;

import com.example.KRspring.Services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EmailController {

    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/send-email")
    public String showEmailForm(@RequestParam(required = false) String recipient, Model model) {
        model.addAttribute("recipient", recipient);
        return "send-email";
    }

    @PostMapping("/send-email")
    public String sendEmail(@RequestParam String recipient, @RequestParam String subject, @RequestParam String message, Model model) {
        try {
            emailService.sendEmail(recipient, subject, message);
            model.addAttribute("successMessage", "Письмо отправлено успешно!");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Произошла ошибка при отправке письма.");
        }
        return "send-email";
    }
}