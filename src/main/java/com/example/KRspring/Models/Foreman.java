package com.example.KRspring.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Foreman {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Имя не может быть пустым")
    @Size(min = 2, max = 50, message = "Имя должно быть от 2 до 50 символов")
    private String foremanName;

    @NotBlank(message = "Фамилия не может быть пустой")
    @Size(min = 2, max = 50, message = "Фамилия должна быть от 2 до 50 символов")
    private String foremanSurname;

    @NotBlank(message = "Отчество не может быть пустым")
    @Size(min = 2, max = 50, message = "Отчество должно быть от 2 до 50 символов")
    private String foremanPatronymic;

    @NotBlank(message = "Специализация не может быть пустой")
    @Size(min = 2, max = 50, message = "Специализация должна быть от 2 до 50 символов")
    private String specialization;

    @NotBlank(message = "Номер телефона не может быть пустым")
    @Size(min = 10, max = 15, message = "Номер телефона должен быть от 10 до 15 символов")
    private String foremanPhoneNumber;

    @NotBlank(message = "Квалификация не может быть пустой")
    @Size(min = 2, max = 50, message = "Квалификация должна быть от 2 до 50 символов")
    private String qualification;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "object_id")
    private Object object; // Добавлено поле для хранения объекта

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(unique = true, nullable = false)
    private String username;

    @PrePersist
    protected void onCreate() {
        if (role == null) {
            role = Role.ROLE_FOREMAN; // Устанавливаем роль по умолчанию
        }
    }
}
