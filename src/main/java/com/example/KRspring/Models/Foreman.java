package com.example.KRspring.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
public class Foreman {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Имя не может быть пустым")
    @Size(min = 2, max = 50, message = "Имя должно быть от 2 до 50 символов")
    private String name;

    @NotBlank(message = "Фамилия не может быть пустой")
    @Size(min = 2, max = 50, message = "Фамилия должна быть от 2 до 50 символов")
    private String surname;

    @NotBlank(message = "Отчество не может быть пустой")
    @Size(min = 2, max = 50, message = "Отчество должна быть от 2 до 50 символов")
    private String patronymic;

    @NotBlank(message = "Специализация не может быть пустой")
    @Size(min = 2, max = 50, message = "Специализация должна быть от 2 до 50 символов")
    private String specialization;

    @NotBlank(message = "Номер телефона не может быть пустым")
    @Size(min = 10, max = 15, message = "Номер телефона должен быть от 10 до 15 символов")
    private String phoneNumber;

    @NotBlank(message = "Квалификация не может быть пустой")
    @Size(min = 2, max = 50, message = "Квалификация должна быть от 2 до 50 символов")
    private String qualification;

    @OneToMany(mappedBy = "foreman")
    private List<Worker> workers;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
