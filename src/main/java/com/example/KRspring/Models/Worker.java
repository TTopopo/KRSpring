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
public class Worker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Имя не может быть пустым")
    @Size(min = 2, max = 50, message = "Имя должно быть от 2 до 50 символов")
    private String name;

    @NotBlank(message = "Фамилия не может быть пустой")
    @Size(min = 2, max = 50, message = "Фамилия должна быть от 2 до 50 символов")
    private String surname;

    private String patronymic;

    @NotBlank(message = "Номер телефона не может быть пустым")
    @Size(min = 10, max = 15, message = "Номер телефона должен быть от 10 до 15 символов")
    private String phoneNumber;

    @NotBlank(message = "Должность не может быть пустой")
    @Size(min = 2, max = 50, message = "Должность должна быть от 2 до 50 символов")
    private String position;

    @ManyToOne
    @JoinColumn(name = "foreman_id")
    private Foreman foreman;

    @ManyToOne
    @JoinColumn(name = "object_id")
    private Object object; // Добавлено поле для хранения объекта
}
